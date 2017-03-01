app.service('unreadservice', function($http) {
    this.loadData = function (callback) {
        //
        //$http.get("/rest/index")
        //    .then(function(response) {
        //        callback(response.data);
        //    });

        callback(
            [
                {title:'hej', cssClass:'tweet'},
                {title:'hej2', cssClass:'reddit'}
            ]
        )
    }
});
