
angular.module('bankheadApp')
    .factory('Observations', function($resource){
        return $resource('api/cognition/observation/');
    });


