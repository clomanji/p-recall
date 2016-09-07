(ns p-recall.core-test
  (:require
   #?(:cljs [cljs.test :refer-macros [deftest is testing]]
      :clj [clojure.test :refer :all])
   [p-recall.core :refer [next-items]]))

(deftest fetching-items

  (testing "when there are only new items"
    (let [recall-items []
          new-items [{:id 1} {:id 2} {:id 3}]]
      (is (= (next-items recall-items new-items :any-day 2)
             [{:id 1} {:id 2}]))
      (is (= (next-items recall-items new-items :any-day 3)
             new-items))
      (is (= (next-items recall-items new-items :any-day 4)
             new-items))))

  (testing "when there are both new and recall items"

    (testing "but recall items are not yet due"
      (let [recall-items [{:id 4 :due-day 24}]
            new-items [{:id 1} {:id 2} {:id 3}]]
        (is (= (next-items recall-items new-items 23 2)
               [{:id 1} {:id 2}]))))

    (testing ", and a recall item is due today"
      (let [recall-items [{:id 4 :due-day 24}
                          {:id 5 :due-day 25}]
            new-items [{:id 1} {:id 2} {:id 3}]]
        (is (= (next-items recall-items new-items 24 2)
               [{:id 4 :due-day 24} {:id 1}]))))

    (testing ", and a recall item was due before today"
      (let [recall-items [{:id 4 :due-day 22}
                          {:id 5 :due-day 25}]
            new-items [{:id 1} {:id 2} {:id 3}]]
        (is (= (next-items recall-items new-items 24 2)
               [{:id 4 :due-day 22} {:id 1}]))))))
