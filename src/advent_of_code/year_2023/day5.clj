(ns advent-of-code.year-2023.day5
  (:require [clojure.string :as str]))

(def seeds [3136945476 509728956 1904897211 495273540 1186343315 66026055 1381149926 11379441 4060485949 190301545 444541979 351779229 1076140984 104902451 264807001 60556152 3676523418 44140882 3895155702 111080695])

(defn parse-nums [s] (map parse-long (str/split s #"\s+")))

(defn parse-entry [entry]
  (let [[name & more] entry
        ranges (map parse-nums more)]
    ranges))

(def input (-> (slurp "resources/2023/day5.txt")
               (str/split #"\r\n\r\n")
               (as-> xs (map str/split-lines xs))
               (as-> xs (map parse-entry xs))))

(def seed-to-soil (nth input 0))
(def soil-to-fertilizer (nth input 1))
(def fertilizer-to-water (nth input 2))
(def water-to-light (nth input 3))
(def light-to-temperature (nth input 4))
(def temperature-to-humidity (nth input 5))
(def humidity-to-location (nth input 6))


(defn in-range? [i [dest start length]]
  (and (>= i start) (< i (+ start length))))

(defn lookup [m k]
  (let [xs (first (filter (partial in-range? k) m))
        [dest start _] xs]
    (if (nil? xs) k
        (+ dest (- k start)))))

(defn seed-location [seed]
  (->> (lookup seed-to-soil seed)
       (lookup soil-to-fertilizer)
       (lookup fertilizer-to-water)
       (lookup water-to-light)
       (lookup light-to-temperature)
       (lookup temperature-to-humidity)
       (lookup humidity-to-location)))

(defn find-lowest [[start length]]
  (let [limit (+ start length)]
    (loop [i start lowest Long/MAX_VALUE]
      (if (>= i limit)
        lowest
        (let [l (seed-location i)]
          (recur (inc i) (min lowest l)))))))

(defn part-1 []
  (apply min (map seed-location seeds)))

(defn part-2 []
  (let [pairs (partition 2 seeds)
        guess 3281180476]
    (find-lowest [(- guess 100000) 200000])))
