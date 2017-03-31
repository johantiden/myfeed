
app.controller('myCtrl', function($scope, $location, $sce, $cookies, $window, documentService) {

    var limitStep = 10;
    $scope.itemLimit = limitStep;

    $scope.$sce = $sce;
    $scope.$location = $location;
    var user = getParameterByName('user');

    $scope.getCookieValue = function () {
        return $cookies.get('searchText');
    };

    var search = getParameterByName('search');
    if (search) {
        $scope.searchText = search;
    } else {
        $scope.searchText = $scope.getCookieValue();

    }

    $scope.$watch('searchText', function(newValue) {
        $cookies.put('searchText', newValue);
    });

    $scope.setDocumentRead = function(item, read, callback) {
        //alert("item '"+ item.pageUrl +"' READ was changed to:"+item.read);
        item.read = read;
        item.username = user;
        documentService.putItem(item, callback);

        var isAnimate = callback === undefined; // only animate if there is no other callback (e.g. open link)
        if (isAnimate) {
            item.hide = true;
        }
    };


    $scope.goFunc = function(url) { // lambdas please.   () => go(url)
        return function() {
            $scope.go(url);
        };
    };

    $scope.go = function(url) {
        $window.location.href = url;
    };


    documentService.getUnread(user, function(json) {
        $scope.items = json;
    });

    $scope.increaseLimit = function() {
        limitStep = limitStep + 1;
        $scope.itemLimit = limitStep;
    };


    /**
     * Gets a query parameter.
     * johan:
     * - Can't get $location.search() working.
     * - Can't get ngRoute to work...
     */
    function getParameterByName(name) {
        var url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }
});