(ns advent-of-code.2021.day2
  (:require [clojure.string :as s]))


(def input (->> (slurp "resources/2021/day2.txt")
                (s/split-lines)))

(defn step [state instruction]
  (let [[direction value] (s/split instruction #"\s+")
        i (Integer/parseInt value)]
    (case direction
      "forward" (update state :horizontal #(+ % i))
      "down"    (update state :depth #(+ % i))
      "up"      (update state :depth #(- % i)))))

(defn step-2 [state instruction]
  (let [[direction value] (s/split instruction #"\s+")
        i (Integer/parseInt value)]
    (case direction
      "forward" (-> (update state :horizontal #(+ % i))
                    (update :depth #(+ % (* (state :aim) i))))
      "down"    (update state :aim #(+ % i))
      "up"      (update state :aim #(- % i)))))


(defn part-1 []
  (reduce step {:horizontal 0 :depth 0} input))


