import org.bson.Document;

public class User {
    public final int id;
    public final String login;
    public final Currency currency;


    public User(Document doc) {
        this(doc.getInteger("id"), doc.getString("login"), Currency.valueOf(doc.getString("currency")));
    }

    public User(int id, String login, Currency currency) {
        this.id = id;
        this.login = login;
        this.currency = currency;
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("login", login)
                .append("currency", currency.name());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", currency='" + currency.name() + '\'' +
                '}';
    }
}