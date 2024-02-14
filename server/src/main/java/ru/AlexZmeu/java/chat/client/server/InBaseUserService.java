package ru.AlexZmeu.java.chat.client.server;

import java.sql.*;
public class InBaseUserService implements UserService{

    private static final String BASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD  = "postgres";
    private static final String SELECT_USER = "SELECT username FROM users WHERE login=? AND password=?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT username FROM users WHERE login=?";
    private static final String SELECT_USER_BY_USERNAME = "SELECT username FROM users WHERE username=?";
    private static final String SELECT_USER_IS_ADMIN = "SELECT username FROM users WHERE username=? AND role=admin";
    private static final String INSERT_USER_SQL = "INSERT INTO users VALUES (?,?,?,?)";

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        try (Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("username");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createNewUser(String login, String password, String username) {
        try(Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2,password);
                preparedStatement.setString(3,username);
                preparedStatement.setString(4,"user");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLoginAlreadyExist(String login) {
        try(Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
                preparedStatement.setString(1, login);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isUsernameAlreadyExist(String username) {
        try(Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {
                preparedStatement.setString(1, username);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAdmin(String username) {
        try(Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_IS_ADMIN)) {
                preparedStatement.setString(1, username);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
