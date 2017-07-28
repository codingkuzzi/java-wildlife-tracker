package routes;

import db.Repository;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationsGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));
    Repository db = new Repository();
    model.put("locations",db.getLocations());
    model.put("stationsDict", db.getStations().stream().collect(Collectors.toMap(s->s.getId(), s->s.getName())));
    model.put("template", "templates/locations.vtl");
    return new ModelAndView(model,"templates/layout.vtl");
  }
}
