package com.soft.database;

import com.soft.entity.Poll;
import com.soft.entity.Question;
import com.soft.security.MyUserDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PollsDAO {

    @Autowired
    QuestionsDAO questionsDAO;

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createPoll(Poll poll) {

        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(poll);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePoll(Poll poll) {

        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(poll);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePoll(Poll poll) {

        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(poll);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Poll getPollById(Integer id) {

        Poll poll = null;
        String query = "SELECT DISTINCT poll FROM Poll poll WHERE id=" + "'" + id + "'";
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Optional<Poll> optionalPoll = session.createQuery(query).list().stream().findFirst();
            if(optionalPoll.isPresent()) poll = optionalPoll.get();

            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            return poll;
        }
    }

    public List<Poll> getAll() {

        List<Poll> pollList = null;
        String query = "from Poll";
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            pollList = session.createQuery(query).list();
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            return pollList;
        }
    }

    public List<Question> getPollQuestionList(Integer id) {

        List<Question> questionList = null;
        String query = "SELECT DISTINCT poll FROM Poll poll WHERE id=" + "'" + id + "'";
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Optional<Poll> optionalPoll = session.createQuery(query).list().stream().findFirst();
            if(optionalPoll.isPresent()) {
                Poll poll = optionalPoll.get();
                if(poll != null) {
                    questionList = poll.getQuestionList();
                }
            }
            session.getTransaction().commit();
            System.out.println("*************************** questionList.size(2) = " + questionList.size());
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            return questionList;
        }
    }

    public void addQuestion(Integer pollId, Question question) {

        String query = "SELECT DISTINCT poll FROM Poll poll WHERE id=" + "'" + pollId + "'";
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Optional<Poll> optionalPoll = session.createQuery(query).list().stream().findFirst();
            if(optionalPoll.isPresent()) {
                Poll poll = optionalPoll.get();
                List<Question> questionList = poll.getQuestionList();
                if(questionList == null) questionList = new ArrayList<>();
                questionList.add(question);
                session.save(poll);
            }
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(Integer pollId, Integer questionId) {

        String query = "SELECT DISTINCT poll FROM Poll poll WHERE id=" + "'" + pollId + "'";
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Optional<Poll> optionalPoll = session.createQuery(query).list().stream().findFirst();
            if(optionalPoll.isPresent()) {
                Poll poll = optionalPoll.get();
                List<Question> questionList = poll.getQuestionList();
                if(questionList != null) {
                    Optional<Question> optionalQuestion = questionList.stream().filter(q -> q.getId().equals(questionId)).findFirst();
                    if(optionalQuestion.isPresent()) {
                        questionList.remove(optionalQuestion.get());
                        session.update(poll);
                        session.remove(optionalQuestion.get());
                    }
                }
            }
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
