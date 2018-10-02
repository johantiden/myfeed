module Common exposing (..)


delimit : a -> List a -> List a
delimit delimiter list =
    case list of
        [] -> []
        [a] -> [a]
        [a,b] -> [a, delimiter, b]
        a::b::tail -> [a, delimiter, b] ++ [delimiter] ++ delimit delimiter tail
