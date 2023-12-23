(ns advent-of-code.year-2015.day15
  (:require [clojure.string :as str]))

(defn parse-line [s]
  (let [[name xs & _] (str/split s #":")
        properties (->> (str/split (str/trim xs) #", ")
                         (map #(str/split % #"\s+"))
                         (map (fn [[k v]] [k (parse-long v)]))
                         (into {}))]
    {:name name :properties properties}))

(def ingredients (->> (slurp "resources/2015/day15.txt")
                      (str/split-lines)
                      (map parse-line)))

(defn cookie [[{:keys [name properties]} i]]
  {:name name
   :properties (into {} (map (fn [[k v]] [k (* i v)]) properties))})

(defn total-score [teaspoons]
  (let [xs (map vector ingredients teaspoons)
        ys (map cookie xs)
        sums (apply merge-with + (map :properties ys))
        calories (sums "calories")
        scores (-> sums
                   (dissoc "calories")
                   (vals))]
    (if (or (some #(<= % 0) scores)
            (not= calories 500)) ;; part 2
      0
      (reduce * scores))))

(defn solve []
  (->> (for [x (range 1 100) y (range 1 100) i (range 1 100) j (range 1 100)]
         (when (= 100 (+ x y i j))
           (total-score [x y i j])))
       (remove nil?)
       (apply max)))
