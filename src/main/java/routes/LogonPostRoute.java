package routes;

import db.Repository;
import model.Ranger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class LogonPostRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    int userId = Integer.parseInt(request.queryParams("ranger"));
    Repository db = new Repository();
    Ranger user = db.getRangerById(userId);
    request.session().attribute("userId", user.getId());
    request.session().attribute("userName", user.getDisplayName());
    response.redirect("/");
    return new ModelAndView(null, null);
  }
}
