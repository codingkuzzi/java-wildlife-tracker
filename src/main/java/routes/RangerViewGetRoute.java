package routes;

import db.Repository;
import model.Ranger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RangerViewGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));
    int rangerId = Integer.parseInt(request.params("id"));
    Repository db = new Repository();

    Ranger ranger = db.getRangerById(rangerId);
    model.put("ranger", ranger);
    model.put("station", db.getStationById(ranger.getStation_Id()));

    model.put("sightings", db.getSightingsByRanger(rangerId));

    model.put("animalsDict", db.getAllAnimals().stream()
      .collect(Collectors.toMap(a->a.getId(),a->a.getDisplayName())));
    model.put("locationsDict", db.getLocations().stream()
      .collect(Collectors.toMap(l->l.getId(),l->l.getName())));

    model.put("template", "templates/ranger-view.vtl");
    return new ModelAndView(model,"templates/layout.vtl");
  }
}
