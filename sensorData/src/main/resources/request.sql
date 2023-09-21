DROP TABLE IF EXISTS sensors CASCADE;

CREATE TABLE IF NOT EXISTS sensors(
    id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name varchar(30) NOT NULL
);

SELECT *
FROM sensors;

DROP TABLE IF EXISTS sensor_data CASCADE;

CREATE TABLE IF NOT EXISTS sensor_data(
    id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    value FLOAT NOT NULL,
    raining BOOLEAN NOT NULL,
    sensor_id INT REFERENCES sensors (id) ON DELETE CASCADE
);

INSERT INTO sensor_data(value, raining, sensor_id)
VALUES (20.3, true, 1);

SELECT *
FROM sensor_data;