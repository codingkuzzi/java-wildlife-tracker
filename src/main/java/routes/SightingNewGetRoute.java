package routes;

import db.Repository;
import model.Animal;
import model.Location;
import spark.Request;
import spark.Response;
import java.util.List;

import java.util.Map;

public class SightingNewGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    Integer userId = request.session().attribute("userId");
    // user MUST be logged in to report sightings
    if (userId == null) {
      response.redirect("/logon");
      return;
    }
    int stationId = db.getRangerById(userId).getStation_Id();

    List<Location> locations = db.getLocationsByStation(stationId);
    // location is REQUIRED for sighting, so if there is no locations, redirect to add at least one
    if (locations.size() == 0) {
      response.redirect("/locations/new"); // ?stationId=...
      return;
    }

    List<Animal> animals = db.getAllAnimals();
    // animal is REQUIRED for sighting, so if there is no animals, redirect to add at least one
    if (animals.size() == 0) {
      response.redirect("/animals/new");
      return;
    }

    model.put("locations", locations);
    model.put("animals", animals);
    model.put("template", "templates/sighting-new.vtl");
  }
}
