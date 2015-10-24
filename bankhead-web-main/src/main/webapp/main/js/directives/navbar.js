angular.module('bankheadApp')
    .directive("navbar", function() {
      return {
        restrict: "E",
        replace: true,
        transclude: true,
        controller: 'NavbarCtrl',
        templateUrl: 'partials/navbar.html'
      };
    });