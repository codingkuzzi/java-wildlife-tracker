package routes;

import db.Repository;
import model.Ranger;
import spark.Request;
import spark.Response;
import java.util.Map;
import java.util.stream.Collectors;

public class RangerViewGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    int rangerId;
    try {
      rangerId = Integer.parseInt(request.params("id"));
    }
    catch (NumberFormatException e) {
      model.put("error", String.format("Invalid id: %s", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

    Ranger ranger = db.getRangerById(rangerId);
    if (ranger == null) {
      model.put("error", String.format("Object id=%s doesn't exist", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

    model.put("ranger", ranger);
    model.put("station", db.getStationById(ranger.getStation_Id()));
    model.put("sightings", db.getSightingsByRanger(rangerId));
    model.put("animalsDict", db.getAllAnimals().stream()
      .collect(Collectors.toMap(a->a.getId(),a->a.getDisplayName())));
    model.put("locationsDict", db.getLocations().stream()
      .collect(Collectors.toMap(l->l.getId(),l->l.getName())));
    model.put("template", "templates/ranger-view.vtl");
  }
}
