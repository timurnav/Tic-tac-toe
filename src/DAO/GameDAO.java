package DAO;

import entity.Game;
import entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import service.ConsoleHelper;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Тимур Мухитдинов on 17.04.2015.
 */
public class GameDAO {

    public static enum ResultOfGame{
        WINNER, LOSER, DRAW
    }

    public static enum Status{
        CREATED, PLAYING, GAME_OVER
    }

    public static enum CurrentPlayer{
        X, Y, D
    }

    public void addGame(Game game){
        Session session = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(game);
            session.getTransaction().commit();
        } catch (Exception e){
            ConsoleHelper.writeString("Exception в add");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateGame(Game game){
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(game);
            session.getTransaction().commit();
        } catch (Exception e) {
            ConsoleHelper.writeString("Exception в update");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Game getCreatedGame() {
        Session session = null;
        Game game = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            //поиск игры со статусом "СОЗДАНО"
            Criteria c = session.createCriteria(Game.class).
                    add(Restrictions.eq("status", Status.CREATED.toString()));
            List<Game> games = c.list();
            //есди есть созданные игры, берем первую
            if (games.size()!=0) game = games.get(0);
        } catch (Exception e) {
            ConsoleHelper.writeString("Exception в getGame");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        //или возвращаем null
        return game;
    }

    public Game getActiveGame(int id){
        Session session = null;
        Game game = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria c = session.createCriteria(Game.class).
                    add(Restrictions.or(
                            Restrictions.eq("x", id),
                            Restrictions.eq("y", id)
                    )).
                    add(Restrictions.or(
                            Restrictions.eq("status", Status.PLAYING.toString()),
                            Restrictions.eq("status", Status.CREATED.toString())
                    ));
            List<Game> games = c.list();
            if (games.size()!=0) game = games.get(0);
        } catch (Exception e) {
            ConsoleHelper.writeString("Exception в isUserPlaying");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return game;
    }

    public Game getGameById(int id) throws SQLException {
        Session session = null;
        Game game = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            game = (Game) session.load(Game.class, id);
        } catch (Exception e) {
            ConsoleHelper.writeString("Exception в get");
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return game;
    }

    /*
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
     */
}
