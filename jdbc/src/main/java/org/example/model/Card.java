package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Card {
    private int id;
    private int userId;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private boolean isActive;
    private String createdAt;

    public Card(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.userId = resultSet.getInt("user_id");
        this.cardNumber = resultSet.getString("card_number");
        this.expirationDate = resultSet.getString("expiration_date");
        this.cvv = resultSet.getString("cvv");
        this.isActive = resultSet.getBoolean("is_active");
        this.createdAt = resultSet.getString("created_at");
    }
}
