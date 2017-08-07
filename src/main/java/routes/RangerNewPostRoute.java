package routes;

import db.Repository;
import spark.Request;
import spark.Response;
import java.util.Map;

public class RangerNewPostRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    db.addRanger(
      Integer.parseInt(request.queryParams("station")),
      request.queryParams("firstName"),
      request.queryParams("lastName"),
      request.queryParams("email")
    );
    response.redirect("/rangers");
  }
}
