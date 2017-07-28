package routes;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;

public class StationNewGetRoute implements TemplateViewRoute{
    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        Map<String, Object>  model = new HashMap<>();
        model.put("userName", request.session().attribute("userName"));
        model.put("template", "templates/station-new.vtl");
        return new ModelAndView(model, "templates/layout.vtl");

    }
}
