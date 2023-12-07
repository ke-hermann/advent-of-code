(ns advent-of-code.year-2016.day10
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(defn parse-token [t]
  (if (number? (read-string t))
    (parse-long t)
    t))

(defn parse-line [s]
  (vec (map parse-token (str/split s #"\s+"))))

(def input (->> (slurp "resources/2016/day10.txt")
                (str/split-lines)
                (map parse-line)))


(defn process [bots line]
  (println line)
  (match line
         ["value" v "goes" "to" "bot" k]
         (assoc bots k v)

         ["bot" k "gives" "low" "to" "bot" l "and" "high" "to" "bot" h]

         :else (throw (Exception. line))))
