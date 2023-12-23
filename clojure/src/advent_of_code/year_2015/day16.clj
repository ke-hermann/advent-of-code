(ns year-2015.day16
  (:require [clojure.string :as s])
  (:gen-class))

(defn parse-input-line [line]
  (read-string (str "{" (s/replace line ":" " ") "}")))

(def aunts (->> (slurp "./resources/2015/day16.txt")
                (s/split-lines)
                (map parse-input-line)))

(defn matches? [k v aunt]
  (if (not (contains? aunt k))
    true
    (= (aunt k) v)))

(defn more-than? [k v aunt]
  (if (not (contains? aunt k))
    true
    (> (aunt k) v)))

(defn less-than? [k v aunt]
  (if (not (contains? aunt k))
    true
    (< (aunt k) v)))

(defn find-correct-aunt []
  (->> aunts
       (filter (partial matches? (symbol "children") 3))
       (filter (partial more-than? (symbol "cats") 7))
       (filter (partial matches? (symbol "samoyeds") 2))
       (filter (partial less-than? (symbol "pomeranians") 3))
       (filter (partial matches? (symbol "akitas") 0))
       (filter (partial matches? (symbol "vizslas") 0))
       (filter (partial less-than? (symbol "goldfish") 5))
       (filter (partial more-than? (symbol "trees") 3))
       (filter (partial matches? (symbol "cars") 2))
       (filter (partial matches? (symbol "perfumes") 1))))
