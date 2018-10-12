module DocumentDecoder exposing (..)

import Json.Decode as D
import Json.Encode as E
import Document exposing (..)
import Subject exposing (..)

decodeDocuments: D.Decoder (List Document)
decodeDocuments = D.list decodeDocument

decodeDocument: D.Decoder Document
decodeDocument =
    D.map8 Document
        (D.field "documentId" D.int)
        (D.field "title" D.string)
        (D.field "text" D.string)
        (D.field "publishedDateShort" D.string)
        (D.field "pageUrl" D.string)
        (D.field "feed" (D.field "name" D.string))
        (D.field "subjects" decodeSubjects)
        (D.field "read" D.bool)

encodeDocument: Document -> E.Value
encodeDocument d =
    E.object
        [ ("documentId", E.int d.documentId)
        , ("title", E.string d.title)
        , ("text", E.string d.text)
        , ("pageUrl", E.string d.pageUrl)
        , ("read", E.bool d.read)
        ]

decodeSubjects: D.Decoder (List Subject)
decodeSubjects = D.list decodeSubject

decodeSubject: D.Decoder Subject
decodeSubject =
    D.map4 Subject
        (D.field "name" D.string)
        (D.field "hashTag" D.bool)
        (D.field "showAsTab" D.bool)
        (D.field "depth" D.int)
