package routes;

import db.Repository;
import spark.Request;
import spark.Response;
import java.util.Map;

public class StationsGetRoute extends BaseRoute {
    @Override
    protected void process(Request request, Response response, Map<String, Object> model, Repository db) {
        model.put("stations",db.getStations());
        model.put("template", "templates/stations.vtl");
    }
}
