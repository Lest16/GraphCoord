package com.tecomgroup.energetics.client.graphCoord;

import com.tecomgroup.energetics.client.graphCoord.Entities.RelationsEntity;
import com.tecomgroup.energetics.client.graphCoord.Entities.VertexEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DbReader {
    public ArrayList<RelationsEntity> ReadEdges(){
        return this.ReadSomething(getEdges);
    }

    public ArrayList<VertexEntity> ReadVertex(){
        return this.ReadSomething(getVertex);
    }

    private <T> ArrayList<T> ReadSomething(Function<Session, List<T>> getSomething) {
        ArrayList<T> objects = new ArrayList();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        objects.addAll(getSomething.apply(session));
        session.getTransaction().commit();
        session.close();
        return objects;
    }

    Function<Session, List<RelationsEntity>> getEdges = session -> {
        Query query = session.createQuery("FROM RelationsEntity");
        List<RelationsEntity> relationsEntityList = query.getResultList();
        return relationsEntityList;
    };

    Function<Session, List<VertexEntity>> getVertex = session -> {
        Query query = session.createQuery("FROM VertexEntity");
        List<VertexEntity> verticesList = query.getResultList();
        return verticesList;
    };

}
