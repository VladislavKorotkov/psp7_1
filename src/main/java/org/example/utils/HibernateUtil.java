package org.example.utils;

import org.example.model.Dormitory;
import org.example.model.Room;
import org.example.model.Student;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
               sessionFactory = new Configuration().addAnnotatedClass(Dormitory.class).addAnnotatedClass(Room.class).addAnnotatedClass(Student.class).buildSessionFactory();;

            } catch (Throwable ex) {
                System.err.println("Failed to create SessionFactory: " + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
}