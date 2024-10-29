package org.example.model;

import lombok.*;

import java.sql.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int id;
    private String name;
    private String username;
    private String phoneNumber;
    private String password;
    private String email;
    private boolean active;
    private String createdDate;

    public User(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.username = resultSet.getString("username");
        this.phoneNumber = resultSet.getString("phone_number");
        this.password = resultSet.getString("password");
        this.email = resultSet.getString("email");
        this.active = resultSet.getBoolean("is_active");
        this.createdDate = resultSet.getString("created_at");
    }
}
