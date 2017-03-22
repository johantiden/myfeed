
app.controller('myCtrl', function($scope, $sce, $cookies, unreadservice) {

    $scope.$sce = $sce;

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