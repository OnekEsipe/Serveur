--
-- PostgreSQL database dump
--

-- Dumped from database version 10.1
-- Dumped by pg_dump version 10.1

-- Started on 2018-02-13 10:13:45

--
-- TOC entry 2877 (class 0 OID 17331)
-- Dependencies: 197
-- Data for Name: utilisateurs; Type: TABLE DATA; Schema: public; Owner: postgres
--
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Axel', 'Attila', 'aa', 'aa@gmail.com', 'O', 'e0c9035898dd52fc65c41454cec9c4d2611bfb37', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Beatrice', 'Boule', 'bb', 'bb@gmail.com', 'O', '9a900f538965a426994e1e90600920aff0b4e8d2', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Charles', 'Chaplin', 'cc', 'cc@gmail.com', 'R', 'bdb480de655aa6ec75ca058c849c4faf3c0f75b1', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Damien', 'Duper', 'dd', 'dd@gmail.com', 'J', 'dd', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Etienne', 'Edouard', 'ee', 'ee@gmail.com', 'J', '1f444844b1ca616009c2b0e3564fecc065872b5b', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Fabienne', 'Fabre', 'ff', 'ff@gmail.com', 'J', 'ed70c57d7564e994e7d5f6fd6967cea8b347efbc', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Gerard', 'Gabin', 'gg', 'gg@gmail.com', 'J', 'f3226f91f77a87d909b8920adc91f9a301a7316b', true);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Hubert', 'Hotte', 'hh', 'hh@gmail.com', 'J', 'd3fc13dc12d8d7a58e7ae87295e93dbaddb5d36b', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Isabelle', 'Irisma', 'ii', 'ii@gmail.com', 'J', '3918373cf5559c54b52c7066428f6c4118d31c23', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Julien', 'Jasmin', 'jj', 'jj@gmail.com', 'O', '7323a5431d1c31072983a6a5bf23745b655ddf59', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Kevin', 'Karin', 'kk', 'kk@gmail.com', 'O', '2ed45186c72f9319dc64338cdf16ab76b44cf3d1', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Lucie', 'Lion', 'll', 'll@gmail.com', 'A', '110c8a30c16070bf2813480d9492a1a170a7d80a', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Lucie2', 'Lion2', 'll2', 'll2@gmail.com', 'A', '110c8a30c16070bf2813480d9492a1a170a7d80b', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Manon', 'Mint', 'mm', 'mm@gmail.com', 'R', 'b8d09b4d8580aacbd9efc4540a9b88d2feb9d7e5', true);
INSERT INTO evenements (code, datestart, datestop, isdeleted, isopened, nom, signingneeded, status, iduser) VALUES ('1013902701', '2018-02-27 07:01:00', '2018-03-01 23:59:00', false, false, 'event1', true, 'Brouillon', 3);
INSERT INTO evenements (code, datestart, datestop, isdeleted, isopened, nom, signingneeded, status, iduser) VALUES ('1013902711', '2018-02-27 07:01:00', '2018-03-01 23:59:00', false, false, 'event2', true, 'Ouvert', 4);
INSERT INTO evenements (code, datestart, datestop, isdeleted, isopened, nom, signingneeded, status, iduser) VALUES ('1013902710', '2018-02-27 07:01:00', '2018-03-01 23:59:00', false, false, 'event3', true, 'Ouvert', 12);
INSERT INTO jurys (idevent,iduser) VALUES (1, 1);
INSERT INTO jurys (idevent,iduser) VALUES (1, 2);
INSERT INTO jurys (idevent,iduser) VALUES (1, 3);
INSERT INTO jurys (idevent,iduser) VALUES (3, 12);
INSERT INTO jurys (idevent,iduser) VALUES (2, 13);
INSERT INTO candidats (nom,prenom,idevent) VALUES ('Candidat1', '', 1);
INSERT INTO candidats (nom,prenom,idevent) VALUES ('Candidat2', 'candid2prenom', 1);
INSERT INTO candidats (nom,prenom,idevent) VALUES ('Candidat3', '', 1);
INSERT INTO criteres (categorie,coefficient,texte,idevent) VALUES ('', 1.00, 'critere1', 1);
INSERT INTO criteres (categorie,coefficient,texte,idevent) VALUES ('Oral', 2.00, 'critere2', 1);
INSERT INTO descripteurs (niveau,poids,texte,idcritere) VALUES ('A', 1.00, 'descripteur1', 1);
INSERT INTO descripteurs (niveau,poids,texte,idcritere) VALUES ('B', 2.00, 'descripteur2', 1);
INSERT INTO descripteurs (niveau,poids,texte,idcritere) VALUES ('C', 3.00, 'descripteur3', 1);
INSERT INTO descripteurs (niveau,poids,texte,idcritere) VALUES ('A', 1.00, 'descripteur1', 2);
INSERT INTO descripteurs (niveau,poids,texte,idcritere) VALUES ('B', 1.00, 'descripteur2', 2);
INSERT INTO evaluations (commentaire,datedernieremodif,issigned,idcandidat,idjuryeval) VALUES ('', '2018-02-27 12:23:10.853', false, 3, 1);
INSERT INTO evaluations (commentaire,datedernieremodif,issigned,idcandidat,idjuryeval) VALUES ('', '2018-02-27 12:23:10.853', false, 1, 2);
INSERT INTO evaluations (commentaire,datedernieremodif,issigned,idcandidat,idjuryeval) VALUES ('', '2018-02-27 12:23:10.853', false, 2, 3);
INSERT INTO notes (commentaire,date,niveau,idcritere,idevaluation) VALUES ('', '2018-02-27 12:23:10.853', 10, 1, 1);
INSERT INTO notes (commentaire,date,niveau,idcritere,idevaluation) VALUES ('', '2018-02-27 12:23:10.853', 10, 2, 1);
INSERT INTO notes (commentaire,date,niveau,idcritere,idevaluation) VALUES ('', '2018-02-27 12:23:10.853', 10, 1, 2);
INSERT INTO notes (commentaire,date,niveau,idcritere,idevaluation) VALUES ('', '2018-02-27 12:23:10.853', 10, 2, 2);
INSERT INTO notes (commentaire,date,niveau,idcritere,idevaluation) VALUES ('', '2018-02-27 12:23:10.853', 10, 1, 3);
INSERT INTO notes (commentaire,date,niveau,idcritere,idevaluation) VALUES ('', '2018-02-27 12:23:10.853', 10, 2, 3);








