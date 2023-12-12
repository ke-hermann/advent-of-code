(ns advent-of-code.year-2023.day12
  (:require [clojure.string :as str]))

(defn parse [s]
  (let [[left right] (str/split s #"\s+")]
    {:string left :pattern (map parse-long (str/split right #","))}))

(def input (->> (slurp "resources/2023/day12.txt")
                (str/split-lines)
                (map parse)))


(defn expand [s]
  [(str/replace-first s "?" "#") (str/replace-first s "?" ".")])

(defn perms [s]
  (loop [stack [s] i 0]
    (if (> i (count (filter #{\?} s)))
      (distinct stack)
      (recur (mapcat expand stack) (inc i)))))

(defn valid? [pattern s]
  (let [s* (str/split s #"\.+")]
    (= pattern (map count (remove #{""} s*)))))

(defn valid-strings [{:keys [string pattern]}]
  (let [candidates (perms string)]
    candidates
    (count (filter (partial valid? pattern)  candidates))))

(defn part-1 []
  (reduce + (map valid-strings input)))
