app.controller('myCtrl', function($scope, $location, $sce, $cookies, $window, documentService) { // jshint ignore:line
    "use strict";
    $scope.documents = [];
    $scope.tabs = {
        'All': s => true
    };

    $scope.$sce = $sce;
    $scope.$location = $location;

    var user = getParameterByName('user');
    if (user) {
        $cookies.put('user', user);
    }
    user = $cookies.get('user');


    $scope.setDocumentRead = function(document, read, callback) {
        document.read = read;
        document.username = user;

        documentService.putDocument(document, callback);
    };

    documentService.getAllKeys(user, function(json) {
        $scope.keys = json;
    });

    $scope.$watch('keys', function(keys) {
        if (keys === undefined) {
            return;
        }
        keys.forEach(function(key) {
            documentService.getDocument(key, document => {
                $scope.tabs[document.tab] = documentTabPredicate(document.tab);
                $scope.documents.push(document);
            });
        });
    });

    $scope.tab = {};

    $scope.markFilteredAsRead = function() {
        if (confirm("Are you sure?")) { // jshint ignore:line
            $scope.documents.forEach(document => {
                if ($scope.tabOrSearchFilter(document)) {
                    $scope.setDocumentRead(document, true);
                }
            });
        }
    };

    var documentTabPredicate = function (tab) {
        return dock => dock.tab === tab;
    };

    $scope.selectTab = function(tabName) {
        $scope.selectedTabName = tabName;
    };

    $scope.tabOrSearchFilter = function(document) {
        if ($scope.search) {
            return match($scope.search, document);
        }

        return $scope.tabs[$scope.selectedTabName](document);
    };

    function match(query, document) {
        return JSON.stringify(document).toLowerCase().includes(query);
    }

    $scope.selectedTabName = $cookies.get('selectedTabName');
    $scope.search = $cookies.get('search');

    if ($scope.selectedTabName === undefined && $scope.search === undefined) {
        $scope.selectedTabName = 'All';
    }
    if ($scope.search === undefined) {
        $scope.search = '';
    }
    var q = getParameterByName('q');
    if (q) {
        $scope.search = q;
    }

    $scope.$watch('selectedTabName', function(newValue) {
        if (newValue) {
            $scope.search = undefined;
        }
        $cookies.put('selectedTabName', newValue);
    });

    $scope.$watch('search', function(newValue) {
        if (newValue) {
            $scope.selectedTabName = undefined;
        }
        $cookies.put('search', newValue);
    });

    $scope.withTab = function(tabName) {
        return function(item) {
            return $scope.tabs[tabName](item);
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
});
