app.service('unreadservice', function($http) {
    this.loadData = function (callback) {



        $http.get("/rest/index")
            .then(function(response) {
                callback(response.data);
            });



        //callback(getFakeData());







        function getFakeData() {
            return [
                {
                    author:'Tyska Ambassaden',
                    text:'I närvaro av familj och vänner mottog Hédi Fried ikväll Förbundsrepubliken Tysklands Bundesverdienstkreuz I. Klasse av ambassadör Heimsoeth.',
                    cssClass:'twitter',
                    authorImageUrl:'https://pbs.twimg.com/profile_images/770254039267278852/wtoxtJZk_bigger.jpg',
                    imageUrl:'http://www.machmo.se/wp-content/uploads/2014/05/bulle.gif',
                    timeSince:'2d'
                },
                {
                    tag:'/r/random',
                    tagUrl:'https://reddit.com/r/random',
                    author:'/u/johantiden',
                    title:'This is a title',
                    cssClass:'reddit',
                    imageUrl:'http://blogg.amelia.se/magnus-carlsson/files/2011/01/SEMLA.jpg',
                    timeSince:'4h'
                },
                {
                    author:'Expressen',
                    authorUrl:'https://twitter.com/Expressen',
                    text:'Gustav Fridolin duckar frågor om MP-tjänstemannen.',
                    cssClass:'twitter',
                    imageUrl:'https://pbs.twimg.com/profile_images/649276235479166976/a61oe7Xe_bigger.jpg',
                    timeSince:'2h'
                },
                {
                    author:'ecky--ptang-zooboing',
                    authorUrl:'https://reddit.com/u/ecky--ptang-zooboing',
                    tag:'/r/dataisbeautiful',
                    tagUrl:'https://reddit.com/r/dataisbeautiful',
                    title:'Countries with the Largest Defense Budgets [OC]',
                    cssClass:'reddit',
                    imageUrl:'https://b.thumbs.redditmedia.com/ZSY4Uin_wRqEwjw7kqTsEkurdnM0UP5zplpkIdSyl6I.jpg',
                    timeSince:'2h'
                }
            ];
        }
    }
});
