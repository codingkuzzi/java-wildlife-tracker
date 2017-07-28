package routes;

import db.Repository;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class StationsGetRoute implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        Map <String, Object> model = new HashMap<>();
        model.put("userName", request.session().attribute("userName"));
        Repository db = new Repository();
        model.put("stations",db.getStations());
        model.put("template", "templates/stations.vtl");
        return new ModelAndView(model,"templates/layout.vtl");
    }
}
