INSERT INTO patients (nom, prenom, date_de_naissance, genre, adresse, telephone)
SELECT 'TestNone', 'Test', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'
WHERE NOT EXISTS (
  SELECT 1 FROM patients WHERE nom = 'TestNone' AND prenom = 'Test' AND date_de_naissance = '1966-12-31'
);

INSERT INTO patients (nom, prenom, date_de_naissance, genre, adresse, telephone)
SELECT 'TestBorderline', 'Test', '1945-06-24', 'M', '2 High St', '200-333-4444'
WHERE NOT EXISTS (
  SELECT 1 FROM patients WHERE nom = 'TestBorderline' AND prenom = 'Test' AND date_de_naissance = '1945-06-24'
);

INSERT INTO patients (nom, prenom, date_de_naissance, genre, adresse, telephone)
SELECT 'TestInDanger', 'Test', '2004-06-18', 'M', '3 Club Road', '300-444-5555'
WHERE NOT EXISTS (
  SELECT 1 FROM patients WHERE nom = 'TestInDanger' AND prenom = 'Test' AND date_de_naissance = '2004-06-18'
);

INSERT INTO patients (nom, prenom, date_de_naissance, genre, adresse, telephone)
SELECT 'TestEarlyOnset', 'Test', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666'
WHERE NOT EXISTS (
  SELECT 1 FROM patients WHERE nom = 'TestEarlyOnset' AND prenom = 'Test' AND date_de_naissance = '2002-06-28'
);
