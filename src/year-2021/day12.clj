(ns advent-of-code.2021.day12
  (:require [clojure.string :as s]))

(defn flip [xs]
  (concat xs (map reverse xs)))


(def connections (->> (slurp "resources/2021/day12.txt")
                      (s/split-lines)
                      (map #(s/split % #"\-"))
                      (flip)
                      (group-by first)
                      (map (fn [[k v]] [k (map second v)]))
                      (into {})))

(defn big-cave? [node]
  (every? #(Character/isUpperCase %) node))

(defn small-cave? [cave]
  (and (every? #(Character/isLowerCase %) cave)
       (not= cave "start")
       (not= cave "end")))

(defn non-twice? [caves]
  (let [sm-caves (filter small-cave? caves)]
    (= sm-caves (distinct sm-caves))))

(defn traverse [pos seen visited]
  (let [nodes (if (non-twice? (conj seen pos)) (remove #{"start"} (connections pos))
                  (remove (set seen) (connections pos)))
        seen* (if (big-cave? pos) seen (conj seen pos))]
    (if (= pos "end")
      [(conj visited "end")]
      (mapcat #(traverse % seen* (conj visited pos)) nodes))))

(defn solution []
  (->> (traverse "start" [] [])
       (distinct)
       (count)))
