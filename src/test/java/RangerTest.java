import model.Ranger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import java.util.List;

public class RangerTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void objectProperties() {
    String rangerLastName = "Brown";
    String rangerFirstName = "Joe";
    String rangerEmail = "joe.brown@example.com";
    int stationId = 5;
    Ranger ranger = new Ranger();
    ranger.setLastName(rangerLastName);
    ranger.setFirstName(rangerFirstName);
    ranger.setEmail(rangerEmail);
    ranger.setStation_Id(stationId);
    Assert.assertEquals(ranger.getLastName(), rangerLastName);
    Assert.assertEquals(ranger.getFirstName(), rangerFirstName);
    Assert.assertEquals(ranger.getEmail(), rangerEmail);
    Assert.assertEquals(ranger.getStation_Id(), stationId);
  }

  @Test
  public void databaseAddAndGet() {
    String rangerLastName = "Brown";
    String rangerFirstName = "Joe";
    String rangerEmail = "joe.brown@example.com";
    int stationId = database.getDb().addStation("Test Station");
    int rangerId = database.getDb().addRanger(stationId, rangerLastName, rangerFirstName, rangerEmail);
    Ranger ranger = database.getDb().getRangerById(rangerId);
    Assert.assertEquals(ranger.getLastName(), rangerLastName);
    Assert.assertEquals(ranger.getFirstName(), rangerFirstName);
    Assert.assertEquals(ranger.getEmail(), rangerEmail);
    Assert.assertEquals(ranger.getStation_Id(), stationId);
  }

  @Test
  public void databaseGetList() {
    int stationId = database.getDb().addStation("Test Station");
    database.getDb().addRanger(stationId,"Brown", "Joe", "joe.brown@example.com");
    database.getDb().addRanger(stationId,"Brown", "Jill", "jill.brown@examlpe.com");
    List<Ranger> rangers = database.getDb().getRangers();
    Assert.assertEquals(rangers.size(), 2);
  }

  @Test
  public void databaseGetListByStation() {
    int station1Id = database.getDb().addStation("Test Station 1");
    int station2Id = database.getDb().addStation("Test Station 2");
    database.getDb().addRanger(station1Id,"Brown", "Joe", "joe.brown@example.com");
    database.getDb().addRanger(station2Id,"Brown", "Jill", "jill.brown@examlpe.com");
    database.getDb().addRanger(station1Id,"Brown", "Jack", "jack.brown@examlpe.com");
    List<Ranger> rangers = database.getDb().getRangersByStation(station1Id);
    Assert.assertEquals(rangers.size(), 2);
  }

  @Test
  public void databaseGetWrongId() {
    int wrongId = Integer.MAX_VALUE;
    Ranger ranger = database.getDb().getRangerById(wrongId);
    Assert.assertNull(ranger);
  }
}
