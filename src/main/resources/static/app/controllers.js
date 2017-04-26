
app.controller('myCtrl', function($scope, $location, $sce, $cookies, $window, documentService) { // jshint ignore:line
    "use strict";
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
        });
    };

    $scope.radioFilter = {};

    $scope.markFilteredAsRead = function() {
        if (confirm("Are you sure?")) { // jshint ignore:line
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
            (isFrom(item, 'new york times') && anyCategoryIs(item, undefined)) ||
            contains(item, "trump");

        return !item.read && !badFilter(item) && flagged;
    };

    var badPredicate = function(i) {
        var bad =
                (isFrom(i, 'hackernews') && contains(i, 'hiring')) ||
                (isFrom(i, "ars") && anyCategoryIs(i, "dealmaster")) ||
                (isFrom(i, "ars") && anyCategoryIs(i, "Opposable Thumbs")) ||
                (isFrom(i, "ars") && anyCategoryIs(i, "air force")) ||
                (isFrom(i, "ars") && anyCategoryIs(i, "laptop")) ||
                (isFrom(i, "svenska dagbladet") && anyCategoryIs(i, "perfect guide")) ||
                (isFrom(i, "svenska dagbladet") && anyCategoryIs(i, "junior")) ||
                (isFrom(i, "svenska dagbladet") && anyCategoryIs(i, "Sport")) ||
                (isFrom(i, "dagens nyheter") && anyCategoryIs(i, "dnbok")) ||
                (isFrom(i, "dagens nyheter") && anyCategoryIs(i, "sport")) ||
                (isFrom(i, "dagens nyheter") && anyCategoryIs(i, "kultur-noje")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "food")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "iama")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "wtf")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "blackpeopletwitter")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "EarthPorn")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "twoxchromosomes")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "Art")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "ps4")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "quityourbullshit")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "hearthstone")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "OldSchoolCool")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "getmotivated")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "me_irl")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "instant_regret")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "wholesomememes")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "reallifedoodles")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "adviceanimals")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "interestingasfuck")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "facepalm")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "perfecttiming")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "bidenbro")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "woahdude")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "photoshopbattles")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "overwatch")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "animalcrossing")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "empiredidnothingwrong")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "politicalhumor")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "polandball")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "eyebleach")) ||
                (isFrom(i, "reddit") && anyCategoryIs(i, "esist")) ||
                (isFrom(i, "new york times") && contains(i, "your") && contains(i, "briefing")) ||
                (isFrom(i, "new york times") && anyCategoryIs(i, "real estate")) ||
                (isFrom(i, "new york times") && anyCategoryIs(i, "unidentified flying objects")) ||
                (isFrom(i, "thelocal") && contains(i, "recipe:")) ||

                contains(i, 'zlatan') ||
                contains(i, 'ibrahimovic') ||
                contains(i, 'medan du sov') ||
                contains(i, 'nutidstestet') ||
                contains(i, 'join us') ||
                contains(i, 'upvote')

            ;
        return bad && !i.read;
    };

    function hasAuthor(item, name) {
        if (item.author == unefined) { // jshint ignore:line
            if (name === undefined) {
                return true;
            }
            return false;
        }
        return item.author.name === name;
    }

    var newsPredicate = function(item) {
        var news =
            (isFrom(item, 'reddit') && anyCategoryIs('news')) ||
            (isFrom(item, 'reddit') && anyCategoryIs('politics')) ||
            (isFrom(item, 'reddit') && anyCategoryIs('worldnews')) ||
            isFrom(item, 'thelocal') ||
            isFrom(item, 'al jazeera') ||
            (isFrom(item, 'dagens nyheter') && anyCategoryIs(item, 'nyheter')) ||
            (isFrom(item, 'dagens nyheter') && anyCategoryIs(item, 'sthlm')) ||
            (isFrom(item, 'dagens nyheter') && anyCategoryIs(item, 'debatt')) ||
            (isFrom(item, 'dagens nyheter') && anyCategoryIs(item, 'ledare')) ||
            (isFrom(item, 'svenska dagbladet') && anyCategoryIs(item, 'Världen')) ||
            (isFrom(item, 'svenska dagbladet') && anyCategoryIs(item, 'Debatt')) ||
            (isFrom(item, 'svenska dagbladet') && anyCategoryIs(item, 'Sverige')) ||
            (isFrom(item, 'svenska dagbladet') && anyCategoryIs(item, 'Ledare')) ||
            (isFrom(item, 'svenska dagbladet') && anyCategoryIs(item, 'Kultur')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Airlines and Airplanes')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Politics')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Politics and Government')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, "Coups D'Etat and Attempted Coups D'Etat")) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, "Demonstrations, Protests and Riots")) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Military')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Earthquake')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Tsunami')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Terrorism')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Police')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Human Rights')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'War Crimes')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Assad')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Australia')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'France')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Great britain')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Iran')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'India')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Italy')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Korea')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'North Korea')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Russia')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Syria')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Venezuela')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Ukraine')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, 'Afghanistan')) ||
            (isFrom(item, 'new york times') && anyCategoryIs('Ice')) ||
            (isFrom(item, 'new york times') && anyCategoryIs(item, undefined)) ||
            hasAuthor(item, '@kinbergbatra'); // questionable :)

        return !item.read && news && !badFilter(item);
    };

    var bizPredicate = function(item) {
        var biz =
            (isFrom(item, 'dagens nyheter') && anyCategoryIs(item, 'ekonomi')) ||
            (isFrom(item, 'svenska dagbladet') && anyCategoryIs(item, 'Näringsliv'));

        return !item.read && biz && !badFilter(item);
    };

    var techPredicate = function(item) {
        var tech =
            isFrom(item, 'slashdot') ||
            isFrom(item, 'xkcd') ||
            isFrom(item, 'hackernews') ||
            (isFrom(item, 'reddit') && anyCategoryIs('science')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'space')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'futurology')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'technology')) ||
            (isFrom(item, 'ars technica') && anyCategoryIs(item, 'Gear & Gadgets')) ||
            (isFrom(item, 'ars technica') && anyCategoryIs(item, 'Law & Disorder')) ||
            (isFrom(item, 'ars technica') && anyCategoryIs(item, 'Technology Lab')) ||
            (isFrom(item, 'ars technica') && anyCategoryIs(item, 'Ministry of Innovation')) ||
            (isFrom(item, 'ars technica') && anyCategoryIs(item, 'Scientific Method')) ||
            (isFrom(item, 'ars technica') && anyCategoryIs(item, 'Net Neutrality')) ||
            hasAuthor(item, '@github') ||
            hasAuthor(item, '@elonmusk') ||
            hasAuthor(item, '@tastapod');

        return !item.read && tech && !badFilter(item);
    };

    var funPredicate = function(item) {
        var fun =
            hasAuthor(item, '@deepdarkfears') ||
            isFrom(item, 'xkcd') ||
            anyCategoryIs(item, 'askreddit') ||
            anyCategoryIs(item, 'todayilearned') ||
            anyCategoryIs(item, 'mildlyinteresting') ||
            anyCategoryIs(item, 'funny') ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'television')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'movies')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'aww')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'gaming')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'videos')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'gifs')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'HighQualityGifs')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'Showerthoughts')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'unexpected')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'oddlysatisfying')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'nottheonion')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'latestagecapitalism')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'crappydesign')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'natureisfuckinglit')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'creepy')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'cringe')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'dataisbeautiful')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'LifeProTips')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'Fuckthealtright')) ||
            (isFrom(item, 'reddit') && anyCategoryIs(item, 'pussypassdenied')) ||
            anyCategoryIs(item, 'pics');

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

    var bizFilter = function(item) {
        if (item.isBiz === undefined) {
            item.isBiz = bizPredicate(item);
        }
        return item.isBiz;
    };

    $scope.radioFilters = {
        'All' : allFilter,
        'News' : newsFilter,
        'Biz' : bizFilter,
        'Tech' : techFilter,
        'Fun' : funFilter,
        'Read' : readFilter,
        'Bad' : badFilter,
        'Unmatched' : unmatchedFilter,
        'Flagged' : flagFilter
    };

    var clearCache = function(item) {
        item.isUnmatched = undefined;
        item.isNews = undefined;
        item.isFun = undefined;
        item.isTech = undefined;
        item.isFlagged = undefined;
        item.isBad = undefined;
        item.isBiz = undefined;
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
        };
    };




    /**
     * Gets a query parameter.
     * johan:
     * - Can't get $location.search() working.
     * - Can't get ngRoute to work...
     */
    function getParameterByName(name) {
        var url = window.location.href; // jshint ignore:line
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
        if (!results) {
            return null;
        }
        if (!results[2]) {
            return '';
        }
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    function isFrom(item, feedName) {
        return item.feed.name.toLowerCase().includes(feedName);
    }

    function anyCategoryIs(item, str) {
        if (item.categories == undefined) { // jshint ignore:line
            return false;
        }
        return item.categories.some(c => c.name === str);
    }

    function contains(item, str) {
        var mergedString = '';
        mergedString += item.html;
        mergedString += item.text;
        mergedString += item.title;
        mergedString = mergedString.toLowerCase();
        return mergedString.includes(str);
    }
});
