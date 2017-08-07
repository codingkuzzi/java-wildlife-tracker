package routes;

import db.Repository;
import model.Station;
import spark.Request;
import spark.Response;
import java.util.List;
import java.util.Map;

public class RangerNewGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    List<Station> stations = db.getStations();
    // station is REQUIRED for ranger, so if there is no stations, redirect to add at least one
    if (stations.size() == 0) {
      response.redirect("/stations/new");
    }
    else {
      model.put("stations", stations);
      model.put("template", "templates/ranger-new.vtl");
    }
  }
}
