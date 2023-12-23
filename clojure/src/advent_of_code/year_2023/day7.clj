(ns advent-of-code.year-2023.day7
  (:require [clojure.string :as str]))


(def input (->> (slurp "resources/2023/day7.txt")
                (str/split-lines)
                (map (fn [s] (str/split s #"\s+")))
                (map (fn [[k v]] [k (parse-long v)]))
                (into {})))

(def cards "J23456789ATQKA" )

(def card-vals (into {} (map vector cards (range))))

(defn five-of-a-kind? [hand]
  (.contains (vals (frequencies hand)) 5))

(defn four-of-a-kind? [hand]
  (.contains (vals (frequencies hand)) 4))

(defn full-house? [hand]
  (= '(2 3) (sort (vals (frequencies hand)))))

(defn three-of-a-kind? [hand]
  (= '(1 1 3) (sort (vals (frequencies hand)))))

(defn two-pair? [hand]
  (= '(1 2 2) (sort (vals (frequencies hand)))))

(defn one-pair? [hand]
  (= '(1 1 1 2) (sort (vals (frequencies hand)))))

(defn high-card? [hand]
  (apply distinct? hand))

(defn score [hand]
  (cond
    (five-of-a-kind? hand) 7
    (four-of-a-kind? hand) 6
    (full-house? hand) 5
    (three-of-a-kind? hand) 4
    (two-pair? hand) 3
    (one-pair? hand) 2
    (high-card? hand) 1
    :else 0))

(defn expand [s i]
  (map #(str/join [(subs s 0 i) % (subs s (inc i))]) cards))

(defn joker-score [hand]
  (loop [xs [hand] i 0]
    (if (= i 5)
      (apply max (map score xs))
      (recur (if (= \J (nth hand i))
               (apply conj xs (mapcat #(expand % i) xs))
               xs)
             (inc i)))))

(defn compare-hands [hand-1 hand-2]
  (let [x (joker-score hand-1) ;; p1 (score hand-1)
        y (joker-score hand-2) ;; p2 (score hand-2)
        ]
    (if (not= x y)
      (compare x y)
      (loop [[[a b] & more] (map vector hand-1 hand-2)]
        (let [s (compare (card-vals a) (card-vals b))]
          (if (zero? s)
            (recur more) s))))))

(defn run []
  (->> (sort compare-hands (keys input))
       (map-indexed (fn [i k] (* (inc i) (input k))))
       (reduce +)))
