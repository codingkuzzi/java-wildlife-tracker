package routes;

import db.Repository;
import spark.Request;
import spark.Response;
import java.time.LocalDateTime;
import java.util.Map;

public class SightingNewPostRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    db.addSighting(
      Integer.parseInt(request.queryParams("animal")),
      Integer.parseInt(request.queryParams("ranger")),
      Integer.parseInt(request.queryParams("location")),
      LocalDateTime.now(),
      request.queryParams("image")
    );
    response.redirect("/sightings/new");
  }
}
