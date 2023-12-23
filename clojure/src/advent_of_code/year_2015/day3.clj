(ns advent-of-code.2015.day3)


(def start {:x 0 :y 0 :seen [[0 0]]})

(def input (slurp "resources/2015/day3.txt"))

(def santa-1-input (apply concat (partition 1 2 input)))

(def santa-2-input (apply concat (partition 1 2 (rest input))))

(defn update-pos [m c]
  (let [m* (case c
             \> (update m :x inc)
             \< (update m :x dec)
             \^ (update m :y dec)
             \v (update m :y inc)
             m)]
    (update m* :seen #(conj % [(m* :x) (m* :y)]))))

(defn part-1 []
  (->> (reduce update-pos start input)
       (:seen)
       (set)
       (count)))

(defn part-2 []
  (let [houses-1 (reduce update-pos start santa-1-input)
        houses-2 (reduce update-pos start santa-2-input)]
    (->> (concat (houses-1 :seen) (houses-2 :seen))
         (set)
         (count))))
