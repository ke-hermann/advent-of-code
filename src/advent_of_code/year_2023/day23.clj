(ns advent-of-code.year-2023.day23
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [pprint-map neighbors index2d]]))

(def input (->> (slurp "resources/2023/day23.txt")
                (str/split-lines)
                (index2d)))

(def goal [140 139])

(defn open? [[x y] [i j :as p]]
  (cond
    (= (input p) ".") true
    (= (input p) ">") (= y (dec j))
    (= (input p) "<") (= y (inc j))
    (= (input p) "v") (= x (dec i))
    (= (input p) ">") (= x (inc y))
    :else false))

(defn open-2? [[x y] [i j :as p]]
  (contains? #{"." ">" "<" "v" "^"} (input p)))

(defn walk [[[x y :as p] visited]]
  (let [nbs (neighbors p)
        valid (remove visited (filter #(open-2? p %) nbs))
        v* (conj visited p)]
    (cond
      (= p goal) [goal v*]
      ;;(= (input p) ">") [[[x (inc y)] v*]]
      ;;(= (input p) "<") [[[x (dec y)] v*]]
      ;;(= (input p) "v") [[[(inc x) y] v*]]
      ;;(= (input p) "^") [[[(dec x) y] v*]]
      :else (map (fn [x] [x v*]) valid))))

(defn target? [[p coll]]
  (= p goal))

(defn part-1 []
  (loop [states [[[0 1] #{}]] results []]
    (let [finished (filter target? states)
          states* (remove target? states)]
      (if (empty? states)
        (map #(count (second %)) results)
        (recur (mapcat walk states*)
               (apply conj results finished))))))
