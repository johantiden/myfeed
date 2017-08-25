app.service('documentService', function($http, $cacheFactory) {
    "use strict";

    this.putDocument = function (item, callback) {
        $http.put("/rest/documents", item).then(callback);
    };


    this.getAllKeys = function (username, callback) {
        $http({
            method: 'GET',
            url: '/rest/index/'+username,
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
            url: '/rest/userdocument/'+key,
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
            url: '/rest/userdocuments?' + param('keys', keys),
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
            url: '/rest/settings/subjectRules',
            headers: {
                'Cache-Control': 'no-cache, no-store'
            }
        }).then(function(response) {
            callback(response.data);
        });
    };

    this.putSubjectRule = function(subjectRule, callback) {
        $http.put("/rest/settings/subjectRules", subjectRule).then(callback);
    };

    this.deleteSubjectRule = function(subjectRule, callback) {
        $http.delete("/rest/settings/subjectRules/"+ subjectRule.id).then(callback);
    };
});
