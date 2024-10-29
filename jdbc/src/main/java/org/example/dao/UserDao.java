package org.example.dao;

import org.example.dao.config.*;
import org.example.model.User;

import java.sql.*;
import java.util.*;

public class UserDao {
    private final DatabaseConfig databaseConfig;
    private static final String GET_USER_LIST = "select * from users";
    private static final String REGISTER_USER = "select * from register_user(i_name := ?, i_username := ?, i_phone_number := ?, i_password := ?, i_email := ?)";
    private static final String LOGIN_USER = "SELECT * FROM user_login(?, ?)";
    private static final String UPDATE_USER = "select * from update_user(i_id := ?, i_name := ?, i_phone_number := ?, i_password := ?, i_email := ?, i_updated_by := ?)";
    private static final String DELETE_USER = "select * from delete_user(i_id := ?)";
    private static final String GET_USER_BY_ID = "select * from users where id = ?";

    public UserDao() {
        this.databaseConfig = new PostgresDatabaseConfig();
    }

    public User signUp(User user) {
        try (Connection connect = databaseConfig.connect();
             PreparedStatement statement = connect.prepareStatement(REGISTER_USER)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet);
            }else {
                throw new IllegalArgumentException();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User signIn(User user) {
        try (Connection connect = databaseConfig.connect();
             PreparedStatement statement = connect.prepareStatement(LOGIN_USER)) {

            statement.setString(1, user.getPhoneNumber());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet);
            } else {
                throw new IllegalArgumentException("Invalid phone number or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during sign-in.");
        }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connect = databaseConfig.connect()) {

            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_USER_LIST);

            while (resultSet.next()) {
                User user = new User(resultSet);
                users.add(user);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserById(int id) {
        User user = null;

        try (Connection connect = databaseConfig.connect();
             PreparedStatement statement = connect.prepareStatement(GET_USER_BY_ID)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(resultSet);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private String wrapStringToSql(String s) {
        return "'" + s + "'";
    }

    public User updateUser(User user) {
        try (Connection connect = databaseConfig.connect();
             PreparedStatement statement = connect.prepareStatement(UPDATE_USER)) {

            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPhoneNumber());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
            statement.setString(6, "ME");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet);
            } else {
                throw new IllegalArgumentException();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUser(int id) {
        try (Connection connect = databaseConfig.connect();
             PreparedStatement statement = connect.prepareStatement(DELETE_USER)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
