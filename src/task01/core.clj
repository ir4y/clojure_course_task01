(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))

(defn handle-tag [tag-item]
  (if (string? tag-item)
    []
    (let [tag-name (tag tag-item)
          tag-attrs (attributes tag-item)
          tag-content (children tag-item)]
      (if (= (:class tag-attrs) "r")
        [tag-item]
        (loop [tag-list tag-content,
               result []]
         (if (= [] tag-list)
           result
           (recur (rest tag-list) (doall (concat result (handle-tag (first tag-list)))))))))))

(defn get-links []
" 1) Find all elements containing {:class \"r\"}.

Example:
[:h3 {:class \"r\"} [:a {:shape \"rect\", :class \"l\",
                         :href \"https://github.com/clojure/clojure\",
                         :onmousedown \"return rwt(this,'','','','4','AFQjCNFlSngH8Q4cB8TMqb710dD6ZkDSJg','','0CFYQFjAD','','',event)\"}
                     [:em {} \"clojure\"] \"/\" [:em {} \"clojure\"] \" · GitHub\"]]

   2) Extract href from the element :a.

The link from the example above is 'https://github.com/clojure/clojure'.

  3) Return vector of all 10 links.

Example: ['https://github.com/clojure/clojure', 'http://clojure.com/', . . .]
"
    (map #(:href (attributes %))(map #(get  % 0) (map #(vec (children %)) (handle-tag (parse "clojure_google.html"))))))


(defn -main []
  (println (str "Found " (filter #(not(= % [])) (get-links))) " links!"))


