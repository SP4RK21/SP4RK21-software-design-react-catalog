import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

import java.util.Objects;

public class CatalogController {

    private MongoReactiveDao dao;

    public CatalogController(MongoReactiveDao dao) {
        this.dao = dao;
    }

    public <T> Observable<String> processRequest(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);
        switch (path) {
            case "add_user":
                return dao
                        .addUser(
                            new User(
                                Integer.parseInt(queryParameterByName(request, "id")),
                                queryParameterByName(request, "login"),
                                Currency.valueOf(queryParameterByName(request, "currency"))
                            )
                        )
                        .map(Object::toString)
                        .map(e -> {
                            System.out.println(e);
                            return e;
                        });

            case "add_product":
                return dao
                        .addProduct(
                            new Product(
                                    Integer.parseInt(queryParameterByName(request, "id")),
                                    queryParameterByName(request, "name"),
                                    Currency.valueOf(queryParameterByName(request, "currency")),
                                    Double.parseDouble(queryParameterByName(request, "price"))
                            )
                        )
                        .map(Object::toString)
                        .map(e -> {
                            System.out.println(e);
                            return e;
                        });

            case "get_products_for_user":
                return dao
                        .getProductsForUserId(Integer.parseInt(queryParameterByName(request, "user_id")))
                        .map(Objects::toString)
                        .map(e -> {
                            System.out.println(e);
                            return e;
                        });

            case "get_users":
                return dao
                        .getAllUsers()
                        .map(Objects::toString)
                        .map(e -> {
                            System.out.println(e);
                            return e;
                        });

            default:
                return Observable.just("unexpected path: " + path);
        }
    }

    private <T> String queryParameterByName(HttpServerRequest<T> request, String param) {
        return request.getQueryParameters().get(param).get(0);
    }
}
