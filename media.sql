CREATE TABLE animals (
  id           SERIAL PRIMARY KEY,
  name         VARCHAR NOT NULL,
  isEndangered BOOLEAN NOT NULL
);

CREATE TABLE locations (
  id   SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE stations (
  id          SERIAL PRIMARY KEY,
  name        VARCHAR NOT NULL,
  location_id INTEGER NOT NULL REFERENCES locations (id)
);

CREATE TABLE rangers (
  id         SERIAL PRIMARY KEY,
  name       VARCHAR NOT NULL,
  station_id INTEGER NOT NULL REFERENCES stations (id)
);

CREATE TABLE sightings (
  id          SERIAL PRIMARY KEY,
  animal_id   INTEGER   NOT NULL REFERENCES animals (id),
  health      VARCHAR,
  age         VARCHAR,
  ranger_id   INTEGER   NOT NULL REFERENCES rangers (id),
  location_id INTEGER   NOT NULL REFERENCES locations (id),
  time        TIMESTAMP NOT NULL,
  image       VARCHAR
);