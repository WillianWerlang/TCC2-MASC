'use strict';

let map;
let marker1;
let marker2;
let linePath;

angular.module('myApp.Home', ['ngRoute'])
    .controller('HomeController', function ($scope, $http) {
        $scope.scheduleIsSelected = false;
        $scope.selectDestination = function () {
            map.addListener('click', function (e) {

                var myEl = angular.element( document.querySelector('#selectedRoute'));
                myEl.addClass('alert-info');
                myEl.removeClass('alert-success');

                $scope.destination = {
                    lat: e.latLng.lat(),
                    lng: e.latLng.lng()
                }
                $scope.currentRouteID = 0;
                $scope.clearMap();
                $scope.callBusRouteInformation();
            });
        }

        $scope.selectSchedule = function (scheduleID) {
            var myEl;
            for (var i = 0; i < $scope.currentSchedules.length; i++) {
                myEl = angular.element(document.querySelector('#scheduleSelectedID'+$scope.currentSchedules[i].ScheduleID));
                myEl.removeClass('active');
            }

            myEl = angular.element(document.querySelector('#scheduleSelectedID'+scheduleID));
            myEl.addClass('active');

            
            $scope.currentSchedule = $scope.retrieveScheduleByID(scheduleID);
            
        }

        $scope.nextRoute = function () {
            $scope.clearMap();
            $scope.currentRouteID = $scope.getNextRouteID($scope.routes, $scope.currentRouteID);
            $scope.callBusRouteInformation();
        }

        $scope.chooseRoute = function () {
            $scope.routeIsSelected = true;
            var myEl = angular.element( document.querySelector('#selectedRoute'));
            myEl.addClass('alert-success');
            myEl.removeClass('alert-info');
            $scope.currentSchedules = $scope.retrievePossibleSchedules($scope.currentRouteID);
        }

        $scope.initMap = function () {

            map = new google.maps.Map(document.getElementById("map"), {
                center: $scope.origin,
                zoom: 15
            });

            marker1 = new google.maps.Marker({
                position: $scope.origin,
                map: map,
                label: "P"
            });
        }

        $scope.clearMap = function () {
            google.maps.event.clearInstanceListeners(map);

            if (typeof marker2 !== 'undefined') {
                marker2.setMap(null);
            }

            if (typeof linePath !== 'undefined') {
                linePath.setMap(null);
            }
        }

        $scope.showRoute = function (routeInformation) {
            $scope.currentRouteID = routeInformation.BusRouteID;
            var route = $scope.retrieveRoute(routeInformation.BusRouteID);
            var routePositions = $scope.formatRoute(route.Positions, routeInformation.Start, routeInformation.End);
            $scope.currentRouteDescription = route.BusRouteName;
            $scope.showRouteInMap(routePositions);

            marker2 = new google.maps.Marker({
                position: $scope.destination,
                map: map,
                label: "C"
            });
        }

        $scope.showRouteInMap = function (positions) {
            linePath = new google.maps.Polyline({
                path: positions,
                geodesic: true,
                strokeColor: "#FF0000",
                strokeOpacity: 1.0,
                strokeWeight: 2
            });

            linePath.setMap(map);
        }

        $scope.getNextRouteID = function (routes, currentID) {
            for (var i = 0; i < routes.length; i++) {
                if (routes[i].BusRouteID == currentID) {
                    if (i + 1 < routes.length) {
                        return routes[i + 1].BusRouteID;
                    } else {
                        return routes[0].BusRouteID;
                    }
                }
            }
        }

        $scope.retrieveRoute = function (ID) {
            for (var i = 0; i < $scope.routes.length; i++) {
                if ($scope.routes[i].BusRouteID == ID) {
                    return $scope.routes[i];
                }
            }
        }

        $scope.retrievePossibleSchedules = function (busRouteID) {
            var schedules = [];

            for (var i = 0; i < $scope.schedules.length; i++) {
                if ($scope.schedules[i].BusRouteID == busRouteID) {
                    schedules.push($scope.schedules[i]);
                }
            }

            return schedules;
        }

        $scope.retrieveScheduleByID = function (scheduleID) {
            for (var i = 0; i < $scope.schedules.length; i++) {
                if ($scope.schedules[i].ScheduleID == scheduleID){
                    return $scope.schedules[i];
                }
            }
        }

        $scope.formatRoute = function (positions, start, end) {
            var result = [];

            if (start < end) {
                for (var i = start; i <= end; i++) {
                    result.push(positions[i]);
                }
            } else {
                for (var i = start; i < positions.length; i++) {
                    result.push(positions[i]);
                }

                for (var j = 1; j <= end; j++) {
                    result.push(positions[j]);
                }
            }

            return result;
        }

        $scope.callAllSchedules = function () {
            $http.get('http://localhost:8080/allBusSchedules')
                .then(function successCallback(response) {
                    $scope.schedules = response.data;
                });
        }

        $scope.callAllRoutes = function () {
            $http.get('http://localhost:8080/allBusRoutes')
                .then(function successCallback(response) {
                    $scope.routes = response.data;
                    $scope.callAllSchedules();
                });
        }

        $scope.callBusRouteInformation = function () {
            $http.get('http://localhost:8080/busRouteDistance?lat1=' + $scope.origin.lat + '&lng1=' + $scope.origin.lng + '&lat2=' + $scope.destination.lat + '&lng2=' + $scope.destination.lng + '&routeID=' + $scope.currentRouteID)
                .then(function successCallback(response) {
                    $scope.showRoute(response.data);
                });
        }

        $scope.origin = {
            lat: -29.762585,
            lng: -51.150705
        }
        
        $scope.currentRouteID = 0;
        $scope.initMap(origin);
        $scope.callAllRoutes();
        
    });