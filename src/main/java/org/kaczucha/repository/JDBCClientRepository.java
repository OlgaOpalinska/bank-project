package org.kaczucha.repository;

import org.kaczucha.Client;

import java.sql.*;

public class JDBCClientRepository implements ClientRepository {

    public static final String USER = "postgres";
    public static final String PASSWORD = "qwerty";
    public static final String JDBC_URL = "jdbc:postgresql://localhost/test";
    

    @Override
    public void save(Client client) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)){
            final String name = client.getName();
            final String email = client.getEmail();
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS(FIRST_NAME,MAIL) VALUES (?,?)");
            statement.setString(1, name);
            statement.setString(2,email);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client findByEmail(String email) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)){
            final PreparedStatement statement = connection.prepareStatement("SELECT FIRST_NAME,MAIL FROM USERS WHERE MAIL=?");
            statement.setString(1,email);
            final ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                final String name = resultSet.getString("FIRST_NAME");
                final String mail = resultSet.getString("MAIL");
                return new Client(name, mail,0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
