package routes;

import db.Repository;
import model.enums.AnimalAge;
import model.enums.AnimalHealth;
import spark.Request;
import spark.Response;
import java.util.Map;

public class AnimalNewGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    model.put("healthOptions", AnimalHealth.values());
    model.put("ageOptions", AnimalAge.values());
    model.put("template", "/templates/animal-new.vtl");
  }
}
