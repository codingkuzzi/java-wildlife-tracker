package routes;

import db.Repository;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseRoute implements TemplateViewRoute {

  protected abstract void process(Request request, Response response, Map<String, Object> model, Repository db);

  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));

    Repository db;
    if (System.getenv("JDBC_DATABASE_URL") != null) {
      db = new Repository(
        System.getenv("JDBC_DATABASE_URL"),
        System.getenv("JDBC_DATABASE_USERNAME"),
        System.getenv("JDBC_DATABASE_PASSWORD"));
    }
    else {
      db = new Repository("jdbc:postgresql://localhost:5432/wildlife_tracker_2");
    }

    process(request, response, model, db);

    if (model.containsKey("template")) {
      return new ModelAndView(model, "templates/layout.vtl");
    }
    else {
      return new ModelAndView(null, null);
    }
  }
}