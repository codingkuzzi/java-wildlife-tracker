package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Sighting {

  private int id;
  private int animal_Id;
  private int ranger_Id;
  private int location_Id;
  private LocalDateTime time;
  private String image;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public int getAnimal_Id() {
    return animal_Id;
  }

  public void setAnimal_Id(int animal_Id) {
    this.animal_Id = animal_Id;
  }


  public int getRanger_Id() {
    return ranger_Id;
  }

  public void setRanger_Id(int ranger_Id) {
    this.ranger_Id = ranger_Id;
  }


  public int getLocation_Id() {
    return location_Id;
  }

  public void setLocation_Id(int location_Id) {
    this.location_Id = location_Id;
  }


  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public String getDisplayTime() {
    return time.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT));
  }


  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

}
