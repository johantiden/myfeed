module Service exposing (..)

import Http exposing (..)
import DocumentDecoder exposing (..)
import Document exposing (Document)
import Json.Decode

baseUrl : String
baseUrl = "" -- Real service should have URL without scheme and authority
--baseUrl = "http://localhost:8080" -- Use this fake base for local testing (e.g. using reactor)



getDocuments : Request (List Document)
getDocuments =
    Http.get (baseUrl ++ "/rest/index/documents") decodeDocuments

hideDocument : Document -> Request String
hideDocument document =
    put (baseUrl ++ "/rest/documents") (jsonBody <| encodeDocument document)


put : String -> Body -> Request String
put url body =
  request
    { method = "PUT"
    , headers = []
    , url = url
    , body = body
    , expect = expectString
    , timeout = Nothing
    , withCredentials = False
    }

