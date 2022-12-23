(ns year-2022.day23
  (:require [clojure.string :as st]
            [utils :refer [index2d]]))

(def start-pos (->> (slurp "resources/2022/day23.txt")
                    (st/split-lines)
                    (index2d)
                    (filter (fn [[k v]] (= v \#)))
                    (into {})))


(defn north? [elves [x y]]
  (not-any? (partial contains? elves) [[(dec x) (dec y)] [(dec x) y] [(dec x) (inc y)]]))

(defn south? [elves [x y]]
  (not-any? (partial contains? elves) [[(inc x) (inc y)] [(inc x) y] [(inc x) (dec y)]]))

(defn west? [elves [x y]]
  (not-any? (partial contains? elves) [[(inc x) (dec y)] [x (dec y)] [(dec x) (dec y)]]))

(defn east? [elves [x y]]
  (not-any? (partial contains? elves) [[(inc x) (inc y)] [x (inc y)] [(dec x) (inc y)]]))

(defn proposition [elves elf]
  (cond
    (north? elves elf) :N
    (south? elves elf) :S
    (west? elves elf) :W
    (east? elves elf) :E))

(defn all-propositions [elves]
  (group-by (partial proposition elves) (keys elves)))

(defn move [elves]
  (let [p (all-propositions elves)]
    (reduce #() elves)))
