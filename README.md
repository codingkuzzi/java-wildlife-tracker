## Wildlife Tracker by Anna Kuznetsova, July 2017

An app for the forest service to track animals for an environmental impact study.

### Description

The Forest Service is considering a proposal from a timber company to clearcut a nearby forest of Douglas Fir. Before this proposal may be approved, they must complete an environmental impact study. This application was developed to allow Rangers to track wildlife sightings in the area.

### Setup

To create the necessary databases, launch postgres, then psql, and run the following commands:

* `CREATE DATABASE wildlife_tracker_2;`
* `\c wildlife_tracker_2;`
* `CREATE TABLE animals (id serial PRIMARY KEY, name varchar);`
* `CREATE TABLE endangered_animals (id serial PRIMARY KEY, name varchar, health varchar, age varchar);`
* `CREATE TABLE sightings (id serial PRIMARY KEY, animal_id int, location varchar, ranger_name varchar);`
* `CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;`



* _Clone the project from Github._
* _Create database "wildlife_tracker_2" on localhost._
* _Navigate to the project folder._ 
* _Create database tables with script media.sql._
* _On the terminal, type "gradlew run", then enter the following in the browser's URL bar: http://localhost:4567/._


### License

Copyright (c) 2017 **_MIT License_**
