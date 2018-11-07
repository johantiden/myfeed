module Subject exposing (..)

import List.Extra exposing (uniqueBy)
import Common exposing (delimit)

type alias Subject =
   { name: String
   , hashTag: Bool
   , showAsTab: Bool
   , subjectType: SubjectType
   }

type alias SubjectType =
    { constant: String
    , title: String
    , order: Int
    }

groupSubjectsByType : List Subject -> List (SubjectType, List Subject)
groupSubjectsByType subjects =
    subjects
        |> List.sortBy (\s -> s.subjectType.constant)
        |> List.Extra.groupWhile (\a b -> a.subjectType == b.subjectType)
        |> List.map (\(prototype, list) -> (prototype.subjectType, list))