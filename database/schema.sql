-- Schema minimal utilise par EleveDao ; aucune suppression de donnees.
CREATE TABLE IF NOT EXISTS eleve (
  num VARCHAR(255) NOT NULL PRIMARY KEY,
  no INT NULL,
  nom VARCHAR(255) NOT NULL,
  age INT NOT NULL,
  adresse VARCHAR(1000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
