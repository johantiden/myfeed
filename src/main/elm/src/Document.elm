module Document exposing (..)

import Json.Decode as D
import List.Extra exposing (uniqueBy)
import Common exposing (..)

type alias Document =
    { documentId: Int
    , title: String
    ,  text: String
    ,  publishedDateShort: String
    ,  pageUrl: String
    ,  feedName: String
    ,  subjects : List Subject
    ,  read: Bool
    }

type alias Subject =
   { name: String
   , hashTag: Bool
   , showAsTab: Bool
   , depth: Int
   }

documentToString : Document -> String
documentToString d =
    [d.title, d.text, d.pageUrl, d.feedName, subjectsToString d.subjects, boolToString d.read]
        |> delimit ","
        |> List.foldr (++) ""

subjectsToString : List Subject -> String
subjectsToString ss =
    ss
        |> List.map subjectToString
        |> delimit ","
        |> List.foldr (++) ""

subjectToString : Subject -> String
subjectToString s =
    [s.name, String.fromInt s.depth]
        |> delimit ","
        |> List.foldr (++) ""

boolToString : Bool -> String
boolToString b =
    case b of
        False -> "False"
        True -> "True"

extractSubjectNames : List Document -> List String
extractSubjectNames documents =
    documents
        |> extractSubjects
        |> List.map (\s -> s.name)

extractSubjects : List Document -> List Subject
extractSubjects documents =
    documents
        |> List.map (\d -> d.subjects)
        |> List.concat
        |> uniqueBy (\s -> s.name)

groupSubjectsByDepth : List Subject -> List (Int, List Subject)
groupSubjectsByDepth subjects =
    subjects
        |> List.sortBy (\s -> s.depth)
        |> List.Extra.groupWhile (\a b -> a.depth == b.depth)
        |> List.map (\(prototype, list) -> (prototype.depth, list))

countMatching : String -> List Document -> Int
countMatching search documents =
    documents
        |> filterDocuments search
        |> List.length

filterDocuments : String -> List Document -> List Document
filterDocuments search documents =
    documents
        |> List.filter (\d -> String.contains search (documentToString d))

equals : Document -> Document -> Bool
equals a b =
    a.documentId == b.documentId

notEquals : Document -> Document -> Bool
notEquals a b =
    a.documentId /= b.documentId
