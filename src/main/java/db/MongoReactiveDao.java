package db;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import model.Currency;
import model.Product;
import model.User;
import org.bson.Document;
import rx.Observable;

public class MongoReactiveDao {
    private final MongoCollection<Document> users;
    private final MongoCollection<Document> products;

    public MongoReactiveDao(MongoCollection<Document> users, MongoCollection<Document> products) {
        this.users = users;
        this.products = products;
    }

    public Observable<Boolean> addUser(User user) {
        return insertToCollectionIfNeeded(users, user.toDocument());
    }

    public Observable<Boolean> addProduct(Product product) {
        return insertToCollectionIfNeeded(products, product.toDocument());
    }

    public Observable<Product> getProductsForUserId(int userId) {
        return users
                .find(Filters.eq("id", userId))
                .toObservable()
                .flatMap(userDoc -> products
                        .find()
                        .toObservable()
                        .map(productDoc ->
                                new Product(productDoc).
                                convertedToCurrency(Currency.valueOf(userDoc.getString("currency")))
                        )
                );
    }

    public Observable<User> getAllUsers() {
        return users
                .find()
                .toObservable()
                .map(User::new);
    }

    private Observable<Boolean> insertToCollectionIfNeeded(MongoCollection<Document> collection, Document document) {
        return collection.find(Filters.eq("id", document.getInteger("id")))
                .toObservable()
                .singleOrDefault(null)
                .flatMap(foundDoc -> {
                    if (foundDoc == null) {
                        return collection
                                .insertOne(document)
                                .isEmpty()
                                .map(f -> !f);
                    } else {
                        return Observable.just(false);
                    }
                });
    }
}
