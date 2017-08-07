import model.Station;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import java.util.List;

public class StationTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void objectProperties() {
    String stationName = "Test Station";
    Station station = new Station();
    station.setName(stationName);
    Assert.assertEquals(station.getName(), stationName);
  }

  @Test
  public void databaseAddAndGet() {
    String stationName = "Test Station";
    int stationId = database.getDb().addStation(stationName);
    Station station = database.getDb().getStationById(stationId);
    Assert.assertEquals(station.getName(), stationName);
  }

  @Test
  public void databaseGetList() {
    database.getDb().addStation("Test Station 1");
    database.getDb().addStation("Test Station 2");
    List<Station> stations = database.getDb().getStations();
    Assert.assertEquals(stations.size(), 2);
  }

  @Test
  public void databaseGetWrongId() {
    int wrongId = Integer.MAX_VALUE;
    Station station = database.getDb().getStationById(wrongId);
    Assert.assertNull(station);
  }
}
