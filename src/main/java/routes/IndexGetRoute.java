package routes;

import db.Repository;
import spark.Request;
import spark.Response;
import java.util.Map;

public class IndexGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    model.put("template", "templates/index.vtl");
  }
}
