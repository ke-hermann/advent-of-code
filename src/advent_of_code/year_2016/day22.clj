(ns year-2016.day22
  (:require [clojure.string :as str]))

(defn parse-line [s]
  (let [xs (map parse-long (re-seq #"\d+" s))
        [x y size used avail perc] xs]
    {:x x :y y :size size :used used :avail avail :perc perc}))

(defn viable-pair? [[n1 n2]]
  (and (not= [(n1 :x) (n1 :y)] [(n2 :x) (n2 :y)])
       (not (zero? (n1 :used)))
       (<= (n1 :used) (n2 :avail))))

(def nodes (->> (slurp "resources/2016/day22.txt")
                (str/split-lines)
                (map parse-line)
                (vec)))

(defn part-1 []
  (->> (for [n1 nodes] (for [n2 nodes] [n1 n2]))
       (apply concat)
       (filter viable-pair?)
       (count)))
