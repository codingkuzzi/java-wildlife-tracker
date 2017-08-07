package routes;

import db.Repository;
import spark.Request;
import spark.Response;

import java.util.Map;

public class LogoffGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    request.session().attribute("userId", null);
    request.session().attribute("userName", null);
    response.redirect(request.headers("Referer"));
  }
}
