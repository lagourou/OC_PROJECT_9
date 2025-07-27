CREATE TABLE IF NOT EXISTS patients (

    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_de_naissance DATE NOT NULL,
    genre VARCHAR(1),
    adresse VARCHAR(255),
    telephone VARCHAR(50)
);

