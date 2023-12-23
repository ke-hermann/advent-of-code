(ns advent-of-code.2015.day7
  (:require [clojure.string :as s]
            [clojure.core.match :refer [match]]))


(def input (->> (slurp "resources/2015/day7.txt")
                (s/split-lines)
                (map #(s/split % #"\s+"))))

(defn num? [s] (number? (read-string s)))

(defn shift-right [vm k1 k2 n]
  (if (nil? (vm k1))
    :stalled
    (assoc vm k2 (bit-shift-right (vm k1) (parse-long n)))))

(defn shift-left [vm k1 k2 n]
  (if (nil? (vm k1))
    :stalled
    (assoc vm k2 (bit-shift-left (vm k1) (parse-long n)))))

(defn or-op [vm x y k]
  (let [a (if (num? x) (parse-long x) (vm x))
        b (if (num? y) (parse-long y) (vm y))]
    (if (or (nil? a) (nil? b))
      :stalled
      (assoc vm k (bit-or a b)))))


(defn and-op [vm x y k]
  (let [a (if (num? x) (parse-long x) (vm x))
        b (if (num? y) (parse-long y) (vm y))]
    (if (or (nil? a) (nil? b))
      :stalled
      (assoc vm k (bit-and a b)))))

(defn set-op [vm k v]
  (let [v (if (num? v) (parse-long v) (vm v))]
    (if (nil? v)
      :stalled
      (assoc vm k v))))

(defn not-op [vm k v]
  (let [v (if (num? v) (parse-long v) (vm v))]
    (if (nil? v)
      :stalled
      (assoc vm k (bit-and-not 16rFFFF v)))))

(defn step [vm instruction]
  (match instruction
         [v "->" k] (set-op vm k v)
         [k1 "RSHIFT" n "->" k2] (shift-right vm k1 k2 n)
         [k1 "LSHIFT" n "->" k2] (shift-left vm k1 k2 n)
         [x "OR" y "->" k] (or-op vm x y k)
         ["NOT" v "->" k] (not-op vm k v)
         [x "AND" y "->" k] (and-op vm x y k)
         :else (throw (Error. "missing clause"))))

(defn process []
  (loop [vm {} [hd & tl :as instructions] input]
    (if (empty? instructions) (vm "a")
        (let [vm* (step vm hd)]
          (if (= :stalled vm*)
            (recur vm (conj (vec tl) hd))
            (recur  vm* (vec tl)))))))
