(ns advent-of-code.2015.day4)

(def input "bgvyzdsv")

(defn valid? [s]
  (= '(\0 \0 \0 \0 \0) (take 5 s)))

(defn valid-2? [s]
  (= '(\0 \0 \0 \0 \0 \0) (take 6 s)))

(defn md5 [^String s]
  (let [algorithm (java.security.MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

(defn part-1 []
  (loop [i 0]
    (if (valid? (md5 (str input i)))
      i
      (recur (inc i)))))

(defn part-2 []
  (loop [i 0]
    (if (valid-2? (md5 (str input i)))
      i
      (recur (inc i)))))
