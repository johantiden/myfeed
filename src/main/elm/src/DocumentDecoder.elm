module DocumentDecoder exposing (..)

import Json.Decode exposing (..)
import Document exposing (..)
import Subject exposing (..)
import Json.Decode.Extra exposing (..)

decodeDocuments : Decoder (List Document)
decodeDocuments = list decodeDocument

decodeDocument : Decoder Document
decodeDocument =
    succeed Document
        |> andMap (field "documentId" int)
        |> andMap (field "title" string)
        |> andMap (field "text" (maybe string))
        |> andMap (field "publishedDateShort" string)
        |> andMap (field "publishedDate" int)
        |> andMap (field "pageUrl" string)
        |> andMap (field "feed" (field "name" string))
        |> andMap (field "subjects" decodeSubjects)
        |> andMap (field "imageUrl" (maybe string))

decodeSubjects : Decoder (List Subject)
decodeSubjects = list decodeSubject

decodeSubject : Decoder Subject
decodeSubject =
    map4 Subject
        (field "name" string)
        (field "hashTag" bool)
        (field "showAsTab" bool)
        (field "depth" int)
