(ns advent-of-code.2015.day5)

(def input (->> (slurp "resources/2015/day5.txt")
                (clojure.string/split-lines)))

(defn vowels? [s]
  (-> (filter (set "aeiou") s)
      (count)
      (>= 3)))

(defn twice-in-row? [s]
  (->> (partition 2 1 s)
       (some (fn [[a b]] (= a b)))))

(defn no-forbidden? [s]
  (not-any? #(.contains s %) ["ab", "cd", "pq", "xy"]))

(defn nice? [s]
  (and (vowels? s)
       (twice-in-row? s)
       (no-forbidden? s)))

(defn no-overlap-pair? [xs]
  (if (< (count xs) 2)
    false
    (let [hd (apply str (take 2 xs))
          tl (apply str (drop 2 xs))]
      (if (.contains tl hd)
        true
        (no-overlap-pair? (apply str (rest xs)))))))

(defn repeats? [xs]
  (->> (partition 3 1 xs)
       (some (fn [[a b c]] (= a c)))))

(defn nice-2? [xs]
  (and (no-overlap-pair? xs) (repeats? xs)))

(defn part-1 []
  (count (filter nice? input)))

(defn part-2 []
  (count (filter nice-2? input)))
