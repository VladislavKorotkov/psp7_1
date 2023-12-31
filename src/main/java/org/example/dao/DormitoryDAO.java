package org.example.dao;

import org.example.model.Dormitory;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;

public class DormitoryDAO {
    private SessionFactory sessionFactory;

    public DormitoryDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Dormitory> getAll(){
        try (Session session = sessionFactory.openSession()) {
            Query<Dormitory> query = session.createQuery("from Dormitory", Dormitory.class);
            return query.list();
        }
    }

    public Dormitory getByName(String name){
        try (Session session = sessionFactory.openSession()) {
            Query<Dormitory> query = session.createQuery("from Dormitory where name= :name", Dormitory.class);
            query.setParameter("name", name);
            return query.getResultList().get(0);
        }
    }

    public Dormitory create(Dormitory dormitory){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(dormitory);
            tx.commit();
            return dormitory;
        }
    }
    public Dormitory update(Dormitory dormitory){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(dormitory);
            tx.commit();
            return dormitory;
        }
    }
    public void delete(Dormitory dormitory){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(dormitory);
            tx.commit();
        }
    }
}
