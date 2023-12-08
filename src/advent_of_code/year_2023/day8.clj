(ns advent-of-code.year-2023.day8
  (:require [clojure.string :as str]))

(defn parse-line [s]
  (let [[root left right] (re-seq #"[A-Z1-9]{3}" s)]
    [root [left right]]))

(def network (->> (slurp "resources/2023/day8.txt")
                  (str/split-lines)
                  (map parse-line)
                  (into {})))

(def directions (cycle (str/trim (slurp "resources/2023/day8dirs.txt"))))

(defn move [direction node]
  (if (= direction \R)
    (second (network node))
    (first (network node))))

(defn part-1 []
  (loop [node "AAA" [hd & tl] directions i 0]
    (if (= node "ZZZ") i
        (recur (move hd node) tl (inc i)))))

(defn part-2 []
  (let [nodes (filter #(str/ends-with? % "A") (keys network))
         [hd & tl] directions
         i 0]
    (if (every? #(str/ends-with? % "Z") nodes) i
        (recur (vec (map #(move hd %) nodes)) tl (inc i)))))
