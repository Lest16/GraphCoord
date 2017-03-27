package com.tecomgroup.energetics.client.graphCoord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DbReader {
    public void GetGraphs() {
        Connection connection = null;
        ArrayList<Integer> sources = new ArrayList<Integer>();
        ArrayList<Integer> receives = new ArrayList<Integer>();
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
                System.out.println(result.getInt("sourceRelations"));
                sources.add(result.getInt("sourceRelations"));
                System.out.println(result.getInt("receiveRelation"));
                receives.add(result.getInt("receiveRelation"));
            }
            statement.close();
            result.close();
            List<Integer> collect = sources.stream().distinct().collect(Collectors.<Integer>toList());

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
    }
}
