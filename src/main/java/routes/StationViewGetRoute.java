package routes;

import db.Repository;
import model.Location;
import model.Station;
import spark.Request;
import spark.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StationViewGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    int stationId;
    try {
      stationId = Integer.parseInt(request.params("id"));
    }
    catch (NumberFormatException e) {
      model.put("error", String.format("Invalid id: %s", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

    Station station = db.getStationById(stationId);
    if (station == null) {
      model.put("error", String.format("Object id=%s doesn't exist", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

    model.put("station", station);
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
  }
}
