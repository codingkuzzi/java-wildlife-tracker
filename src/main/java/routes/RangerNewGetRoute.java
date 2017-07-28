package routes;

import db.Repository;
import model.Station;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RangerNewGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));
    Repository db = new Repository();

    List<Station> stations = db.getStations();
    // station is REQUIRED for ranger, so if there is no stations, redirect to add at least one
    if (stations.size() == 0) {
      response.redirect("/stations/new");
      return new ModelAndView(null, null);
    }

    // model.put("rangers", db.getRangers());
    model.put("stations", stations);
    model.put("template", "templates/ranger-new.vtl");
    return new ModelAndView(model, "templates/layout.vtl");
  }
}
