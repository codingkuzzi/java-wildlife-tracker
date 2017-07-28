package routes;

import db.Repository;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.time.LocalDateTime;

public class SightingNewPostRoute implements TemplateViewRoute{
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Repository db = new Repository();
    db.addSighting(
      Integer.parseInt(request.queryParams("animal")),
      Integer.parseInt(request.queryParams("ranger")),
      Integer.parseInt(request.queryParams("location")),
      LocalDateTime.now(),
      request.queryParams("image")
    );
    response.redirect("/sightings/new");
    return new ModelAndView(null, null);
  }
}
