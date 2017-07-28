package routes;

import db.Repository;
import model.Animal;
import model.Location;
import model.Ranger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

public class SightingNewGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Integer userId = request.session().attribute("userId");
    // user MUST be logged in to report sightings
    if (userId == null) {
      response.redirect("/logon");
      return new ModelAndView(null, null);
    }
    Map<String, Object> model = new HashMap<>();
    model.put("userId", userId);
    model.put("userName", request.session().attribute("userName"));

    Repository db = new Repository();
    Ranger ranger = db.getRangerById(userId);
    List<Location> locations = db.getLocationsByStation(ranger.getStation_Id());
    // location is REQUIRED for sighting, so if there is no locations, redirect to add at least one
    if (locations.size() == 0) {
      response.redirect("/locations/new"); // ?stationId=...
      return new ModelAndView(null, null);
    }
    List<Animal> animals = db.getAllAnimals();
    // animal is REQUIRED for sighting, so if there is no animals, redirect to add at least one
    if (animals.size() == 0) {
      response.redirect("/animals/new");
      return new ModelAndView(null, null);
    }
    model.put("locations", locations);
    model.put("animals", animals);
    model.put("template", "templates/sighting-new.vtl");
    return new ModelAndView(model, "templates/layout.vtl");
  }
}
