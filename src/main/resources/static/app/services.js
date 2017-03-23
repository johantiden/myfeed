app.service('documentService', function($http) {

    this.putItem = function (item) {
        $http.put("/rest/documents", item);
    };


    this.getUnread = function (callback) {



        $http.get("/rest/index")
            .then(function(response) {
                callback(response.data);
            });



        //callback(getFakeData());

        //function getFakeData() {
        //    return [{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Malmö shooters 'may have fled by bicycle’","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170305/malm-shooters-may-have-fled-by-bicycle","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/e0afea7c1bfcf6bf6dd35a8fabbe925bd4e42a18a2fd3009f48b32d94b3aee15.jpg","publishedDate":{"nano":0,"epochSecond":1488730604},"publishedDateShort":"4h"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Swedish archbishop calls for support for Iraqi Christians","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170305/swedish-archbishop-calls-for-support-for-iraqi-christians","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/9d085cf06e0e2f25605f4f82ecf19a61d293d31c3b141670e1899b364c3ebcc5.jpg","publishedDate":{"nano":0,"epochSecond":1488725120},"publishedDateShort":"5h"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Man shot dead in car in Malmö","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170305/man-shot-dead-in-car-in-malm","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/7ed01ec42bbeedb8903cf439ec204688854b8ab14ffb3c81eca8dfb4bcc55f58.jpg","publishedDate":{"nano":0,"epochSecond":1488700681},"publishedDateShort":"12h"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Tummen upp för nya Visual Studio","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.677068/tummen-upp-for-nya-visual-studio","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1488692400},"publishedDateShort":"14h"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Swedish minister does U-turn on comments about Sweden's sex crimes ","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170304/fake-news-minister-does-u-turn-on-comments-about-swedens-sex-crimes","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/211a6ed190b19ba06e8ad4745db9dc5f45f9edadfcc2d64ed4c3fa9513ca27b8.jpg","publishedDate":{"nano":0,"epochSecond":1488642258},"publishedDateShort":"1d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Southern Sweden warned of heavy weekend of snow","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170304/southern-sweden-warned-for-heavy-weekend-of-snow","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/3c52b0392f1482b0098d03190f8688910d5ee7753e54a9599c738978c497c293.jpg","publishedDate":{"nano":0,"epochSecond":1488634034},"publishedDateShort":"1d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Traffic chaos on icy roads in western Sweden","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170304/traffic-chaos-on-icy-roads-in-western-sweden","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/7382db04b4b45bd3b2271eb48adaef88a30a777cdcb329a66a354fee910b146a.jpg","publishedDate":{"nano":0,"epochSecond":1488618032},"publishedDateShort":"1d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Debate rages in Sweden over Muslim Brotherhood report","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170303/debate-rages-in-sweden-over-muslim-brotherhood-report","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/602beabd5101dd1b0a64552db62268f0471d3b34579c8a0f5713e504323aec2f.jpg","publishedDate":{"nano":0,"epochSecond":1488558618},"publishedDateShort":"2d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Here's where to get Sweden's best coffee","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170303/heres-where-to-get-swedens-best-coffee","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/01ee3262053d64acb0f8a231f825b67ba5366dd905d7556b8b753140e6e41501.jpg","publishedDate":{"nano":0,"epochSecond":1488554118},"publishedDateShort":"2d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Ebay-vd:n tar över Servicenow – här är veckans chefssvep","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.677188/veckans-chefer","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1488548820},"publishedDateShort":"2d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Sweden's national rail operator challenges gender norms in new ad","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170303/swedens-national-rail-operator-challenges-gender-norms-in-new-ad","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/5a636495a975062162c8b00614672674d7b04e798e4c03d96f63e39d5541e15d.jpg","publishedDate":{"nano":0,"epochSecond":1488544506},"publishedDateShort":"2d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Sweden to the White House: okay guys, let's talk","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170303/sweden-foreign-minister-margot-wallstrom-rex-tillerson-trump","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/2c9d93d0fa51b9915cd87857a160eeb6069eb8d76c16e5125e067285760daaf1.jpg","publishedDate":{"nano":0,"epochSecond":1488540278},"publishedDateShort":"2d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Missing cat returns home after NINE years","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170303/missing-cat-returns-home-after-nine-years-sweden-skelleftea","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/ab0d6967439a57005bc80bc9b8008971d7aac2cf56a6aaabffa10da77ad2e6e0.jpg","publishedDate":{"nano":0,"epochSecond":1488539777},"publishedDateShort":"2d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"The Swedish towns where women dominate men","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170303/the-swedish-towns-where-women-dominate-menin-the-population","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/8e3b94373cb39d35f9a75ff229bd744ed5120a733b410ff4caa6a68a6c263c2c.jpg","publishedDate":{"nano":0,"epochSecond":1488536035},"publishedDateShort":"2d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Guess how many people now pay to use Spotify? Yup, 50 million","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170303/spotify-hits-landmark-50-million-paying-users","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/b47f3752950f6154a668c2f2c37a7af35cc35ce724099fc09969e0f405877e6e.jpg","publishedDate":{"nano":0,"epochSecond":1488522206},"publishedDateShort":"2d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Bomb threat: We were stuck on plane for hours, says passenger","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170303/bomb-threat-grounds-thailand-bound-plane-from-sweden","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/8f0efacd0c750a30058847dc6e097f479e7fa5bb7f4efb91ef5a1875e54c4a0e.jpg","publishedDate":{"nano":0,"epochSecond":1488520753},"publishedDateShort":"2d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Recipe: How to make these Swedish 'mandelflarn'","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170303/recipe-how-to-make-these-swedish-mandelflarn-almond-tuiles-biscuits","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/8f1075ea830379f338c4f6c7fa6f342ee9993f2d94df3f9ec0643cea499a6896.jpg","publishedDate":{"nano":0,"epochSecond":1488517965},"publishedDateShort":"2d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Why Sweden is bringing back the draft","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170302/why-sweden-is-bringing-back-the-draft","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/8b01e7b10ee5e2261274e8f014b9a75e976615356eb3402190a60bcc3ad89f92.jpg","publishedDate":{"nano":0,"epochSecond":1488468447},"publishedDateShort":"3d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Why it's an unofficial 'national day' in Sweden","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170302/why-today-people-in-smland-go-crazy","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/935d486fc8ac61482a2e2f44315d7125db9a4834e9796af75b29eb2070097dcf.jpg","publishedDate":{"nano":0,"epochSecond":1488463307},"publishedDateShort":"3d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Here's what happened when this Swede introduced fika at her London office","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170302/heres-what-happened-when-this-swede-introduced-fika-at-her-london-office","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/739d2dd8c251a1e3566e0730678121cd3ed7ac5fa3f08cf579cb4a06a51ffdee.jpg","publishedDate":{"nano":0,"epochSecond":1488453664},"publishedDateShort":"3d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"'Women can do anything they decide to': Sweden team sends a message with new shirts","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170302/women-can-do-anything-they-decide-to-sweden-team-sends-a-message-with-new-shirts","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/7309d63739abb6c8026e26e12b96ba27f41f60af029421eaeee1c76b8ca9b9e2.jpg","publishedDate":{"nano":0,"epochSecond":1488452322},"publishedDateShort":"3d"},{"feedName":"TheLocal","feedUrl":"http://www.thelocal.se","title":"Young Swedes swapping alcohol for social media and the gym: study","author":"","cssClass":".thelocal","pageUrl":"http://www.thelocal.se/20170302/young-swedes-are-swapping-alcohol-for-social-media-and-the-gym-study","imageUrl":"http://www.thelocal.se/userdata/images/article/w468/d085f2c44bbce524c96a1e4a6c5e0e8d76f6796bbc4c2fd523a644485c239178.jpg","publishedDate":{"nano":0,"epochSecond":1488450142},"publishedDateShort":"3d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Här är konsulten som tycker att alla vd:ar ska lära sig programmera","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.676881/vd-programmera","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1488431040},"publishedDateShort":"3d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Facebook släpper gratisverktyget Prophet för Python","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.676989/facebook-prophet-python","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1488369973},"publishedDateShort":"4d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Filosofen: Vi måste tala mer etik och teknik","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.676700/filosof-etik-teknik","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1488086640},"publishedDateShort":"7d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Här är företaget som vågar bryta sig ur standardfällan","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.676480/propellerhead-egen-plattform","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1487998920},"publishedDateShort":"8d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Nu tar devops-bottarna över it-driften","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.676621/devops-bottar-it-drift","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1487912580},"publishedDateShort":"9d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Därför älskar utvecklare mikrotjänster","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.676584/utvecklare-mikrotjanster","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1487845765},"publishedDateShort":"10d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Se upp Raspberry Pi! Här är konkurrenterna som utmanar den populära minidatorn","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.654374/raspberry-pi-tips-guide-projekt","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1487671260},"publishedDateShort":"12d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Nu finns en guide för uppgradering till Java 9","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.676308/java-9-uppgradering","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1487591219},"publishedDateShort":"13d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Vad bubblar i säkerhetssvängen? Här är snackisarna från jättekonferensen RSA","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.676204/rsa-sakerhet-konferens","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1487581000},"publishedDateShort":"13d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Google tar stort kliv i databaskampen – nu får Oracle se upp","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.676035/google-databas-kamp-oracle","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1487307720},"publishedDateShort":"16d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"I mars smäller det – nya Visual Studio avtäcks","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.675852/visual-studio-ny-version","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1486988025},"publishedDateShort":"20d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Här är språket som utvecklare helst labbar med på fritiden","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.675838/utvecklare-sprak-fritid","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1486979193},"publishedDateShort":"20d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Windows Server hårdbantas – vi tittar närmare på Nano Server","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.675765/windows-server-nano-server","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1486879620},"publishedDateShort":"21d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Köpfest över gränserna – här är veckans it-affärer","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.675755/veckans-it-affarer","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1486736567},"publishedDateShort":"23d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"10 glödheta kompetenser inom it","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.675657/10-heta-it-jobb","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1486732363},"publishedDateShort":"23d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"\"Oracles skryt påminner om Orwells 1984\"","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.675541/oracle-orwell-1984","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1486635649},"publishedDateShort":"24d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Här är ännu en utmanare till Raspberry Pi: Dragonboard","author":"","cssClass":".computersweden","pageUrl":"http://techworld.idg.se/2.2524/1.675530/dragonboard-raspberry-pi","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1486549397},"publishedDateShort":"25d"},{"feedName":"ComputerSweden:Systemutveckling","feedUrl":"http://computersweden.idg.se/systemutveckling","title":"Oracle vill locka unga till Java med Raspberry Pi","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.675468/raspberry-pi-oracle-java","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1486530060},"publishedDateShort":"25d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Märklig mackapär som maxar vr-upplevelsen en av veckans knasigaste prylar","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.631711/marklig-mackapar-som-maxar-vr-upplevelsen-en-av-veckans-knasigast-prylar","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1434868380},"publishedDateShort":"623d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Spara ström med ett klick. Grön mackapär bland veckans prylar.","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.629830/spara-strom-med-ett-klick-gron-mackapar-bland-veckans-prylar","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1433574360},"publishedDateShort":"638d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Blinkande handskar visar vägen i trafiken. Här är veckans viktigaste (?) prylar.","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.628774/blinkande-handskar-visar-vagen-i-trafiken-har-ar-veckans-viktigaste--prylar","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1432963620},"publishedDateShort":"645d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Ett smart hundhalsband och en dundermus. Djuriskt coola grejer i veckans prylsvep.","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.627765/ett-smart-hundhalsband-och-en-dundermus-djuriskt-coola-grejer-i-veckans-prylsvep","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1432447800},"publishedDateShort":"651d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Petabytelagring i en liten box","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.579760/petabytelagring-i-en-liten-box","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1408968845},"publishedDateShort":"923d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Grafiktung Acer-Chromebook","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.577520/acers-chromebook-far-battre-grafik","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1407916233},"publishedDateShort":"935d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Android tar plats i 14-tummare","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.573144/android-i-14-tummare","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1407159332},"publishedDateShort":"944d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Kontorstelefon med Android","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.565885/kontorstelefon-med-android","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1402987153},"publishedDateShort":"992d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Vill ersätta den externa lagringen med flashkort","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.564818/flashkort-med-64-tb","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1402303746},"publishedDateShort":"1000d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Sandisk dubblar flashstorleken","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.564108/sandisk-fordubblar-flashstorleken","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1401798590},"publishedDateShort":"1006d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Här är HP:s Android-laptop","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.563793/barbar-dator-med-android","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1401707115},"publishedDateShort":"1007d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Dell optimerar skärmar för Lync","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.562783/dell-optimerar-skarmar-for-lync","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1401095080},"publishedDateShort":"1014d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"De pressar priset på flashlagring","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.561448/de-pressar-priset-pa-flashlagring","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1400231570},"publishedDateShort":"1024d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Trådlös hårddisk\n från Samsung","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.560521/tradlos-harddisk-fran-samsung","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1399906528},"publishedDateShort":"1028d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Firefoxtelefon för 650 kronor","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.560357/firefoxtelefon-for-650-kronor","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1399642327},"publishedDateShort":"1031d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Ny Sandisk-flash rymmer 4 TB","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.559080/ny-sandisk-flash-rymmer-4-tb","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1399021587},"publishedDateShort":"1038d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Dunderskrivare i miljonklassen","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.558679/dunderskrivare-i-miljonklassen","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1398849677},"publishedDateShort":"1040d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Ny flashdisk lagrar 800 GB","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.557694/flashdisk-pa-800-gb","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1398240977},"publishedDateShort":"1047d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Låt inte betongen hindra surfandet","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.556845/d-link-forlanger-natverket","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1397561297},"publishedDateShort":"1055d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Här är veckans hetaste\n appar och tjänster","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.556444/har-ar-veckans-hetaste-appar-och-tjanster","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1397373420},"publishedDateShort":"1057d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Flashfest i nya Fujitsu-diskar","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.555520/fujitsu-gillar-flash","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1396852214},"publishedDateShort":"1063d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Roterande skärm\n på slagtålig dator","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.555035/roterande-skarm-pa-slagtalig-dator","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1396529083},"publishedDateShort":"1067d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"HP satsar hårt på trådlöst","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.554757/hp-satsar-hart-pa-tradlost","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1396423439},"publishedDateShort":"1068d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Portabel router\n med mobilladdare","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.554707/portabel-router-med-mobilladdare","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1396350938},"publishedDateShort":"1069d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"8 miljoner pixlar i ny Samsung-skärm","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.554582/8-miljoner-pixlar-i-samsungs-nya-skarm","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1396335884},"publishedDateShort":"1069d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Bläckskrivaren som vill bli kontorets nya kelgris","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.552849/blackskrivare-tar-laser-vid-hornen","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1395656951},"publishedDateShort":"1077d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Skärmfot med smart klient","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.552547/skarmhallare-med-smart-klient","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1395397061},"publishedDateShort":"1080d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"AOC kör Android i skärmdatorn","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.552057/aoc-kor-android-i-skarmdatorn","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1395218015},"publishedDateShort":"1082d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Liten brandvägg \n skyddar ip-växeln","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.551874/liten-brandvagg-skyddar-ip-vaxeln","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1395065880},"publishedDateShort":"1084d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Megaskärmen som går lös på en kvarts miljon","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.551011/extrem-skarm-med-extremt-pris","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1394617287},"publishedDateShort":"1089d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Seagate vill koppla\n lagringen till luren","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.550705/seagate-kopplar-data-till-luren","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1394538865},"publishedDateShort":"1090d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Snordyr flashdisk \n lagrar 3,2 terabyte","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.549817/flashdisk-pa-32-tb","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1394014671},"publishedDateShort":"1096d"},{"feedName":"ComputerSweden:Prylar","feedUrl":"http://computersweden.idg.se/prylar","title":"Windows 8-skärmar\n för fingerfärdiga","author":"","cssClass":".computersweden","pageUrl":"http://computersweden.idg.se/2.2683/1.549437/nya-skarmar-for-windows-8","imageUrl":null,"publishedDate":{"nano":0,"epochSecond":1393853308},"publishedDateShort":"1098d"}];
        //}
    };
});
