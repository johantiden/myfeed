app.controller('indexCtrl', function($scope, $location, $sce, $cookies, $window, documentService) { // jshint ignore:line
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
    $scope.user = user;

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

        let keyBatches = batcheroo(keys, 1000);

        function getDocumentsSlowly() {
            getDocumentz(keyBatches[0]);
            if (keyBatches.length > 0) {
                keyBatches = keyBatches.splice(1);
                setTimeout(getDocumentsSlowly, 100);
            }
        }
        getDocumentsSlowly();

    });

    function getDocumentz(keys) {
        documentService.getDocuments(keys, documents => {
            documents.forEach(document => {
                document.tabs.forEach(tab => {
                    $scope.tabs[tab] = documentTabPredicate(tab);
                });
                $scope.documents.push(document);
            });
        });
    }


    function batcheroo(list, size) {
        let lists = [];
        while (list.length > 0) {
            lists.push(list.splice(0, size));
        }
        return lists;
    }


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
        return doc => doc.tabs.some(t => t === tab);
    };

    $scope.selectTab = function(tabName) {
        $scope.selectedTabName = tabName;
    };

    $scope.setSearch = function(query) {
        $scope.search = query;
    };

    $scope.tabOrSearchFilter = function(document) {
        if ($scope.search) {
            return match($scope.search, document);
        }

        return $scope.tabs[$scope.selectedTabName](document);
    };

    function match(query, document) {
        return JSON.stringify(document).toLowerCase().includes(query.toLowerCase());
    }

    $scope.search = $cookies.get('search');


    if ($scope.search === undefined) {
        $scope.search = '';
    }
    var q = getParameterByName('q');
    if (q !== undefined && q !== null && q.length > 0) {
        $scope.search = q;
    } else {
        let cookieTabName = $cookies.get('selectedTabName');
        $scope.selectedTabName = cookieTabName;
        if ($scope.selectedTabName === undefined && $scope.search === undefined) {
            $scope.selectedTabName = 'All';
        }
    }

    $scope.$watch('selectedTabName', function(newValue) {
        if (newValue) {
            $scope.search = undefined;
        }
        $cookies.put('selectedTabName', newValue);
    });

    $scope.$watch('search', function(newValue) {
        if (newValue) {
            if (newValue.length > 0) {
                $scope.selectedTabName = undefined;
            } else {
                $scope.selectedTabName = 'All';
            }
        } else if (newValue !== undefined && newValue.length === 0) {
            $scope.selectedTabName = 'All';
        }
        $cookies.put('search', newValue);
    });

    $scope.withTab = function(tabName) {
        return function(document) {
            return $scope.tabs[tabName](document);
        };
    };
});

app.controller('settingsCtrl', function($scope, $location, $sce, $cookies, $window, settingsService) { // jshint ignore:line
    "use strict";

    $scope.subjectRules = [];


    settingsService.getAllSubjectRules(function(json) {
        $scope.subjectRules = json;
    });

    $scope.$sce = $sce;
    $scope.$location = $location;

    var user = getParameterByName('user');
    if (user) {
        $cookies.put('user', user);
    }
    user = $cookies.get('user');
    $scope.user = user;


    $scope.putSubjectRule = function(subjectRule) {
        settingsService.putSubjectRule(subjectRule);
    };

    $scope.createSubjectRule = function(subjectRule) {
        settingsService.putSubjectRule(subjectRule, function() {
            $scope.subjectRules.push(subjectRule);
        });
    };

    $scope.deleteSubjectRule = function(subjectRule) {
        settingsService.deleteSubjectRule(subjectRule);
    };
});



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
