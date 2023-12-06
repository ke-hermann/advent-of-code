(ns advent-of-code.year-2023.day6)


(def races [[51 222] [92 2031] [68 1126] [90 1225]])

(defn distance [duration waited]
  (* (- duration waited) waited))

(defn winners [[time record]]
  (filter #(> (distance time %) record) (range time)))

(defn part-1 []
  (reduce * (map (comp count winners) races)))

(defn part-2 []
  (->> (winners [51926890 222203111261225])
       (count)))
