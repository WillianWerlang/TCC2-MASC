'use strict';

let map;
let marker1;
let marker2;
let flightPath;

angular.module('myApp.Home', ['ngRoute'])
    .controller('HomeController', function ($scope, $http) {   
        
        var origin = {
            lat: -29.762585,
            lng: -51.150705
        }
    
        initMap(origin, $http);

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

                showRoute(origin, destination, $http);
              });
        }
    });


function clearMap() {
    google.maps.event.clearInstanceListeners(map);

    if (typeof marker2 !== 'undefined') {
        marker2.setMap(null);
    }

    if (typeof flightPath !== 'undefined') {
        flightPath.setMap(null);
    }
}

function showRoute (origin, destination, $http) {

    marker2 = new google.maps.Marker({
        position: destination, 
        map: map,
        label: "C"
    });

    callBusRoute(origin, destination, $http);
}    

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

function callBusRoute(origin, destination, $http) {
    $http.get('http://localhost:8080/busStop?lat1='+origin.lat+'&longe1='+origin.lng+'&lat2='+destination.lat+'&longe2='+destination.lng)
    .then(function successCallback(response) {

          flightPath = new google.maps.Polyline({
            path: formatRoute(response.data.Positions, response.data.Start, response.data.End),
            geodesic: true,
            strokeColor: "#FF0000",
            strokeOpacity: 1.0,
            strokeWeight: 2
          });

          flightPath.setMap(map);

    }, function errorCallback(response) {
    });
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

