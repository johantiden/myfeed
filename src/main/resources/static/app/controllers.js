app.controller('myCtrl', function($scope, $location, $sce, $cookies, $window, documentService) { // jshint ignore:line
    "use strict";
    $scope.subjects = [];
    $scope.tabs = {
        'All': s => true
    };

    $scope.$sce = $sce;
    $scope.$location = $location;
    var user = getParameterByName('user');


    $scope.setDocumentRead = function(document, read, callback) {
        document.read = read;
        document.username = user;

        clearCache(document);

        documentService.putDocument(document, callback);
    };

    documentService.getAllSubjectKeys(user, function(json) {
        $scope.subjectKeys = json;
    });

    $scope.$watch('subjectKeys', function(subjectKeys) {
        if (subjectKeys === undefined) {
            return;
        }
        subjectKeys.forEach(function(key) {
            documentService.getSubject(key, user, subject => {
                $scope.tabs[subject.tab] = subjectTabPredicate(subject.tab);
                $scope.subjects.push(subject);
            });
        });
    });

    $scope.tab = {};

    $scope.markFilteredAsRead = function() {
        if (confirm("Are you sure?")) { // jshint ignore:line
            $scope.subjects.forEach(subject => {
                if ($scope.tab(subject)) {
                    subject.userDocuments.forEach(document => {
                        $scope.setDocumentRead(document, true);
                    });
                }
            });
        }
    };

    $scope.unwrapRadioFilter = function(subject) {
        return $scope.tab.predicate(subject);
    };

    var clearCache = function(item) {
        item.isUnmatched = undefined;
    };

    var subjectTabPredicate = function (tab) {
        return subject => subject.tab === tab;
    };

    $scope.selectTab = function(tabName) {
        $scope.selectedTabName = tabName;
    };

    $scope.tabFilter = function(subject) {
        return $scope.tabs[$scope.selectedTabName](subject);
    };

    $scope.hasUnread = function(subject) {
        return subject.userDocuments.some(d => d.read === false);
    };


    $scope.selectedTabName = $cookies.get('selectedTabName');
    if ($scope.selectedTabName === undefined) {
        $scope.selectedTabName = 'All';
    }

    $scope.$watch('selectedTabName', function(newValue) {
        $cookies.put('selectedTabName', newValue);
    });

    $scope.withFilter = function(tabName) {
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
