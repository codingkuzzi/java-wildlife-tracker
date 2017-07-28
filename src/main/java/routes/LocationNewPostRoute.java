package routes;

import db.Repository;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class LocationNewPostRoute implements TemplateViewRoute{
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Repository db = new Repository();
    int stationId = Integer.parseInt(request.queryParams("station"));
    db.addLocation(request.queryParams("name"), stationId);
    response.redirect("/locations");
    return new ModelAndView(null, null);
  }
}
