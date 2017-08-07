import model.Location;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import java.util.List;

public class LocationTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void objectProperties() {
    String locationName = "Test Location";
    int stationId = 5;
    Location location = new Location();
    location.setName(locationName);
    location.setStation_Id(stationId);
    Assert.assertEquals(location.getName(), locationName);
    Assert.assertEquals(location.getStation_Id(), stationId);
  }

  @Test
  public void databaseAddAndGet() {
    String locationName = "Test Location";
    int stationId = database.getDb().addStation("Test Station");
    int locationId = database.getDb().addLocation(locationName, stationId);
    Location location = database.getDb().getLocationById(locationId);
    Assert.assertEquals(location.getName(), locationName);
    Assert.assertEquals(location.getStation_Id(), stationId);
  }

  @Test
  public void databaseGetList() {
    int stationId = database.getDb().addStation("Test Station");
    database.getDb().addLocation("Test Location 1", stationId);
    database.getDb().addLocation("Test Location 2", stationId);
    List<Location> locations = database.getDb().getLocations();
    Assert.assertEquals(locations.size(), 2);
  }

  @Test
  public void databaseGetListByStation() {
    int station1Id = database.getDb().addStation("Test Station 1");
    int station2Id = database.getDb().addStation("Test Station 2");
    database.getDb().addLocation("Test Location 1", station1Id);
    database.getDb().addLocation("Test Location 2", station2Id);
    database.getDb().addLocation("Test Location 3", station1Id);
    List<Location> locations = database.getDb().getLocationsByStation(station1Id);
    Assert.assertEquals(locations.size(), 2);
  }

  @Test
  public void databaseGetWrongId() {
    int wrongId = Integer.MAX_VALUE;
    Location location = database.getDb().getLocationById(wrongId);
    Assert.assertNull(location);
  }
}
