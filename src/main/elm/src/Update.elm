module Update exposing (..)

import Http
import Document exposing (..)
import Model exposing (..)
import Service
import List.Extra exposing (..)
type Msg
      = GetDocuments
      | GotDocuments (Result Http.Error (List Document))
      | HideDocument Document
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
        HideDocument document ->
            let
                doc =
                    {document | read = True}
            in
                ({model | documents = removeDoc doc model.documents}, hideDocument doc)

        DocumentHidden result ->
            case result of
                Ok str ->
                    (model, Cmd.none)
                Err error ->
                    ( { model | error = Just "Network error, could not hide document. See console." }
                    , Cmd.none
                    )


        SetSearch query ->
            ({model | search = query}, Cmd.none)

replaceDoc : Document -> List Document -> List Document
replaceDoc doc documents =
        List.Extra.setIf (Document.equalId doc) doc documents

removeDoc : Document -> List Document -> List Document
removeDoc doc documents =
        List.filter (Document.notEquals doc) documents


getDocuments : Cmd Msg
getDocuments =
  Http.send GotDocuments Service.getDocuments

hideDocument : Document -> Cmd Msg
hideDocument document =
  Http.send DocumentHidden <| Service.hideDocument document
