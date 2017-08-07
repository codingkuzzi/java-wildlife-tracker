## Wildlife Tracker by Anna Kuznetsova, July 2017

An app for the forest service to track animals for an environmental impact study.

### Description

The Forest Service is considering a proposal from a timber company to clearcut a nearby forest of Douglas Fir. Before this proposal may be approved, they must complete an environmental impact study. This application was developed to allow Rangers to track wildlife sightings in the area.

* User can go to the homepage and see a list of possible actions: login (if he is in the list of rangers), see and report sightings, manage stations, locations, rangers and animals.
* User should be able to add a new station, new location, new ranger and new animal.
* User can view list of sightings, list of stations, list of locations, list of rangers and list of animals.
* User can go to the location's page and see all sightings reported in that region.
* User can go to the ranger's page and see his/her name, email, station and sightings he/she has reported.
* User can go to the sighting's page and see station'name, location's name, ranger's name, animal's name, time it was reported and image(if it was added).
* User can add animal to the list by adding name. If it's endangered, user should be able to add health and age parameters.
* User can report sighting by adding ranger(or selecting one from the list),location, selecting animal from the list(drop-down menu) and adding image url(to existing images on the internet).

### Changes made

* Added a parent class for the Animal and EndangeredAnimal to extend from.
* Added a timestamp to the sightings table and LocalDateTime type to sighting java class (for when the animal was last seen).
* Added a enum for health and age options to make them value safe.
* The sightings database was altered to fix an issue where an endangered animal with the same id as a regular animal would both be treated as the same animals.
* Added java classes for stations, locations, rangers and vtl template pages for them.

### Setup

To create the necessary databases, launch postgres, then psql, and run the following commands:

* _Clone the project from Github._
* _Create database "wildlife_tracker_2" on localhost._
* _Navigate to the project folder._ 
* _Create database tables with script media.sql._
* _On the terminal, type "gradlew run", then enter the following in the browser's URL bar: http://localhost:4567/._
* _Create database "wildlife_tracker_2_test" with template wildlife_tracker_2._


### License

Copyright (c) 2017 **_MIT License_**
