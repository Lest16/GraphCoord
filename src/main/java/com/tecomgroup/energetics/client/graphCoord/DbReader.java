package com.tecomgroup.energetics.client.graphCoord;

import com.tecomgroup.energetics.client.graphCoord.Graphs.Edge;

import java.sql.*;
import java.util.ArrayList;

public class DbReader {
    public ArrayList<Edge> GetGraphs() {
        Connection connection = null;
        ArrayList<Edge> edges = new ArrayList<Edge>();
        String url = "jdbc:postgresql://127.0.0.1:5432/graphs";
        String name = "postgres";
        String password = "admin";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM relations");
            while (result.next()) {
                edges.add(new Edge(result.getInt("sourceRelations"), result.getInt("receiveRelation")));
            }
            statement.close();
            result.close();

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

        return edges;
    }
}
