module Document exposing (..)

import Json.Decode as D
import List.Extra exposing (uniqueBy)
import Common exposing (..)
import Subject exposing (..)

type alias Document =
    { documentId: Int
    , title: String
    , text: Maybe String
    , publishedDateShort: String
    , publishedDate: Int
    , pageUrl: String
    , feedName: String
    , subjects : List Subject
    , imageUrl : Maybe String
    }

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

countMatching : Subject -> List Document -> Int
countMatching search documents =
    documents
        |> filterDocuments search
        |> List.length

filterDocuments : Subject -> List Document -> List Document
filterDocuments searchSubject documents =
    documents
        |> List.filter (documentMatches searchSubject)

documentMatches : Subject -> Document -> Bool
documentMatches searchSubject d =
    List.member searchSubject d.subjects

equalId : Document -> Document -> Bool
equalId a b =
    a.documentId == b.documentId

notEquals : Document -> Document -> Bool
notEquals a b =
    a.documentId /= b.documentId

sortByDate : List Document -> List Document
sortByDate documents =
    documents
        |> Common.sortDescendingBy .publishedDate