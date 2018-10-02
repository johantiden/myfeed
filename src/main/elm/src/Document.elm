module Document exposing (..)

import Json.Decode as D exposing (..)
import Set

type alias Documents =
    List Document

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

extractSubjectNames : Documents -> List String
extractSubjectNames documents =
    documents
        |> List.map (\d -> d.subjects)
        |> List.concat
        |> List.map (\s -> s.name)
        |> Set.fromList
        |> Set.toList
