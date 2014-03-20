(ns liveanal.i3d3
  (:require [clojure.data.json :as json]
            [clj-time.core :refer [date-time interval in-seconds now]]
            [clj-time.coerce :refer [to-long]])  ; fixme - check unused
  (:import [org.joda.time DateTime]))


(def local-js "docs/local.js")


(defn js-div-header [div]
  (format "// BEGIN DIV %s\n" div))


(defn js-div-footer [div]
  (format "// END DIV %s\n" div))


(defn wrap-js-str [div body]
  (str (js-div-header div)
       body
       "\n"
       (js-div-footer div)))


(defn div-re [div]
  (re-pattern (str "(?ms)("
                   (js-div-header div)
                   ".*?"
                   (js-div-footer div)
                   ")")))


(defn update-js-str [div body new]
  (let [new-content (wrap-js-str div new)]
    (if (re-find (div-re div) body)
      (clojure.string/replace body (div-re div) new-content)
      (str body new-content))))


(defn update-local-js
  "
  Update `div` code in file `local-js`
  "
  [local-js div content]
  (if (.exists (clojure.java.io/file local-js))
    (let [current (slurp local-js)]
      (spit local-js (update-js-str div current content)))
    (spit local-js (wrap-js-str div content))))


;; Is `x` a time?
(defn is-time [x] (= (class x) DateTime))


(defn time-to-js
  "
  Convert `t` to JavaScript long
  "
  [t]
  (if (is-time t) (format "(new Date(%d)" (to-long t)) t))


(defn i3d3-str
  "Return JavaScript needed to make an i3d3 graph based on input `params`"
  [params]
  (format "i3d3.plot(%s);\n" 
          (json/write-str params)))


(defn i3d3
  "
  Update `local.js` to display i3d3 plot in specified div. You need to
  have a `div` specified elsewhere in your HTML; e.g., for Marginalia,
  add:

  `;; <div id='plot9'></div>`
  "
  [div params]
  (update-local-js local-js
                   div
                   (i3d3-str
                    (merge {:div div} params))))
