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

public class StationViewGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));
    int stationId = Integer.parseInt(request.params("id"));
    Repository db = new Repository();

    model.put("station", db.getStationById(stationId));

    List<Location> locations = db.getLocationsByStation(stationId);

    model.put("rangers", db.getRangersByStation(stationId));
    model.put("locations", locations);
    model.put("sightings", db.getSightingsByStation(stationId));

    model.put("animalsDict", db.getAllAnimals().stream()
      .collect(Collectors.toMap(a->a.getId(),a->a.getDisplayName())));
    model.put("locationsDict", locations.stream()
      .collect(Collectors.toMap(l->l.getId(),l->l.getName())));
    // all rangers to dictionary, because rangers can be relocated since sighting has reported
    model.put("rangersDict", db.getRangers().stream()
      .collect(Collectors.toMap(r->r.getId(),r->r.getDisplayName())));

    model.put("template", "templates/station-view.vtl");
    return new ModelAndView(model,"templates/layout.vtl");
  }
}
