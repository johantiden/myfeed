module Subject exposing (..)

import List.Extra exposing (uniqueBy)
import Common exposing (delimit)

type alias Subject =
   { name: String
   , hashTag: Bool
   , showAsTab: Bool
   , depth: Int
   , subjectType: SubjectType
   }

type alias SubjectType =
    { constant: String
    , title: String
    , order: Int
    }

groupSubjectsByDepth : List Subject -> List (Int, List Subject)
groupSubjectsByDepth subjects =
    subjects
        |> List.sortBy .depth
        |> List.Extra.groupWhile (\a b -> a.depth == b.depth)
        |> List.map (\(prototype, list) -> (prototype.depth, list))

groupSubjectsByType : List Subject -> List (SubjectType, List Subject)
groupSubjectsByType subjects =
    subjects
        |> List.sortBy (\s -> s.subjectType.constant)
        |> List.Extra.groupWhile (\a b -> a.subjectType == b.subjectType)
        |> List.map (\(prototype, list) -> (prototype.subjectType, list))

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
