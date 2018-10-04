module Model exposing (..)

import Document exposing (..)

import Http

fakeData : Bool
fakeData = False


type alias Model =
    { documents : List Document
    , search: String
    , error: Maybe String
    }


initModelFake : Model
initModelFake =
     { documents =
        [initFakeDocument "1" "Gabba"
        , initFakeDocument "2" "Hey"
        , initFakeDocument "3" "Foo"
        ]
        , search = ""
        , error = Nothing
    }

initFakeDocument : String -> String -> Document
initFakeDocument id extraSubject =
    { documentId = 0
    , title = id ++ " Title lorem ipsum"
    , text = "Text lorem ispum dolor sin amet."
    , publishedDateShort  = "30m"
    , pageUrl = "google.com"
    , feedName = "FakeNews"
    , subjects =
        [{name = "Trump", hashTag = True, showAsTab = True, depth = 1}
        , {name = "News", hashTag = True, showAsTab = True, depth = 0}
        , {name = "Fun", hashTag = True, showAsTab = True, depth = 0}
        , {name = extraSubject, hashTag = True, showAsTab = True, depth = 2}
        ]
    , read = False}

initModel : Model
initModel =
  { documents = [],
    search = "",
    error = Nothing
  }