(ns year-2015.day20)


(def limit 29000000)

(defn house-value [x]
  (->> (range 1 (inc (quot x 2)))
       (filter #(zero? (mod x %)))
       (cons x)
       (map #(* % 10))
       (reduce +)))

(defn part-1 []
  (loop [i 1000000]
    (if (>= (house-value i) limit)
      i
      (recur (inc i)))))
