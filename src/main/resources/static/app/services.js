app.service('bubbservice', function($http) {
    this.loadData = function (callback) {

        $http
            .get("/rest/index")
            .then(callback);
    }
});
