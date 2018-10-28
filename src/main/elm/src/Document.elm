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

documentToString : Document -> String
documentToString d =
    [d.title,
        (case d.text of
            Just text -> text
            Nothing -> ""
        ), d.pageUrl, d.feedName, subjectsToString d.subjects]
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

countMatching : String -> List Document -> Int
countMatching search documents =
    documents
        |> filterDocuments search
        |> List.length

filterDocuments : String -> List Document -> List Document
filterDocuments search documents =
    documents
        |> List.filter (documentMatches search)

documentMatches : String -> Document -> Bool
documentMatches search d =
    String.contains (String.toLower search) (String.toLower (documentToString d))

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