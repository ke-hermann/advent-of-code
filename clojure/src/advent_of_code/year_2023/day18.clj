(ns advent-of-code.year-2023.day18
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [pprint-map neighbors]]))

(defn parse-line [s]
  (let [[dir i color] (str/split s #"\s+")]
    {:direction dir :steps (parse-long i)}))

(defn parse-line-2 [s]
  (let [[_ _  color] (str/split s #"\s+")
        hs (subs color 2 7)
        d (subs color 7 8)
        hexval (Long/parseLong hs 16)]
    {:direction ({"0" "R" "1" "D" "2" "L" "3" "U"} d)  :steps hexval}))

(def instructions (->> (slurp "resources/2023/day18.txt")
                       (str/split-lines)
                       (map parse-line-2)))

(defn expand [[x y direction steps seen]]
  (if (zero? steps) [x y seen]
      (recur 
       (case direction
         "R" [x (inc y) "R" (dec steps) (cons [x y] seen)]
         "L" [x (dec y) "L" (dec steps) (cons [x y] seen)]
         "D" [(inc x) y "D" (dec steps) (cons [x y] seen)]
         "U" [(dec x) y "U" (dec steps) (cons [x y] seen)]))))

(defn dig-trench []
  (loop [x 0 y 0 seen '() [hd & tl :as xs] instructions]
    (if (empty? xs)
      seen
      (let [{:keys [direction steps]} hd
            [x* y* seen*] (expand [x y direction steps seen])]
        (recur x* y* seen* tl)))))

(defn flood-fill [walls start]
  (loop [queue [start] seen (set walls)]
    (if (empty? queue)
      (count seen)
      (recur (remove seen (set (mapcat neighbors queue)))
             (apply conj seen queue)))))

(defn shoelace [vertices]
  (let [xs (conj vertices (last vertices))
        sum1 (reduce (fn [acc [[x y] [i j]]] (+ acc (* x j))) 0 (partition 2 1 xs))
        sum2 (reduce (fn [acc [[x y] [i j]]] (+ acc (* y i))) 0 (partition 2 1 xs))]
    (- (+ (inc (count vertices ))
          (quot (abs (- sum1 sum2)) 2 ))
       (quot (count vertices) 2))))

(defn part-1 []
  (flood-fill (dig-trench) [1 1]))
