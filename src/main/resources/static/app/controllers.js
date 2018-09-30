app.controller('indexCtrl', function($scope, $location, $sce, $cookies, $window, documentService) { // jshint ignore:line
    "use strict";
    $scope.documents = [];
    $scope.subjectLevels = [
       /* [{name: 'All', showAsTab: true}]*/
    ];

    $scope.$sce = $sce;
    $scope.$location = $location;

    $scope.setDocumentRead = function(document, read, callback) {
        document.read = true;

        let putDoc = JSON.parse(JSON.stringify(document));

        documentService.putDocument(putDoc, callback);
    };

    documentService.getAllKeys(function(json) {
        $scope.keys = json;
    });

    $scope.$watch('keys', function(keys) {
        if (keys === undefined) {
            return;
        }

        let keyBatches = batcheroo(keys, 10);

        function getDocumentsSlowly() {
            if (keyBatches.length > 0) {
                getDocumentz(keyBatches[0]);
                keyBatches = keyBatches.splice(1);
                if (keyBatches.length > 0) {
                    setTimeout(getDocumentsSlowly, 100);
                }
            }
        }
        getDocumentsSlowly();

    });

    function getDocumentz(keys) {
        documentService.getDocuments(keys, documents => {
            documents.forEach(document => {
                document.subjects.forEach(subject => {
                    if ($scope.subjectLevels[subject.depth] === undefined) {
                        $scope.subjectLevels[subject.depth] = [];
                    }
                    addIfNew($scope.subjectLevels[subject.depth], subject, s => s.name === subject.name);
                });
                $scope.documents.push(document);
            });
        });
    }

    function addIfNew(list, item, predicate) {
        let contains = list.some(predicate);
        if (!contains) {
            list.push(item);
        }
    }

    // $scope.documents.push({title: "title", text:"lorem ipsum dolor", subjects:['asd'], read:false, publishedDateShort: "20m"});
    // $scope.documents.push({title: "title", text:"lorem ipsum dolor", subjects:['asd'], read:false, publishedDateShort: "2d", videos: [], imageUrl: "https://www.svtstatic.se/image-cms/svtse/1537731483/svts/article19410212.svt/alternates/large/ferm-malm-1920-jpg"});
    // $scope.documents.push({tabs:["Nyheter", "Biz"], title: "title", text:"lorem ipsum dolor", subjects:['asd'], read:false, publishedDateShort: "3d"});


    function batcheroo(list, size) {
        let lists = [];
        while (list.length > 0) {
            lists.push(list.splice(0, size));
        }
        return lists;
    }

    $scope.markFilteredAsRead = function() {
        if (confirm("Are you sure you want to mark all visible documents as read?")) { // jshint ignore:line
            $scope.documents.forEach(document => {
                if ($scope.searchFilter(document)) {
                    $scope.setDocumentRead(document, true);
                }
            });
        }
    };

    $scope.setSearch = function(query) {
        $scope.search = query;
    };

    $scope.searchFilter = function(document) {
        if ($scope.search) {
            return match($scope.search, document);
        }
        return true;
    };

    function match(query, document) {
        return JSON.stringify(document).toLowerCase().includes(query.toLowerCase());
    }

    $scope.search = $cookies.get('search');
    if ($scope.search === undefined) {
        $scope.search = '';
    }

    let q = getParameterByName('q');
    if (q !== undefined && q !== null && q.length > 0) {
        $scope.search = q;
    }

    $scope.$watch('search', function(newValue) {
        if (newValue === 'All') {
            $scope.search = undefined;
        }
        $cookies.put('search', newValue);
    });

    $scope.withSearch = function(query) {
        return document => match(query, document);
    };
});

/**
 * Gets a query parameter.
 * johan:
 * - Can't get $location.search() working.
 * - Can't get ngRoute to work...
 */
function getParameterByName(name) {
    let url = window.location.href; // jshint ignore:line
    name = name.replace(/[\[\]]/g, "\\$&");
    let regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) {
        return null;
    }
    if (!results[2]) {
        return '';
    }
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
