package routes;

import db.Repository;
import spark.Request;
import spark.Response;
import java.util.Map;

public class AnimalsGetRoute extends BaseRoute {
    @Override
    protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
        model.put("animals", db.getNonEndangeredAnimals());
        model.put("endangeredAnimals", db.getEndangeredAnimals());
        model.put("template", "templates/animals.vtl");
    }
 }
