module View exposing (..)
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
import List.Extra exposing (group)
import List
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
            , viewTabs model.search model.documents
            , viewSearchBox model.search
            , viewDocuments model.search model.documents
            , viewError model.error
            ]
    in
        { body = [ Html.Styled.toUnstyled body ]
        , title = "fidn!"}

viewNav : Html Msg
viewNav =
    nav
        [css
            [backgroundColor (hex "ffffff")
            , displayFlex
            ]
        ]
        [ div [onClick (SetSearch ""), css [cursor pointer]] [text "Fidn!"]
        ]

viewTabs : String -> List Document -> Html Msg
viewTabs search documents =
    div []
        (documents
            |> extractSubjects
            |> List.filter (\s -> s.showAsTab)
            |> groupSubjectsByDepth
            |> List.map (viewTabRow search documents)
        )


viewTabRow : String -> List Document -> (Int, List Subject) -> Html Msg
viewTabRow search documents (depth, subjects) =
    div [css []]
        (subjects
            |> List.map (\s -> ((countMatching s.name documents), s))
            |> List.filter (\(hitCount, _) -> hitCount > 1)
            |> List.map (viewTab search documents)
        )


viewTab : String -> List Document -> (Int, Subject)-> Html Msg
viewTab search documents (hitCount, subject) =
    let
        css_ =
            css (
                [cursor pointer, margin (px 10), paddingTop (px 27), display inlineBlock] ++
                (case search == subject.name of
                    True -> [borderBottom3 (px 1) solid (hex "000000") ]
                    False -> []
                ) ++
                   [color (colorFromHitCount hitCount)]
                )
        cssHitCount =
            css []
    in
        div
            [onClick (SetSearch subject.name), css_]
            [text subject.name
            , span [cssHitCount] [text ("(" ++ (Debug.toString hitCount) ++ ")")]
            ]

percentageFromHitCount : Int -> Float
percentageFromHitCount hitCount =
    toFloat (Basics.min 20 hitCount) / 20

colorFromHitCount : Int -> Color
colorFromHitCount hitCount =
    let
        p = percentageFromHitCount hitCount
    in
        hsl (120-p*120) 1 (0.9-p*0.4)



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
    subjects
        |> List.filter (\s -> s.hashTag)
        |> List.map viewSubjectSubtitle

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
