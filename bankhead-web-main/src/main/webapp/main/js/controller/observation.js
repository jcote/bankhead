angular.module('bankheadApp')
    .controller('ObservationCtrl', ['$scope','Observations', function($scope, Observation){

        $scope.isSuccessShown = false;
        $scope.isErrorShown = false;
        $scope.isFirstFocus = true;

        $scope.create = function(item)
        {
            var observation = new Observation();
            observation.text = item.text;
            console.log(observation);

            observation.$save(
              function(data, responseHeaders) { 
                $scope.isErrorShown = false;
                $scope.isSuccessShown = true;
              },
              function(data, responseHeaders) {
                $scope.isSuccessShown = false;
                $scope.isErrorShown = true;
            });

        };

        $scope.onFocusHandle = function(item) {
            if ($scope.isFirstFocus) {
                item.text = "";
                $scope.isFirstFocus = false;
            }
        };

    }]);
