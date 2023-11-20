package org.example.dao;

import org.example.model.Dormitory;
import org.example.model.Room;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RoomDAO {
    private SessionFactory sessionFactory;

    public RoomDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Room> getAll(){
        try (Session session = sessionFactory.openSession()) {
            Query<Room> query = session.createQuery("from Room", Room.class);
            return query.list();
        }
    }

    public Room getByNumber(int number){
        try (Session session = sessionFactory.openSession()) {
            Query<Room> query = session.createQuery("from Room where number= :number", Room.class);
            query.setParameter("number", number);
            return query.getResultList().get(0);
        }
    }

    public Room create(Room room){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(room);
            tx.commit();
            return room;
        }
    }
    public Room update(Room room){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(room);
            tx.commit();
            return room;
        }
    }
    public void delete(Room room){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(room);
            tx.commit();
        }
    }
}
