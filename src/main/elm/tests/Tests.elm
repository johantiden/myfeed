module Tests exposing (..)



import Expect exposing (Expectation)
import Test exposing (..)

import Document exposing (..)
import Model exposing (initFakeDocument)

suite : Test
suite =
    describe "Documents"
        [ test "equals" <|
            (\() ->
                (Document.equalId
                    (initFakeDocument 1 "1" "Gabba")
                    (initFakeDocument 2 "2" "Hey" ))
                    |> Expect.equal False)

        , test "Document.toString" <|
            \() ->
                (initFakeDocument 1 "1" "Gabba")
                    |> Document.documentToString
                    |> Expect.equal "1 Title lorem ipsum,Text lorem ispum dolor sin amet.,google.com,FakeNews,Trump,1,News,0,Fun,0,Gabba,2,False"


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

        ]