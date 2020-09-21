'use strict';

let map;
let marker1;
let marker2;
let flightPath;
let routes;

angular.module('myApp.Home', ['ngRoute'])
    .controller('HomeController', function ($scope, $http) {   
        
        var origin = {
            lat: -29.762585,
            lng: -51.150705
        }
    
        initMap(origin, $http);
        callAllRoutes($http);

        $scope.selectDestination = function() {
            map.addListener('click', function(e) {
                clearMap();
                $scope.showAddress = true;
                $scope.selectedAddress = e.latLng;
                $scope.$apply() 

                var destination = {
                    lat: e.latLng.lat(),
                    lng: e.latLng.lng()
                }

                callBusRouteInformation(origin, destination, 0, $http);
              });
        }
    });

function initMap(origin, $http) {

    map = new google.maps.Map(document.getElementById("map"), {
        center: origin,
        zoom: 15
    });

    marker1 = new google.maps.Marker({
        position: origin, 
        map: map,
        label: "P"
    });
}    

function clearMap() {
    google.maps.event.clearInstanceListeners(map);

    if (typeof marker2 !== 'undefined') {
        marker2.setMap(null);
    }

    if (typeof flightPath !== 'undefined') {
        flightPath.setMap(null);
    }
}

function callAllRoutes($http) {
    $http.get('http://localhost:8080/allBusRoutes')
    .then(function successCallback(response) {
        routes = response.data;
    });
}

function callBusRouteInformation(origin, destination, busRouteID, $http) {
    $http.get('http://localhost:8080/busRouteDistance?lat1=' + origin.lat + '&lng1=' + origin.lng + '&lat2=' + destination.lat + '&lng2=' + destination.lng + '&routeID=' + busRouteID)
    .then(function successCallback(response) {
          showRoute(origin, destination, response.data, $http);
    });
}

function showRoute(origin, destination, routeInformation, $http) {

    var route = retrieveRoute(routeInformation.BusRouteID); 
    var routePositions = formatRoute(route.Positions, routeInformation.Start, routeInformation.End);

    showRouteInMap(routePositions);

    marker2 = new google.maps.Marker({
        position: destination, 
        map: map,
        label: "C"
    }); 
}    

function retrieveRoute(ID) {
    for (var i = 0; i < routes.length; i++) {
        if (routes[i].BusRouteID == ID) {
            return routes[i];
        }
    }
}

function showRouteInMap(positions) {
    flightPath = new google.maps.Polyline({
        path: positions,
        geodesic: true,
        strokeColor: "#FF0000",
        strokeOpacity: 1.0,
        strokeWeight: 2
      });

      flightPath.setMap(map);
}

function formatRoute(positions, start, end) {
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
