module Tests exposing (..)



import Expect exposing (Expectation)
import Test exposing (..)

import Document exposing (..)
import Model exposing (initFakeDocument, initFakeDocuments)

suite : Test
suite =
    describe "Documents"
        [ test "equals" <|
            (\() ->
                (Document.equalId
                    (initFakeDocument 1 "1" "Gabba")
                    (initFakeDocument 2 "2" "Hey" ))
                    |> Expect.equal False)

        , test "Document.matches on substrings" <|
            \() ->
                (initFakeDocument 1 "1" "Gabba")
                    |> Document.documentMatches "abba"
                    |> Expect.equal True

        , test "Document.matches case" <|
            \() ->
                (initFakeDocument 1 "1" "Gabba")
                    |> Document.documentMatches "gabba"
                    |> Expect.equal True

        , test "Document.extractSubjects asd" <|
            \() ->
                (initFakeDocuments)
                    |> Document.extractSubjects
                    |> Expect.equal []

        ]