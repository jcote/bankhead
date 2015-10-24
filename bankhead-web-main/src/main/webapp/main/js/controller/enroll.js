angular.module('bankheadApp')
    .controller('EnrollCtrl', ['$scope', '$http', 'PageTitle', function ($scope, $http, PageTitle) {

        PageTitle.setTitle('Enroll');

         $scope.isAlertShown = false;
         $scope.alertStatus = "info";
         
         $scope.enroll = function(user) {
          $http.defaults.headers.common['X-bankhead-Username'] = user.name;
          $http.defaults.headers.common['X-bankhead-Hashpass'] = CryptoJS.SHA256('com.bankhead|v1|' + user.name + '|' + user.password);
          
          $http.post('enroll').
            success(function(data) {
              $scope.alertText = "Success!";
              $scope.alertStatus = "success";
              $scope.isAlertShown = true;
            }).
            error(function(data) {
              $scope.alertText = "Error: " + data;
              $scope.alertStatus = "danger";
              $scope.isAlertShown = true;
            });
          
          $http.defaults.headers.common['X-bankhead-Username'] = '';
          $http.defaults.headers.common['X-bankhead-Hashpass'] = '';
         };
        }]);

