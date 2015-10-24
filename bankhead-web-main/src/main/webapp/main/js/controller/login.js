angular.module('bankheadApp')
    .controller('LoginCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.login = function(user) {
          $http.defaults.headers.common['X-Bankhead-Username'] = user.name;
          $http.defaults.headers.common['X-Bankhead-Hashpass'] = CryptoJS.SHA256('com.bankhead|v1|' + user.name + '|' + user.password);
          $http.post('login').success(function(data) {
             alert('hi');
             });
          $http.defaults.headers.common['X-Bankhead-Username'] = '';
          $http.defaults.headers.common['X-Bankhead-Hashpass'] = '';
        };
    }]);

