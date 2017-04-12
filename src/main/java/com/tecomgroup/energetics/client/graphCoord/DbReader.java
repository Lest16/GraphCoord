package com.tecomgroup.energetics.client.graphCoord;

import com.tecomgroup.energetics.client.graphCoord.Graphs.Edge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DbReader {
    public ArrayList<Object> ReadEdges(Function<Connection, List<Object>> getSomething) {
        Connection connection = null;
        ArrayList<Object> objects = new ArrayList<Object>();
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

    Function<Connection, List<Object>> getEdges = new Function<Connection, List<Object>>() {
        public List<Object> apply(Connection connection) {
            ArrayList<Object> edges = new ArrayList<Object>();
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

    Function<Connection, List<Object>> getVertex = new Function<Connection, List<Object>>() {
        public List<Object> apply(Connection connection) {
            ArrayList<Object> edges = new ArrayList<Object>();
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
    
}
