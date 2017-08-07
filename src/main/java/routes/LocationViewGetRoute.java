package routes;

import db.Repository;
import model.Location;
import spark.Request;
import spark.Response;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationViewGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    int locationId;
    try {
      locationId = Integer.parseInt(request.params("id"));
    }
    catch (NumberFormatException e) {
      model.put("error", String.format("Invalid id: %s", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

    Location location = db.getLocationById(locationId);
    if (location == null) {
      model.put("error", String.format("Object id=%s doesn't exist", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

    model.put("location", location);
    model.put("station", db.getStationById(location.getStation_Id()));
    model.put("sightings", db.getSightingsByLocation(locationId));
    model.put("animalsDict", db.getAllAnimals().stream()
      .collect(Collectors.toMap(a->a.getId(),a->a.getDisplayName())));
    model.put("rangersDict", db.getRangers().stream()
      .collect(Collectors.toMap(l->l.getId(),l->l.getDisplayName())));
    model.put("template", "templates/location-view.vtl");
  }
}
