-- phpMyAdmin SQL Dump
-- version 4.9.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Apr 24, 2020 at 09:22 PM
-- Server version: 5.7.26
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `prevention31`
--

-- --------------------------------------------------------

--
-- Table structure for table `Chambre`
--

CREATE TABLE `Chambre` (
  `nom_chambre` enum('CMA','CA','CCI') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Chambre`
--

INSERT INTO `Chambre` (`nom_chambre`) VALUES
('CMA'),
('CA'),
('CCI');

-- --------------------------------------------------------

--
-- Table structure for table `CodeActivite`
--

CREATE TABLE `CodeActivite` (
  `code` smallint(5) UNSIGNED NOT NULL,
  `activité` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `CodeActivite`
--

INSERT INTO `CodeActivite` (`code`, `activité`) VALUES
(11, 'Culture'),
(12, 'Elevage'),
(20, 'Exploitation forestière'),
(30, 'Pèche'),
(50, 'Extraction'),
(101, 'Fabrication à base de viande'),
(102, 'Fabrication alimentaire'),
(110, 'Fabrication de boisson'),
(120, 'Fabrication de tabac'),
(130, 'Fabrication à base de textiles'),
(140, 'Industrie de l\'habillement fabrication'),
(170, 'Fabrication à base de papier ou carton'),
(180, 'Imprimerie entreprise de reproduction'),
(190, ' Rafinage du pétrol'),
(200, 'Fabrication à base de produit chimique'),
(210, 'Industrie pharmaceutique'),
(220, 'Fabrication à base de plastique ou caoutchouc'),
(230, 'Fabrication de produit minéraux non métalique'),
(240, 'Métallurgie'),
(250, 'Fabrication de produit métalliques'),
(260, 'Fabrication de produits informatiques ou électronique et optique'),
(270, 'Fabrication d’équipement électrique'),
(280, 'Fabrication de machine'),
(290, 'Fabrication d’automobile ou produits en lien avec l’automobile'),
(300, 'Fabrication de véhicules hors automobile'),
(310, 'Fabrication de meubles'),
(320, 'Fabrications diverses – monnaie bijoux jeux articles de sport lunettes ...'),
(330, 'Réparation'),
(350, 'Production d’énergie'),
(360, 'Captation et distribution de l’eau'),
(370, 'Traitement des eaux usés'),
(380, 'Collecte et traitement des déchets'),
(410, 'Construction de bâtiments'),
(420, 'Construction de génie civil'),
(430, 'Travaux de construction'),
(450, 'Commerce d’automobiles et moto'),
(460, 'Commerce de gros'),
(470, 'Commerce de détail hyper supermarché grandes surface'),
(471, 'Commerce de détail boulangerie pâtisserie'),
(472, 'Commerce de détail restaurant'),
(473, 'Commerce de détail opticien'),
(474, 'Commerce de détail tabac presse'),
(475, 'Commerce de détail bar'),
(476, 'Commerce de détail pharmacie'),
(477, 'Commerce de détail fleuristes'),
(478, 'Commerce de détail parfumerie'),
(479, 'Commerce de détail viande'),
(480, 'Commerce de détail fruits et légumes'),
(481, 'Commerce de détail de carburant'),
(482, 'Commerce de détail animalerie'),
(490, 'Transport ferroviaire'),
(491, 'Transport routier taxis'),
(492, 'Transport routier camion'),
(493, 'Transport routier déménageur'),
(500, 'Transport par l’eau'),
(510, 'Transport aérien'),
(530, 'Activité liée à la poste'),
(540, 'Hébergement Hôtel'),
(541, 'Hébergement Camping'),
(542, 'Hébergement Privé'),
(560, 'Restauration traditionnelle à table'),
(561, 'Restauration rapide à emporter'),
(562, 'Restauration collectives'),
(563, 'Traiteurs'),
(580, 'Édition de livres ou journaux'),
(581, 'Édition de logiciel outil développement ou applicatif'),
(582, 'Édition de jeux'),
(590, 'Production de films'),
(600, 'Production et ou diffusion radio'),
(610, 'Télécommunication'),
(620, 'Conseils et maintenance en informatique'),
(630, 'Services d’information'),
(640, 'Activités bancaire'),
(650, 'Assurance'),
(660, 'Activités auxiliaires finances ou assurances'),
(680, 'Activité immobilière'),
(690, 'Activité juridique'),
(700, 'Conseil en gestion'),
(710, 'Activité architecture géomètre'),
(720, 'Recherche et développement'),
(730, 'Agence de publicités'),
(740, 'Design photographe interprète'),
(750, 'Vétérinaires'),
(770, 'Location de camion'),
(771, 'Location de voiture'),
(772, 'Location d’engin agricole'),
(780, 'Activité recherche d’emploi travail temporaire'),
(790, 'Agence de voyage'),
(800, 'Activité de sécurité privé'),
(810, 'Activité de nettoyage'),
(820, 'Activité de soutien aux entreprises'),
(840, 'Administration publique'),
(850, 'Enseignement primaire et maternel'),
(851, 'Enseignement secondaire'),
(852, 'Enseignement supérieur'),
(853, 'Enseignement conduite'),
(860, 'Hôpital clinique'),
(861, 'Cabinet médical médecin'),
(862, 'Dentiste'),
(863, 'Ambulances'),
(864, 'Radiologie'),
(865, 'Laboratoires d’analyse médicales'),
(866, 'Infirmier'),
(870, 'Maison de retraite EHPAD'),
(871, 'Hébergement social pour personnes handicapées'),
(900, 'Activité artistique'),
(910, 'Musé site historique ou touristique'),
(930, 'Club de sport'),
(960, 'CoiffeuR'),
(961, 'Salon de beauté'),
(962, 'Pompes funèbres'),
(970, 'Activités en lien avec le ménage');

-- --------------------------------------------------------

--
-- Table structure for table `Commune`
--

CREATE TABLE `Commune` (
  `id_commune` int(10) UNSIGNED NOT NULL,
  `codePostal` int(5) UNSIGNED NOT NULL,
  `commune` varchar(27) DEFAULT NULL,
  `secteur` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Commune`
--

INSERT INTO `Commune` (`id_commune`, `codePostal`, `commune`, `secteur`) VALUES
(1, 31000, 'TOULOUSE', 7),
(2, 31100, 'TOULOUSE', 7),
(3, 31200, 'TOULOUSE', 7),
(4, 31300, 'TOULOUSE', 7),
(5, 31400, 'TOULOUSE', 7),
(6, 31500, 'TOULOUSE', 7),
(7, 31110, 'ANTIGNAC', 6),
(8, 31110, 'ARTIGUE', 6),
(9, 31110, 'BAGNERES-DE-LUCHON', 6),
(10, 31110, 'BENQUE-DESSOUS-ET-DESSUS', 6),
(11, 31110, 'BILLIERE', 6),
(12, 31110, 'BOURG-D\'OUEIL', 6),
(13, 31110, 'CASTILLON-DE-LARBOUST', 6),
(14, 31110, 'CATHERVIELLE', 6),
(15, 31110, 'CAUBOUS', 6),
(16, 31110, 'CAZARIL-LASPENES', 6),
(17, 31110, 'CAZEAUX-DE-LARBOUST', 6),
(18, 31110, 'CIER-DE-LUCHON', 6),
(19, 31110, 'CIRES', 6),
(20, 31110, 'GARIN', 6),
(21, 31110, 'GOUAUX-DE-LARBOUST', 6),
(22, 31110, 'GOUAUX-DE-LUCHON', 6),
(23, 31110, 'JURVIELLE', 6),
(24, 31110, 'JUZET-DE-LUCHON', 6),
(25, 31110, 'MAYREGNE', 6),
(26, 31110, 'MONTAUBAN-DE-LUCHON', 6),
(27, 31110, 'MOUSTAJON', 6),
(28, 31110, 'OO', 6),
(29, 31110, 'PORTET-DE-LUCHON', 6),
(30, 31110, 'POUBEAU', 6),
(31, 31110, 'SACCOURVIELLE', 6),
(32, 31110, 'SAINT-AVENTIN', 6),
(33, 31110, 'SAINT-MAMET', 6),
(34, 31110, 'SAINT-PAUL-D\'OUEIL', 6),
(35, 31110, 'SALLES-ET-PRATVIEL', 6),
(36, 31110, 'SODE', 6),
(37, 31110, 'TREBONS-DE-LUCHON', 6),
(38, 31120, 'GOYRANS', 5),
(39, 31120, 'LACROIX-FALGARDE', 5),
(40, 31120, 'PINSAGUEL', 5),
(41, 31120, 'PORTET-SUR-GARONNE', 5),
(42, 31120, 'ROQUES', 5),
(43, 31120, 'ROQUETTES', 5),
(44, 31130, 'BALMA', 4),
(45, 31130, 'FLOURENS', 4),
(46, 31130, 'PIN-BALMA', 4),
(47, 31130, 'QUINT-FONSEGRIVES', 4),
(48, 31140, 'AUCAMVILLE', 2),
(49, 31140, 'FONBEAUZARD', 2),
(50, 31140, 'LAUNAGUET', 2),
(51, 31140, 'MONTBERON', 2),
(52, 31140, 'PECHBONNIEU', 2),
(53, 31140, 'SAINT-ALBAN', 2),
(54, 31140, 'SAINT-LOUP-CAMMAS', 2),
(55, 31150, 'BRUGUIERES', 2),
(56, 31150, 'FENOUILLET', 2),
(57, 31150, 'GAGNAC-SUR-GARONNE', 2),
(58, 31150, 'GRATENTOUR', 2),
(59, 31150, 'LESPINASSE', 2),
(60, 31160, 'ARBAS', 6),
(61, 31160, 'ARBON', 6),
(62, 31160, 'ARGUENOS', 6),
(63, 31160, 'ASPET', 6),
(64, 31160, 'CABANAC-CAZAUX', 6),
(65, 31160, 'CASTELBIAGUE', 6),
(66, 31160, 'CAZAUNOUS', 6),
(67, 31160, 'CHEIN-DESSUS', 6),
(68, 31160, 'COURET', 6),
(69, 31160, 'ENCAUSSE-LES-THERMES', 6),
(70, 31160, 'ESTADENS', 6),
(71, 31160, 'FOUGARON', 6),
(72, 31160, 'GANTIES', 6),
(73, 31160, 'HERRAN', 6),
(74, 31160, 'IZAUT-DE-L\'HOTEL', 6),
(75, 31160, 'JUZET-D\'IZAUT', 6),
(76, 31160, 'LESPITEAU', 6),
(77, 31160, 'MILHAS', 6),
(78, 31160, 'MONCAUP', 6),
(79, 31160, 'MONTASTRUC-DE-SALIES', 6),
(80, 31160, 'PORTET-D\'ASPET', 6),
(81, 31160, 'RAZECUEILLE', 6),
(82, 31160, 'ROUEDE', 6),
(83, 31160, 'SENGOUAGNET', 6),
(84, 31160, 'SOUEICH', 6),
(85, 31170, 'TOURNEFEUILLE', 7),
(86, 31180, 'CASTELMAUROU', 2),
(87, 31180, 'LAPEYROUSE-FOSSAT', 2),
(88, 31180, 'ROUFFIAC-TOLOSAN', 2),
(89, 31180, 'SAINT-GENIES-BELLEVUE', 2),
(90, 31190, 'AURAGNE', 5),
(91, 31190, 'AURIBAIL', 5),
(92, 31190, 'AUTERIVE', 5),
(93, 31190, 'CAUJAC', 5),
(94, 31190, 'ESPERCE', 5),
(95, 31190, 'GRAZAC', 5),
(96, 31190, 'GREPIAC', 5),
(97, 31190, 'LABRUYERE-DORSA', 5),
(98, 31190, 'LAGRACE-DIEU', 5),
(99, 31190, 'MAURESSAC', 5),
(100, 31190, 'MAUVAISIN', 5),
(101, 31190, 'MIREMONT', 5),
(102, 31190, 'PUYDANIEL', 5),
(103, 31210, 'ARDIEGE', 6),
(104, 31210, 'AUSSON', 6),
(105, 31210, 'BORDES-DE-RIVIERE', 6),
(106, 31210, 'CLARAC', 6),
(107, 31210, 'CUGURON', 3),
(108, 31210, 'LE CUING', 3),
(109, 31210, 'FRANQUEVIELLE', 3),
(110, 31210, 'GOURDAN-POLIGNAN', 6),
(111, 31210, 'HUOS', 6),
(112, 31210, 'MARTRES-DE-RIVIERE', 6),
(113, 31210, 'MONTREJEAU', 6),
(114, 31210, 'POINTIS-DE-RIVIERE', 6),
(115, 31210, 'PONLAT-TAILLEBOURG', 6),
(116, 31210, 'LES TOURREILLES', 3),
(117, 31220, 'CAZERES', 5),
(118, 31220, 'COULADERE', 5),
(119, 31220, 'LAVELANET-DE-COMMINGES', 5),
(120, 31220, 'LESCUNS', 3),
(121, 31220, 'MARIGNAC-LASPEYRES', 3),
(122, 31220, 'MARTRES-TOLOSANE', 5),
(123, 31220, 'MAURAN', 5),
(124, 31220, 'MONDAVEZAN', 3),
(125, 31220, 'MONTBERAUD', 5),
(126, 31220, 'MONTCLAR-DE-COMMINGES', 5),
(127, 31220, 'PALAMINY', 5),
(128, 31220, 'PLAGNE', 5),
(129, 31220, 'LE PLAN', 5),
(130, 31220, 'SAINT-JULIEN-SUR-GARONNE', 5),
(131, 31220, 'SAINT-MICHEL', 5),
(132, 31220, 'SANA', 3),
(133, 31230, 'AGASSAC', 3),
(134, 31230, 'AMBAX', 3),
(135, 31230, 'ANAN', 3),
(136, 31230, 'BOISSEDE', 3),
(137, 31230, 'CASTELGAILLARD', 3),
(138, 31230, 'COUEILLES', 3),
(139, 31230, 'FABAS', 3),
(140, 31230, 'FRONTIGNAN-SAVES', 3),
(141, 31230, 'GOUDEX', 3),
(142, 31230, 'L\'ISLE-EN-DODON', 3),
(143, 31230, 'LABASTIDE-PAUMES', 3),
(144, 31230, 'LILHAC', 3),
(145, 31230, 'MARTISSERRE', 3),
(146, 31230, 'MAUVEZIN', 3),
(147, 31230, 'MIRAMBEAU', 3),
(148, 31230, 'MOLAS', 3),
(149, 31230, 'MONTBERNARD', 3),
(150, 31230, 'MONTESQUIEU-GUITTAUT', 3),
(151, 31230, 'PUYMAURIN', 3),
(152, 31230, 'RIOLAS', 3),
(153, 31230, 'SAINT-FRAJOU', 3),
(154, 31230, 'SAINT-LAURENT', 3),
(155, 31230, 'SALERM', 3),
(156, 31230, 'CAZAC', 3),
(157, 31240, 'SAINT-JEAN', 2),
(158, 31240, 'L\'UNION', 2),
(159, 31250, 'REVEL', 4),
(160, 31250, 'VAUDREUILLE', 4),
(161, 31260, 'AUSSEING', 5),
(162, 31260, 'BELBEZE-EN-COMMINGES', 5),
(163, 31260, 'CASSAGNE', 5),
(164, 31260, 'CASTAGNEDE', 6),
(165, 31260, 'FIGAROL', 5),
(166, 31260, 'FRANCAZAL', 6),
(167, 31260, 'HIS', 6),
(168, 31260, 'MANE', 6),
(169, 31260, 'MARSOULAS', 5),
(170, 31260, 'MAZERES-SUR-SALAT', 5),
(171, 31260, 'MONTESPAN', 6),
(172, 31260, 'MONTGAILLARD-DE-SALIES', 6),
(173, 31260, 'MONTSAUNES', 5),
(174, 31260, 'SALEICH', 6),
(175, 31260, 'SALIES-DU-SALAT', 5),
(176, 31260, 'TOUILLE', 5),
(177, 31260, 'URAU', 6),
(178, 31260, 'ESCOULIS', 5),
(179, 31270, 'CUGNAUX', 3),
(180, 31270, 'FROUZINS', 3),
(181, 31270, 'VILLENEUVE-TOLOSANE', 3),
(182, 31280, 'AIGREFEUILLE', 4),
(183, 31280, 'DREMIL-LAFAGE', 4),
(184, 31280, 'MONS', 4),
(185, 31290, 'AVIGNONET-LAURAGAIS', 4),
(186, 31290, 'BEAUTEVILLE', 5),
(187, 31290, 'CESSALES', 4),
(188, 31290, 'FOLCARDE', 4),
(189, 31290, 'GARDOUCH', 2),
(190, 31290, 'LAGARDE', 5),
(191, 31290, 'LUX', 4),
(192, 31290, 'MAUREMONT', 4),
(193, 31290, 'MONTCLAR-LAURAGAIS', 4),
(194, 31290, 'MONTGAILLARD-LAURAGAIS', 4),
(195, 31290, 'RENNEVILLE', 4),
(196, 31290, 'RIEUMAJOU', 4),
(197, 31290, 'SAINT-GERMIER', 4),
(198, 31290, 'SAINT-ROME', 4),
(199, 31290, 'SAINT-VINCENT', 4),
(200, 31290, 'TREBONS-SUR-LA-GRASSE', 4),
(201, 31290, 'VALLEGUE', 4),
(202, 31290, 'VIEILLEVIGNE', 4),
(203, 31290, 'VILLEFRANCHE-DE-LAURAGAIS', 4),
(204, 31290, 'VILLENOUVELLE', 4),
(205, 31310, 'BAX', 5),
(206, 31310, 'CANENS', 5),
(207, 31310, 'CASTAGNAC', 5),
(208, 31310, 'GENSAC-SUR-GARONNE', 5),
(209, 31310, 'GOUTEVERNISSE', 5),
(210, 31310, 'GOUZENS', 5),
(211, 31310, 'LAHITERE', 5),
(212, 31310, 'LAPEYRERE', 5),
(213, 31310, 'LATOUR', 5),
(214, 31310, 'LATRAPE', 5),
(215, 31310, 'MAILHOLAS', 5),
(216, 31310, 'MASSABRAC', 5),
(217, 31310, 'MONTBRUN-BOCAGE', 5),
(218, 31310, 'MONTESQUIEU-VOLVESTRE', 5),
(219, 31310, 'RIEUX-VOLVESTRE', 5),
(220, 31310, 'SAINT-CHRISTAUD', 5),
(221, 31320, 'AUREVILLE', 5),
(222, 31320, 'AUZEVILLE-TOLOSANE', 4),
(223, 31320, 'CASTANET-TOLOSAN', 5),
(224, 31320, 'MERVILLA', 5),
(225, 31320, 'PECHABOU', 5),
(226, 31320, 'PECHBUSQUE', 5),
(227, 31320, 'REBIGUE', 5),
(228, 31320, 'VIEILLE-TOULOUSE', 5),
(229, 31320, 'VIGOULET-AUZIL', 5),
(230, 31330, 'LE BURGAUD', 1),
(231, 31330, 'GRENADE', 1),
(232, 31330, 'LAUNAC', 1),
(233, 31330, 'MERVILLE', 1),
(234, 31330, 'ONDES', 1),
(235, 31330, 'SAINT-CEZERT', 1),
(236, 31330, 'LARRA', 1),
(237, 31340, 'BONDIGOUX', 2),
(238, 31340, 'LE BORN', 2),
(239, 31340, 'LAYRAC-SUR-TARN', 2),
(240, 31340, 'LA MAGDELAINE-SUR-TARN', 2),
(241, 31340, 'MIREPOIX-SUR-TARN', 2),
(242, 31340, 'VACQUIERS', 2),
(243, 31340, 'VILLEMATIER', 2),
(244, 31340, 'VILLEMUR-SUR-TARN', 2),
(245, 31350, 'BLAJAN', 3),
(246, 31350, 'BOULOGNE-SUR-GESSE', 3),
(247, 31350, 'CARDEILHAC', 3),
(248, 31350, 'CASTERA-VIGNOLES', 3),
(249, 31350, 'CHARLAS', 3),
(250, 31350, 'CIADOUX', 3),
(251, 31350, 'ESCANECRABE', 3),
(252, 31350, 'GENSAC-DE-BOULOGNE', 3),
(253, 31350, 'LESPUGUE', 3),
(254, 31350, 'LUNAX', 3),
(255, 31350, 'MONDILHAN', 3),
(256, 31350, 'MONTGAILLARD-SUR-SAVE', 3),
(257, 31350, 'MONTMAURIN', 3),
(258, 31350, 'NENIGAN', 3),
(259, 31350, 'NIZAN-GESSE', 3),
(260, 31350, 'PEGUILHAN', 3),
(261, 31350, 'SAINT-FERREOL-DE-COMMINGES', 3),
(262, 31350, 'SAINT-LARY-BOUJEAN', 3),
(263, 31350, 'SAINT-LOUP-EN-COMMINGES', 3),
(264, 31350, 'SAINT-PE-DELBOSC', 3),
(265, 31350, 'SAMAN', 3),
(266, 31350, 'SARRECAVE', 3),
(267, 31350, 'SARREMEZAN', 3),
(268, 31360, 'ARNAUD-GUILHEM', 3),
(269, 31360, 'AUZAS', 3),
(270, 31360, 'BEAUCHALOT', 6),
(271, 31360, 'BOUSSENS', 3),
(272, 31360, 'CASTILLON-DE-SAINT-MARTORY', 3),
(273, 31360, 'LE FRECHET', 3),
(274, 31360, 'LAFFITE-TOUPIERE', 3),
(275, 31360, 'LESTELLE-DE-SAINT-MARTORY', 3),
(276, 31360, 'MANCIOUX', 3),
(277, 31360, 'PROUPIARY', 3),
(278, 31360, 'ROQUEFORT-SUR-GARONNE', 5),
(279, 31360, 'SAINT-MARTORY', 3),
(280, 31360, 'SAINT-MEDARD', 6),
(281, 31360, 'SEPX', 3),
(282, 31370, 'BEAUFORT', 3),
(283, 31370, 'BERAT', 3),
(284, 31370, 'FORGUES', 3),
(285, 31370, 'LABASTIDE-CLERMONT', 3),
(286, 31370, 'LAHAGE', 3),
(287, 31370, 'LAUTIGNAC', 3),
(288, 31370, 'MONES', 3),
(289, 31370, 'MONTASTRUC-SAVES', 3),
(290, 31370, 'MONTGRAS', 3),
(291, 31370, 'LE PIN-MURELET', 3),
(292, 31370, 'PLAGNOLE', 3),
(293, 31370, 'POUCHARRAMET', 3),
(294, 31370, 'RIEUMES', 3),
(295, 31370, 'SABONNERES', 3),
(296, 31370, 'SAJAS', 3),
(297, 31370, 'SAVERES', 3),
(298, 31380, 'AZAS', 2),
(299, 31380, 'BAZUS', 2),
(300, 31380, 'GARIDECH', 2),
(301, 31380, 'GEMIL', 2),
(302, 31380, 'GRAGNAGUE', 2),
(303, 31380, 'MONTASTRUC-LA-CONSEILLERE', 2),
(304, 31380, 'MONTJOIRE', 2),
(305, 31380, 'MONTPITOL', 2),
(306, 31380, 'PAULHAC', 2),
(307, 31380, 'ROQUESERIERE', 2),
(308, 31380, 'SAINT-JEAN-LHERM', 2),
(309, 31380, 'VILLARIES', 2),
(310, 31390, 'BOIS-DE-LA-PIERRE', 3),
(311, 31390, 'CARBONNE', 5),
(312, 31390, 'LACAUGNE', 5),
(313, 31390, 'LAFITTE-VIGORDANE', 3),
(314, 31390, 'MARQUEFAVE', 5),
(315, 31390, 'PEYSSIES', 3),
(316, 31390, 'SALLES-SUR-GARONNE', 5),
(317, 31410, 'CAPENS', 5),
(318, 31410, 'LE FAUGA', 5),
(319, 31410, 'LAVERNOSE-LACASSE', 3),
(320, 31410, 'LONGAGES', 3),
(321, 31410, 'MAUZAC', 5),
(322, 31410, 'MONTAUT', 5),
(323, 31410, 'MONTGAZIN', 5),
(324, 31410, 'NOE', 5),
(325, 31410, 'SAINT-HILAIRE', 3),
(326, 31410, 'SAINT-SULPICE-SUR-LEZE', 5),
(327, 31420, 'ALAN', 3),
(328, 31420, 'AULON', 3),
(329, 31420, 'AURIGNAC', 3),
(330, 31420, 'BACHAS', 3),
(331, 31420, 'BENQUE', 3),
(332, 31420, 'BOUSSAN', 3),
(333, 31420, 'BOUZIN', 3),
(334, 31420, 'CASSAGNABERE-TOURNAS', 3),
(335, 31420, 'CAZENEUVE-MONTAUT', 3),
(336, 31420, 'EOUX', 3),
(337, 31420, 'ESPARRON', 3),
(338, 31420, 'FRANCON', 3),
(339, 31420, 'MONTOULIEU-SAINT-BERNARD', 3),
(340, 31420, 'PEYRISSAS', 3),
(341, 31420, 'PEYROUZET', 3),
(342, 31420, 'SAINT-ANDRE', 3),
(343, 31420, 'SAINT-ELIX-SEGLAN', 3),
(344, 31420, 'SAMOUILLAN', 3),
(345, 31420, 'TERREBASSE', 3),
(346, 31430, 'CASTELNAU-PICAMPEAU', 3),
(347, 31430, 'CASTIES-LABRANDE', 3),
(348, 31430, 'LE FOUSSERET', 3),
(349, 31430, 'FUSTIGNAC', 3),
(350, 31430, 'GRATENS', 3),
(351, 31430, 'LUSSAN-ADEILHAC', 3),
(352, 31430, 'MARIGNAC-LASCLARES', 3),
(353, 31430, 'MONTEGUT-BOURJAC', 3),
(354, 31430, 'MONTOUSSIN', 3),
(355, 31430, 'POLASTRON', 3),
(356, 31430, 'POUY-DE-TOUGES', 3),
(357, 31430, 'SAINT-ARAILLE', 3),
(358, 31430, 'SAINT-ELIX-LE-CHATEAU', 3),
(359, 31430, 'SENARENS', 3),
(360, 31440, 'ARGUT-DESSOUS', 6),
(361, 31440, 'ARLOS', 6),
(362, 31440, 'BACHOS', 6),
(363, 31440, 'BAREN', 6),
(364, 31440, 'BEZINS-GARRAUX', 6),
(365, 31440, 'BOUTX', 6),
(366, 31440, 'BURGALAYS', 6),
(367, 31440, 'CAZAUX-LAYRISSE', 6),
(368, 31440, 'CHAUM', 6),
(369, 31440, 'CIERP-GAUD', 6),
(370, 31440, 'ESTENOS', 6),
(371, 31440, 'EUP', 6),
(372, 31440, 'FOS', 6),
(373, 31440, 'FRONSAC', 6),
(374, 31440, 'GURAN', 6),
(375, 31440, 'LEGE', 6),
(376, 31440, 'LEZ', 6),
(377, 31440, 'MARIGNAC', 6),
(378, 31440, 'MELLES', 6),
(379, 31440, 'SAINT-BEAT', 6),
(380, 31440, 'SIGNAC', 6),
(381, 31440, 'BINOS', 6),
(382, 31450, 'AYGUESVIVES', 4),
(383, 31450, 'BAZIEGE', 4),
(384, 31450, 'BELBERAUD', 4),
(385, 31450, 'BELBEZE-DE-LAURAGAIS', 4),
(386, 31450, 'CORRONSAC', 5),
(387, 31450, 'DEYME', 5),
(388, 31450, 'DONNEVILLE', 4),
(389, 31450, 'ESPANES', 5),
(390, 31450, 'FOURQUEVAUX', 4),
(391, 31450, 'ISSUS', 5),
(392, 31450, 'LABASTIDE-BEAUVOIR', 4),
(393, 31450, 'MONTBRUN-LAURAGAIS', 5),
(394, 31450, 'MONTESQUIEU-LAURAGAIS', 4),
(395, 31450, 'MONTGISCARD', 5),
(396, 31450, 'MONTLAUR', 4),
(397, 31450, 'NOUEILLES', 5),
(398, 31450, 'ODARS', 4),
(399, 31450, 'POMPERTUZAT', 5),
(400, 31450, 'POUZE', 5),
(401, 31450, 'VARENNES', 4),
(402, 31460, 'ALBIAC', 4),
(403, 31460, 'AURIAC-SUR-VENDINELLE', 4),
(404, 31460, 'BEAUVILLE', 4),
(405, 31460, 'LE CABANIAL', 4),
(406, 31460, 'CAMBIAC', 4),
(407, 31460, 'CARAGOUDES', 4),
(408, 31460, 'CARAMAN', 4),
(409, 31460, 'LE FAGET', 4),
(410, 31460, 'FRANCARVILLE', 4),
(411, 31460, 'LOUBENS-LAURAGAIS', 4),
(412, 31460, 'MASCARVILLE', 4),
(413, 31460, 'MAUREVILLE', 4),
(414, 31460, 'MOURVILLES-BASSES', 4),
(415, 31460, 'PRUNET', 4),
(416, 31460, 'LA SALVETAT-LAURAGAIS', 4),
(417, 31460, 'SAUSSENS', 4),
(418, 31460, 'SEGREVILLE', 4),
(419, 31460, 'TOUTENS', 4),
(420, 31460, 'VENDINE', 4),
(421, 31470, 'BONREPOS-SUR-AUSSONNELLE', 3),
(422, 31470, 'BRAGAYRAC', 3),
(423, 31470, 'CAMBERNARD', 3),
(424, 31470, 'EMPEAUX', 3),
(425, 31470, 'FONSORBES', 3),
(426, 31470, 'FONTENILLES', 3),
(427, 31470, 'SAIGUEDE', 3),
(428, 31470, 'SAINTE-FOY-DE-PEYROLIERES', 3),
(429, 31470, 'SAINT-LYS', 3),
(430, 31470, 'SAINT-THOMAS', 3),
(431, 31480, 'BELLESSERRE', 1),
(432, 31480, 'BRIGNEMONT', 1),
(433, 31480, 'CABANAC-SEGUENVILLE', 1),
(434, 31480, 'CADOURS', 1),
(435, 31480, 'CAUBIAC', 1),
(436, 31480, 'COX', 1),
(437, 31480, 'DRUDAS', 1),
(438, 31480, 'GARAC', 1),
(439, 31480, 'LE GRES', 1),
(440, 31480, 'LAGRAULET-SAINT-NICOLAS', 1),
(441, 31480, 'LAREOLE', 1),
(442, 31480, 'PELLEPORT', 1),
(443, 31480, 'PUYSSEGUR', 1),
(444, 31480, 'VIGNAUX', 1),
(445, 31490, 'BRAX', 1),
(446, 31490, 'LEGUEVIN', 1),
(447, 31510, 'ANTICHAN-DE-FRONTIGNES', 6),
(448, 31510, 'BAGIRY', 6),
(449, 31510, 'BARBAZAN', 6),
(450, 31510, 'CIER-DE-RIVIERE', 6),
(451, 31510, 'FRONTIGNAN-DE-COMMINGES', 6),
(452, 31510, 'GALIE', 6),
(453, 31510, 'GENOS', 6),
(454, 31510, 'LABROQUERE', 6),
(455, 31510, 'LOURDE', 6),
(456, 31510, 'LUSCAN', 6),
(457, 31510, 'MALVEZIE', 6),
(458, 31510, 'MONT-DE-GALIE', 6),
(459, 31510, 'ORE', 6),
(460, 31510, 'PAYSSOUS', 6),
(461, 31510, 'SAINT-BERTRAND-DE-COMMINGES', 6),
(462, 31510, 'SAINT-PE-D\'ARDET', 6),
(463, 31510, 'SAUVETERRE-DE-COMMINGES', 6),
(464, 31510, 'SEILHAN', 6),
(465, 31510, 'VALCABRERE', 6),
(466, 31520, 'RAMONVILLE-SAINT-AGNE', 5),
(467, 31530, 'BELLEGARDE-SAINTE-MARIE', 1),
(468, 31530, 'BRETX', 1),
(469, 31530, 'LE CASTERA', 1),
(470, 31530, 'LASSERRE', 1),
(471, 31530, 'LEVIGNAC', 1),
(472, 31530, 'MENVILLE', 1),
(473, 31530, 'MERENVIELLE', 1),
(474, 31530, 'MONTAIGUT-SUR-SAVE', 1),
(475, 31530, 'PRADERE-LES-BOURGUETS', 1),
(476, 31530, 'SAINTE-LIVRADE', 1),
(477, 31530, 'SAINT-PAUL-SUR-SAVE', 1),
(478, 31530, 'THIL', 1),
(479, 31540, 'BELESTA-EN-LAURAGAIS', 4),
(480, 31540, 'FALGA', 4),
(481, 31540, 'JUZES', 4),
(482, 31540, 'MAURENS', 4),
(483, 31540, 'MONTEGUT-LAURAGAIS', 4),
(484, 31540, 'MOURVILLES-HAUTES', 4),
(485, 31540, 'NOGARET', 4),
(486, 31540, 'ROUMENS', 4),
(487, 31540, 'SAINT-FELIX-LAURAGAIS', 4),
(488, 31540, 'SAINT-JULIA', 4),
(489, 31540, 'VAUX', 4),
(490, 31550, 'AIGNES', 5),
(491, 31550, 'CINTEGABELLE', 5),
(492, 31550, 'GAILLAC-TOULZA', 5),
(493, 31550, 'MARLIAC', 5),
(494, 31560, 'CAIGNAC', 5),
(495, 31560, 'CALMONT', 5),
(496, 31560, 'GIBEL', 5),
(497, 31560, 'MONESTROL', 5),
(498, 31560, 'MONTGEARD', 5),
(499, 31560, 'NAILLOUX', 5),
(500, 31560, 'SAINT-LEON', 5),
(501, 31560, 'SEYRE', 5),
(502, 31570, 'AURIN', 4),
(503, 31570, 'BOURG-SAINT-BERNARD', 4),
(504, 31570, 'LANTA', 4),
(505, 31570, 'PRESERVILLE', 4),
(506, 31570, 'SAINTE-FOY-D\'AIGREFEUILLE', 4),
(507, 31570, 'SAINT-PIERRE-DE-LAGES', 4),
(508, 31570, 'TARABEL', 4),
(509, 31570, 'VALLESVILLES', 4),
(510, 31580, 'BALESTA', 3),
(511, 31580, 'BOUDRAC', 3),
(512, 31580, 'CAZARIL-TAMBOURES', 3),
(513, 31580, 'LARROQUE', 3),
(514, 31580, 'LECUSSAN', 3),
(515, 31580, 'LOUDET', 3),
(516, 31580, 'SAINT-PLANCARD', 3),
(517, 31580, 'SEDEILHAC', 3),
(518, 31580, 'VILLENEUVE-LECUSSAN', 3),
(519, 31590, 'BONREPOS-RIQUET', 4),
(520, 31590, 'GAURE', 4),
(521, 31590, 'LAVALETTE', 4),
(522, 31590, 'SAINT-MARCEL-PAULEL', 4),
(523, 31590, 'SAINT-PIERRE', 4),
(524, 31590, 'VERFEIL', 2),
(525, 31600, 'EAUNES', 5),
(526, 31600, 'LABASTIDETTE', 3),
(527, 31600, 'LAMASQUERE', 3),
(528, 31600, 'LHERM', 3),
(529, 31600, 'MURET', 5),
(530, 31600, 'SAINT-CLAR-DE-RIVIERE', 3),
(531, 31600, 'SAUBENS', 5),
(532, 31600, 'SEYSSES', 3),
(533, 31620, 'BOULOC', 2),
(534, 31620, 'CASTELNAU-D\'ESTRETEFONDS', 2),
(535, 31620, 'CEPET', 2),
(536, 31620, 'FRONTON', 2),
(537, 31620, 'GARGAS', 2),
(538, 31620, 'LABASTIDE-SAINT-SERNIN', 2),
(539, 31620, 'SAINT-RUSTICE', 2),
(540, 31620, 'VILLAUDRIC', 2),
(541, 31620, 'VILLENEUVE-LES-BOULOC', 2),
(542, 31650, 'AUZIELLE', 4),
(543, 31650, 'LAUZERVILLE', 4),
(544, 31650, 'SAINT-ORENS-DE-GAMEVILLE', 4),
(545, 31660, 'BESSIERES', 1),
(546, 31660, 'BUZET-SUR-TARN', 2),
(547, 31670, 'LABEGE', 4),
(548, 31700, 'BEAUZELLE', 1),
(549, 31700, 'BLAGNAC', 7),
(550, 31700, 'CORNEBARRIEU', 1),
(551, 31700, 'DAUX', 1),
(552, 31700, 'MONDONVILLE', 1),
(553, 31750, 'ESCALQUENS', 4),
(554, 31770, 'COLOMIERS', 7),
(555, 31780, 'CASTELGINEST', 2),
(556, 31790, 'SAINT-JORY', 2),
(557, 31790, 'SAINT-SAUVEUR', 2),
(558, 31800, 'ASPRET-SARRAT', 6),
(559, 31800, 'ESTANCARBON', 6),
(560, 31800, 'LABARTHE-INARD', 6),
(561, 31800, 'LABARTHE-RIVIERE', 6),
(562, 31800, 'LALOURET-LAFFITEAU', 3),
(563, 31800, 'LANDORTHE', 3),
(564, 31800, 'LARCAN', 3),
(565, 31800, 'LATOUE', 3),
(566, 31800, 'LIEOUX', 3),
(567, 31800, 'LODES', 3),
(568, 31800, 'MIRAMONT-DE-COMMINGES', 6),
(569, 31800, 'POINTIS-INARD', 6),
(570, 31800, 'REGADES', 6),
(571, 31800, 'RIEUCAZE', 6),
(572, 31800, 'SAINT-GAUDENS', 7),
(573, 31800, 'SAINT-IGNAN', 3),
(574, 31800, 'SAINT-MARCET', 3),
(575, 31800, 'SAUX-ET-POMAREDE', 3),
(576, 31800, 'SAVARTHES', 6),
(577, 31800, 'VALENTINE', 6),
(578, 31800, 'VILLENEUVE-DE-RIVIERE', 6),
(579, 31810, 'CLERMONT-LE-FORT', 5),
(580, 31810, 'VENERQUE', 5),
(581, 31810, 'VERNET', 5),
(582, 31820, 'PIBRAC', 1),
(583, 31830, 'PLAISANCE-DU-TOUCH', 3),
(584, 31840, 'AUSSONNE', 1),
(585, 31840, 'SEILH', 1),
(586, 31850, 'BEAUPUY', 2),
(587, 31850, 'MONDOUZIL', 4),
(588, 31850, 'MONTRABE', 2),
(589, 31860, 'LABARTHE-SUR-LEZE', 5),
(590, 31860, 'PINS-JUSTARET', 5),
(591, 31860, 'VILLATE', 5),
(592, 31870, 'BEAUMONT-SUR-LEZE', 5),
(593, 31870, 'LAGARDELLE-SUR-LEZE', 5),
(594, 31880, 'LA SALVETAT-SAINT-GILLES', 3),
(595, 47340, 'HAUTEFAGE-LA-TOUR', NULL),
(596, 47380, 'PINEL-HAUTERIVE', NULL),
(597, 47400, 'HAUTESVIGNES', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `Gestionnaire`
--

CREATE TABLE `Gestionnaire` (
  `id_gestionnaire` int(255) UNSIGNED NOT NULL,
  `mdp_gestionnaire` varchar(255) NOT NULL,
  `nom_gestionnaire` varchar(255) NOT NULL,
  `prenom_gestionnaire` varchar(255) NOT NULL,
  `chambre` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Message`
--

CREATE TABLE `Message` (
  `id_message` int(255) UNSIGNED NOT NULL,
  `id_auteur` int(255) UNSIGNED NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_chambre` int(5) UNSIGNED NOT NULL,
  `texte` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Societe`
--

CREATE TABLE `Societe` (
  `id_societe` int(255) UNSIGNED NOT NULL,
  `id_activite` smallint(5) UNSIGNED NOT NULL,
  `secteur` tinyint(2) UNSIGNED NOT NULL,
  `codePostal` smallint(5) UNSIGNED NOT NULL,
  `telephone` int(10) UNSIGNED NOT NULL,
  `mail` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Utilisateur`
--

CREATE TABLE `Utilisateur` (
  `id_utilisateur` int(255) UNSIGNED NOT NULL,
  `cle` varchar(255) NOT NULL,
  `codeAct` smallint(5) UNSIGNED NOT NULL,
  `secteur` tinyint(2) UNSIGNED NOT NULL,
  `codePostal` smallint(5) UNSIGNED NOT NULL,
  `telephone` varchar(255) NOT NULL,
  `mail` text NOT NULL,
  `chambre` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Chambre`
--
ALTER TABLE `Chambre`
  ADD PRIMARY KEY (`nom_chambre`);

--
-- Indexes for table `CodeActivite`
--
ALTER TABLE `CodeActivite`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `Commune`
--
ALTER TABLE `Commune`
  ADD PRIMARY KEY (`id_commune`);

--
-- Indexes for table `Gestionnaire`
--
ALTER TABLE `Gestionnaire`
  ADD PRIMARY KEY (`id_gestionnaire`);

--
-- Indexes for table `Message`
--
ALTER TABLE `Message`
  ADD PRIMARY KEY (`id_message`),
  ADD KEY `fk_idAuteur` (`id_auteur`);

--
-- Indexes for table `Societe`
--
ALTER TABLE `Societe`
  ADD PRIMARY KEY (`id_societe`),
  ADD KEY `fk_idActivite` (`id_activite`);

--
-- Indexes for table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD KEY `fk_codeAct` (`codeAct`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Commune`
--
ALTER TABLE `Commune`
  MODIFY `id_commune` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=598;

--
-- AUTO_INCREMENT for table `Message`
--
ALTER TABLE `Message`
  MODIFY `id_message` int(255) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Societe`
--
ALTER TABLE `Societe`
  MODIFY `id_societe` int(255) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  MODIFY `id_utilisateur` int(255) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Message`
--
ALTER TABLE `Message`
  ADD CONSTRAINT `fk_idAuteur` FOREIGN KEY (`id_auteur`) REFERENCES `Utilisateur` (`id_utilisateur`);

--
-- Constraints for table `Societe`
--
ALTER TABLE `Societe`
  ADD CONSTRAINT `fk_idActivite` FOREIGN KEY (`id_activite`) REFERENCES `CodeActivite` (`code`);

--
-- Constraints for table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  ADD CONSTRAINT `fk_codeAct` FOREIGN KEY (`codeAct`) REFERENCES `CodeActivite` (`code`);
