
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
                item.flagged = item.flagged || flagPredicate(item);
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

    $scope.markFilteredAsRead = function() {
        if (confirm("Are you sure?")) {
            $scope.items.forEach(function (item) {
                if ($scope.radioFilter(item)) {
                    $scope.setDocumentRead(item, true);
                }
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

    var flagPredicate = function(item) {
        return contains(item, 'quiz') ||
            contains(item, 'up vote') ||
            contains(item, 'här är') ||
            contains(item, 'tipsen') ||
            contains(item, '-- number of people');
    };

    var flagFilter = function(item) {
        return flagPredicate(item) && !item.read && !badFilter(item);
    };

    var badFilter = function(i) {
        var bad =
            categoryContains(i, 'sport') ||
            categoryContains(i, 'kultur') ||
            categoryContains(i, 'insidan') ||
            categoryContains(i, 'idagsidan') ||
            (categoryContains(i, 'mat') && categoryContains(i, 'dryck')) ||
            (categoryContains(i, 'mat') && categoryContains(i, 'vin')) ||
            categoryContains(i, 'resor') ||
            categoryContains(i, 'webb-tv') ||
            categoryContains(i, 'dnbok') ||
            categoryContains(i, 'familj') ||
            (isFrom(i, 'hackernews') && contains(i, 'hiring')) ||
            contains(i, 'upvote') ||
            (isFrom(i, "ars") && categoryContains("dealmaster")) ||
            (isFrom(i, "svenska dagbladet") && categoryContains("perfect guide")) ||
            contains(i, "trump")


                ;

        return !i.read && bad;
    };

    var unreadFilter = function(item) {
        return !item.read;
    };

    var newsFilter = function(item) {
        var news =
            item.category.name.includes('News') ||
            item.category.name === 'news' ||
            item.category.name === 'worldnews' ||
            item.category.name === 'politics' ||
            item.feed.name === 'TheLocal' ||
            item.feed.name === 'Dagens Nyheter' ||
            item.feed.name === 'Svenska Dagbladet' ||
            item.author.name === '@annieloof' || // questionable :)
            item.author.name === '@kinbergbatra'; // questionable :)

        return !item.read && news && !badFilter(item);
    };

    var techFilter = function(item) {
        var tech =
            isFrom(item, 'Ars Technica') ||
            isFrom(item, 'Slashdot') ||
            isFrom(item, 'xkcd') ||
            isFrom(item, 'HackerNews') ||
            item.category.name === 'science' ||
            item.author.name === '@github' ||
            item.author.name === '@elonmusk' ||
            item.author.name === '@tastapod';

        return !item.read && tech && !badFilter(item);
    };

    var funFilter = function(item) {
        var fun =
            item.author.name === '@deepdarkfears' ||
            isFrom(item, 'xkcd') ||
            item.category.name === 'AskReddit' ||
            item.category.name === 'todayilearned' ||
            item.category.name === 'mildlyinteresting' ||
            item.category.name === 'funny' ||
            item.category.name === 'gifs' ||
            item.category.name === 'pics';

        return !item.read && fun && !badFilter(item);
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

    $scope.withFilter = function(filterName) {
        return function(item) {
            return $scope.radioFilters[filterName](item);
        }
    };

    $scope.radioFilters = {
        'All' : unreadFilter,
        'News' : newsFilter,
        'Tech' : techFilter,
        'Fun' : funFilter,
        'Read' : readFilter,
        'Bad' : badFilter,
        'Unmatched' : unmatchedFilter,
        'Flagged' : flagFilter,
    };

    $scope.selectFilter = function(filterName) {
        $scope.radioFilterName = filterName;
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

    function isFrom(item, feedName) {
        if (item.feed === undefined) {
            throw "Illegal item, there is no feed";
        }
        return item.feed.name === feedName;
    }

    function categoryContains(item, str) {
        return item.category.name.includes(str);
    }

    function contains(item, str) {
        var mergedString = '';
        mergedString += item.author.name;
        mergedString += item.category.name;
        mergedString += item.cssClass;
        mergedString += item.feed.name;
        mergedString += item.html;
        mergedString += item.text;
        mergedString += item.title;
        mergedString = mergedString.toLowerCase();
        return mergedString.includes(str);
    }
});
