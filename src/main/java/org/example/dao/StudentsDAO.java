package org.example.dao;


import org.example.model.Room;
import org.example.model.Student;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentsDAO {
    private SessionFactory sessionFactory;

    public StudentsDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Student> getAll(){
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("from Student", Student.class);
            return query.list();
        }
    }

    public Student create(Student student){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
            return student;
        }
    }
    public Student update(Student student){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(student);
            tx.commit();
            return student;
        }
    }
    public void delete(Student student){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(student);
            tx.commit();
        }
    }
}
