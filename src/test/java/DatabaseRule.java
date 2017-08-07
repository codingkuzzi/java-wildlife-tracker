import db.Repository;
import org.junit.rules.ExternalResource;

public class DatabaseRule extends ExternalResource {
  private Repository db;

  public Repository getDb() {
    return db;
  }

  @Override
  protected void before() {
    db = new Repository("jdbc:postgresql://localhost:5432/wildlife_tracker_2_test");
    }
  @Override
  protected void after() {
    db.deleteSightings();
    db.deleteAnimals();
    db.deleteLocations();
    db.deleteRangers();
    db.deleteStations();
  }
}
