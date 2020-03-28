module Service exposing (..)

import Http exposing (..)
import DocumentDecoder exposing (..)
import Document exposing (Document)
import Json.Decode

baseUrl : String
--baseUrl = "" -- Real service should have URL without scheme and authority
baseUrl = "http://localhost:8080" -- Use this fake base for local testing (e.g. using reactor)
--baseUrl = "http://tidn.se:8080" -- Use prod :)



getDocuments : Request (List Document)
getDocuments =
    Http.get (baseUrl ++ "/rest/index/documents") decodeDocuments

hideDocuments : List Document -> Request String
hideDocuments documents =
    Http.post (baseUrl ++ "/rest/documents/hide")
    ( documents
        |> List.map .documentId
        |> List.map (\id -> stringPart "id" (String.fromInt id))
        |> multipartBody)
    ignoreResponse

ignoreResponse =
  Json.Decode.succeed "asd"

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


