package com.tecomgroup.energetics.client.graphCoord;

import com.tecomgroup.energetics.client.graphCoord.Entities.RelationsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by uvarov.a on 19.05.2017.
 */
public class Test {
    public void testMethod(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM RelationsEntity");
        List<RelationsEntity> rank = query.getResultList();
        session.getTransaction().commit();
        System.out.println(rank);

        session.close();
    }
}
