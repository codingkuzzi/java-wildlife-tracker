package routes;

import db.Repository;
import model.Location;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SightingsGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));

    Repository db = new Repository();
    model.put("sightings", db.getSightings());

    model.put("animalsDict", db.getAllAnimals().stream()
      .collect(Collectors.toMap(a->a.getId(),a->a.getDisplayName())));
    model.put("locationsDict", db.getLocations().stream()
      .collect(Collectors.toMap(l->l.getId(),l->l.getName())));
    model.put("rangersDict", db.getRangers().stream()
      .collect(Collectors.toMap(r->r.getId(),r->r.getDisplayName())));

    model.put("template", "templates/sightings.vtl");
    return new ModelAndView(model,"templates/layout.vtl");
  }
}
