
app.controller('myCtrl', function($scope, bubbservice) {
    //alert("hej myCtrl");
    bubbservice.loadData(function(json) {
        $scope.bullar = json;
    });
});