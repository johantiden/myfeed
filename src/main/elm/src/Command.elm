module Command exposing (..)

import Http
import Document exposing (..)
import Model exposing (..)
import Settings exposing (..)

type Msg
      = GetDocuments
      | GotDocuments (Result Http.Error (List Document))
      | HideDocument Document
      | SetSearch String


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
              ( { model | error = Just (Debug.toString error) }
              , Cmd.none
              )
        HideDocument document ->
            ({model | documents = [document]}, Cmd.none)

        SetSearch query ->
            ({model | search = query}, Cmd.none)


getDocuments : Cmd Msg
getDocuments =
  Http.send GotDocuments (Http.get url decodeDocuments)
