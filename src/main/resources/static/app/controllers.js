
app.controller('myCtrl', function($scope, unreadservice) {
    unreadservice.loadData(function(json) {
        $scope.items = json;
    });
});