module Model exposing (..)

import Document exposing (..)

import Http

fakeData : Bool
fakeData = False


type alias Model =
    { documents : List Document
    , filteredDocuments: List Document
    , search: String
    , error: Maybe String
    }


initModelFake : Model
initModelFake =
     { documents = initFakeDocuments
     , filteredDocuments = initFakeDocuments
     , search = ""
     , error = Nothing
    }

initFakeDocuments : List Document
initFakeDocuments =
    [initFakeDocument 1 "1" "Gabba"
    , initFakeDocument 2 "2" "Hey"
    , initFakeDocument 3 "3" "Foo"
    ]

initFakeDocument : Int -> String -> String -> Document
initFakeDocument id idStr extraSubject =
    { documentId = id
    , title = idStr ++ " Title lorem ipsum"
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
    }

initModel : Model
initModel =
  { documents = []
  , filteredDocuments = []
  ,  search = ""
  ,  error = Nothing
  }