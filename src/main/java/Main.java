import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import io.reactivex.netty.protocol.http.server.HttpServer;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        HttpServer
            .newServer(8080)
            .awaitShutdown();
    }
    public static MongoReactiveDao createMongoDao() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("catalog");
        MongoCollection<Document> users = database.getCollection("users");
        MongoCollection<Document> products = database.getCollection("products");
        return new MongoReactiveDao(users, products);
    }
}
