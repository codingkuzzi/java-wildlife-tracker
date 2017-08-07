package routes;

import db.Repository;
import model.Animal;
import model.EndangeredAnimal;
import spark.Request;
import spark.Response;
import java.util.Map;
import java.util.stream.Collectors;

public class AnimalViewGetRoute extends BaseRoute {
  @Override
  protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
    int animalId;
    try {
      animalId = Integer.parseInt(request.params("id"));
    }
    catch (NumberFormatException e) {
      model.put("error", String.format("Invalid id: %s", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

    Animal animal = db.getAnimalById(animalId);
    if (animal == null) {
      model.put("error", String.format("Object id=%s doesn't exist", request.params("id")));
      model.put("template", "templates/error.vtl");
      return;
    }

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
  }
}
