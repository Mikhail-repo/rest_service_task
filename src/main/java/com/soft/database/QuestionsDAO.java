package com.soft.database;

import com.soft.entity.Poll;
import com.soft.entity.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class QuestionsDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createQuestion(Question question) {

        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(question);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuestion(Question question) {

        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(question);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void removeQuestion(Question question) {

        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.remove(question);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Question getQuestionById(Integer id) {

        Question question = null;
        String query = "SELECT DISTINCT question FROM Question question WHERE id=" + "'" + id + "'";
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Optional<Question> optionalQuestion = session.createQuery(query).list().stream().findFirst();
            if(optionalQuestion.isPresent()) question = optionalQuestion.get();

            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            return question;
        }
    }
}
