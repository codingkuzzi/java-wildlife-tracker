package routes;

import db.Repository;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class AnimalsGetRoute implements TemplateViewRoute{
    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("userName", request.session().attribute("userName"));
        Repository db = new Repository();
        model.put("animals", db.getNonEndangeredAnimals());
        model.put("endangeredAnimals", db.getEndangeredAnimals());
        model.put("template", "templates/animals.vtl");
        return new ModelAndView (model, "templates/layout.vtl");
    }
}
