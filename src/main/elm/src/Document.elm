module Document exposing (Document, Documents, decodeList, decode)
import Json.Decode exposing (..)



type alias Documents =
    List Document

type alias Document =
    { title: String,
      text: String,
      pageUrl: String,
      feedName: String,
      read: Bool}


decodeList: Decoder (List Document)
decodeList = list decode

decode: Decoder Document
decode =
    map5 Document
        (field "title" string)
        (field "text" string)
        (field "pageUrl" string)
        (field "feed" (field "name" string))
        (field "read" bool)

