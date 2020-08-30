'use strict';

let map;

function initMap($http) {
    var origen = {
        lat: -29.762585,
        lng: -51.150705
    }

    var destiny = {
        lat: -29.765852, 
        lng: -51.146756
    }
    
    map = new google.maps.Map(document.getElementById("map"), {
        center: origen,
        zoom: 15
    });

    var marker1 = new google.maps.Marker({
        position: origen, 
        map: map,
        label: "P"
    });

    var marker2 = new google.maps.Marker({
        position: destiny, 
        map: map,
        label: "C"
    });

    $http.get('http://localhost:8080/busStop?lat1='+origen.lat+'&longe1='+origen.lng+'&lat2='+destiny.lat+'&longe2='+destiny.lng)
    .then(function successCallback(response) {

          const flightPath = new google.maps.Polyline({
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


angular.module('myApp.Home', ['ngRoute'])
    .controller('HomeController', function ($scope, $http) {   
        initMap($http);
    });
