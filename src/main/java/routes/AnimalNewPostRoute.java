package routes;

import db.Repository;
import model.enums.AnimalAge;
import model.enums.AnimalHealth;
import spark.Request;
import spark.Response;
import java.util.Map;

public class AnimalNewPostRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    if (request.queryParams("is_endangered") == null) {
      db.addNonEndangeredAnimal(request.queryParams("name"));
    }
    else {
      db.addEndangeredAnimal(
        request.queryParams("name"),
        // convert to enum values from ordinals
        AnimalHealth.values()[Integer.parseInt(request.queryParams("health"))],
        AnimalAge.values()[Integer.parseInt(request.queryParams("age"))]
      );
    }
    response.redirect("/animals");
  }
}
