import java.time.LocalDateTime;

import db.LocalDateTimeConverter;
import org.sql2o.converters.Convert;
import routes.*;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

public class App {
  public static void main(String[] args) {

    Spark.staticFileLocation("/public");

    // FIX: out of the box sql2o can't convert to and from LocalDateTime
    Convert.registerConverter(LocalDateTime.class, new LocalDateTimeConverter());

    // Use Heroku environment variables, if detected
    if (System.getenv("PORT") != null) {
      Spark.port(Integer.parseInt(System.getenv("PORT")));
    }

    Spark.get("/", new IndexGetRoute(), new VelocityTemplateEngine());

    Spark.get("/logon", new LogonGetRoute(), new VelocityTemplateEngine());
    Spark.post("/logon", new LogonPostRoute(), new VelocityTemplateEngine());
    Spark.get("/logoff", new LogoffGetRoute(), new VelocityTemplateEngine());

    Spark.get("/stations", new StationsGetRoute(), new VelocityTemplateEngine());
    Spark.get("/stations/new", new StationNewGetRoute(), new VelocityTemplateEngine());
    Spark.post("/stations/new", new StationNewPostRoute(), new VelocityTemplateEngine());
    Spark.get("/stations/:id", new StationViewGetRoute(), new VelocityTemplateEngine());

    Spark.get("/rangers", new RangersGetRoute(), new VelocityTemplateEngine());
    Spark.get("/rangers/new", new RangerNewGetRoute(), new VelocityTemplateEngine());
    Spark.post("/rangers/new", new RangerNewPostRoute(), new VelocityTemplateEngine());
    Spark.get("/rangers/:id", new RangerViewGetRoute(), new VelocityTemplateEngine());

    Spark.get("/locations", new LocationsGetRoute(), new VelocityTemplateEngine());
    Spark.get("/locations/new", new LocationNewGetRoute(), new VelocityTemplateEngine());
    Spark.post("/locations/new", new LocationNewPostRoute(), new VelocityTemplateEngine());
    Spark.get("/locations/:id", new LocationViewGetRoute(), new VelocityTemplateEngine());

    Spark.get("/animals", new AnimalsGetRoute(), new VelocityTemplateEngine());
    Spark.get("/animals/new", new AnimalNewGetRoute(), new VelocityTemplateEngine());
    Spark.post("/animals/new", new AnimalNewPostRoute(), new VelocityTemplateEngine());
    Spark.get("/animals/:id", new AnimalViewGetRoute(), new VelocityTemplateEngine());

    Spark.get("/sightings", new SightingsGetRoute(), new VelocityTemplateEngine());
    Spark.get("/sightings/new", new SightingNewGetRoute(), new VelocityTemplateEngine());
    Spark.post("/sightings/new", new SightingNewPostRoute(), new VelocityTemplateEngine());
    Spark.get("/sightings/:id", new SightingViewGetRoute(), new VelocityTemplateEngine());

    Spark.get("/error/:id", new ErrorGetRoute(), new VelocityTemplateEngine());
  }
}
