
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
        var includes =
            item.category.name.includes('News') ||
            item.category.name === 'news' ||
            item.category.name === 'worldnews' ||
            item.category.name === 'politics' ||
            item.feed.name === 'TheLocal' ||
            item.feed.name === 'Dagens Nyheter' ||
            item.feed.name === 'Svenska Dagbladet' ||
            item.author.name === '@annieloof' || // questionable :)
            item.author.name === '@kinbergbatra'; // questionable :)
        return !item.read && includes;
    };

    var techFilter = function(item) {
        var tech =
            item.feed.name === 'Ars Technica' ||
            item.feed.name === 'Slashdot' ||
            item.feed.name === 'xkcd' ||
            item.feed.name === 'HackerNews' ||
            item.category.name === 'science' ||
            item.author.name === '@github' ||
            item.author.name === '@tastapod';

        return !item.read && tech;
    };

    var funFilter = function(item) {
        var fun =
            item.author.name === '@deepdarkfears' ||
            item.feed.name === 'xkcd' ||
            item.category.name === 'AskReddit' ||
            item.category.name === 'todayilearned' ||
            item.category.name === 'mildlyinteresting' ||
            item.category.name === 'funny' ||
            item.category.name === 'gifs' ||
            item.category.name === 'pics';

        return !item.read && fun;
    };

    var readFilter = function(item) {
        return item.read;
    };

    var unmatchedFilter = function(item) {
        for (var filterName in $scope.radioFilters) {
            if (filterName !== 'All' &&
                    filterName !== 'Unmatched' &&
                    $scope.radioFilters[filterName](item)) {
                return false;
            }
        }
        return true;
    };


    $scope.radioFilters = {
        'All' : unreadFilter,
        'News' : newsFilter,
        'Tech' : techFilter,
        'Fun' : funFilter,
        'Read' : readFilter,
        'Unmatched' : unmatchedFilter,
    };


    $scope.radioFilter = function(item) {
        return $scope.radioFilters[$scope.radioFilterName](item);
    };

    $scope.radioFilterName = $cookies.get('radioFilterName');
    if ($scope.radioFilterName === undefined) {
        $scope.radioFilterName = 'All';
    }

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
