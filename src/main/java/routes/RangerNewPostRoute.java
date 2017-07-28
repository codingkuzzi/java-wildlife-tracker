package routes;

import db.Repository;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class RangerNewPostRoute implements TemplateViewRoute{
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Repository db = new Repository();
    db.addRanger(
      Integer.parseInt(request.queryParams("station")),
      request.queryParams("firstName"),
      request.queryParams("lastName"),
      request.queryParams("email")
    );
    response.redirect("/rangers");
    return new ModelAndView(null, null);
  }
}
