app.service('documentService', function($http, $cacheFactory) {
    "use strict";

    this.putDocument = function (item, callback) {
        $http.put("/rest/documents", item).then(callback);
    };

    this.getAllKeys = function (accountname, callback) {
        $http({
            method: 'GET',
            url: '/rest/index/'+accountname,
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
            url: '/rest/accountdocument/'+key,
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
            url: '/rest/accountdocuments?' + param('keys', keys),
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

app.service('settingsService', function($http, $cacheFactory) {
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
