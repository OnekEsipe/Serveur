INSERT INTO utilisateurs(iduser, isanonym, droits, login, mail, motdepasse, nom, prenom) VALUES (1, false, 'R', 'admin', 'admin@gmail.com', 'hash', 'Bob', 'L Eponge');

INSERT INTO utilisateurs(iduser, isanonym, droits, login, mail, motdepasse, nom, prenom) VALUES (2, false, 'J', 'rdrocourt', 'rodolphe@gmail.com', 'hash', 'Rodolphe', 'Drocourt');
	
INSERT INTO utilisateurs(iduser, isanonym, droits, login, mail, motdepasse, nom, prenom) VALUES (3, false, 'O', 'jplancqu', 'julien.plancqueel@hotmail.fr', 'julien', 'Julien', 'Plancqueel');

INSERT INTO evenements(idevent, datestart, datestop, isopened, issigned, nom, status, iduser, code) VALUES (1, '2018-02-07', '2018-02-08', true, true, 'nom1', 'brouillon', 1, 'code');

INSERT INTO criteres(idcritere, categorie, coefficient, texte, idevent) VALUES (1, 'cat1', 2, 'je suis un critere', 1);

INSERT INTO criteres(idcritere, categorie, coefficient, texte, idevent) VALUES (2, 'cat2', 4, 'je suis un critere 2', 1);

INSERT INTO descripteur(iddescripteur, niveau, poids, texte, idcritere) VALUES (1, 5, 0, 'je suis un descripteur', 1);

INSERT INTO descripteur(iddescripteur, niveau, poids, texte, idcritere) VALUES (2, 3, 0, 'je suis un descripteur 2', 1);

INSERT INTO candidats(idcandidat, nom, prenom, idevent) VALUES (1, 'Poppi', 'Tora', 1);

INSERT INTO candidats(idcandidat, nom, prenom, idevent) VALUES (2, 'Pyra', 'Mythra', 1);

INSERT INTO candidats(idcandidat, nom, prenom, idevent)	VALUES (3, 'Rex', 'Nia', 1);

INSERT INTO jurys(idjury, idevent, iduser) VALUES (1, 1, 1);

INSERT INTO jurys(idjury, idevent, iduser) VALUES (2, 1, 2);

INSERT INTO evaluations(idevaluation, commentaire, datedernieremodif, signature, idcandidat, idjuryeval) VALUES (1, 'je suis un commentaire', '2018-02-08', null, 2, 1);

INSERT INTO evaluations(idevaluation, commentaire, datedernieremodif, signature, idcandidat, idjuryeval) VALUES (2, 'je suis un commentaire 2', '2018-02-08', null, 1, 2);

INSERT INTO evaluations(idevaluation, commentaire, datedernieremodif, signature, idcandidat, idjuryeval) VALUES (3, 'je suis un commentaire 3', '2018-02-08', null, 3, 1);

INSERT INTO notes(idnote, commentaire, date, niveau, idcritere, idevaluation) VALUES (1, 'je suis un commentaire', '2018-02-09', 1, 1, 1);

