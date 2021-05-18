package com.soft.database;

import com.soft.entity.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class AnswersDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createAnswer(Answer answer) {
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(answer);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAnswer(Answer answer) {
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.remove(answer);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Answer getAnswerById(Integer id) {

        Answer answer = null;
        String query = "SELECT DISTINCT answer FROM Answer answer WHERE id=" + "'" + id + "'";
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Optional<Answer> optionalAnswer = session.createQuery(query).list().stream().findFirst();
            if(optionalAnswer.isPresent()) answer = optionalAnswer.get();

            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            return answer;
        }
    }

    public List<Answer> getAnswerByUserTopic(Integer accountId, Integer topicId) {

        List<Answer> answerList = null;
        String query = "SELECT DISTINCT answer FROM Answer answer WHERE account_id=" + "'" + accountId + "'" + " AND poll_id=" + "'" + topicId + "'";
        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            answerList = session.createQuery(query).list();
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            return answerList;
        }
    }

    public void updateAnswer(Answer answer) {

        Session session;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(answer);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
