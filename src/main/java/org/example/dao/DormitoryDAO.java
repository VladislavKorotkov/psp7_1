package org.example.dao;

import org.example.model.Dormitory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DormitoryDAO {
    private SessionFactory sessionFactory;

    public DormitoryDAO() {
        sessionFactory =  new Configuration().addAnnotatedClass(Dormitory.class).buildSessionFactory();
    }

    public List<Dormitory> getAll(){
        try (Session session = sessionFactory.openSession()) {
            Query<Dormitory> query = session.createQuery("from Dormitory", Dormitory.class);
            return query.list();
        }
    }

    public void create(Dormitory dormitory){
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction();
            session.persist(dormitory);
            session.getTransaction().commit();
        }
    }
}
