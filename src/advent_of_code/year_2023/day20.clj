(ns advent-of-code.year-2023.day20
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(defn parse-line [s]
  (let [[l r] (str/split s #" -> ")]
    [l (str/split r #", ")]))

(def input (->> (slurp "resources/2023/day20.txt")
                 (str/split-lines)
                 (map parse-line)))

(defn conj-conns [m]
  (->> (filter (fn [[k v]] (.contains v m)) input)
       (map first)
       (map #(subs % 1))
       (map (fn [s] [s 0]))
       (into {})))

(defn init [s]
  (let [[prefix module] (map str/join (split-at 1 s))]
    (match prefix
           "b" ["broadcaster" {:name "broadcaster" :out 0 :type "broadcaster" }]
           "%" [module {:name module :state false :type "flip" :out 0}]
           "&" [module {:name module :inputs (conj-conns module)  :type "conj"}])))

(def init-modules (into {} (map init (map first input ))))
(def signals (into {} (map (fn [[k v]] [(str/replace k #"%|&" "") v]) input)))
(def sig-count (atom {0 0 1 0}))

(defn send-signal [sender {:keys [name type state out] :as receiver} signal]
  (cond
    (and (= 1 signal) (= type "flip")) [receiver []]
    
    (and (zero? signal) (= type "flip"))
    [{:name name :type type :state (not state) :out (if (zero? out) 1 0)}
     (signals name)]

    (= type "conj")
    [(assoc-in receiver [:inputs (sender :name)] signal)
     (signals name)]))


(defn process []
  (loop [queue ["broadcaster"] m init-modules]
    (if (empty? queue) m
        (let [[hd & tl] queue
              node (m hd)]))]))
