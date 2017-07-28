package routes;

import db.Repository;
import model.Animal;
import model.EndangeredAnimal;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AnimalViewGetRoute implements TemplateViewRoute {
  @Override
  public ModelAndView handle(Request request, Response response) throws Exception {
    Map<String, Object> model = new HashMap<>();
    model.put("userName", request.session().attribute("userName"));
    int animalId;
    try {
      animalId = Integer.parseInt(request.params("id"));
    }
    catch (NumberFormatException e) {
      model.put("template", "templates/error.vtl");
      return new ModelAndView(model,"templates/layout.vtl");
    }
    Repository db = new Repository();

    Animal animal = db.getAnimalById(animalId);
    model.put("animal", animal);

    model.put("sightings", db.getSightingsByAnimal(animalId));

    model.put("locationsDict", db.getLocations().stream()
      .collect(Collectors.toMap(l->l.getId(),l->l.getName())));
    model.put("rangersDict", db.getRangers().stream()
      .collect(Collectors.toMap(l->l.getId(),l->l.getDisplayName())));

    if (animal instanceof EndangeredAnimal) {
      model.put("template", "templates/animal-endangered-view.vtl");
    }
    else {
      model.put("template", "templates/animal-view.vtl");
    }
    return new ModelAndView(model,"templates/layout.vtl");
  }
}
