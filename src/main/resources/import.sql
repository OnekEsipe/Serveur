INSERT INTO utilisateurs(droits, login, mail, motdepasse, nom, prenom, isdeleted) VALUES ('R', 'admin', 'admin@gmail.com', 'hash', 'Bob', 'L Eponge', false);

INSERT INTO utilisateurs(droits, login, mail, motdepasse, nom, prenom, isdeleted) VALUES ('J', 'rdrocourt', 'rodolphe@gmail.com', 'hash', 'Rodolphe', 'Drocourt', false);
	
INSERT INTO utilisateurs(droits, login, mail, motdepasse, nom, prenom, isdeleted) VALUES ('O', 'jplancqu', 'julien.plancqueel@hotmail.fr', 'julien', 'Julien', 'Plancqueel', false);

INSERT INTO evenements(datestart, datestop, isopened, issigned, nom, status, iduser, code, isdeleted) VALUES ('2018-02-07', '2018-02-08', true, true, 'nom1', 'brouillon', 1, 'code', false);

INSERT INTO criteres(categorie, coefficient, texte, idevent) VALUES ('cat1', 2, 'je suis un critere', 1);

INSERT INTO criteres(categorie, coefficient, texte, idevent) VALUES ('cat2', 4, 'je suis un critere 2', 1);

INSERT INTO descripteur(niveau, poids, texte, idcritere) VALUES (5, 0, 'je suis un descripteur', 1);

INSERT INTO descripteur(niveau, poids, texte, idcritere) VALUES (3, 0, 'je suis un descripteur 2', 1);

INSERT INTO candidats(nom, prenom, idevent) VALUES ('Poppi', 'Tora', 1);

INSERT INTO candidats(nom, prenom, idevent) VALUES ('Pyra', 'Mythra', 1);

INSERT INTO candidats(nom, prenom, idevent)	VALUES ('Rex', 'Nia', 1);

INSERT INTO jurys(idevent, iduser) VALUES (1, 1);

INSERT INTO jurys(idevent, iduser) VALUES (1, 2);

INSERT INTO evaluations(commentaire, datedernieremodif, signature, idcandidat, idjuryeval) VALUES ('je suis un commentaire', '2018-02-08', null, 2, 1);

INSERT INTO evaluations(commentaire, datedernieremodif, signature, idcandidat, idjuryeval) VALUES ('je suis un commentaire 2', '2018-02-08', null, 1, 2);

INSERT INTO evaluations(commentaire, datedernieremodif, signature, idcandidat, idjuryeval) VALUES ('je suis un commentaire 3', '2018-02-08', null, 3, 1);

INSERT INTO notes(commentaire, date, niveau, idcritere, idevaluation) VALUES ('je suis un commentaire', '2018-02-09', 1, 1, 1);