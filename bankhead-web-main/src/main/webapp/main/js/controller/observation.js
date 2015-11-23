angular.module('bankheadApp')
    .controller('ObservationCtrl', ['$scope','Observations', function($scope, Observation){

        $scope.isSuccessShown = false;
        $scope.isErrorShown = false;

        $scope.create = function(item)
        {
            console.log(item);
            var observation = new Observation(item);
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

    }]);
