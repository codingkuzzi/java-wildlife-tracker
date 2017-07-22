CREATE TABLE animals (
    id serial PRIMARY KEY,
    name character varying
);

CREATE TABLE endangered_animals (
    id serial PRIMARY KEY,
    name character varying,
    health character varying,
    age character varying
);

CREATE TABLE sightings (
    id serial PRIMARY KEY,
    animal_id integer REFERENCES animals(id),
    location character varying,
    ranger_name character varying

);

