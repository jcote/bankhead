/* Created by alaaawad on 12/30/13.*/

var bankheadAdminApp = angular.module('bankheadAdminApp', [
    'ngRoute',
    'landingControllers'
]);


bankheadAdminApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
	        when('/landing', {
	            templateUrl: 'partials/landing.html',
	            controller: 'LandingCtrl'
	        }).
            otherwise({
                redirectTo: '/landing'
            });
    }]);

