(ns advent-of-code.year-2016.day10
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(defn parse-token [t]
  (if (number? (read-string t))
    (parse-long t)
    t))

(defn parse-line [s]
  (vec (map parse-token (str/split s #"\s+"))))

(def input (->> (slurp "resources/2016/day10.txt")
                (str/split-lines)
                (mapv parse-line)))

(defn conj-maybe [coll x]
  (if (nil? coll) [x] (conj coll x)))

(defn distribute [bots giver receiver-low receiver-high]
  (let [low (apply min (bots giver))
        high (apply max (bots giver))]
    (-> (assoc bots giver [])
        (update receiver-low #(conj-maybe % low))
        (update receiver-high #(conj-maybe % high)))))


(defn process-bots [bots line]
  (match line
         ["value" v "goes" "to" "bot" k]
         [(update bots k #(conj-maybe % v)) :done]

         ["bot" k "gives" "low" "to" "bot" l "and" "high" "to" "bot" h]
         (if (= 2 (count (bots k)))
           [(distribute bots k l h) :done]
           [bots :stalled])

         ["bot" k "gives" "low" "to" "output" l "and" "high" "to" "bot" h]
         (if (= 2 (count (bots k)))
           [(distribute bots k (format "output-%d" l) h) :done]
           [bots :stalled])
         
         ["bot" k "gives" "low" "to" "output" l "and" "high" "to" "output" h]
         (if (= 2 (count (bots k)))
           [(distribute bots k
                        (format "output-%d" l)
                        (format "output-%d" h))
            :done]
           [bots :stalled])

         :else (throw (Exception. line))))

(defn simulate []
  (loop [bots {} instructions input]
    (if (empty? instructions)
      bots
      (let [[hd & tl] instructions
            [bots* status] (process-bots bots hd)
            instructions* (if (= status :done) tl (conj (vec tl) hd))]
        (recur bots* instructions*)))))

(defn part-1 []
  (simulate))

(defn part-2 []
  (let [bots (simulate)]
    (reduce * (concat (bots "output-0" )
                      (bots "output-1")
                      (bots "output-2")))))
