(ns advent-of-code.year-2023.day19
  (:require [clojure.string :as str]))

(defn parse-condition [s]
  (let [[a b out] (str/split s #"<|>|:")]
    {:category a :value (parse-long b) :destination out
     :f (if (str/includes? s ">") > <)}))

(defn parse-workflow [s]
  (let [name (str/join (take-while #(not= % \{) s))
        rest (last (first (re-seq #"\{(.*)\}" s)))
        conditions (str/split rest #",")]
    [name (conj (mapv parse-condition (butlast conditions))
                 (last conditions))]))

(defn parse-category [s]
  (let [[k v] (str/split s #"=")]
    [k (parse-long v)]))

(defn parse-part [s]
  (let [l (count s)
        s* (str/split (subs s 1 (dec l)) #",")]
    s*))

(def input
  (let [file (slurp "resources/2023/day19.txt")
        [xs ys] (map str/split-lines (str/split file #"\r\n\r\n"))
        workflows (into {} (map parse-workflow xs))
        parts (map #(into {} (map parse-category (parse-part %))) ys)]
    [workflows parts]))

(def workflows (first input))
(def parts (last input))

(defn process-workflow [part [hd & tl]]
  (cond
    (string? hd) hd
    ((hd :f) (part (hd :category)) (hd :value)) (hd :destination)
    :else (process-workflow part tl)))

(defn accepted? [part]
  (loop [p part wf "in" ]
    (let [next-wf (process-workflow p (workflows wf))]
      (case next-wf
        "A" true
        "R" false
        (recur p next-wf)))))

(defn part-1 []
  (->> (filter accepted? parts)
       (mapcat vals)
       (reduce +)))

(defn boundary [state {:keys [category value f]}]
  (if (= f <)
    [(assoc state (keyword (str category "h")) (dec value))
     (assoc state (keyword (str category "l")) value)]
    [(assoc state (keyword (str category "l")) (inc value))
     (assoc state (keyword (str category "h")) value)]))

(defn split-ranges [[state s]]
  (loop [states [] m state [hd & tl] (workflows s)]
    (if (string? hd) (conj states [m hd])
        (let [[success fail] (boundary m hd)]
          (recur (conj states [success (hd :destination)])
                 fail
                 tl)))))

(defn score [{:keys [xl xh al ah sl sh ml mh]}]
  (* (- xh (dec xl)) (- ah (dec al)) (- sh (dec sl)) (- mh (dec ml))))

(defn part-2 []
  (loop [queue [[{:xl 1 :xh 4000 :al 1 :ah 4000 :sl 1 :sh 4000 :ml 1 :mh 4000} "in"]] finished #{}]
    (let [valid (map first (filter (fn [[m wf]] (= wf "A")) queue))
          queue* (remove (fn [[m wf]] (or (= wf "A") (= wf "R"))) queue)]
      (if (empty? queue)
        (reduce + (map score finished))
        (recur (mapcat split-ranges queue*) (apply conj finished valid))))))
