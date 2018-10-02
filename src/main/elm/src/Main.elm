module Main exposing (..)
import Model exposing (..)
import View exposing (..)
import Command exposing (..)

import Browser
import Http
import Document exposing (Document, Documents, Subject)
import Document as D
import Debug

-- MAIN
main =
  Browser.document
    { init = init
    , subscriptions = subscriptions
    , view = view
    , update = update
    }

-- INIT
init: () -> (Model, Cmd Msg)
init _ =
    case fakeData of
        False -> update GetDocuments initModel
        True -> (initModelFake, Cmd.none)


-- SUBSCRIPTIONS

subscriptions : Model -> Sub Msg
subscriptions model =
  Sub.none

