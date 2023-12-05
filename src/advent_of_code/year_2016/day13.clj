(ns year-2016.day13
  (:require [clojure.string :as str]))


(defn checksum [x y]
  (+ (* x x)
     (* 3 x)
     (* 2 x y)
     (y * y)
     y))

