## Wildlife Tracker by Anna Kuznetsova, July 2017

An app for the forest service to track animals for an environmental impact study.

### Description

The Forest Service is considering a proposal from a timber company to clearcut a nearby forest of Douglas Fir. Before this proposal may be approved, they must complete an environmental impact study. This application was developed to allow Rangers to track wildlife sightings in the area.

* User can go to the homepage and see a list of possible actions: login (if he is in the list of rangers), see and report sightings, manage stations, locations, rangers and animals.
* User should be able to add a new station, new location, new ranger and new animal.
* User can view list of sightings, list of stations, list of locations, list of rangers and list of animals.
* User can add animal to the list by adding name. If it's endangered, user should be able to add health and age parameters.
* User can report sighting by adding ranger(or selecting one from the list),location, selecting animal from the list and adding image url.


### Setup

To create the necessary databases, launch postgres, then psql, and run the following commands:

* _Clone the project from Github._
* _Create database "wildlife_tracker_2" on localhost._
* _Navigate to the project folder._ 
* _Create database tables with script media.sql._
* _On the terminal, type "gradlew run", then enter the following in the browser's URL bar: http://localhost:4567/._






### License

Copyright (c) 2017 **_MIT License_**
