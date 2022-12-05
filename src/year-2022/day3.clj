(ns aoc-clojure.year-2022.day3
  (:require [clojure.string :as s]))


(def rucksacks (->> (slurp "resources/2022/day3.txt")
                    (s/split-lines)))

(def elf-groups (partition 3 rucksacks))

(defn build-compartment [s]
  (let [l (quot (count s) 2)]
    [(take l s) (drop l s)]))

(defn common-items [xs]
  (apply clojure.set/intersection (map set xs)))

(defn score [c]
  (if (Character/isLowerCase c)
    (- (int c) 96)
    (- (int c) 38)))

(defn solve [xs]
  (->> (mapcat common-items xs)
       (reduce #(+ %1 (score %2)))))

(defn part-1 []
  (solve (map build-compartment rucksacks)))

(defn part-2 [] (solve elf-groups))
