package model;


import model.enums.AnimalAge;
import model.enums.AnimalHealth;

public class EndangeredAnimal extends Animal {

  private AnimalHealth health;
  private AnimalAge age;

  public AnimalHealth getHealth() {
    return health;
  }

  public void setHealth(AnimalHealth health) {
    this.health = health;
  }


  public AnimalAge getAge() {
    return age;
  }

  public void setAge(AnimalAge age) {
    this.age = age;
  }

  @Override
  public String getDisplayName() {
    return String.format("%s, %s, %s", getName(), getHealth(), getAge());
  }
}
