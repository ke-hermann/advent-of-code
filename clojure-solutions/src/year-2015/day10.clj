(ns advent-of-code.2015.day10)

(def input [1 1 1 3 2 2 2 1 1 3])

(defn transform [xs]
  [(count xs) (first xs)])

(defn look-and-say [xs]
  (let [grps (partition-by identity xs)]
    (flatten (map transform grps))))

(defn solve []
  (-> (iterate look-and-say input)
      (nth 50)
      (count)))
