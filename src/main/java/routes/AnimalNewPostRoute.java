package routes;

import db.Repository;
import model.enums.AnimalAge;
import model.enums.AnimalHealth;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class AnimalNewPostRoute implements TemplateViewRoute{
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Repository db = new Repository();
    // if checkbox is not checked, value is null, otherwise value is "on"
    if (request.queryParams("is_endangered") == null) {
      db.addNonEndangeredAnimal(request.queryParams("name"));
    }
    else {
      db.addEndangeredAnimal(
        request.queryParams("name"),
        // convert to enum values from ordinals
        AnimalHealth.values()[Integer.parseInt(request.queryParams("health"))],
        AnimalAge.values()[Integer.parseInt(request.queryParams("age"))]
        );
    }
    response.redirect("/animals");
    return new ModelAndView(null, null);
  }
}
