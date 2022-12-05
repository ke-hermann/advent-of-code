(ns advent-of-code.2021.day8
  (:require [clojure.string :as s]))

(defn parse-inputs [s]
  (->> (s/split s #"\|")
       (first)
       (s/trim)
       (#(s/split % #"\s+"))))

(defn parse-outputs [s]
  (->> (s/split s #"\|")
       (last)
       (s/trim)
       (#(s/split % #"\s+"))))

(defn unique-pattern? [xs]
  (contains? #{2 4 3 7} (count xs)))

(defn unique-occurences [xs]
  (count (filter unique-pattern? xs)))

(def outputs (->> (slurp "resources/2021/day8.txt")
                  (s/split-lines)
                  (map parse-outputs)))

(def inputs (->> (slurp "resources/2021/day8.txt")
                 (s/split-lines)
                 (map parse-inputs)))

(def foo (s/split "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab" #"\s+"))

(defn find-nine [segments four]
  (->> (filter #(= 6 (count %)) segments)
       (filter #(every? (fn [c] (.contains % (str c))) four))
       (first)))

(defn find-zero [segments one nine]
  (->> (filter #(= 6 (count %)) segments)
       (remove #{nine})
       (filter #(every? (fn [c] (.contains % (str c))) one))
       (first)))

(defn find-six [segments zero nine]
  (->> (filter #(= 6 (count %)) segments)
       (remove #{nine zero})
       (first)))

(defn find-three [segments one]
  (->> (filter #(= 5 (count %)) segments)
       (filter #(every? (fn [c] (.contains % (str c))) one))
       (first)))

(defn find-five [segments six]
  (->> (filter #(= 5 (count %)) segments)
       (filter #(every? (fn [c] (.contains six (str c))) %))
       (first)))

(defn find-two [segments three five]
  (->> (filter #(= 5 (count %)) segments)
       (remove #{three five})
       (first)))

(defn build-signal-map [segments]
  (let [eight (first (filter #(= 7 (count %)) segments))
        one   (first (filter #(= 2 (count %)) segments))
        four (first (filter #(= 4 (count %)) segments))
        seven (first (filter #(= 3 (count %)) segments))
        nine (find-nine segments four)
        zero (find-zero segments one nine)
        six (find-six segments zero nine)
        three (find-three segments one)
        five (find-five segments six)
        two (find-two segments three five)]
    {eight "8" one "1" four "4" seven "7" nine "9" zero "0" six "6" three "3" five "5" two "2"}))

(defn decode-output [entry]
  (let [[in out] entry
        signal-map (build-signal-map (map #(apply str (sort %)) in))]
    (->> (map signal-map (map #(apply str (sort %)) out))
         (apply str)
         (Integer/parseInt))))

(defn solution []
  (->> (map vector inputs outputs)
       (map decode-output)
       (reduce +)))
