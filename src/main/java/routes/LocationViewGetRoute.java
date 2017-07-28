package routes;

import db.Repository;
import model.Location;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationViewGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));
    int locationId;
    try {
     locationId =Integer.parseInt(request.params("id"));
    }
    catch (NumberFormatException e) {
      model.put("template", "templates/error.vtl");
      return new ModelAndView(model,"templates/layout.vtl");
    }
    Repository db = new Repository();

    Location location = db.getLocationById(locationId);
    model.put("location", location);
    model.put("station", db.getStationById(location.getStation_Id()));

    model.put("sightings", db.getSightingsByLocation(locationId));

    model.put("animalsDict", db.getAllAnimals().stream()
      .collect(Collectors.toMap(a->a.getId(),a->a.getDisplayName())));
    model.put("rangersDict", db.getRangers().stream()
      .collect(Collectors.toMap(l->l.getId(),l->l.getDisplayName())));

    model.put("template", "templates/location-view.vtl");
    return new ModelAndView(model,"templates/layout.vtl");
  }
}
