package routes;

import db.Repository;
import spark.Request;
import spark.Response;
import java.util.Map;

public class LocationNewPostRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    db.addLocation(
      request.queryParams("name"),
      Integer.parseInt(request.queryParams("station")));
    response.redirect("/locations");
  }
}
