(ns year-2020.day7
  (:require [clojure.string :as st]))

(def input (->> (slurp "resources/2020/day7.txt")
                (st/split-lines)
                (map #(rest (re-seq #"[A-Z]{1}" %)))
                (map (fn [[x y]] [y x]))))

(def requirements (->> (group-by first input)
                       (map (fn [[hd  more]] [hd (map second more)]))
                       (into {})))

(def start
  (let [rqs (map first requirements)
        hd (filter #(not (.contains rqs %)) (flatten (map second requirements)))]
    (first  hd)))

(defn sort-instructions []
  (loop [q requirements done [start]]
    (if (empty? q)
      done
      (let [q* (map (fn [[k v]] [k (remove (set done) v)]) q)
            n (filter (fn [[k v]] (empty? v)) q*)
            r (first (sort (map first n)))]
        (recur (dissoc q r) (conj done r))))))
