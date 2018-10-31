module Model exposing (..)

import Document exposing (..)
import Subject exposing (..)

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
    , text = Just "Text lorem ispum dolor sin amet."
    , publishedDateShort = "30m"
    , publishedDate = 1000
    , pageUrl = "google.com"
    , feedName = "FakeNews"
    , subjects =
        [{name = "Trump", hashTag = True, showAsTab = True, subjectType = (fakeSubjectType "PERSON"), depth = 1}
        , {name = "News", hashTag = True, showAsTab = True, subjectType = (fakeSubjectType "CATEGORY"), depth = 0}
        , {name = "Fun", hashTag = True, showAsTab = True, subjectType = (fakeSubjectType "CATEGORY"), depth = 0}
        , {name = extraSubject, hashTag = True, showAsTab = True, subjectType = (fakeSubjectType "SUBJECT"), depth = 2}
        ]
    , imageUrl = Just "https://www.google.se/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"
    }

fakeSubjectType : String -> SubjectType
fakeSubjectType constant =
    { constant = constant
    , title = constant
    , order = 1
    }


initModel : Model
initModel =
  { documents = []
  , filteredDocuments = []
  ,  search = ""
  ,  error = Nothing
  }