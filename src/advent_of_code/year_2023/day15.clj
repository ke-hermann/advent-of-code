(ns advent-of-code.year-2023.day15
  (:require [clojure.string :as str]))


(def input (-> (slurp "resources/2023/day15.txt")
               (str/trim)
               (str/split #",")))

(defn hash-value [s]
  (reduce (fn [acc i] (mod (* (+ acc (int i)) 17) 256)) 0 s))

(defn equalop [lenses [label focal]]
  (if (.contains (map first lenses) label)
    (mapv (fn [[l f]] (if (= l label) [l focal] [l f])) lenses)
    (conj lenses [label focal])))

(defn removeop [lenses label]
  (vec (remove (fn [[l f]] (= l label)) lenses)))

(defn process [boxes instruction]
  (let [[label focal] (str/split instruction #"\=|\-")
        pos (hash-value label)
        lenses (boxes pos)]
    (if (str/includes? instruction "=")
      (assoc boxes pos (equalop lenses [label focal]))
      (assoc boxes pos (removeop lenses label)))))

(defn box-score [[pos lenses]]
  (reduce + (map-indexed (fn [i [_ f]] (* (inc pos) (inc i) (parse-long f))) lenses)))

(defn part-1 []
  (reduce + (map hash-value input)))

(defn part-2 []
  (let [boxes (into {} (map (fn [i] [i []]) (range 257)))]
    (->> (reduce process boxes input)
         (map box-score)
         (reduce +))))
