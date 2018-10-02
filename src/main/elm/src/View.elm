module View exposing (view)
import Command exposing (..)
import Model exposing (..)
import Document exposing (..)
import Common exposing (..)

import Browser
import Html
import Html.Styled exposing (..)
import Html.Styled.Attributes exposing (css, href, src, placeholder, value)
import Html.Styled.Events exposing (onClick)
import Css exposing (..)

percent = pct

view : Model -> Browser.Document Msg
view model =
    let
        body =
            div [css [
                fontFamilies [ "Roboto", "serif" ],
                padding (px 5)
            ]]
            [viewNav
            , viewTabs model.documents
            , viewSearchBox model.search
            , viewDocuments model.search model.documents
            , viewError model.error
            ]
    in
        { body = [ Html.Styled.toUnstyled body ]
        , title = "fidn!"}

viewNav : Html Msg
viewNav =
    nav [css [backgroundColor (hex "ffffff")]]
        [text "Fidn!"]

viewTabs : Documents -> Html Msg
viewTabs documents =
    div []
        (documents
            |> extractSubjectNames
            |> List.map viewTab
            |> viewDelimitedList " "
        )

viewTab : String -> Html Msg
viewTab name =
    span [onClick (SetSearch name), css [cursor pointer]] [text name]

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


viewDocuments : String -> List Document -> Html Msg
viewDocuments search documents  =
    div []
        (documents
            |> List.filter (\d -> String.contains search (Debug.toString d))
            |> List.map viewDocument
        )

viewDocument : Document -> Html Msg
viewDocument document =
    div
        [css [padding (px 5)
             , margin2 (px 5) (px 5)
             , backgroundColor (hex "ffffff")
             , styleShadow
             ]
        ]
        [ div
            [css [marginBottom (px 2), displayFlex ]]
            [button [ onClick (HideDocument document), css stylesButton] []
            , button [ onClick (HideDocument document), css ((marginLeft auto)::stylesButton)] []
            ]
        , div
            [css [fontWeight bold, marginBottom (px 2)]]
            [text document.title]
        , div []
            (viewSubtitleRow document)
        , div [] [
            text document.text
          ]
        ]

viewSubtitleRow : Document -> List (Html Msg)
viewSubtitleRow document =
    viewSubtitleElements document
        |> viewDelimitedList " | "

viewSubtitleElements : Document -> List (Html Msg)
viewSubtitleElements document =
    ([a [href document.pageUrl, css (stylesLink)] [text "link"]
            , a [onClick (SetSearch document.feedName), css stylesSubtitleLink] [text document.feedName]
            ] ++ (viewSubjectsSubtitle document.subjects))


viewSubjectsSubtitle : List Subject -> List (Html Msg)
viewSubjectsSubtitle subjects =
    List.map viewSubjectSubtitle subjects

viewSubjectSubtitle : Subject -> Html Msg
viewSubjectSubtitle subject =
    span [onClick (SetSearch subject.name), css stylesSubtitleLink] [text subject.name]

viewDelimitedList : String -> List (Html Msg) -> List (Html Msg)
viewDelimitedList delimiter =
    viewDelimiter delimiter
        |> delimit

viewDelimiter : String -> Html Msg
viewDelimiter delimiter = span [css stylesSubtitle] [text delimiter]


-- STYLES

styleShadow : Style
styleShadow = boxShadow4 (px 0.5) (px 1) (px 3) (hex "888888")


stylesButton : List Style
stylesButton =
    [ width (vw 15)
    , marginTop zero
    , cursor pointer
    , backgroundColor (hex "EEEEFF")
    , styleShadow
    , display inlineBlock
    , minHeight (px 40)
    , borderStyle none
    ]


stylesLink : List Style
stylesLink =
    [ color (hex "000000")
    , textDecoration none
    ]

stylesSubtitleLink : List Style
stylesSubtitleLink =
    stylesSubtitle ++
        [ cursor pointer
        ]

stylesSubtitle : List Style
stylesSubtitle =
    [ color (hex "777777")
    ]
