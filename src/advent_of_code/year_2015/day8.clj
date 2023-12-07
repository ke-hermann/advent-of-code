(ns advent-of-code.year-2015.day8
  (:require [clojure.string :as str]))


(def input (->> (slurp "resources/2015/day8.txt")
                (str/split-lines)))

(defn rep-hex [s]
  (str/replace s #"\\x[0-9a-f]{2}" "?"))

(defn rep-back [s]
  (str/replace s #"\\\\" "1"))

(defn rep-quote [s]
  (str/replace s #"\\\"" "\""))

(defn rep-all [s]
  (-> (rep-hex s)
      (rep-quote)
      (rep-back)))

(defn length [s]
  (- (count (rep-all s)) 2))

(defn part-1 []
  (- (reduce + (map count input))
     (reduce + (map length input))))
