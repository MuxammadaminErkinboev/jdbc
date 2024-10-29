package org.example.dao;

import org.example.dao.config.*;
import org.example.model.Card;

import java.sql.*;
import java.util.*;

public class CardDao {
    private final DatabaseConfig databaseConfig;
    private static final String GET_CARD_LIST = "select * from cards";
    private static final String ADD_CARD = "select * from add_card(i_id := ?, i_user_id := ?, card_number :=?, expiration_date :=?, cvv :=?, is_active :=?, created_at :=?)";
    private static final String UPDATE_CARD = "select * from update_card( i_id :=?, i_user_id :=?, i_card_number :=?, i_expiration_date :=?, i_cvv :=?, i_is_active :=?)";
    private static final String DELETE_CARD = "select * from delete_card(i_id :=?)";
    private static final String GET_CARD_BY_ID = "select * from cards where id =?";
    private static final String GET_MY_CARDS = "select * from cards where user_id =?";
    private static final String DELETE_MY_CARD = "delete from cards where user_id =?";

    public CardDao() {
        this.databaseConfig = new PostgresDatabaseConfig();
    }

    public Card getCardById(int id) {
        Card card = null;

        try (Connection connection = databaseConfig.connect();

            PreparedStatement statement = connection.prepareStatement(GET_CARD_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                card = new Card(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return card;
    }

    public Card addCard(int userId, String cardNumber, String expirationDate, String cvv) throws SQLException {

        try (Connection connection = databaseConfig.connect();

            PreparedStatement statement = connection.prepareStatement(ADD_CARD)) {
            statement.setInt(1, userId);
            statement.setString(2, cardNumber);
            statement.setString(3, expirationDate);
            statement.setString(4, cvv);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                System.out.println("Card added: " + rs.getString("card_number"));
            }
        }
        return getCardById(userId);
    }

    public void deleteCard(int id) {
        try (Connection connection = databaseConfig.connect();

            PreparedStatement statement = connection.prepareStatement(DELETE_CARD)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();

        try (Connection connection = databaseConfig.connect();

             PreparedStatement statement = connection.prepareStatement(GET_CARD_LIST)) {
             ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Card card = new Card(resultSet);
                cards.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public Card update_card(Card card) {
        try (Connection connection = databaseConfig.connect();

            PreparedStatement statement = connection.prepareStatement(UPDATE_CARD)) {
            statement.setInt(1, card.getId());
            statement.setInt(2, card.getUserId());
            statement.setString(3, card.getCardNumber());
            statement.setString(4, card.getExpirationDate());
            statement.setString(5, card.getCvv());
            statement.setBoolean(6, card.isActive());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getCardById(card.getId());
    }

    public List<Card> getMyCards(int userId) {
        List<Card> cards = new ArrayList<>();

        try (Connection connection = databaseConfig.connect();

             PreparedStatement statement = connection.prepareStatement(GET_MY_CARDS)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Card card = new Card(resultSet);
                cards.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }

    public void deleteMyCard(int userId) {
        try (Connection connection = databaseConfig.connect();

             PreparedStatement statement = connection.prepareStatement(DELETE_MY_CARD)) {
            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
