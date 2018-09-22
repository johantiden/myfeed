app.service('documentService', function($http) {
    "use strict";

    let fakeData = false;
    this.putDocument = function (item, callback) {
        $http.put("/rest/documents", item).then(callback);
    };

    this.getAllKeys = function (callback) {
        if (fakeData) {
            callback([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57]);
            return;
        }
        $http({
            method: 'GET',
            url: '/rest/index',
            headers: {
                'Cache-Control': 'no-cache, no-store'
            }
        }).then(function(response) {
            callback(response.data);
        });
    };

    this.getDocument = function (key, callback) {
        $http({
            method: 'GET',
            url: '/rest/document/'+key,
            headers: {
                'Cache-Control': 'no-cache, no-store'
            }
        }).then(function(response) {
            callback(response.data);
        });
    };

    this.getDocuments = function (keys, callback) {
        $http({
            method: 'GET',
            url: '/rest/documents?' + param('keys', keys),
            headers: {
                'Cache-Control': 'no-cache, no-store'
            }
        }).then(function(response) {
            callback(response.data);
        });
    };

    function param(name, list) {


        let paramString = '';
        list.forEach(e => paramString += '&' + name + '=' + e);
        return paramString.substring(1);
    }
});

app.service('settingsService', function($http) {
    "use strict";

    this.getAllSubjectRules = function(callback) {
        $http({
            method: 'GET',
            url: '/rest/subjectRules',
            headers: {
                'Cache-Control': 'no-cache, no-store'
            }
        }).then(function(response) {
            callback(response.data);
        });
    };

    this.putSubjectRule = function(subjectRule, callback) {
        $http.put("/rest/subjectRules", subjectRule).then(function(response) {
            callback(response.data);
        });
    };

    this.deleteSubjectRule = function(subjectRule, callback) {
        $http.delete("/rest/subjectRules/"+ subjectRule.id).then(callback);
    };

    this.getAllTabRules = function(callback) {
        $http({
            method: 'GET',
            url: '/rest/tabRules',
            headers: {
                'Cache-Control': 'no-cache, no-store'
            }
        }).then(function(response) {
            callback(response.data);
        });
    };

    this.putTabRule = function(tabRule, callback) {
        $http.put("/rest/tabRules", tabRule).then(function(response) {
            callback(response.data);
        });
    };

    this.deleteTabRule = function(tabRule, callback) {
        $http.delete("/rest/tabRules/"+ tabRule.id).then(callback);
    };
});
