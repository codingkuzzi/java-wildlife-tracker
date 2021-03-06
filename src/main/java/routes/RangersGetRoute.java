package routes;

import db.Repository;
import spark.Request;
import spark.Response;
import java.util.Map;
import java.util.stream.Collectors;

public class RangersGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    model.put("rangers", db.getRangers());
    model.put("stationsDict", db.getStations().stream().collect(Collectors.toMap(s->s.getId(), s->s.getName())));
    model.put("template", "templates/rangers.vtl");
  }
}
