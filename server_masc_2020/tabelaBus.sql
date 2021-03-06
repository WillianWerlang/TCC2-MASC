DROP TABLE IF EXISTS `BusRoutePosition`;
DROP TABLE IF EXISTS `BusRoute`;
DROP TABLE IF EXISTS `Position`;
DROP TABLE IF EXISTS `BusStop`;
DROP TABLE IF EXISTS `Bus`;
DROP TABLE IF EXISTS `Schedule`;

-----

CREATE TABLE `BusStop` (
`BusStopID` INT(6) PRIMARY KEY,
`AverageFeedback` FLOAT
);

-----

CREATE TABLE `Position` (
`PositionID` INT(6) PRIMARY KEY,
`Lat` DECIMAL(8, 6),
`Lng` DECIMAL(8, 6),
`BusStopID` INT(6)
);

-----

CREATE TABLE `BusRoute` (
`BusRouteID` INT(6),
`BusRouteName` VARCHAR(100)
);

-----

CREATE TABLE `BusRoutePosition` (
`BusRoutePositionID` INT(6),
`BusRouteID` INT(6),
`Order` INT(6),
`PositionID` INT(6)
);

-----

CREATE TABLE `Bus` (
`BusID` INT(6),
`AverageFeedback` FLOAT,
`Driver` VARCHAR(100),
`Seats` INT(6),
`UsedSeats` INT(6)
);

-----

CREATE TABLE `Schedule` (
`ScheduleID` INT(6),
`BusID` INT(6),
`BusRoutePositionID` INT(6),
`Time` TIMESTAMP 
);

-----

INSERT INTO `Bus` VALUES 
(1, 9, "Motorista 1", 2, 0),  (2, 9, "Motorista 2", 2, 0),  (3, 9, "Motorista 3", 2, 0),  (4, 9, "Motorista 4", 2, 0),  (5, 9, "Motorista 5", 2, 0),  
(6, 9, "Motorista 6", 2, 0),  (7, 9, "Motorista 7", 2, 0),  (8, 9, "Motorista 8", 2, 0),  (9, 9, "Motorista 9", 2, 0),  (10, 9, "Motorista 10", 2, 0),  
(11, 9, "Motorista 11", 2, 0),  (12, 9, "Motorista 12", 2, 0),  (13, 9, "Motorista 13", 2, 0),  (14, 9, "Motorista 14", 2, 0),  (15, 9, "Motorista 15", 2, 0),  
(16, 9, "Motorista 16", 2, 0),  (17, 9, "Motorista 17", 2, 0),  (18, 9, "Motorista 18", 2, 0),  (19, 9, "Motorista 19", 2, 0),  (20, 9, "Motorista 20", 2, 0),  
(21, 9, "Motorista 21", 2, 0),  (22, 9, "Motorista 22", 2, 0),  (23, 9, "Motorista 23", 2, 0),  (24, 9, "Motorista 24", 2, 0),  (25, 9, "Motorista 25", 2, 0);

-----

INSERT INTO `BusStop` VALUES 
(1, 8), (2, 8), (3, 8), (4, 8), (5, 8), (6, 8), (7, 8), (8, 8), (9, 8), (10, 8), (11, 8), (12, 8), (13, 8), (14, 8), (15, 8), (16, 8), (17, 8), (18, 8), (19, 8), 
(20, 8), (21, 8), (22, 8), (23, 8), (24, 8), (25, 8), (26, 8), (27, 8), (28, 8), (29, 8), (30, 8), (31, 8), (32, 8), (33, 8), (34, 8), (35, 8), (36, 8);

-----

INSERT INTO `Position` VALUES 
(1, -29.762852, -51.148588, 1), (2,-29.764258, -51.148245, 2), (3, -29.766837, -51.147589, 3), (4, -29.765698, -51.148769, 4), (5, -29.761784, -51.149694, 5), 
(6, -29.763107, -51.145931, 6), (7, -29.760033, -51.144257, 7), (8, -29.762138, -51.142519, 8), (9, -29.763749, -51.141639, 9), (10, -29.764317, -51.144815, 10), 
(11, -29.765113, -51.149319, 11), (12, -29.761341, -51.151197, 12), (13, -29.762296, -51.144949, 13), (14, -29.764880, -51.144336, 14), (15, -29.768222, -51.143997, 15), 
(16, -29.767207, -51.146014, 16), (17, -29.765277, -51.146855, 17), (18, -29.764573, -51.150746, 18), (19, -29.767300, -51.150076, 19), (20, -29.768637, -51.146580, 20), 
(21, -29.769899, -51.146205, 21), (22, -29.770466, -51.149695, 22), (23, -29.768705, -51.150589, 23), (24, -29.766114, -51.151259, 24), (25, -29.763305, -51.143101, 25), 
(26, -29.765817, -51.142438, 26), (27, -29.767305, -51.142888, 27), (28, -29.764749, -51.143488, 28), (29, -29.765743, -51.141591, 29), (30, -29.766740, -51.143348, 30), 
(31, -29.769784, -51.143119, 31), (32, -29.770918, -51.145001, 32), (33, -29.771645, -51.149595, 33), (34, -29.768800, -51.152286, 34), (35, -29.766534, -51.149805, 35), 
(36, -29.768496, -51.151473, NULL), (37, -29.762774, -51.151616, NULL), (38, -29.761095, -51.149828, NULL), (39, -29.760970, -51.149274, NULL), 
(40, -29.761135, -51.149019, NULL), (41, -29.767798, -51.141895, NULL), (42, -29.767994, -51.142738, NULL), (43, -29.762806, -51.143942, NULL), 
(44, -29.762676, -51.143271, NULL), (45, -29.761212, -51.143623, NULL), (46, -29.770715, -51.150911, NULL), (47, -29.762863, -51.152010, NULL), 
(48, -29.768144, -51.143543, NULL), (49, -29.768256, -51.144383, NULL), (50, -29.766971, -51.144678, NULL), (51, -29.767265, -51.146353, NULL), 
(52, -29.763347, -51.147378, NULL), (53, -29.763038, -51.145608, NULL), (54, -29.760338, -51.146247, NULL), (55, -29.760234, -51.145459, NULL), 
(56, -29.763522, -51.148400, NULL), (57, -29.759735, -51.143114, NULL), (58, -29.762529, -51.142401, NULL), (59, -29.762385, -51.141578, NULL), 
(60, -29.763703, -51.141299, NULL), (61, -29.765305, -51.150561, NULL), (62, -29.761440, -51.151526, NULL), (63, -29.769170, -51.149640, NULL), 
(64, -29.768405, -51.145199, NULL), (65, -29.769630, -51.144902, NULL), (66, -29.770577, -51.150142, NULL), (67, -29.764013, -51.151747, NULL), 
(68, -29.763887, -51.150925, NULL), (69, -29.767042, -51.152698, NULL), (70, -29.765122, -51.141781, NULL), (71, -29.766357, -51.141463, NULL), 
(72, -29.766844, -51.143849, NULL), (73, -29.770378, -51.142949, NULL), (74, -29.771922, -51.151514, NULL), (75, -29.768939, -51.141648, NULL), 
(76, -29.767413, -51.151734, NULL), (77, -29.767293, -51.150962, NULL), (78, -29.761904, -51.147158, 36), (79, -29.767454, -51.147446, NULL), 
(80, -29.767620, -51.148289, NULL), (81, -29.765590, -51.144521, 37);

-----

INSERT INTO `BusRoute` VALUES 
(1, "Linha Vermelha"), (2, "Purple"), (3, "Pink"), (4, "Orange"), (5, "Green"), (6, "Yellow"), (7, "Brown");

-----

INSERT INTO `BusRoutePosition` VALUES 
(1, 1, 1, 1), (2, 1, 2, 2), (3, 1, 3, 3), (4, 1, 4, 79), (5, 1, 5, 80), (6, 1, 6, 4), (7, 1, 7, 5), (8, 1, 8, 38), (9, 1, 9, 39), (10, 1, 10, 40), (11, 1, 11, 1), 
(12, 2, 1, 25), (13, 2, 2, 26), (14, 2, 3, 41), (15, 2, 4, 42), (16, 2, 5, 27), (17, 2, 6, 28), (18, 2, 7, 43), (19, 2, 8, 44), (20, 2, 9, 25), 
(21, 3, 1, 78), (22, 3, 2, 45), (23, 3, 3, 25), (24, 3, 4, 26), (25, 3, 5, 75), (26, 3, 6, 21), (27, 3, 7, 22), (28, 3, 8, 46), 
(29, 3, 9, 36), (30, 3, 10, 76), (31, 3, 11, 77), (32, 3, 12, 24), (33, 3, 13, 47), (34, 3, 14, 37), (35, 3, 15, 78), 
(36, 4, 1, 13), (37, 4, 2, 14), (38, 4, 3, 48), (39, 4, 4, 15), (40, 4, 5, 49), (41, 4, 6, 50), (42, 4, 7, 16), (43, 4, 8, 51), 
(44, 4, 9, 17), (45, 4, 10, 52), (46, 4, 11, 6), (47, 4, 12, 53), (48, 4, 13, 54), (49, 4, 14, 55), (50, 4, 15, 13), 
(51, 5, 1, 1), (52, 5, 2, 56), (53, 5, 3, 6), (54, 5, 4, 53), (55, 5, 5, 54), (56, 5, 6, 7), (57, 5, 7, 57), (58, 5, 8, 8), (59, 5, 9, 58), (60, 5, 10, 59), (61, 5, 11, 60), 
(62, 5, 12, 9), (63, 5, 13, 10), (64, 5, 14, 11), (65, 5, 16, 61), (66, 5, 17, 18), (67, 5, 18, 62), (68, 5, 19, 12), (69, 5, 20, 39), (70, 5, 21, 40), (71, 5, 22, 1), 
(72, 6, 1, 18), (73, 6, 2, 19), (74, 6, 3, 63), (75, 6, 4, 20), (76, 6, 5, 64), (77, 6, 6, 65), (78, 6, 7, 21), 
(79, 6, 8, 22), (80, 6, 9, 66), (81, 6, 10, 23), (82, 6, 11, 24), (83, 6, 12, 67), (84, 6, 13, 68), (85, 6, 14, 18), 
(86, 7, 1, 34), (87, 7, 2, 69), (88, 7, 3, 35), (89, 7, 4, 81), (90, 7, 5, 70), (91, 7, 6, 29), (92, 7, 7, 71), (93, 7, 8, 30), 
(94, 7, 9, 72), (95, 7, 10, 31), (96, 7, 11, 73), (97, 7, 12, 32), (98, 7, 13, 33), (99, 7, 14, 74), (100, 7, 15, 34);

-----

INSERT INTO `Schedule` VALUES
(1, 1, 1, '2020-01-01 15:10:00'), (1, 1, 2, '2020-01-01 15:12:00'), (1, 1, 3, '2020-01-01 15:14:00'), (1, 1, 4, '2020-01-01 15:15:00'), (1, 1, 5, '2020-01-01 15:18:00'), 
(1, 1, 6, '2020-01-01 15:20:00'), (1, 1, 7, '2020-01-01 15:22:00'), (1, 1, 8, '2020-01-01 15:24:00'), (1, 1, 9, '2020-01-01 15:26:00'), (1, 1, 10, '2020-01-01 15:28:00'), 
(1, 1, 11, '2020-01-01 15:30:00'), (2, 2, 1, '2020-01-01 15:14:00'), (2, 2, 2, '2020-01-01 15:15:00'), (2, 2, 3, '2020-01-01 15:18:00'), (2, 2, 4, '2020-01-01 15:20:00'), 
(2, 2, 5, '2020-01-01 15:22:00'), (2, 2, 6, '2020-01-01 15:24:00'), (2, 2, 7, '2020-01-01 15:26:00'), (2, 2, 8, '2020-01-01 15:28:00'), (2, 2, 9, '2020-01-01 15:30:00'), 
(2, 2, 10, '2020-01-01 15:34:00'), (2, 2, 11, '2020-01-01 15:35:00'), (3, 3, 1, '2020-01-01 15:20:00'), (3, 3, 2, '2020-01-01 15:22:00'), (3, 3, 3, '2020-01-01 15:24:00'), 
(3, 3, 4, '2020-01-01 15:26:00'), (3, 3, 5, '2020-01-01 15:28:00'), (3, 3, 6, '2020-01-01 15:30:00'), (3, 3, 7, '2020-01-01 15:34:00'), (3, 3, 8, '2020-01-01 15:35:00'), 
(3, 3, 9, '2020-01-01 15:36:00'), (3, 3, 10, '2020-01-01 15:37:00'), (3, 3, 11, '2020-01-01 15:38:00'), (4, 4, 1, '2020-01-01 15:24:00'), (4, 4, 2, '2020-01-01 15:26:00'), 
(4, 4, 3, '2020-01-01 15:28:00'), (4, 4, 4, '2020-01-01 15:30:00'), (4, 4, 5, '2020-01-01 15:34:00'), (4, 4, 6, '2020-01-01 15:35:00'), (4, 4, 7, '2020-01-01 15:36:00'), 
(4, 4, 8, '2020-01-01 15:37:00'), (4, 4, 9, '2020-01-01 15:38:00'), (4, 4, 10, '2020-01-01 15:40:00'), (4, 4, 11, '2020-01-01 15:42:00'), (5, 5, 1, '2020-01-01 15:30:00'), 
(5, 5, 2, '2020-01-01 15:34:00'), (5, 5, 3, '2020-01-01 15:35:00'), (5, 5, 4, '2020-01-01 15:36:00'), (5, 5, 5, '2020-01-01 15:37:00'), (5, 5, 6, '2020-01-01 15:38:00'), 
(5, 5, 7, '2020-01-01 15:40:00'), (5, 5, 8, '2020-01-01 15:42:00'), (5, 5, 9, '2020-01-01 15:44:00'), (5, 5, 10, '2020-01-01 15:50:00'), (5, 5, 11, '2020-01-01 15:52:00'), 
(6, 6, 12, '2020-01-01 15:15:00'), (6, 6, 13, '2020-01-01 15:18:00'), (6, 6, 14, '2020-01-01 15:20:00'), (6, 6, 15, '2020-01-01 15:22:00'), (6, 6, 16, '2020-01-01 15:24:00'), 
(6, 6, 17, '2020-01-01 15:26:00'), (6, 6, 18, '2020-01-01 15:28:00'), (6, 6, 19, '2020-01-01 15:29:00'), (6, 6, 20, '2020-01-01 15:30:00'), (7, 7, 12, '2020-01-01 15:37:00'), 
(7, 7, 13, '2020-01-01 15:38:00'), (7, 7, 14, '2020-01-01 15:40:00'), (7, 7, 15, '2020-01-01 15:42:00'), (7, 7, 16, '2020-01-01 15:44:00'), (7, 7, 17, '2020-01-01 15:50:00'), 
(7, 7, 18, '2020-01-01 15:52:00'), (7, 7, 19, '2020-01-01 15:53:00'), (7, 7, 20, '2020-01-01 15:55:00'), (8, 8, 12, '2020-01-01 15:44:00'), (8, 8, 13, '2020-01-01 15:50:00'), 
(8, 8, 14, '2020-01-01 15:52:00'), (8, 8, 15, '2020-01-01 15:55:00'), (8, 8, 16, '2020-01-01 16:10:00'), (8, 8, 17, '2020-01-01 16:11:00'), (8, 8, 18, '2020-01-01 16:12:00'), 
(8, 8, 19, '2020-01-01 16:14:00'), (8, 8, 20, '2020-01-01 16:16:00'), (9, 9, 12, '2020-01-01 16:12:00'), (9, 9, 13, '2020-01-01 16:14:00'), (9, 9, 14, '2020-01-01 16:16:00'), 
(9, 9, 15, '2020-01-01 16:18:00'), (9, 9, 16, '2020-01-01 16:19:00'), (9, 9, 17, '2020-01-01 16:20:00'), (9, 9, 18, '2020-01-01 16:22:00'), 
(9, 9, 19, '2020-01-01 16:24:00'), (9, 9, 20, '2020-01-01 16:26:00'), (10, 10, 12, '2020-01-01 16:37:00'), (10, 10, 13, '2020-01-01 16:38:00'), 
(10, 10, 14, '2020-01-01 16:39:00'), (10, 10, 15, '2020-01-01 16:40:00'), (10, 10, 16, '2020-01-01 16:42:00'), (10, 10, 17, '2020-01-01 16:44:00'), 
(10, 10, 18, '2020-01-01 16:50:00'), (10, 10, 19, '2020-01-01 16:52:00'), (10, 10, 20, '2020-01-01 16:55:00');

