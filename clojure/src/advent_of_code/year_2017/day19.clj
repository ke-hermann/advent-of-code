(ns year-2017.day19
  (:require [clojure.string :as str]))


(def input (->> (slurp "resources/2017/day19.txt")
                (str/split-lines)))


(def start-pos
  (->> (range (count (input 0)))
       (filter #(= \| (get-in input [0 %])))
       (first)
       ((fn [i] [0 i]))))

(defn move [{:keys [direction] :as packet}]
  (case direction
    :up (update packet :x dec)
    :right (update packet :y inc)
    :down (update packet :x inc)
    :left (update packet :y dec)))

(defn peek-dir [{:keys [x y direction] :as packet}]
  (let [up (get-in input [(dec x) y] \space)
        down (get-in input [(inc x) y] \space)
        left (get-in input [x (dec y)] \space)
        right (get-in input [x (inc y)] \space)]
    (case direction
      :down (cond 
              (not= down \space) (assoc packet :direction :down)
              (not= left \space) (assoc packet :direction :left)
              (not= right \space) (assoc packet :direction :right))
      :right (cond 
               (not= down \space) (assoc packet :direction :down)
               (not= up \space) (assoc packet :direction :up)
               (not= right \space) (assoc packet :direction :right))
      :left (cond 
              (not= down \space) (assoc packet :direction :down)
              (not= up \space) (assoc packet :direction :up)
              (not= left \space) (assoc packet :direction :left))
      :up (cond 
            (not= right \space) (assoc packet :direction :right)
            (not= up \space) (assoc packet :direction :up)
            (not= left \space) (assoc packet :direction :left)))))



(defn update-packet [{:keys [x y direction letters] :as packet}]
  (let [c (get-in input [x y] \space)]
    (cond
      (Character/isUpperCase c) (move (update packet :letters #(conj % c)))
      (= c \+) (move (peek-dir packet))
      :else (move packet))))

(defn solve []
  (loop [packet {:x 0 :y 169 :direction :down :letters []}
         step-count 0]
    (if (= \space (get-in input [(packet :x) (packet :y)]))
      [step-count (str/join (packet :letters))]
      (recur (update-packet packet)
             (inc step-count)))))
