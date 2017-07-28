package routes;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class LogoffGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    request.session().attribute("userId", null);
    request.session().attribute("userName", null);
    response.redirect(request.headers("Referer"));
    return new ModelAndView(null, null);
  }
}
