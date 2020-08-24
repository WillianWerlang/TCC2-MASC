DROP TABLE IF EXISTS `BusStop`;
DROP TABLE IF EXISTS `Position`;
DROP TABLE IF EXISTS `BusRoute`;
DROP TABLE IF EXISTS `Schedule`;
DROP TABLE IF EXISTS `Bus`;

CREATE TABLE `BusStop` (
`BusStopID` INT(6) PRIMARY KEY,
`AverageFeedback` FLOAT
);

CREATE TABLE `Position` (
`PositionID` INT(6) PRIMARY KEY,
`Lat` DECIMAL(8, 6),
`Longe` DECIMAL(8, 6),
`BusStopID` INT(6)
);

CREATE TABLE `BusRoute` (
`BusRouteID` INT(6),
`PositionID` INT(6),
`Order` INT(6)
);

CREATE TABLE `Schedule` (  
`BusRouteID` INT(6),
`ScheduleID` INT(6),
`BusRoutePositionID` INT(6),
`Horary` DATETIME
);

CREATE TABLE `Bus` (
`BusID` INT(6) PRIMARY KEY,
`CurrentScheduleID` INT(6),
`TotalSpaces` INT(6),
`UsedSpaces` INT(6)
);


INSERT INTO `BusStop` VALUES (1, 9.5), (2, 9.5), (3, 9.5), (4, 9.5), (5, 9.5);

INSERT INTO `Position` VALUES 
(1, -29.763099, -51.148521, 1), (2, -29.765365, -51.147942, 2), (3, -29.767456, -51.147443, null), (4, -29.767622, -51.148293, null), 
(5, -29.767127, -51.148418, 3), (6, -29.764307, -51.149072, 4), (7, -29.761764, -51.149714, 5), 
(8, -29.761093, -51.149826, null), (9, -29.760969, -51.149283, null), (10, -29.761186, -51.148993, null);


INSERT INTO `BusRoute` VALUES 
(1, 1, 1), (1, 2, 2), (1, 3, 3), (1, 4, 4), (1, 5, 5), (1, 6, 6), (1, 7, 7), (1, 8, 8), (1, 9, 9), (1, 10, 10), (1, 1, 11);

INSERT INTO `Schedule` VALUES 
(1, 1, 1, '2020-01-01 15:00:00'), (1, 1, 2, '2020-01-01 15:02:00'), (1, 1, 3, null), (1, 1, 4, null), 
(1, 1, 5, '2020-01-01 15:04:00'), (1, 1, 6, '2020-01-01 15:06:00'), (1, 1, 7, '2020-01-01 15:08:00'), (1, 1, 8, null), 
(1, 1, 9, null), (1, 1, 10, null), (1, 1, 1, '2020-01-01 15:11:00'),

(1, 2, 1, '2020-01-01 16:00:00'), (1, 2, 2, '2020-01-01 16:03:00'), (1, 2, 3, null), (1, 2, 4, null), 
(1, 2, 5, '2020-01-01 16:06:00'), (1, 2, 6, '2020-01-01 16:08:00'), (1, 2, 7, '2020-01-01 16:10:00'), (1, 2, 8, null), 
(1, 2, 9, null), (1, 2, 10, null), (1, 2, 1, '2020-01-01 16:14:00');


INSERT INTO `Bus` VALUES (1, 2, 2, 0);