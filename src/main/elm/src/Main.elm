module Main exposing (..)
import Browser

import Http
import Document exposing (Document, Documents)
import Document as D
import Debug
import Css exposing (..)
import Html
import Html.Styled exposing (..)
import Html.Styled.Attributes exposing (css, href, src, placeholder, value)
import Html.Styled.Events exposing (onClick)
import Css.Global

main =
  Browser.document
    { init = init
    , subscriptions = subscriptions
    , view = view
    , update = update
    }

-- INIT
init: () -> (Model, Cmd Msg)
init _ = update GetDocuments initModel

initModel : Model
initModel = initModelReal

--initModelFake : Model
--initModelFake =
--     { documents =
--        [initFakeDocument "1"
--        , initFakeDocument "2"
--        , initFakeDocument "3"
--        ]
--        , error = Nothing
--    }
--
--initFakeDocument : String -> Document
--initFakeDocument id = {
--               title = id ++ " Title lorem ipsum",
--               text = "Text lorem ispum dolor sin amet.",
--               read = False
--           }

initModelReal : Model
initModelReal =
  { documents = [],
    search = "",
    error = Nothing
  }

-- SUBSCRIPTIONS

subscriptions : Model -> Sub Msg
subscriptions model =
  Sub.none

-- MODEL
type alias Model =
    { documents : Documents
    , search: String
    , error: Maybe String
    }


-- UPDATE
type Msg
  = GetDocuments
  | GotDocuments (Result Http.Error Documents)
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

-- VIEW
percent = pct

view : Model -> Browser.Document Msg
view model =
    let
        body =
            div [css [
--                backgroundColor (hex "F8F8F8"),
                fontFamilies [ "Roboto", "serif" ],
                padding (px 5)
            ]]
            [viewNav
            , viewSearchBox model.search
            , viewDocuments model.documents
            , viewError model.error
            ]
    in
        { body = [ Html.Styled.toUnstyled body ]
                , title = "fidn!"
                }

viewNav : Html Msg
viewNav =
    nav
    [css
        [backgroundColor (hex "ffffff")
        ]
    ]
    [text "Fidn!"
    ]

viewSearchBox : String -> Html Msg
viewSearchBox currentQuery =
    input [placeholder "Search", value currentQuery] []


viewError : Maybe String -> Html Msg
viewError error =
    case error of
        Nothing ->
            text ""
        Just error1 ->
            text error1


viewDocuments : List Document -> Html Msg
viewDocuments documents =
    div [] (List.map viewDocument documents)


viewDocument : Document -> Html Msg
viewDocument document =
    div [
        css [
            padding (px 5),
            margin2 (px 5) (px 5),
            backgroundColor (hex "ffffff"),
            styleShadow
        ]
    ] [

        div [css [marginBottom (px 2), displayFlex ]] [
            button [ onClick (HideDocument document), css stylesButton] [],
            button [ onClick (HideDocument document), css ((marginLeft auto)::stylesButton)] []
        ],
        div [css [fontWeight bold, marginBottom (px 2)]] [
            text document.title
        ],
        div [] [
            a [href document.pageUrl, css (stylesLink)] [text "link"],
            text " | ",
            a [onClick (SetSearch document.feedName), css stylesSubtitle] [text document.feedName]
        ],
        div [] [
            text document.text
        ]
    ]


-- STYLES

styleShadow : Style
styleShadow = boxShadow4 (px 0.5) (px 1) (px 3) (hex "888888")


stylesButton : List Style
stylesButton =
    [
        width (vw 15),
        marginTop zero,
        cursor pointer,
        backgroundColor (hex "EEEEFF"),
        styleShadow,
        display inlineBlock,
        minHeight (px 40),
        borderStyle none
    ]


stylesLink : List Style
stylesLink =
    [
        color (hex "000000"),
        textDecoration none
    ]

stylesSubtitle : List Style
stylesSubtitle =
    [
        color (hex "777777"),
        cursor pointer
    ]




-- HTTP

url : String
url =  "http://localhost:8080/rest/index/documents"

getDocuments : Cmd Msg
getDocuments =
  Http.send GotDocuments (Http.get url D.decodeList)
