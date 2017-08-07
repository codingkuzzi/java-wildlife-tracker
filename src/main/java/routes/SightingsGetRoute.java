package routes;

import db.Repository;
import spark.Request;
import spark.Response;
import java.util.Map;
import java.util.stream.Collectors;

public class SightingsGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    model.put("sightings", db.getSightings());
    model.put("animalsDict", db.getAllAnimals().stream()
      .collect(Collectors.toMap(a->a.getId(),a->a.getDisplayName())));
    model.put("locationsDict", db.getLocations().stream()
      .collect(Collectors.toMap(l->l.getId(),l->l.getName())));
    model.put("rangersDict", db.getRangers().stream()
      .collect(Collectors.toMap(r->r.getId(),r->r.getDisplayName())));
    model.put("template", "templates/sightings.vtl");
  }
}
