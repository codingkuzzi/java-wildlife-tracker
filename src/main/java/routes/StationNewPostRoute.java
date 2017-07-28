package routes;

import db.Repository;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

public class StationNewPostRoute implements TemplateViewRoute{
    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        Repository db = new Repository();
        db.addStation(request.queryParams("name"));
        response.redirect("/stations");
        return new ModelAndView(null, null);
    }
}
