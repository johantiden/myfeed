module Document exposing (..)

import Json.Decode as D exposing (..)
import List.Extra exposing (uniqueBy)

type alias Document =
    { title: String
    ,  text: String
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

decodeDocuments: Decoder (List Document)
decodeDocuments = list decodeDocument

decodeDocument: Decoder Document
decodeDocument =
    map6 Document
        (field "title" string)
        (field "text" string)
        (field "pageUrl" string)
        (field "feed" (field "name" string))
        (field "subjects" decodeSubjects)
        (field "read" bool)

decodeSubjects: Decoder (List Subject)
decodeSubjects = list decodeSubject

decodeSubject: Decoder Subject
decodeSubject =
    map4 Subject
        (field "name" string)
        (field "hashTag" bool)
        (field "showAsTab" bool)
        (field "depth" int)

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
filterDocuments search documents=
    documents
        |> List.filter (\d -> String.contains search (Debug.toString d))