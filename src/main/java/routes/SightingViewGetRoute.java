package routes;

import db.Repository;
import model.Location;
import model.Sighting;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class SightingViewGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));
    int sightingId = Integer.parseInt(request.params("id"));
    Repository db = new Repository();

    Sighting sighting = db.getSightingById(sightingId);
    Location location = db.getLocationById(sighting.getLocation_Id());
    model.put("location", location);
    model.put("station", db.getStationById(location.getStation_Id()));
    model.put("ranger", db.getRangerById(sighting.getRanger_Id()));
    model.put("animal", db.getAnimalById(sighting.getAnimal_Id()));
    model.put("displayTime", sighting.getDisplayTime() );
    model.put("imageUrl" ,sighting.getImage());
    model.put("template", "templates/sighting-view.vtl");
    return new ModelAndView(model,"templates/layout.vtl");
  }
}