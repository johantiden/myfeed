module View exposing (..)
import Update exposing (..)
import Model exposing (..)
import Document exposing (..)
import Subject exposing (..)
import Common exposing (..)

import Browser
import Html
import Html.Styled exposing (..)
import Html.Styled.Attributes exposing (css, href, src, placeholder, value)
import Html.Styled.Events exposing (onClick, onInput)
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
            [ viewTop model
            , viewDocuments model.filteredDocuments
            , viewError model.error
            ]
    in
        { body = [ Html.Styled.toUnstyled body ]
        , title = "fidn!"}

viewTop : Model -> Html Msg
viewTop model =
    div [css [height (px  430)]]
    [ viewLogo
    , viewSearchRow model
    , viewTabs model
    ]

viewLogo : Html Msg
viewLogo =
    nav
        [css
            [ backgroundColor (hex "ffffff")
            , marginBottom (px 10)
            ]
        ]
        [ div [onClick (SetSearch ""), css [cursor pointer]] [text "Fidn!"]
        ]

viewTabs : Model -> Html Msg
viewTabs model =
    div []
        (model.documents
            |> extractSubjects
            |> List.filter .showAsTab
            |> groupSubjectsByDepth
            |> List.map (viewTabRow model)
        )


viewTabRow : Model -> (Int, List Subject) -> Html Msg
viewTabRow model (depth, subjects) =
    div [css []]
        (subjects
            |> List.map (\s -> ((countMatching s.name model.documents), s))
            |> List.filter (\(hitCount, _) -> hitCount > 1)
            |> Common.sortDescendingBy (\(hitCount, s) -> hitCount)
            |> List.take 5
            |> List.map (viewTab (List.length subjects) model.search)
        )

viewTab : Int -> String -> (Int, Subject)-> Html Msg
viewTab debug search (hitCount, subject) =
    let
        css_ =
            css (
                [cursor pointer, margin (px 10), padding2 (px 10) (px 0), display inlineBlock] ++
                (case search == subject.name of
                    True -> [borderBottom3 (px 1) solid (hex "000000") ]
                    False -> []
                ) ++
                   [color (colorFromHitCount hitCount), styleShadow]
                   ++ (stylesButton (hex "FFFFFF"))
                )
    in
        button
            [onClick (SetSearch subject.name), css_]
            [text ((String.fromInt debug) ++ subject.name)
            , span [] [text ("(" ++ (String.fromInt hitCount) ++ ")")]
            ]

percentageFromHitCount : Int -> Float
percentageFromHitCount hitCount =
    toFloat (Basics.min 20 hitCount) / 20

colorFromHitCount : Int -> Color
colorFromHitCount hitCount =
    let
        p = percentageFromHitCount hitCount
    in
        rgb
            (Basics.floor (p*255))
            (Basics.floor (50-(p*50)))
            0

viewSearchRow : Model -> Html Msg
viewSearchRow model =
    div [css [marginBottom (px 30)]]
    [ viewSearchBox model.search
    , viewHideAllButton model.filteredDocuments
    ]

viewHideAllButton : List Document -> Html Msg
viewHideAllButton filteredDocuments =
        button [onClick (HideDocuments filteredDocuments), css (stylesButton (hex "FBFBFB"))] [text "ALL"]


viewSearchBox : String -> Html Msg
viewSearchBox currentQuery =
    input [ placeholder "Search"
          , value currentQuery
          , onInput SetSearch
          , css [margin (px 10)]]
          []


viewError : Maybe String -> Html Msg
viewError error =
    case error of
        Nothing ->
            text ""
        Just error1 ->
            text error1


viewDocuments : List Document -> Html Msg
viewDocuments filteredDocuments =
    div []
        (filteredDocuments
            |> List.take 20
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
            [button [ onClick (HideDocuments [document]), css (stylesButton (hex "FBFBFB"))] [text "X"]
            , button [ onClick (HideDocuments [document]), css ((marginLeft auto) :: (stylesButton (hex "FBFBFB")))] [text "X"]
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
            ++ [viewPublishedDate document.publishedDateShort]

viewPublishedDate : String -> Html Msg
viewPublishedDate publishedDateShort =
    span [css stylesSubtitle] [text publishedDateShort]


viewSubjectsSubtitle : List Subject -> List (Html Msg)
viewSubjectsSubtitle subjects =
    subjects
        |> List.filter .hashTag
        |> List.sortBy .name
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


stylesButton : Color -> List Style
stylesButton color =
    [ width (vw 15)
    , marginTop zero
    , cursor pointer
    , backgroundColor color
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
