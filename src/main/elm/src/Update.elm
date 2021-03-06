module Update exposing (..)

import Common exposing (notContains)
import Http
import Document exposing (..)
import Subject exposing (Subject)
import Model exposing (..)
import Service
import List.Extra exposing (..)

type Msg
      = GetDocuments
      | GotDocuments (Result Http.Error (List Document))
      | HideDocuments (List Document)
      | SetSearch Subject
      | DocumentHidden (Result Http.Error String)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GetDocuments ->
            ( { model | error = Nothing }, getDocuments )

        GotDocuments result ->
            case result of
                Ok documents ->
                    let sorted = Document.sortByDate documents
                    in
                        ( { model | documents = sorted, filteredDocuments = filterDocuments model.search sorted, error = Nothing }
                        , Cmd.none
                        )

                Err err ->
                    ( { model | error = Just ("Network error, could not fetch documents." ++ (errorToString err)) }
                    , Cmd.none
                    )
        HideDocuments documentsToHide ->
            let
                newDocs =
                    removeDocs documentsToHide model.documents
            in
                ({model | documents = newDocs, filteredDocuments = Document.filterDocuments model.search newDocs}, hideDocuments documentsToHide)

        DocumentHidden result ->
            case result of
                Ok str ->
                    (model, Cmd.none)
                Err err ->
                    ( { model | error = Just ("Network error, could not hide document." ++ (errorToString err)) }
                    , Cmd.none
                    )


        SetSearch subject ->
            ({model | search = subject, filteredDocuments = Document.filterDocuments subject model.documents}, Cmd.none)

errorToString : Http.Error -> String
errorToString error =
    case error of
        Http.BadUrl s -> "BadUrl " ++ s
        Http.Timeout -> "Timeout"
        Http.NetworkError -> "NetworkError"
        Http.BadStatus response -> "BadStatus " ++ (String.fromInt response.status.code)
        Http.BadPayload str response -> "BadPayload" ++ str ++ response.body



replaceDoc : Document -> List Document -> List Document
replaceDoc doc documents =
        List.Extra.setIf (Document.equalId doc) doc documents

removeDocs : List Document -> List Document -> List Document
removeDocs docs documents =
    documents
        |> List.filter (\d -> not (List.any (Document.equalId d) docs))


removeDoc : Document -> List Document -> List Document
removeDoc doc documents =
    List.filter (Document.notEquals doc) documents


getDocuments : Cmd Msg
getDocuments =
  Http.send GotDocuments Service.getDocuments

hideDocuments : List Document -> Cmd Msg
hideDocuments documents =
  Http.send DocumentHidden <| Service.hideDocuments documents
