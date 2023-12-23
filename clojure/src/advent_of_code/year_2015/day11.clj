(ns advent-of-code.year-2015.day11
  (:require [clojure.string :as str]))

(defn inc-char [c]
  (char (inc (int c))))

(defn inc-string [string]
  (loop [s string i (dec (count string))]
    (let [c (nth s i nil)]
      (cond
        (= i -1) (str "a" s)
        (not= c \z) (str/join (assoc (vec s) i (inc-char c)))
        :else (recur (str/join (assoc (vec s) i \a)) (dec i) )))))

(defn straight? [s]
  (some (fn [[a b c]] (= (+ 2 (int a)) (inc (int b)) (int c))) (partition 3 1 s)))

(defn no-forbidden? [s]
  (empty? (filter #{\i \o \l} s)))

(defn pairs? [s]
  (->> (partition-by identity s)
       (filter #(= 2 (count %)))
       (#(= 2 (count %)))))

(defn password? [s]
  (and (straight? s) (no-forbidden? s) (pairs? s)))

(defn part-1 []
  (loop [s (inc-string "hepxxyzz")]
    (if (password? s) s
        (recur (inc-string s)))))
