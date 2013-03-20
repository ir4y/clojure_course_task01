(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))

(defn handle-tag [tag-item]
  "Walk through html tree"
  "Found tags with class r and return them"
  (if (string? tag-item)
    []
    (if (-> tag-item attributes :class (= "r"))
      [tag-item]
      (loop [tag-list (children tag-item),
             result []]
       (if (= [] tag-list)
         result
         (recur (rest tag-list) (->> tag-list first handle-tag (concat result) doall)))))))

(defn get-links []
    "Extract href from list of h tags"
    (->> (parse "clojure_google.html") handle-tag (map #(-> % children first attributes :href))))

(defn -main []
  (println (str "Found " (count (get-links))) " links!"))
