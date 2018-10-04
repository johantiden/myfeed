module Common exposing (..)


delimit : a -> List a -> List a
delimit delimiter list =
    case list of
        [] -> []
        [a] -> [a]
        [a,b] -> [a, delimiter, b]
        a::b::tail -> [a, delimiter, b] ++ [delimiter] ++ delimit delimiter tail


descending : comparable -> comparable -> Order
descending a b =
    case compare a b of
      LT -> GT
      EQ -> EQ
      GT -> LT

sortDescendingBy : (a -> comparable) -> List a -> List a
sortDescendingBy mapper =
    List.sortWith (\a b -> descending (mapper a) (mapper b))