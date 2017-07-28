package routes;

import model.enums.AnimalAge;
import model.enums.AnimalHealth;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class AnimalNewGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));
    model.put("healthOptions", AnimalHealth.values());
    model.put("ageOptions", AnimalAge.values());
    model.put("template", "/templates/animal-new.vtl");
    return new ModelAndView(model, "templates/layout.vtl");
  }
}
