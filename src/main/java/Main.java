import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import controller.CatalogController;
import db.MongoReactiveDao;
import io.reactivex.netty.protocol.http.server.HttpServer;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        CatalogController controller = new CatalogController(createMongoDao());
        HttpServer
            .newServer(8080)
            .start((req, resp) -> resp.writeString(controller.processRequest(req)))
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
