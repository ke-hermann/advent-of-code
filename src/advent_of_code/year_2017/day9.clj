(ns year-2017.day9)

(defn in-group [coll]
  (let [[hd & rest] coll]
    (cond
      (empty? coll) []
      (= hd \<) (in-garbage rest)
      :else (cons hd (in-group rest)))))

(defn in-garbage [coll]
  (let [[n m & rest] coll]
    (cond
      (empty? coll) []
      (= n \!) (in-garbage rest)
      (= n \>) (in-group (cons m rest))
      :else (in-garbage (cons m rest)))))

(defn score [g]
  (loop [d 0 coll g score 0]
    (let [[x & xs] coll
          depth* (case x \{ (inc d) \} (dec d) d)
          score* (if (= x \{) (+ depth* score) score)]
      (if (empty? coll) score*
          (recur depth* xs score*)))))

