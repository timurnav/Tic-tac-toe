package DAO;

import entity.User;
import org.hibernate.Session;
import service.ConsoleHelper;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тимур Мухитдинов on 17.04.2015.
 */
public class UserDAO {

    public void addUser(User user) throws SQLException {
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e){
            ConsoleHelper.writeString("Exception в add");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<User> getAllUser() throws SQLException {
        Session session = null;
        List<User> users = new ArrayList<User>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createCriteria(User.class).list();
        } catch (Exception e) {
            ConsoleHelper.writeString("Exception в getAll");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return users;
    }

    public User getUserById(int id) throws SQLException {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = (User) session.load(User.class, id);
        } catch (Exception e) {
            ConsoleHelper.writeString("Exception в get");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    public Boolean consistsId(Integer id) throws SQLException {
        List<User> u = getAllUser();
        for (User us : u) {
            if (us.getId()==id) return true;
        }
        return false;
    }

    public void updateUser(User user){
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            ConsoleHelper.writeString("Exception в update");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
