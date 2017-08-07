import model.Animal;
import model.EndangeredAnimal;
import model.enums.AnimalAge;
import model.enums.AnimalHealth;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import java.util.List;

public class EndangeredAnimalTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void objectProperties() {
    String animalName = "Test Endangered Animal";
    AnimalHealth animalHealth = AnimalHealth.Ill;
    AnimalAge animalAge = AnimalAge.Newborn;
    EndangeredAnimal endangeredAnimal = new EndangeredAnimal();
    endangeredAnimal.setName(animalName);
    endangeredAnimal.setHealth(animalHealth);
    endangeredAnimal.setAge(animalAge);
    Assert.assertEquals(endangeredAnimal.getName(), animalName);
    Assert.assertEquals(endangeredAnimal.getHealth(), animalHealth);
    Assert.assertEquals(endangeredAnimal.getAge(), animalAge);
  }

  @Test
  public void databaseAddAndGet() {
    String animalName = "Test Endangered Animal";
    AnimalHealth animalHealth = AnimalHealth.Ill;
    AnimalAge animalAge = AnimalAge.Newborn;
    int animalId = database.getDb().addEndangeredAnimal(animalName, animalHealth, animalAge);
    Animal animal = database.getDb().getAnimalById(animalId);
    Assert.assertTrue(animal instanceof EndangeredAnimal);
    EndangeredAnimal endangeredAnimal = (EndangeredAnimal) animal;
    Assert.assertEquals(endangeredAnimal.getName(), animalName);
    Assert.assertEquals(endangeredAnimal.getHealth(), animalHealth);
    Assert.assertEquals(endangeredAnimal.getAge(), animalAge);
  }

  @Test
  public void databaseGetList() {
    database.getDb().addEndangeredAnimal("Test Endangered Animal 1", AnimalHealth.Ok, AnimalAge.Adult);
    database.getDb().addNonEndangeredAnimal("Test Animal");
    database.getDb().addEndangeredAnimal("Test Endangered Animal 2", AnimalHealth.Ill, AnimalAge.Young);
    List<EndangeredAnimal> endangeredAnimals = database.getDb().getEndangeredAnimals();
    Assert.assertEquals(endangeredAnimals.size(), 2);
  }

  @Test
  public void databaseGetAllList() {
    database.getDb().addEndangeredAnimal("Test Endangered Animal 1", AnimalHealth.Ok, AnimalAge.Adult);
    database.getDb().addNonEndangeredAnimal("Test Animal");
    database.getDb().addEndangeredAnimal("Test Endangered Animal 2", AnimalHealth.Ill, AnimalAge.Young);
    List<Animal> animals = database.getDb().getAllAnimals();
    Assert.assertEquals(animals.size(), 3);
  }
}
