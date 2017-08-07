package routes;

import db.Repository;
import model.Location;
import model.Sighting;
import spark.Request;
import spark.Response;
import java.util.Map;

public class SightingViewGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    int sightingId;
    try {
      sightingId = Integer.parseInt(request.params("id"));
    }
    catch (NumberFormatException e) {
      model.put("error", String.format("Invalid id: %s", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

    Sighting sighting = db.getSightingById(sightingId);
    if (sighting == null) {
      model.put("error", String.format("Object id=%s doesn't exist", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

    Location location = db.getLocationById(sighting.getLocation_Id());
    model.put("location", location);
    model.put("station", db.getStationById(location.getStation_Id()));
    model.put("ranger", db.getRangerById(sighting.getRanger_Id()));
    model.put("animal", db.getAnimalById(sighting.getAnimal_Id()));
    model.put("displayTime", sighting.getDisplayTime() );
    model.put("imageUrl" ,sighting.getImage());
    model.put("template", "templates/sighting-view.vtl");
  }
}