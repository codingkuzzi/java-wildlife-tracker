package model;


public class Ranger {

  private int id;
  private int station_Id;
  private String firstName;
  private String lastName;
  private String email;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public int getStation_Id() {
    return station_Id;
  }

  public void setStation_Id(int station_Id) {
    this.station_Id = station_Id;
  }


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDisplayName() {
    return String.format("%s, %s", getLastName(), getFirstName());
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
