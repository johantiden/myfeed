module DocumentDecoder exposing (..)

import Json.Decode exposing (..)
import Document exposing (..)
import Subject exposing (..)

decodeDocuments : Decoder (List Document)
decodeDocuments = list decodeDocument

decodeDocument : Decoder Document
decodeDocument =
    map8 Document
        (field "documentId" int)
        (field "title" string)
        (field "text" (maybe string))
        (field "publishedDateShort" string)
        (field "pageUrl" string)
        (field "feed" (field "name" string))
        (field "subjects" decodeSubjects)
        (field "imageUrl" (maybe string))

decodeSubjects : Decoder (List Subject)
decodeSubjects = list decodeSubject

decodeSubject : Decoder Subject
decodeSubject =
    map4 Subject
        (field "name" string)
        (field "hashTag" bool)
        (field "showAsTab" bool)
        (field "depth" int)
