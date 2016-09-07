(ns p-recall.core)

(defn next-items [recall-items new-items day num-items]
  (->>
   (concat (filter #(>= day (:due-day %)) recall-items) new-items)
   (take num-items))
  )
