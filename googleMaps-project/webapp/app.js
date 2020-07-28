'use strict';

angular.module('myApp', [
  'ngRoute',
  'myApp.Home',
]).
  config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
    $locationProvider.hashPrefix('!');
    $routeProvider.otherwise({ redirectTo: '/Home' });
    $routeProvider.when('/Home', {
      templateUrl: 'Home/Home.html',
      controller: 'HomeController'
    });
  }]);
