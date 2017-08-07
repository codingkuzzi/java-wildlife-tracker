import model.Sighting;
import model.enums.AnimalAge;
import model.enums.AnimalHealth;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class SightingTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void objectProperties() {
    int locationId = 5;
    int rangerId = 7;
    int animalId = 2;
    LocalDateTime sightingTime = LocalDateTime.now();
    String sightingImage = "http://example.com/example.jpg";
    Sighting sighting = new Sighting();
    sighting.setLocation_Id(locationId);
    sighting.setRanger_Id(rangerId);
    sighting.setAnimal_Id(animalId);
    sighting.setTime(sightingTime);
    sighting.setImage(sightingImage);
    Assert.assertEquals(sighting.getLocation_Id(), locationId);
    Assert.assertEquals(sighting.getRanger_Id(), rangerId);
    Assert.assertEquals(sighting.getAnimal_Id(), animalId);
    Assert.assertEquals(sighting.getTime(), sightingTime);
    Assert.assertEquals(sighting.getImage(), sightingImage);
  }

  @Test
  public void databaseAddAndGet() {
    int stationId = database.getDb().addStation("Test Station");
    int locationId = database.getDb().addLocation("Test Location", stationId);
    int rangerId = database.getDb().addRanger(stationId,"Brown", "Joe", "joe.brown@example.com");
    int animalId = database.getDb().addNonEndangeredAnimal("Test Animal");
    LocalDateTime sightingTime = LocalDateTime.now();
    String sightingImage = "http://example.com/example.jpg";
    int sightingId = database.getDb().addSighting(animalId, rangerId, locationId, sightingTime, sightingImage);
    Sighting sighting = database.getDb().getSightingById(sightingId);
    Assert.assertEquals(sighting.getLocation_Id(), locationId);
    Assert.assertEquals(sighting.getRanger_Id(), rangerId);
    Assert.assertEquals(sighting.getAnimal_Id(), animalId);
    Assert.assertEquals(sighting.getTime(), sightingTime);
    Assert.assertEquals(sighting.getImage(), sightingImage);
  }

  @Test
  public void databaseGetList() {
    int stationId = database.getDb().addStation("Test Station");
    int locationId = database.getDb().addLocation("Test Location", stationId);
    int rangerId = database.getDb().addRanger(stationId,"Brown", "Joe", "joe.brown@example.com");
    int animalId = database.getDb().addNonEndangeredAnimal("Test Animal");
    database.getDb().addSighting(animalId, rangerId, locationId, LocalDateTime.now().minusHours(1), "http://example.com/example1.jpg");
    database.getDb().addSighting(animalId, rangerId, locationId, LocalDateTime.now(), "http://example.com/example2.jpg");
    List<Sighting> sightings = database.getDb().getSightings();
    Assert.assertEquals(sightings.size(), 2);
  }

  @Test
  public void databaseGetListBySomething() {
    int station1Id = database.getDb().addStation("Test Station 1");
    int station2Id = database.getDb().addStation("Test Station 2");
    int location1Id = database.getDb().addLocation("Test Location 1", station1Id);
    int location2Id = database.getDb().addLocation("Test Location 2", station2Id);
    int ranger1Id = database.getDb().addRanger(station1Id,"Brown", "Joe", "joe.brown@example.com");
    int ranger2Id = database.getDb().addRanger(station2Id,"Brown", "Jack", "jack.brown@example.com");
    int animal1Id = database.getDb().addNonEndangeredAnimal("Test Animal");
    int animal2Id = database.getDb().addEndangeredAnimal("Test Endangered Animal", AnimalHealth.Healthy, AnimalAge.Newborn);
    database.getDb().addSighting(animal1Id, ranger1Id, location1Id, LocalDateTime.now().minusHours(7), "http://example.com/example1.jpg");
    database.getDb().addSighting(animal2Id, ranger1Id, location1Id, LocalDateTime.now().minusHours(6), "http://example.com/example2.jpg");
    database.getDb().addSighting(animal1Id, ranger2Id, location1Id, LocalDateTime.now().minusHours(5), "http://example.com/example3.jpg");
    database.getDb().addSighting(animal2Id, ranger2Id, location1Id, LocalDateTime.now().minusHours(4), "http://example.com/example4.jpg");
    database.getDb().addSighting(animal1Id, ranger1Id, location2Id, LocalDateTime.now().minusHours(3), "http://example.com/example5.jpg");
    database.getDb().addSighting(animal2Id, ranger1Id, location2Id, LocalDateTime.now().minusHours(2), "http://example.com/example6.jpg");
    database.getDb().addSighting(animal1Id, ranger2Id, location2Id, LocalDateTime.now().minusHours(1), "http://example.com/example7.jpg");
    database.getDb().addSighting(animal2Id, ranger2Id, location2Id, LocalDateTime.now(), "http://example.com/example8.jpg");

    List<Sighting> sightings1 = database.getDb().getSightingsByAnimal(animal1Id);
    Assert.assertEquals(sightings1.size(), 4);

    List<Sighting> sightings2 = database.getDb().getSightingsByLocation(location1Id);
    Assert.assertEquals(sightings2.size(), 4);

    List<Sighting> sightings3 = database.getDb().getSightingsByRanger(ranger1Id);
    Assert.assertEquals(sightings3.size(), 4);

    List<Sighting> sightings4 = database.getDb().getSightingsByStation(station1Id);
    Assert.assertEquals(sightings4.size(), 4);
  }

  @Test
  public void databaseGetWrongId() {
    int wrongId = Integer.MAX_VALUE;
    Sighting sighting = database.getDb().getSightingById(wrongId);
    Assert.assertNull(sighting);
  }
}
