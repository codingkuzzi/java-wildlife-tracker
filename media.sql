CREATE TABLE animals (
  id            SERIAL PRIMARY KEY,
  name          VARCHAR NOT NULL,
  is_endangered BOOLEAN NOT NULL,
  health        VARCHAR,
  age           VARCHAR
);

CREATE TABLE stations (
  id   SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE locations (
  id         SERIAL PRIMARY KEY,
  name       VARCHAR NOT NULL,
  station_id INTEGER NOT NULL REFERENCES stations (id)
);

CREATE TABLE rangers (
  id         SERIAL PRIMARY KEY,
  lastname   VARCHAR NOT NULL,
  firstname  VARCHAR NOT NULL,
  email      VARCHAR NOT NULL,
  station_id INTEGER NOT NULL REFERENCES stations (id)
);

CREATE TABLE sightings (
  id          SERIAL PRIMARY KEY,
  animal_id   INTEGER   NOT NULL REFERENCES animals (id),
  ranger_id   INTEGER   NOT NULL REFERENCES rangers (id),
  location_id INTEGER   NOT NULL REFERENCES locations (id),
  time        TIMESTAMP NOT NULL,
  image       VARCHAR
);