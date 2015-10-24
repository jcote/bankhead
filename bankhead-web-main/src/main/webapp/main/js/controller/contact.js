angular.module('bankheadApp')
    .controller('ContactCtrl', ['$scope','Contacts', function($scope, Contacts){

        $scope.isSuccessShown = false;
        $scope.isErrorShown = false;

        $scope.create = function(item)
        {
            console.log(item);
            var contact = new Contacts(item);
            contact.email = item.email;
            contact.zipCode = item.zipCode;
            console.log(contact);

            contact.$save(
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
