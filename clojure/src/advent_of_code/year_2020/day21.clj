(ns advent-of-code.year-2020.day21
  (:require [clojure.string :as str]
            [clojure.set :as set]))


(defn parse-line [s]
  (let [[left right] (str/split s #"\(contains")]
    [(re-seq #"[a-z]+" left)
     (re-seq #"[a-z]+" right)]))

(def input (->> (slurp "resources/2020/day21.txt")
                (str/split-lines)
                (map parse-line)))

(def allergens (set (mapcat second input)))

(def ingredients (set (mapcat first input)))

(defn candidates [allergen]
  (->> (filter (fn [[xs ys]] (.contains ys allergen)) input)
       (map (comp set first))
       (apply set/intersection)))

(def index 
  (loop [xs allergens pairs {} known #{}]
    (if (empty? xs)
      pairs
      (let [cnd (map (fn [a] [a (set/difference (candidates a) known)]) xs)
            valid (filter (fn [[k v]] (= 1 (count v))) cnd)]
        (recur (apply disj xs (set (map first valid)))
               (apply conj pairs valid)
               (set (apply conj known (mapcat second valid))))))))
        

(defn part-1 []
  (let [xs (set/difference ingredients (set (mapcat second index)))]
    (count (filter xs (mapcat first input)))))

(defn part-2 []
  (->> (map (fn [[k v]] [k (first v)]) index)
       (sort-by first)
       (map second)
       (str/join ",")))
