(ns advent-of-code.year-2019.day4)

(defn digits
  "Returns a sequence of digits of n (a non-negative integer),
   from left to right (most significant digit first).
   Uses only arithmetic – no string conversion."
  [n]
  (if (zero? n)
    [0]  ; or '() if you prefer empty sequence for zero – choose one convention
    (loop [num n
           acc []]
      (if (zero? num)
        (reverse acc)
        (recur (quot num 10)      ; remove last digit
               (conj acc (rem num 10))))))) ; keep last digit

(defn adjacent? [i]
  (let [xs (partition 2 1 (digits i))]
    (some (fn [[a b]] (= a b)) xs)))

(defn adjacent-p2? [i]
  (let [xs (partition 4 1 (concat '(\?) (digits i) '(\?)))]
    (some (fn [[a b c d]] (and (not= a b ) (= b c) (not= c d))) xs)))

(defn increasing? [i]
  (let [xs (partition 2 1 (digits i))]
    (every? (fn [[a b]] (>= b a)) xs)))

(defn valid-pw? [i]
  (and (adjacent? i) (increasing? i)))

(defn valid-pw-p2? [i]
  (and (adjacent-p2? i) (increasing? i)))

(defn part-1 []
  (count (filter valid-pw? (range 353096 843212))))

(defn part-2 []
  (count (filter valid-pw-p2? (range 353096 843212))))
