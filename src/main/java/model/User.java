package model;

import org.bson.Document;

public class User {
    private final int id;
    private final String login;
    private final Currency currency;


    public User(Document doc) {
        this(doc.getInteger("id"), doc.getString("login"), Currency.valueOf(doc.getString("currency")));
    }

    public User(int id, String login, Currency currency) {
        this.id = id;
        this.login = login;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("login", login)
                .append("currency", currency.name());
    }

    @Override
    public String toString() {
        return "model.User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", currency='" + currency.name() + '\'' +
                '}';
    }
}