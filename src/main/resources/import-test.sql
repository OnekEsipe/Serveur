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
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Damien', 'Duper', 'dd', 'dd@gmail.com', 'J', '388ad1c312a488ee9e12998fe097f2258fa8d5ee', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Etienne', 'Edouard', 'ee', 'ee@gmail.com', 'J', '1f444844b1ca616009c2b0e3564fecc065872b5b', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Fabienne', 'Fabre', 'ff', 'ff@gmail.com', 'J', 'ed70c57d7564e994e7d5f6fd6967cea8b347efbc', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Gerard', 'Gabin', 'gg', 'gg@gmail.com', 'J', 'f3226f91f77a87d909b8920adc91f9a301a7316b', true);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Hubert', 'Hotte', 'hh', 'hh@gmail.com', 'J', 'd3fc13dc12d8d7a58e7ae87295e93dbaddb5d36b', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Isabelle', 'Irisma', 'ii', 'ii@gmail.com', 'J', '3918373cf5559c54b52c7066428f6c4118d31c23', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Julien', 'Jasmin', 'jj', 'jj@gmail.com', 'O', '7323a5431d1c31072983a6a5bf23745b655ddf59', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Kevin', 'Karin', 'kk', 'kk@gmail.com', 'O', '2ed45186c72f9319dc64338cdf16ab76b44cf3d1', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Lucie', 'Lion', 'll', 'll@gmail.com', 'A', '110c8a30c16070bf2813480d9492a1a170a7d80a', false);
INSERT INTO utilisateurs (nom, prenom, login, mail, droits, motdepasse, isdeleted) VALUES ('Manon', 'Mint', 'mm', 'mm@gmail.com', 'R', 'b8d09b4d8580aacbd9efc4540a9b88d2feb9d7e5', true);
INSERT INTO candidats (nom, prenom, idevent) VALUES ('Tamela', 'Hamed', '1',);
INSERT INTO evenements (nom) VALUES ('event 1');