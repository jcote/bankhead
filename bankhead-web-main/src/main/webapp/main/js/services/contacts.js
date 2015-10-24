
angular.module('bankheadApp')
    .factory('Contacts', function($resource){
        return $resource('api/contact/contact/');
    });


