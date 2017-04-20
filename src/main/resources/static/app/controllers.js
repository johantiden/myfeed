
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

        clearCache(item);

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
        var flagged = contains(item, 'quiz') ||
            contains(item, 'up vote') ||
            contains(item, 'här är') ||
            contains(item, 'tipsen') ||
            contains(item, '-- number of people') ||
            contains(item, "trump");

        return !item.read && !badFilter(item) && flagged;
    };

    var badPredicate = function(i) {
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
                (isFrom(i, "ars") && categoryContains(i, "dealmaster")) ||
                (isFrom(i, "ars") && categoryContains(i, "opposable thumbs")) ||
                (isFrom(i, "ars") && categoryContains(i, "air force")) ||
                (isFrom(i, "ars") && categoryContains(i, "laptop")) ||
                (isFrom(i, "svenska dagbladet") && categoryContains(i, "perfect guide")) ||
                (isFrom(i, "reddit") && categoryContains(i, "iama")) ||
                (isFrom(i, "reddit") && categoryContains(i, "wtf")) ||
                (isFrom(i, "reddit") && categoryContains(i, "blackpeopletwitter")) ||
                (isFrom(i, "reddit") && categoryContains(i, "porn")) || // earthporn, etc
                (isFrom(i, "reddit") && categoryContains(i, "twoxchromosomes")) ||
                (isFrom(i, "reddit") && categoryContains(i, "art")) ||
                (isFrom(i, "reddit") && categoryContains(i, "ps4")) ||
                (isFrom(i, "reddit") && categoryContains(i, "quityourbullshit")) ||
                (isFrom(i, "reddit") && categoryContains(i, "hearthstone")) ||
                (isFrom(i, "reddit") && categoryContains(i, "oldschoolcool")) ||
                (isFrom(i, "reddit") && categoryContains(i, "getmotivated")) ||
                (isFrom(i, "reddit") && categoryContains(i, "me_irl")) ||
                (isFrom(i, "reddit") && categoryContains(i, "instant_regret")) ||
                (isFrom(i, "reddit") && categoryContains(i, "wholesomememes")) ||
                (isFrom(i, "reddit") && categoryContains(i, "reallifedoodles")) ||
                (isFrom(i, "reddit") && categoryContains(i, "adviceanimals")) ||
                (isFrom(i, "reddit") && categoryContains(i, "interestingasfuck")) ||
                (isFrom(i, "reddit") && categoryContains(i, "facepalm")) ||
                (isFrom(i, "reddit") && categoryContains(i, "perfecttiming")) ||
                (isFrom(i, "reddit") && categoryContains(i, "bidenbro")) ||
                (isFrom(i, "reddit") && categoryContains(i, "woahdude")) ||
                (isFrom(i, "reddit") && categoryContains(i, "photoshopbattles")) ||
                (isFrom(i, "reddit") && categoryContains(i, "overwatch")) ||
                (isFrom(i, "reddit") && categoryContains(i, "animalcrossing")) ||
                (isFrom(i, "reddit") && categoryContains(i, "empiredidnothingwrong")) ||
                (isFrom(i, "reddit") && categoryContains(i, "politicalhumor")) ||
                (isFrom(i, "reddit") && categoryContains(i, "polandball")) ||
                (isFrom(i, "reddit") && categoryContains(i, "eyebleach")) ||

                contains(i, 'medan du sov') ||
                contains(i, 'nutidstestet') ||
                contains(i, 'join us') ||
                contains(i, 'upvote')

            ;
        return bad && !i.read;
    };

    var newsPredicate = function(item) {
        var news =
            item.category.name.includes('News') ||
            item.category.name === 'news' ||
            item.category.name === 'worldnews' ||
            item.category.name === 'politics' ||
            isFrom(item, 'thelocal') ||
            (isFrom(item, 'dagens nyheter') && categoryContains(item, 'nyheter')) ||
            (isFrom(item, 'dagens nyheter') && categoryContains(item, 'ekonomi')) ||
            (isFrom(item, 'dagens nyheter') && categoryContains(item, 'sthlm')) ||
            (isFrom(item, 'dagens nyheter') && categoryContains(item, 'debatt')) ||
            (isFrom(item, 'dagens nyheter') && categoryContains(item, 'ledare')) ||
            (isFrom(item, 'svenska dagbladet') && categoryContains(item, 'världen')) ||
            (isFrom(item, 'svenska dagbladet') && categoryContains(item, 'näringsliv')) ||
            (isFrom(item, 'svenska dagbladet') && categoryContains(item, 'debatt')) ||
            (isFrom(item, 'svenska dagbladet') && categoryContains(item, 'sverige')) ||
            (isFrom(item, 'svenska dagbladet') && categoryContains(item, 'ledare')) ||
            item.author.name === '@kinbergbatra'; // questionable :)

        return !item.read && news && !badFilter(item);
    };

    var techPredicate = function(item) {
        var tech =
            isFrom(item, 'ars technica') ||
            isFrom(item, 'slashdot') ||
            isFrom(item, 'xkcd') ||
            isFrom(item, 'hackernews') ||
            (isFrom(item, 'reddit') && categoryContains(item, 'space')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'futurology')) ||
            item.category.name === 'science' ||
            item.author.name === '@github' ||
            item.author.name === '@elonmusk' ||
            item.author.name === '@tastapod';

        return !item.read && tech && !badFilter(item);
    };

    var funPredicate = function(item) {
        var fun =
            item.author.name === '@deepdarkfears' ||
            isFrom(item, 'xkcd') ||
            categoryContains(item, 'askreddit') ||
            categoryContains(item, 'todayilearned') ||
            categoryContains(item, 'mildlyinteresting') ||
            categoryContains(item, 'funny') ||
            categoryContains(item, 'gifs') ||
            (isFrom(item, 'reddit') && categoryContains(item, 'television')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'movies')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'aww')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'gaming')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'videos')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'showerthoughts')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'unexpected')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'oddlysatisfying')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'nottheonion')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'latestagecapitalism')) ||
            (isFrom(item, 'reddit') && categoryContains(item, 'crappydesign')) ||
            categoryContains(item, 'pics');

        return !item.read && fun && !badFilter(item);
    };

    var unmatchedPredicate = function(item) {
        for (var filterName in $scope.radioFilters) {
            if (filterName !== 'All' &&
                    filterName !== 'Unmatched' &&
                    $scope.radioFilters[filterName](item)) {
                return false;
            }
        }
        return true;
    };

    var badFilter = function(item) {
        if (item.isBad === undefined) {
            item.isBad = badPredicate(item);
        }
        return item.isBad;
    };


    var flagFilter = function(item) {
        if (item.isFlagged === undefined) {
            item.isFlagged = flagPredicate(item);
        }
        return item.isFlagged;
    };

    var allFilter = function(item) {
        return !item.read && !badFilter(item);
    };

    var techFilter = function(item) {
        if (item.isTech === undefined) {
            item.isTech = techPredicate(item);
        }
        return item.isTech;
    };

    var funFilter = function(item) {
        if (item.isFun === undefined) {
            item.isFun = funPredicate(item);
        }
        return item.isFun;
    };

    var readFilter = function(item) {
        return item.read;
    };

    var unmatchedFilter = function(item) {
        if (item.isUnmatched === undefined) {
            item.isUnmatched = unmatchedPredicate(item);
        }
        return item.isUnmatched;
    };

    var newsFilter = function(item) {
        if (item.isNews === undefined) {
            item.isNews = newsPredicate(item);
        }
        return item.isNews;
    };

    $scope.radioFilters = {
        'All' : allFilter,
        'News' : newsFilter,
        'Tech' : techFilter,
        'Fun' : funFilter,
        'Read' : readFilter,
        'Bad' : badFilter,
        'Unmatched' : unmatchedFilter,
        'Flagged' : flagFilter,
    };

    var clearCache = function(item) {
        item.isUnmatched = undefined;
        item.isNews = undefined;
        item.isFun = undefined;
        item.isTech = undefined;
        item.isFlagged = undefined;
        item.isBad = undefined;
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

    $scope.withFilter = function(filterName) {
        return function(item) {
            return $scope.radioFilters[filterName](item);
        }
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

    function isFrom(item, feedName) {
        return item.feed.name.toLowerCase().includes(feedName);
    }

    function categoryContains(item, str) {
        return item.category.name.toLowerCase().includes(str);
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
