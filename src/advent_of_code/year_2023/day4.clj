(ns year-2023.day4
  (:require [clojure.string :as str]
            [clojure.math :refer [pow]]))

(defn parse-numbers [s]
  (map #(Integer/parseInt %) (re-seq #"\d+" s)))

(defn parse-line [s]
  (let [[left right] (map parse-numbers (str/split s #"\|"))]
    {:id (first left) :winning (rest left) :player right}))

(def cards (->> (slurp "resources/2023/day4.txt")
                (str/split-lines)
                (map parse-line)
                (vec)))

(defn score [x] (int (pow 2 (dec x))))

(defn winners [{:keys [winning player]}]
  (count (filter (set winning) player)))

(defn fetch-copies [{:keys [id] :as card}]
  (subvec cards id (+ id (winners card))))

(defn fetch-rec [card]
  (let [new-cards (fetch-copies card)]
    (cons card (mapcat fetch-rec new-cards))))

(defn part-1 []
  (reduce + (map (comp score winners) cards)))

(defn part-2 []
  (count (mapcat fetch-rec cards)))
