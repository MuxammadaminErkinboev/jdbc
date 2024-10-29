package org.example.service;

import org.example.dao.CardDao;
import org.example.model.Card;

import java.sql.SQLException;
import java.util.List;

public class CardService {

    private CardDao cardDao;

    public CardService(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public Card getCardById(int id) {
        return cardDao.getCardById(id);
    }

    public Card addCard(int userId, String cardNumber, String expirationDate, String cvv) throws SQLException {
        return cardDao.addCard(userId, cardNumber, expirationDate, cvv);
    }

    public Card updateCard(Card card) {
        return cardDao.update_card(card);
    }

    public void deleteCard(int id) {
        cardDao.deleteCard(id);
    }

    public List<Card> getAllCards() {
        return cardDao.getCards();
    }

    public List<Card> getMyCards(int userId) {
        return cardDao.getMyCards(userId);
    }

    public void deleteMyCards(int userId) {
        cardDao.deleteMyCard(userId);
    }
}
