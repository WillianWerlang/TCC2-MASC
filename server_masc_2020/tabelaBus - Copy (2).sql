DROP TABLE IF EXISTS `BusRoutePosition`;
DROP TABLE IF EXISTS `BusRoute`;
DROP TABLE IF EXISTS `Position`;
DROP TABLE IF EXISTS `BusStop`;

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
`BusRouteID` INT(6),
`Order` INT(6),
`PositionID` INT(6)
);

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
(80, -29.767620, -51.148289, NULL);

-----

INSERT INTO `BusRoute` VALUES 
(1, "Linha Vermelha"), (2, "Purple"), (3, "Pink"), (4, "Orange"), (5, "Green"), (6, "Yellow"), (7, "Brown");

-----

INSERT INTO `BusRoutePosition` VALUES 
(1, 1, 1), (1, 2, 2), (1, 3, 3), (1, 4, 79), (1, 5, 80), (1, 6, 4), (1, 7, 5), (1, 8, 38), (1, 9, 39), (1, 10, 40), (1, 11, 1), 
(2, 1, 25), (2, 2, 26), (2, 3, 41), (2, 4, 42), (2, 5, 27), (2, 6, 28), (2, 7, 43), (2, 8, 44), (2, 9, 25), 
(3, 1, 78), (3, 2, 45), (3, 3, 25), (3, 4, 26), (3, 5, 75), (3, 6, 21), (3, 7, 22), (3, 8, 46), 
(3, 9, 36), (3, 10, 76), (3, 11, 77), (3, 12, 24), (3, 13, 47), (3, 14, 37), (3, 15, 78), 
(4, 1, 13), (4, 2, 14), (4, 3, 48), (4, 4, 15), (4, 5, 49), (4, 6, 50), (4, 7, 16), (4, 8, 51), 
(4, 9, 17), (4, 10, 52), (4, 11, 6), (4, 12, 53), (4, 13, 54), (4, 14, 55), (4, 15, 13), 
(5, 1, 1), (5, 2, 56), (5, 3, 6), (5, 4, 53), (5, 5, 54), (5, 6, 7), (5, 7, 57), (5, 8, 8), (5, 9, 58), (5, 10, 59), (5, 11, 60), 
(5, 12, 9), (5, 13, 10), (5, 14, 11), (5, 16, 61), (5, 17, 18), (5, 18, 62), (5, 19, 12), (5, 20, 39), (5, 21, 40), (5, 22, 1), 
(6, 1, 18), (6, 2, 19), (6, 3, 63), (6, 4, 20), (6, 5, 64), (6, 6, 65), (6, 7, 21), 
(6, 8, 22), (6, 9, 66), (6, 10, 23), (6, 11, 24), (6, 12, 67), (6, 13, 68), (6, 14, 18), 
(7, 1, 34), (7, 2, 69), (7, 3, 35), (7, 4, 26), (7, 5, 70), (7, 6, 29), (7, 7, 71), (7, 8, 30), 
(7, 9, 72), (7, 10, 31), (7, 11, 73), (7, 12, 32), (7, 13, 33), (7, 14, 74), (7, 15, 34);