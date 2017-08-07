import model.Animal;
import model.enums.AnimalAge;
import model.enums.AnimalHealth;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import java.util.List;

public class AnimalTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void objectProperties() {
    String animalName = "Test Animal";
    Animal animal = new Animal();
    animal.setName(animalName);
    Assert.assertEquals(animal.getName(), animalName);
  }

  @Test
  public void databaseAddAndGet() {
    String animalName = "Test Animal";
    int animalId = database.getDb().addNonEndangeredAnimal(animalName);
    Animal animal = database.getDb().getAnimalById(animalId);
    Assert.assertEquals(animal.getName(), animalName);
  }

  @Test
  public void databaseGetList() {
    database.getDb().addNonEndangeredAnimal("Test Animal 1");
    database.getDb().addEndangeredAnimal("Test Endangered Animal", AnimalHealth.Ok, AnimalAge.Adult);
    database.getDb().addNonEndangeredAnimal("Test Animal 2");
    List<Animal> animals = database.getDb().getNonEndangeredAnimals();
    Assert.assertEquals(animals.size(), 2);
  }

  @Test
  public void databaseGetWrongId() {
    int wrongId = Integer.MAX_VALUE;
    Animal animal = database.getDb().getAnimalById(wrongId);
    Assert.assertNull(animal);
  }
}
