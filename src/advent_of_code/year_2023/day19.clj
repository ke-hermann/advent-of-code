(ns advent-of-code.year-2023.day19
  (:require [clojure.string :as str]))

(defn parse-condition [s]
  (let [[a b out] (str/split s #"<|>|:")]
    {:category a :value (parse-long b) :destination out
     :f (if (str/includes? s ">") > <)}))

(defn parse-workflow [s]
  (let [name (str/join (take-while #(not= % \{) s))
        rest (last (first (re-seq #"\{(.*)\}" s)))
        conditions (str/split rest #",")]
    [name (conj (mapv parse-condition (butlast conditions))
                 (last conditions))]))

(defn parse-category [s]
  (let [[k v] (str/split s #"=")]
    [k (parse-long v)]))

(defn parse-part [s]
  (let [l (count s)
        s* (str/split (subs s 1 (dec l)) #",")]
    s*))

(def input
  (let [file (slurp "resources/2023/day19.txt")
        [xs ys] (map str/split-lines (str/split file #"\r\n\r\n"))
        workflows (into {} (map parse-workflow xs))
        parts (map #(into {} (map parse-category (parse-part %))) ys)]
    [workflows parts]))

(def workflows (first input))
(def parts (last input))

(defn process-workflow [part [hd & tl]]
  (cond
    (string? hd) hd
    ((hd :f) (part (hd :category)) (hd :value)) (hd :destination)
    :else (process-workflow part tl)))

(defn accepted? [part]
  (loop [p part wf "in" ]
    (let [next-wf (process-workflow p (workflows wf))]
      (case next-wf
        "A" true
        "R" false
        (recur p next-wf)))))

(defn part-1 []
  (->> (filter accepted? parts)
       (mapcat vals)
       (reduce +)))
