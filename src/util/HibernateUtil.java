package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Тимур Мухитдинов on 17.04.2015.
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory = null;

    static {
        try {

            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
