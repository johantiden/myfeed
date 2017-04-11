
app.controller('myCtrl', function($scope, $location, $sce, $cookies, $window, documentService) {

    $scope.items = [];

    var limitStep = 10;
    $scope.itemLimit = limitStep;

    $scope.$sce = $sce;
    $scope.$location = $location;
    var user = getParameterByName('user');




    $scope.setDocumentRead = function(item, read, callback) {
        item.read = read;
        item.username = user;
        $scope.itemLimit += 1;
        documentService.putItem(item, callback);
    };

    documentService.getAllKeys(user, function(json) {
        $scope.keys = json;
    });

    $scope.$watch('keys', function(keys) {
        if (keys === undefined) {
            return;
        }
        keys.forEach(function(key) {
            documentService.getItem(key, function(item) {
                $scope.items.push(item);
            });
        });
    });

    $scope.getItem = function(key) {
        documentService.getItem(key, function(item) {
            $scope.items.push(item);
        })
    };

    $scope.radioFilter = {};

    $scope.markAllAsRead = function() {
        if (confirm("Are you sure?")) {
            $scope.items.forEach(function (item) {
                $scope.setDocumentRead(item, true);
            });
        }
    };

    $scope.increaseLimit = function() {
        limitStep += 1;
        $scope.itemLimit = limitStep;
    };



    $scope.unwrapRadioFilter = function(item) {
        return $scope.radioFilter.predicate(item);
    };


    var unreadFilter = function(item) {
        return !item.read;
    };

    var newsFilter = function(item) {
        var includes = item.category.name.includes('ews');
        return !item.read && includes;
    };

    var arsFilter = function(item) {
        var includes = item.feed.name === 'Ars Technica';
        return !item.read && includes;
    };

    var readFilter = function(item) {
        return item.read;
    };

    $scope.radioFilter = function(item) {
        return $scope.radioFilters[$scope.radioFilterName](item);
    };


    $scope.radioFilters = {
        "All" : unreadFilter,
        "News" : newsFilter,
        "Ars" : arsFilter,
        "Read" : readFilter,
    };


    $scope.radioFilterName = $cookies.get('radioFilterName');

    $scope.$watch('radioFilterName', function(newValue) {
        $cookies.put('radioFilterName', newValue);
    });

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
