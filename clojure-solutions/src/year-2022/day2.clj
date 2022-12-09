(ns aoc-clojure.year-2022.day2
  (:require [clojure.string :as s]))

(def input (->> (slurp "resources/2022/day2.txt")
                (s/split-lines)
                (map #(s/split % #"\s+"))))

(defn score-game [[x y]]
  (case [x y]
    ["A" "X"] 4
    ["A" "Y"] 8
    ["A" "Z"] 3
    ["B" "X"] 1
    ["B" "Y"] 5
    ["B" "Z"] 9
    ["C" "X"] 7
    ["C" "Y"] 2
    ["C" "Z"] 6))

(defn score-game-2 [[x y]]
  (case [x y]
    ["A" "X"] 3
    ["A" "Y"] 4
    ["A" "Z"] 8
    ["B" "X"] 1
    ["B" "Y"] 5
    ["B" "Z"] 9
    ["C" "X"] 2
    ["C" "Y"] 6
    ["C" "Z"] 7))

(defn part-1 []
  (reduce + (map score-game input)))

(defn part-2 []
  (reduce + (map score-game-2 input)))
