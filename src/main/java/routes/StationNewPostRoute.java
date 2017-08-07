package routes;

import db.Repository;
import spark.Request;
import spark.Response;

import java.util.Map;

public class StationNewPostRoute extends BaseRoute {
    @Override
    protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
        db.addStation(request.queryParams("name"));
        response.redirect("/stations");
    }
}
