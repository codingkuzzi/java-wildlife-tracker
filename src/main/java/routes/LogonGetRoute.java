package routes;

import db.Repository;
import model.Ranger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogonGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Repository db = new Repository();
    List<Ranger> rangers = db.getRangers();
    // any ranger is REQUIRED for logon, so if there is no rangers, redirect to add at least one
    if (rangers.size() == 0) {
      response.redirect("/rangers/new");
      return new ModelAndView(null, null);
    }

    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));
    model.put("rangers", rangers);
    model.put("stationsDict", db.getStations().stream()
      .collect(Collectors.toMap(s->s.getId(), s->s.getName())));

    model.put("template", "templates/logon.vtl");
    return new ModelAndView(model, "templates/layout.vtl");
  }
}
