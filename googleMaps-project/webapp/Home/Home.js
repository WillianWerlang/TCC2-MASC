'use strict';

let map;


function initMap(data) {

    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: parseInt(data[0][0].lat), lng: parseInt(data[0][0].lon) },
        zoom: 8
    });

    var dataHeatmap = {
        data: [],
        opacity: 1
    }


    for (var i = 0; i < data.length; i++) {
        for (var j = 0; j < data[i].length; j++) {
            dataHeatmap.data.push(
                { location: new google.maps.LatLng(data[i][j].lat, parseFloat(data[i][j].lon)), weight: 1 }
            )
        }
    }

    /*
       dataHeatmap.data.push(new google.maps.LatLng(-29.763493142125790, -51.148388433140630));
       dataHeatmap.data.push(new google.maps.LatLng(-29.762957067549596, -51.151989698410034));
       */

    var heatmap = new google.maps.visualization.HeatmapLayer(dataHeatmap);
    heatmap.setMap(map);
}

angular.module('myApp.Home', ['ngRoute'])
    .controller('HomeController', function ($scope, $http) {

        $http.get('http://ec2-34-217-138-53.us-west-2.compute.amazonaws.com:8089/rotasMaisAcessadas')
            .then(function successCallback(response) {
                initMap(response.data);
            }, function errorCallback(response) {
            });
    });
