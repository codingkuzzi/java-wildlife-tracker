package db;

import model.*;
import model.enums.AnimalAge;
import model.enums.AnimalHealth;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.converters.Convert;
import org.sql2o.data.Row;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Repository {
  private Sql2o sqlHelper;

  public Repository(String url) {
    this(url, null, null);
  }

  public Repository(String url, String user, String password) {
    // FIX: out of the box sql2o can't convert to and from LocalDateTime
    Convert.registerConverter(LocalDateTime.class, new LocalDateTimeConverter());
    sqlHelper = new Sql2o(url, user, password);
  }

  // region Animal

  public List<Animal> getAllAnimals() {
    try (Connection connection = sqlHelper.open()) {
      List<Animal> result = new ArrayList<>();
      List<Row> animalsTableRows = connection
        .createQuery("SELECT id,name,is_endangered,health,age FROM animals ORDER BY name,health,age")
        .executeAndFetchTable()
        .rows();
      for (Row animalsTableRow : animalsTableRows) {
        if (animalsTableRow.getBoolean("is_endangered")) {
          EndangeredAnimal endangeredAnimal = new EndangeredAnimal();
          endangeredAnimal.setId(animalsTableRow.getInteger("id"));
          endangeredAnimal.setName(animalsTableRow.getString("name"));
          endangeredAnimal.setHealth(AnimalHealth.valueOf(animalsTableRow.getString("health")));
          endangeredAnimal.setAge(AnimalAge.valueOf(animalsTableRow.getString("age")));
          result.add(endangeredAnimal);
        } else {
          Animal animal = new Animal();
          animal.setId(animalsTableRow.getInteger("id"));
          animal.setName(animalsTableRow.getString("name"));
          result.add(animal);
        }
      }
      return result;
    }
  }

  public List<Animal> getNonEndangeredAnimals() {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,name FROM animals WHERE is_endangered=false ORDER BY name")
        .executeAndFetch(Animal.class);
    }
  }

  public List<EndangeredAnimal> getEndangeredAnimals() {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,name,health,age FROM animals WHERE is_endangered=true ORDER BY name,health,age")
        .executeAndFetch(EndangeredAnimal.class);
    }
  }

  public Animal getAnimalById(int id) {
    try (Connection connection = sqlHelper.open()) {
      Row animalsTableRow = connection
        .createQuery("SELECT id,name,is_endangered,health,age FROM animals WHERE id=:id")
        .addParameter("id", id)
        .executeAndFetchTable()
        .rows()
        .stream()
        .findFirst()
        .orElse(null);
      // if such id does not exist
      if (animalsTableRow == null)
        return null;
      if (animalsTableRow.getBoolean("is_endangered")) {
        EndangeredAnimal endangeredAnimal = new EndangeredAnimal();
        endangeredAnimal.setId(animalsTableRow.getInteger("id"));
        endangeredAnimal.setName(animalsTableRow.getString("name"));
        endangeredAnimal.setHealth(AnimalHealth.valueOf(animalsTableRow.getString("health")));
        endangeredAnimal.setAge(AnimalAge.valueOf(animalsTableRow.getString("age")));
        return endangeredAnimal;
      } else {
        Animal animal = new Animal();
        animal.setId(animalsTableRow.getInteger("id"));
        animal.setName(animalsTableRow.getString("name"));
        return animal;
      }
    }
  }

  public int addNonEndangeredAnimal(String name) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("INSERT INTO animals(name,is_endangered) VALUES (:name,:is_endangered)", true)
        .addParameter("name", name)
        .addParameter("is_endangered", false)
        .executeUpdate()
        .getKey(int.class);
    }

  }

  public int addEndangeredAnimal(String name, AnimalHealth health, AnimalAge age) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("INSERT INTO animals(name,is_endangered,health,age) VALUES (:name,:is_endangered,:health,:age)", true)
        .addParameter("name", name)
        .addParameter("is_endangered", true)
        // sql2o can auto-convert enum to String and vice versa
        .addParameter("health", health)
        .addParameter("age", age)
        .executeUpdate()
        .getKey(int.class);
    }
  }
  // endregion

  // region Location

  public List<Location> getLocations() {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,name,station_id FROM locations ORDER BY name")
        .executeAndFetch(Location.class);
    }
  }

  public List<Location> getLocationsByStation(int stationId) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,name,station_id FROM locations WHERE station_id=:stationId ORDER BY name")
        .addParameter("stationId", stationId)
        .executeAndFetch(Location.class);
    }
  }

  public Location getLocationById(int id) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,name,station_id FROM locations WHERE id=:id")
        .addParameter("id", id)
        .executeAndFetchFirst(Location.class);
    }
  }

  public int addLocation(String name, int stationId) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("INSERT INTO locations(name,station_id) VALUES (:name,:stationId)", true)
        .addParameter("name", name)
        .addParameter("stationId", stationId)
        .executeUpdate()
        .getKey(int.class);
    }
  }
  // endregion

  // region Ranger

  public List<Ranger> getRangers() {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,lastname,firstname,email,station_id FROM rangers ORDER BY lastname,firstname")
        .executeAndFetch(Ranger.class);
    }
  }

  public List<Ranger> getRangersByStation(int stationId) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        // FIX: don't miss columns that should be matched to class fields even if that columns are in WHERE clause
        .createQuery("SELECT id,lastname,firstname,email,station_id FROM rangers WHERE station_id=:stationId ORDER BY lastname,firstname")
        .addParameter("stationId", stationId)
        .executeAndFetch(Ranger.class);
    }
  }

  public Ranger getRangerById(int id) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,lastname,firstname,email,station_id FROM rangers WHERE id=:id")
        .addParameter("id", id)
        .executeAndFetchFirst(Ranger.class);
    }
  }

  public int addRanger(int stationId, String lastName, String firstName, String email) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("INSERT INTO rangers(lastname,firstname,email,station_id) VALUES (:lastName,:firstName,:email,:stationId)", true)
        .addParameter("lastName", lastName)
        .addParameter("firstName", firstName)
        .addParameter("email", email)
        .addParameter("stationId", stationId)
        .executeUpdate()
        .getKey(int.class);
    }
  }
  // endregion

  // region Sighting
  public List<Sighting> getSightings() {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,animal_id,ranger_id,location_id,\"time\",image FROM sightings ORDER BY \"time\" DESC")
        .executeAndFetch(Sighting.class);
    }
  }

  public List<Sighting> getSightingsByAnimal(int animalId) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,animal_id,ranger_id,location_id,\"time\",image FROM sightings WHERE animal_id=:animalId ORDER BY \"time\" DESC")
        .addParameter("animalId", animalId)
        .executeAndFetch(Sighting.class);
    }
  }

  public List<Sighting> getSightingsByRanger(int rangerId) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,animal_id,ranger_id,location_id,\"time\",image FROM sightings WHERE ranger_id=:rangerId ORDER BY \"time\" DESC")
        .addParameter("rangerId", rangerId)
        .executeAndFetch(Sighting.class);
    }
  }

  public List<Sighting> getSightingsByLocation(int locationId) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,animal_id,ranger_id,location_id,\"time\",image FROM sightings WHERE location_id=:locationId ORDER BY \"time\" DESC")
        .addParameter("locationId", locationId)
        .executeAndFetch(Sighting.class);
    }
  }

  public List<Sighting> getSightingsByStation(int stationId) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        // subquery and join are alternatives!
        .createQuery("SELECT id,animal_id,ranger_id,location_id,\"time\",image FROM sightings WHERE location_id IN (SELECT id FROM locations WHERE station_id=:stationId) ORDER BY \"time\" DESC")
        .addParameter("stationId", stationId)
        .executeAndFetch(Sighting.class);
    }
  }

  public Sighting getSightingById(int id) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,animal_id,ranger_id,location_id,\"time\",image FROM sightings WHERE id=:id")
        .addParameter("id", id)
        .executeAndFetchFirst(Sighting.class);
    }
  }

  public int addSighting(int animalId, int rangerId, int locationId, LocalDateTime time, String image) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("INSERT INTO sightings(animal_id,ranger_id,location_id,time,image) VALUES (:animalId,:rangerId,:locationId,:time,:image)", true)
        .addParameter("animalId", animalId)
        .addParameter("rangerId", rangerId)
        .addParameter("locationId", locationId)
        .addParameter("time", time)
        .addParameter("image", image)
        .executeUpdate()
        .getKey(int.class);
    }
  }
  // endregion

  // region Station

  public List<Station> getStations() {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,name FROM stations ORDER BY name")
        .executeAndFetch(Station.class);
    }
  }

  public Station getStationById(int id) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("SELECT id,name FROM stations WHERE id=:id")
        .addParameter("id", id)
        .executeAndFetchFirst(Station.class);
    }
  }

  public int addStation(String name) {
    try (Connection connection = sqlHelper.open()) {
      return connection
        .createQuery("INSERT INTO stations(name) VALUES (:name)", true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey(int.class);
    }
  }
  // endregion

  // region Deletions
  public void deleteAnimals() {
    try (Connection connection = sqlHelper.open()) {
      connection
        .createQuery("DELETE FROM animals")
        .executeUpdate();
    }
  }
  public void deleteLocations() {
    try (Connection connection = sqlHelper.open()) {
      connection
        .createQuery("DELETE FROM locations")
        .executeUpdate();
    }
  }
  public void deleteRangers() {
    try (Connection connection = sqlHelper.open()) {
      connection
        .createQuery("DELETE FROM rangers")
        .executeUpdate();
    }
  }
  public void deleteSightings() {
    try (Connection connection = sqlHelper.open()) {
      connection
        .createQuery("DELETE FROM sightings")
        .executeUpdate();
    }
  }
  public void deleteStations() {
    try (Connection connection = sqlHelper.open()) {
      connection
        .createQuery("DELETE FROM stations")
        .executeUpdate();
    }
  }
  // endregion
}
