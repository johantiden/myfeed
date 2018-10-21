module Subject exposing (..)

import List.Extra exposing (uniqueBy)
import Common exposing (delimit)

type alias Subject =
   { name: String
   , hashTag: Bool
   , showAsTab: Bool
   , depth: Int
   }


groupSubjectsByDepth : List Subject -> List (Int, List Subject)
groupSubjectsByDepth subjects =
    subjects
        |> List.sortBy .depth
        |> List.Extra.groupWhile (\a b -> a.depth == b.depth)
        |> List.map (\(prototype, list) -> (prototype.depth, list))

subjectsToString : List Subject -> String
subjectsToString ss =
    ss
        |> List.map subjectToString
        |> delimit ","
        |> List.foldr (++) ""

subjectToString : Subject -> String
subjectToString s =
    [s.name, String.fromInt s.depth]
        |> delimit ","
        |> List.foldr (++) ""
