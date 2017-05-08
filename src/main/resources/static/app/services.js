app.service('documentService', function($http, $cacheFactory) {

    this.putDocument = function (item, callback) {
        $http.put("/rest/documents", item).then(callback);
    };


    this.getAllSubjectKeys = function (username, callback) {
        $http({
            method: 'GET',
            url: '/rest/index/' + username,
            headers: {
                'Cache-Control': 'no-cache, no-store'
            }
        }).then(function (response) {
            callback(response.data);
        });
    };

    this.getSubject = function (subjectKey, username, callback) {
        $http({
            method: 'GET',
            url: '/rest/subjects/'+subjectKey+'/users/'+username,
            headers: {
                'Cache-Control': 'no-cache, no-store'
            }
        }).then(function(response) {
            callback(response.data);
        });
    };
});
