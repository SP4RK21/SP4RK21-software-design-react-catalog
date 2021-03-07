package model;

import org.bson.Document;

public class Product {
    public final int id;
    public final String name;
    public final Currency currency;
    public final double price;


    public Product(Document doc) {
        this(doc.getInteger("id"),
                doc.getString("name"),
                Currency.valueOf(doc.getString("currency")),
                doc.getDouble("price"));
    }

    public Product(int id, String name, Currency currency, double price) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.price = price;
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("name", name)
                .append("currency", currency.name())
                .append("price", price);
    }

    public Product convertedToCurrency(Currency currencyToConvert) {
        return new Product(id, name, currencyToConvert, this.currency.convertPriceToCurrency(price, currencyToConvert));
    }

    @Override
    public String toString() {
        return "model.Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency='" + currency.name() + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}