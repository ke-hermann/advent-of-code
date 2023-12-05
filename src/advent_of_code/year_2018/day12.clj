(ns year-2018.day12
  (:require [clojure.string :as str]))


(def rules (->> (slurp "resources/2018/day12.txt")
                (str/split-lines)
                (map #(str/split % #" => "))
                (into {})))

(def initial-state "##.###.......#..#.##..#####...#...#######....##.##.##.##..#.#.##########...##.##..##.##...####..####")

(defn pot-slides [state]
  (let [state* (str/join ["...." state "...."])
        slides (map str/join (partition 5 1 state*))]
    slides))

(defn evolve [pots]
  (let [xs (pot-slides pots)]
    (->> (map rules  xs)
         (str/join))))

(defn pot-score [pots i]
  (->> (map vector pots (range (* i -2) (count pots)))
       (filter (fn [[p idx]] (= p \#)))
       (map second)
       (reduce +)))

(defn part-1 []
  (loop [i 0 pots initial-state]
    (if (= i 112)
      (pot-score pots i)
      (recur (inc i) (evolve pots)))))

(defn part-2 []
  (+ 3010 (* 23 (- 50000000000 111))))
