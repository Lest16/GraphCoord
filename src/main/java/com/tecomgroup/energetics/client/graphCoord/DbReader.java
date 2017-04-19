package com.tecomgroup.energetics.client.graphCoord;

import com.tecomgroup.energetics.client.graphCoord.Graphs.Edge;
import com.tecomgroup.energetics.client.graphCoord.Graphs.Vertex;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DbReader {
    public ArrayList<Edge> ReadEdges(){
        return this.ReadSomething(getEdges);
    }

    public ArrayList<Vertex> ReadVertex(){
        return this.ReadSomething(getVertex);
    }

    private <T> ArrayList<T> ReadSomething(Function<Connection, List<T>> getSomething) {
        Connection connection = null;
        ArrayList<T> objects = new ArrayList<T>();
        String url = "jdbc:postgresql://127.0.0.1:5432/graphs";
        String name = "postgres";
        String password = "admin";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, name, password);
            objects.addAll(getSomething.apply(connection));
        } catch (Exception ex) {
            System.out.println(DbReader.class.getName() + ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println(DbReader.class.getName() + ex);
                }
            }
        }

        return objects;
    }



    Function<Connection, List<Edge>> getEdges = new Function<Connection, List<Edge>>() {
        public List<Edge> apply(Connection connection) {
            ArrayList<Edge> edges = new ArrayList<Edge>();
            try{
                Statement statement = null;
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery(
                        "SELECT * FROM relations");
                while (result.next()) {
                    edges.add(new Edge(result.getInt("sourceRelations"), result.getInt("receiveRelation")));
                }
                statement.close();
                result.close();
            } catch (Exception ex){}
            return edges;
        }
    };

    Function<Connection, List<Vertex>> getVertex = new Function<Connection, List<Vertex>>() {
        public List<Vertex> apply(Connection connection) {
            ArrayList<Vertex> vertex = new ArrayList<Vertex>();
            try{
                Statement statement = null;
                statement = connection.createStatement();
                ResultSet result = statement.executeQuery(
                        "SELECT * FROM vertex");
                while (result.next()) {
                    vertex.add(new Vertex(result.getInt("id"), result.getString("caption")));
                }
                statement.close();
                result.close();
            } catch (Exception ex){}
            return vertex;
        }
    };

}
