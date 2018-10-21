module Update exposing (..)

import Common exposing (notContains)
import Http
import Document exposing (..)
import Model exposing (..)
import Service
import List.Extra exposing (..)
type Msg
      = GetDocuments
      | GotDocuments (Result Http.Error (List Document))
      | HideDocuments (List Document)
      | SetSearch String
      | DocumentHidden (Result Http.Error String)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GetDocuments ->
            ( { model | error = Nothing }, getDocuments )

        GotDocuments result ->
            case result of
                Ok documents ->
                    ( { model | documents = documents, error = Nothing }
                    , Cmd.none
                    )

                Err error ->
                    ( { model | error = Just "Network error, could not fetch documents. See console." }
                    , Cmd.none
                    )
        HideDocuments documents ->
            let
                docs =
                    documents
                        |> List.map (\d -> {d | read = True})
            in
                ({model | documents = removeDocs docs model.documents}, hideDocuments docs)

        DocumentHidden result ->
            case result of
                Ok str ->
                    (model, Cmd.none)
                Err err ->
                    ( { model | error = Just ("Network error, could not hide document. See console." ++ (Debug.toString err)) }
                    , Cmd.none
                    )


        SetSearch query ->
            ({model | search = query}, Cmd.none)

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
