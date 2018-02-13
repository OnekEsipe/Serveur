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
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Axel', 'Attila', 'aa', 'aa@gmail.com', 'O', 'aa', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Beatrice', 'Boule', 'bb', 'bb@gmail.com', 'O', 'bb', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Charles', 'Chaplin', 'cc', 'cc@gmail.com', 'R', 'cc', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Damien', 'Duper', 'dd', 'dd@gmail.com', 'J', '388ad1c312a488ee9e12998fe097f2258fa8d5ee', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Etienne', 'Edouard', 'ee', 'ee@gmail.com', 'J', 'ee', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Fabienne', 'Fabre', 'ff', 'ff@gmail.com', 'J', 'ff', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Gerard', 'Gabin', 'gg', 'gg@gmail.com', 'J', 'gg', true);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Hubert', 'Hotte', 'hh', 'hh@gmail.com', 'J', 'hh', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Isabelle', 'Irisma', 'ii', 'ii@gmail.com', 'J', 'ii', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Julien', 'Jasmin', 'jj', 'jj@gmail.com', 'O', 'jj', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Kevin', 'Karin', 'kk', 'kk@gmail.com', 'O', 'kk', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Lucie', 'Lion', 'll', 'll@gmail.com', 'A', 'll', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Manon', 'Mint', 'mm', 'mm@gmail.com', 'R', 'mm', true);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Jury01_01', NULL, 'jury_01_01', NULL, 'A', 'ABCD1234', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Jury02_01', NULL, 'jury_02_01', NULL, 'A', 'ABCD1234', true);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Jury01_02', NULL, 'jury_01_02', NULL, 'A', 'ABCD1234', false);

--
-- TOC entry 2879 (class 0 OID 17344)
-- Dependencies: 199
-- Data for Name: evenements; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 1', '2017-02-15', '2017-02-16', 'Ouvert', 'Code1', false, true, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 2', '2017-02-16', '2017-02-17', 'Ouvert', 'Code2', false, true, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 3', '2017-02-17', '2017-02-18', 'Ouvert', 'Code3', false, true, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 4', '2017-02-17', '2017-02-18', 'Ouvert', 'Code4', false, true, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 5', '2017-02-17', '2017-02-18', 'Ouvert', 'Code 5', true, false, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 6', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 6', true, false, 1, true);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 7', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 7', false, false, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 8', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 8', false, false, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 9', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 9', false, false, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 10', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 10', false, false, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 11', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 11', false, false, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 12', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 12', false, false, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 13', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 13', false, false, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 14', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 14', false, false, 1, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 15', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 15', false, false, 2, false);
INSERT INTO evenements (nom, datestart, datestop, status, code, issigned, isopened, iduser, isdeleted) VALUES ('Olympiade 16', '2017-02-20', '2017-02-22', 'Ouvert', 'Code 16', false, false, 2, false);

--
-- TOC entry 2883 (class 0 OID 17372)
-- Dependencies: 203
-- Data for Name: jurys; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO jurys (iduser, idevent) VALUES (4, 1);
INSERT INTO jurys (iduser, idevent) VALUES (4, 2);
INSERT INTO jurys (iduser, idevent) VALUES (4, 3);
INSERT INTO jurys (iduser, idevent) VALUES (4, 4);
INSERT INTO jurys (iduser, idevent) VALUES (4, 5);
INSERT INTO jurys (iduser, idevent) VALUES (4, 6);
INSERT INTO jurys (iduser, idevent) VALUES (5, 1);
INSERT INTO jurys (iduser, idevent) VALUES (5, 2);
INSERT INTO jurys (iduser, idevent) VALUES (5, 3);
INSERT INTO jurys (iduser, idevent) VALUES (5, 4);
INSERT INTO jurys (iduser, idevent) VALUES (5, 5);
INSERT INTO jurys (iduser, idevent) VALUES (5, 6);
INSERT INTO jurys (iduser, idevent) VALUES (6, 4);
INSERT INTO jurys (iduser, idevent) VALUES (6, 5);
INSERT INTO jurys (iduser, idevent) VALUES (6, 6);
INSERT INTO jurys (iduser, idevent) VALUES (6, 1);
INSERT INTO jurys (iduser, idevent) VALUES (6, 2);
INSERT INTO jurys (iduser, idevent) VALUES (6, 3);
INSERT INTO jurys (iduser, idevent) VALUES (4, 7);
INSERT INTO jurys (iduser, idevent) VALUES (4, 8);
INSERT INTO jurys (iduser, idevent) VALUES (4, 9);
INSERT INTO jurys (iduser, idevent) VALUES (4, 10);
INSERT INTO jurys (iduser, idevent) VALUES (4, 11);
INSERT INTO jurys (iduser, idevent) VALUES (4, 12);

--
-- TOC entry 2881 (class 0 OID 17359)
-- Dependencies: 201
-- Data for Name: candidats; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO candidats (nom, prenom, idevent) VALUES ('Hugo', 'Fourcade', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Vincent', 'Leman', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Medalie', 'Noubigh', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Yanis', 'Salah', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Etienne', 'Jannot', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Florie', 'Monnier', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Benjamin', 'Gonzales', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Paul', 'Ochon', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Gregoire', 'Duhail', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Kevin', 'Mernissi', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Antoine', 'Ganthier', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Rodolphe', 'Drocourt', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Kevin', 'Mernissi', 2);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Antoine', 'Ganthier', 2);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Rodolphe', 'Drocourt', 2);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Thibault', 'Outerovitch', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Julien', 'Brossard', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Axel', 'Rolo', 1);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Fabienne', 'Heimburger', 1);


--
-- TOC entry 2887 (class 0 OID 17411)
-- Dependencies: 207
-- Data for Name: criteres; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO criteres (texte, categorie, coefficient, idevent) VALUES ('Aisance et Presence', 'Oral', 5, 1);
INSERT INTO criteres (texte, categorie, coefficient, idevent) VALUES ('Qualite du Powerpoint', 'Oral', 3, 1);
INSERT INTO criteres (texte, categorie, coefficient, idevent) VALUES ('Travail effectue', NULL, 3, 11);
INSERT INTO criteres (texte, categorie, coefficient, idevent) VALUES ('Contenu', 'Ecrit', 5, 2);
INSERT INTO criteres (texte, categorie, coefficient, idevent) VALUES ('Redaction', 'Ecrit', 4, 2);


--
-- TOC entry 2891 (class 0 OID 17448)
-- Dependencies: 211
-- Data for Name: descripteur; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('A', 'Tres bon niveau', 8, 1);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('B', 'Niveau acceptable', 6, 1);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('C', 'Niveau trop juste', 4, 1);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('D', 'Travail necessaire', 2, 1);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('B', 'Entre 5 et 20 erreurs', 4, 2);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('A', 'Moins de 5 erreurs', 5, 2);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('C', 'Plus de 20 erreurs', 2, 2);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('B', 'Bien', 3, 5);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('A', 'Tres bien', 4, 5);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('C', 'Mauvais', 1, 5);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('A', 'Tres bien', 3, 4);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('C', 'Mauvais', 1, 4);
INSERT INTO descripteur (niveau, texte, poids, idcritere) VALUES ('B', 'Bien', 2, 4);


--
-- TOC entry 2885 (class 0 OID 17390)
-- Dependencies: 205
-- Data for Name: evaluations; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO evaluations (datedernieremodif, signature, commentaire, idjuryeval, idcandidat) VALUES ('2018-02-12', NULL, '', 16, 2);
INSERT INTO evaluations (datedernieremodif, signature, commentaire, idjuryeval, idcandidat) VALUES ('2018-02-12', NULL, '', 17, 2);
INSERT INTO evaluations (datedernieremodif, signature, commentaire, idjuryeval, idcandidat) VALUES ('2018-02-12', NULL, '', 18, 2);
INSERT INTO evaluations (datedernieremodif, signature, commentaire, idjuryeval, idcandidat) VALUES ('2018-02-12', NULL, '', 18, 8);
INSERT INTO evaluations (datedernieremodif, signature, commentaire, idjuryeval, idcandidat) VALUES ('2018-02-12', NULL, '', 16, 8);
INSERT INTO evaluations (datedernieremodif, signature, commentaire, idjuryeval, idcandidat) VALUES ('2018-02-12', NULL, '', 17, 8);
INSERT INTO evaluations (datedernieremodif, signature, commentaire, idjuryeval, idcandidat) VALUES ('2018-02-12', NULL, '', 17, 14);
INSERT INTO evaluations (datedernieremodif, signature, commentaire, idjuryeval, idcandidat) VALUES ('2018-02-12', NULL, '', 16, 14);
INSERT INTO evaluations (datedernieremodif, signature, commentaire, idjuryeval, idcandidat) VALUES ('2018-02-12', NULL, '', 18, 14);

--
-- TOC entry 2889 (class 0 OID 17427)
-- Dependencies: 209
-- Data for Name: notes; Type: TABLE DATA; Schema: public; Owner: postgres
--


-- Completed on 2018-02-13 10:13:46

--
-- PostgreSQL database dump complete
--