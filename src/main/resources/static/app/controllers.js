
app.controller('myCtrl', function($scope, $cookies, unreadservice) {

    $scope.getCookieValue = function () {
        return $cookies.get('searchText');
    };

    $scope.searchText = $scope.getCookieValue();

    $scope.$watch('searchText', function(newValue) {
        $cookies.put('searchText', newValue);
    });

    unreadservice.loadData(function(json) {
        $scope.items = json;
    });
});