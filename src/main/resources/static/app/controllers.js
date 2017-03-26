
app.controller('myCtrl', function($scope, $sce, $cookies, documentService) {



    $scope.$sce = $sce;

    $scope.getCookieValue = function () {
        return $cookies.get('searchText');
    };

    $scope.searchText = $scope.getCookieValue();

    $scope.$watch('searchText', function(newValue) {
        $cookies.put('searchText', newValue);
    });

    $scope.setDocumentRead = function(item){
        //alert("item '"+ item.pageUrl +"' READ was changed to:"+item.read);
        documentService.putItem(item);
    };


    documentService.getUnread(function(json) {
        $scope.items = json;
    });
});