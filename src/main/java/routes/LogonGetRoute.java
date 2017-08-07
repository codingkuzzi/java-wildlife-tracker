package routes;

import db.Repository;
import model.Ranger;
import spark.Request;
import spark.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogonGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    List<Ranger> rangers = db.getRangers();
    // any ranger is REQUIRED for logon, so if there is no rangers, redirect to add at least one
    if (rangers.size() == 0) {
      response.redirect("/rangers/new");
    }
    else {
      model.put("rangers", rangers);
      model.put("stationsDict", db.getStations().stream()
        .collect(Collectors.toMap(s -> s.getId(), s -> s.getName())));
      model.put("template", "templates/logon.vtl");
    }
  }
}
