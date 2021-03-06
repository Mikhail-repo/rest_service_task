package com.soft.controller;

import com.soft.database.AnswersDAO;
import com.soft.database.PollsDAO;
import com.soft.database.QuestionsDAO;
import com.soft.database.UserAccountsDAOImpl;
import javax.servlet.http.HttpServletRequest;

import com.soft.entity.Answer;
import com.soft.entity.Poll;
import com.soft.entity.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import com.soft.security.MyUserDetails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import java.net.URI;
import java.util.stream.Collectors;

/* REST controller */

@Controller
public class ServiceController {

    @Autowired
    UserAccountsDAOImpl userAccountsDAO;
    @Autowired
    PollsDAO pollsDAO;
    @Autowired
    QuestionsDAO questionsDAO;
    @Autowired
    AnswersDAO answersDAO;

    @RequestMapping(method=GET, path="/login.html")
    public String renderLoginPage(ModelMap model) {
        model.addAttribute("loginError", false);
        model.addAttribute("logoutSuccess", false);
     return "login.html";
    }
   
    @RequestMapping(method=GET, path="/home.html")
    public String renderHomePage(ModelMap model, @AuthenticationPrincipal MyUserDetails user) {

      model.addAttribute("fullName", user.getFirstName()+" "+user.getLastName());
      model.addAttribute("firstName", user.getFirstName());
      model.addAttribute("lastName", user.getLastName());
      model.addAttribute("email", user.getEmail());

       String roleStr = "USER";
        Iterator userAuthoritiesIterator = user.getAuthorities().iterator();
          while(userAuthoritiesIterator.hasNext()){
            if("ADMIN".equals(userAuthoritiesIterator.next().toString())){
             roleStr = "ADMINISTRATOR";
              break;
            }
          }
         model.addAttribute("role", roleStr);

     return "home.html";
    }
      
    @RequestMapping(method=GET, path="/admin.html")
    public String renderAdminPage(ModelMap model) {

        List<Poll> pollList = pollsDAO.getAll();
        if(pollList == null) pollList = new ArrayList<>();
        model.addAttribute("pollList", pollList);
     return "admin.html";
    }

    /** Create new topic */
    @RequestMapping(method=POST, path="/api/createtopic", consumes = {"application/xml", "application/json"})
    public ResponseEntity createTopic(@RequestBody Poll poll, ModelMap model) {

        List<Question> questions = new ArrayList<>();
        pollsDAO.createPoll(poll);
        List<Poll> pollList = pollsDAO.getAll();
        if(pollList == null) pollList = new ArrayList<>();
        model.addAttribute("pollList", pollList);
        ResponseEntity responseEntity = ResponseEntity.created(URI.create("/" + poll.getId())).build();

     return responseEntity;
    }

    /** Delete topic */
    @RequestMapping(method=DELETE, path="/api/deletetopic/{id}")
    public ResponseEntity deleteTopic(@PathVariable("id") Integer id, ModelMap model) {

        Poll poll = pollsDAO.getPollById(id);
        pollsDAO.deletePoll(poll);
        List<Poll> pollList = pollsDAO.getAll();

        if(pollList == null) pollList = new ArrayList<>();
        model.addAttribute("pollList", pollList);
        ResponseEntity responseEntity = ResponseEntity.ok().build();

     return responseEntity;
    }

    /** Edit topic */
    @RequestMapping(method=GET, path="/api/edittopic/{id}")
    public String editTopic(@PathVariable("id") Integer id, ModelMap model) {

        List<Question> questionList = pollsDAO.getPollQuestionList(id);
        if(questionList == null) questionList = new ArrayList<>();
        model.addAttribute("questionList", questionList);
        model.addAttribute("pollId", id);

    return "questions.html";
    }

    /** Create new question */
    @RequestMapping(method=POST, path="/api/createquestion", consumes = {"application/xml", "application/json"})
    public ResponseEntity createQuestion(@RequestBody Question question, HttpServletRequest request) {

        pollsDAO.addQuestion(Integer.parseInt(request.getHeader("PollId")), question);
        ResponseEntity responseEntity = ResponseEntity.created(URI.create("/" + question.getId())).build();

     return responseEntity;
    }

    /** Delete question */
    @RequestMapping(method=DELETE, path="/api/deletequestion/{id}")
    public ResponseEntity deleteQuestion(@PathVariable("id") Integer questionId, HttpServletRequest request, ModelMap model) {

        pollsDAO.deleteQuestion(Integer.parseInt(request.getHeader("PollId")), questionId);
        ResponseEntity responseEntity = ResponseEntity.ok().build();

     return responseEntity;
    }

    /** Edit question */
    @RequestMapping(method=GET, path="/api/editquestion/{id}")
    public String editQuestion(@PathVariable("id") Integer id, HttpServletRequest request, ModelMap model) {

        model.addAttribute("pollId", request.getParameter("pollId"));
        model.addAttribute("questionId", id);
        Question question = questionsDAO.getQuestionById(id);
        model.addAttribute("questionType", question.getQuestionType());
        model.addAttribute("questionText", question.getQuestionText());

     return "editquestion.html";
    }

    /** Update question */
    @RequestMapping(method=PUT, path="/api/updatequestion/{id}", consumes = {"application/xml", "application/json"})
    public ResponseEntity updateQuestion(@RequestBody Question question, @PathVariable("id") Integer id, HttpServletRequest request, ModelMap model) {

        Integer pollId = Integer.parseInt((request.getHeader("PollId")));
        Question oldQuestion = questionsDAO.getQuestionById(id);
        oldQuestion.setQuestionType(question.getQuestionType());
        oldQuestion.setQuestionText(question.getQuestionText());
        questionsDAO.updateQuestion(oldQuestion);
        ResponseEntity responseEntity = ResponseEntity.ok().build();

     return responseEntity;
    }

    /** Get topic's list */
    @RequestMapping(method=GET, path="/api/getalltopics", produces = {"application/json"})
    public ResponseEntity getAllTopics() {

        ResponseEntity responseEntity;
        List<Poll> pollList = pollsDAO.getAll();
        if(pollList == null) responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            List<String> pollNames =  pollList.stream().map(p -> p.getName()).collect(Collectors.toList());
            responseEntity = ResponseEntity.ok().body(pollNames);
        }
     return responseEntity;
    }

    /** Get questions's list. */
    @RequestMapping(method=GET, path="/api/getquestions/{id}", produces = {"application/json"})
    public ResponseEntity getTopicQuestions(@PathVariable("id") Integer id) {

        ResponseEntity responseEntity;
        List<Question> questionList = pollsDAO.getPollQuestionList(id);
        if(questionList != null && questionList.size() > 0) {
            List<String> questions = questionList.stream().map(q -> q.getQuestionText()).collect(Collectors.toList());
            responseEntity = ResponseEntity.ok().body(questions);
        } else responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

     return responseEntity;
    }

    /** Create new answer */
    @RequestMapping(method=POST, path="/api/createanswer", consumes = {"application/xml", "application/json"})
    public ResponseEntity createAnswer(@RequestBody Answer answer, HttpServletRequest request) {

        answersDAO.createAnswer(answer);
        ResponseEntity responseEntity = ResponseEntity.created(URI.create("/" + answer.getId())).build();

        return responseEntity;
    }

    /** Delete answer */
    @RequestMapping(method=DELETE, path="/api/deleteanswer/{id}")
    public ResponseEntity deleteAnswer(@PathVariable("id") Integer answerId, HttpServletRequest request, ModelMap model) {

        Answer answer = answersDAO.getAnswerById(answerId);
        answersDAO.removeAnswer(answer);
        ResponseEntity responseEntity = ResponseEntity.ok().build();

        return responseEntity;
    }

    /** Update answer */
    @RequestMapping(method=PUT, path="/api/updateanswer/{id}", consumes = {"application/xml", "application/json"})
    public ResponseEntity updateAnswer(@RequestBody Answer answer, @PathVariable("id") Integer id, HttpServletRequest request, ModelMap model) {

        answer.setId(id);
        answersDAO.updateAnswer(answer);
        ResponseEntity responseEntity = ResponseEntity.ok().build();

        return responseEntity;
    }

    /** Get user's answers. */
    @RequestMapping(method=GET, path="/api/getanswers/{accountId}/{topicId}", produces = {"application/json"})
    public ResponseEntity getUserTopicAnswers(@PathVariable("accountId") Integer accountId, @PathVariable("topicId") Integer topicId) {

        ResponseEntity responseEntity;

        List<Answer> answerList = answersDAO.getAnswerByUserTopic(accountId, topicId);
        if(answerList != null && answerList.size() > 0) {
            responseEntity = ResponseEntity.ok().body(answerList);
        } else responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return responseEntity;
    }

    @RequestMapping("/login-error")
    public String renderLoginError(ModelMap model) {
        model.addAttribute("loginError", true);
     return "login.html";
    }

    @RequestMapping("/logout-success")
    public String renderLogoutSuccess(ModelMap model) {
        model.addAttribute("logoutSuccess", true);
     return "login.html";
    }

    /** Registration request. */
    @RequestMapping(method=POST, path="/register")
    public String registerNewAccount(HttpServletRequest request, ModelMap model) {

         String email = request.getParameter("regEmail");

         if(userAccountsDAO.isAccountInDatabase(email)) {
           model.addAttribute("regAccountAlreadyExist", true);
         }
         else {
              try{
                   String firstName = request.getParameter("regFirstName");
                   String lastName = request.getParameter("regLastName");
                   boolean isAdmin = "admin".equals(request.getParameter("regUserAdmin"));

                   String passwordEncoded = new BCryptPasswordEncoder().encode(request.getParameter("regPassword"));

                   /** Create new user's account. */
                   userAccountsDAO.createUserAccount(new MyUserDetails(firstName, lastName, email, passwordEncoded, isAdmin));
                   model.addAttribute("regSuccess", true);
              }
              catch(Exception exc){
                model.addAttribute("regError", true);
              }
         }
     return "login.html";
    }
}