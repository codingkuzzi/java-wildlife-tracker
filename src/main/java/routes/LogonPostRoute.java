package routes;

import db.Repository;
import model.Ranger;
import spark.Request;
import spark.Response;

import java.util.Map;

public class LogonPostRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    int userId = Integer.parseInt(request.queryParams("ranger"));
    Ranger user = db.getRangerById(userId);
    request.session().attribute("userId", user.getId());
    request.session().attribute("userName", user.getDisplayName());
    response.redirect("/");
  }
}
