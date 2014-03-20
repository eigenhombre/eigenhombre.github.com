;; *[Clojure bookkeeping ...] --->*
(ns liveanal.core
  (:require [clojure.data.json :as json]
            [liveanal.i3d3 :refer [i3d3 time-to-js]]
            [clojure.pprint :refer [pprint]]
            [clj-time.core :refer [date-time interval in-seconds now]]
            [clj-ssh.cli :refer [ssh sftp]]))

;; # First look at a day's worth of messages
;;
;; I've unpacked one day's worth of SPADE Priority 3 files from the
;; Data Warehouse (everything from
;; `/data/exp/IceCube/2013/internal-system/I3Live/1120/I3Live-Msgs-Prio3*`).
;; These files, once unzipped/untarred, should have all messages for
;; the entire day.
;;
;; We want to see the relative abundance of messages by service name.

;; This is a very short Clojure or Python program:
;;
;;     import os
;;     import toolz
;;     import json
;;  
;;  
;;     def messages_from_file(fname):
;;         with file(fname) as ff:
;;             return json.loads(ff.read())
;;  
;;     def gen():
;;         for fname in os.listdir("../resources"):
;;             if not fname.startswith("I3Live"):
;;                 continue
;;             for m in messages_from_file("../resources/" + fname):
;;                 yield m["service"]
;;  
;;     print toolz.frequencies(gen())
;;
;; The Clojure code is enclosed in a `comment` expression, since this
;; is a [literate
;; program](http://en.wikipedia.org/wiki/Literate_programming) which
;; we may choose to execute later.
;;
(comment
  (frequencies (for [file (->> "/Users/jacobsen/Desktop/liveanal/resources/"
                                 clojure.java.io/file
                                 file-seq)
                       :when (.isFile file)
                       message (->> file
                                    clojure.java.io/reader
                                    json/read)]
                   (message "service"))))

;; Our relative abundances are shown in the map (dictionary) on the
;; left. Execution time is **6.8 seconds in Clojure**, 53 seconds in
;; Python. The 13 messages that have `nil` (in Python, `None`) as a
;; service name are alerts (not user-alerts) and comment actions from
;; the Run Coordinator.
;;
{nil 13,
 "I3DAQDispatch" 11283,
 "pdaq" 29835,
 "OpticalFollowUp" 39762,
 "PFRawWriter" 10220,
 "I3MoniPhysA" 287,
 "TFRateMonitor" 479,
 "uptimer" 1696,
 "sn-email" 12,
 "I3MoniDomMon" 288,
 "livecontrol" 2595,
 "diskmon-expcont" 192,
 "GammaFollowUp" 39752,
 "PFFiltWriter" 15200,
 "temperature" 882,
 "I3MoniDomTcal" 287,
 "I3MoniDomSn" 287,
 "meteorology" 144,
 "PFServer1" 8508,
 "PFServer2" 8474,
 "sndaq" 1037,
 "PFServer3" 8460,
 "I3MoniMover" 667,
 "HSiface" 9713,
 "PFFiltDispatch" 2165,
 "PFServer4" 8449,
 "DB" 624}



;; Refactoring the above code slightly, we pull out a function to
;; extract all messages from the files in our directory.
(def dirname "/Users/jacobsen/Desktop/liveanal/resources/")

(defn day-msgs []
  (for [file (->> dirname
                  clojure.java.io/file
                  file-seq)
        :when (.isFile file)
        message (->> file
                     clojure.java.io/reader
                     json/read)]
    message))


;; Using `day-msgs`, the total number of messages is just
;;
;; => `201311`
(comment
  (count (day-msgs)))


;; What sort of lengths do the messages have?  Though the details
;; depend on the transport mechanism, as a rough measure we can take
;; the length of the messages as serialized to JSON strings.
(defn average-message-length []
  (->> (day-msgs)
       (map json/write-str)
       (map count)
       (apply +')
       (#(/ % 201311))
       float))

;; => `409.18787`

;; To see how the lengths are distributed, we first convert
;; values to bins...
(defn hist-values [xmin xmax nbins xs]
  (let [binfunc (fn [x]
                  (int (* nbins (/ (- x xmin)
                                   (- xmax xmin)))))
        binned-values (map binfunc xs)
        freqs (frequencies binned-values)]
    (map #(get freqs % 0) (range nbins))))

;; ..and use [i3d3](http://i3d3.org) to show the distribution of
;; message lengths (as serialized into JSON).
(defn length-distributions []
  (let [bins (->> (day-msgs)
                  (map (comp count json/write-str))
                  (hist-values 0 3000 300))]
    (i3d3 "plot1" {:data [{:type "bars"
                           :bins bins
                           :color "#3b6c9d"
                           :range [0 3000]}]
                   :size [700, 250]
                   :yscale "log"
                   :xlabel "JSON message length, bytes",
                   :ylabel "Entries"})))

;;     (length-distributions)
;; <div class="plot" id="plot1"></div>

;; How about priorities?  How are they distributed?
;;
(defn prio-distributions []
  (let [bins (->> (day-msgs)
                  (map #(% "prio"))
                  (hist-values 0 4 3))]
    (i3d3 "plot2" {:data [{:type "bars"
                           :bins bins
                           :color "grey"
                           :range [0 4]}]
                   :size [700, 250]
                   :xlabel "Priorities"
                   :ylabel "Entries"})))

;;     (prio-distributions)
;; <div class="plot" id="plot2"></div>

;; How about viewing message lengths broken down by priority?
;;
(defn lengths-by-priority [p div]
   (let [bins (->> (day-msgs)
                   (filter #(= (% "prio") p))
                   (map (comp count json/write-str))
                   (hist-values 0 3000 300))]
     (i3d3 div {:data [{:type "bars"
                        :bins bins
                        :color "#3b6c9d"
                        :range [0 3000]}]
                :size [700, 150]
                :yscale "log"
                :xlabel "JSON message length, bytes",
                :ylabel "Entries"})))

;;     (lengths-by-priority 1 "plot3")
;; <div class="plot" id="plot3"></div>

;;     (lengths-by-priority 2 "plot4")
;; <div class="plot" id="plot4"></div>

;;     (lengths-by-priority 3 "plot5")
;; <div class="plot" id="plot5"></div>

;; This is a work in progress.  Next steps are to explore more
;; characteristics of this data, and to consider ways to meet new
;; requirements under consideration for IceCube Live messaging.

;; Next steps -- look at _times_.
(defn time-for [s]
  (->> s
       (re-find #"^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})(?:\.(\d{0,10}))?$")
       rest
       (take 6)
       (map #(Integer/parseInt %))
       (apply date-time)))


(defn delta-times []
  (let [first-time (->> (day-msgs)
                        (map #(% "t"))
                        first
                        time-for)
        max-t-sec 250
        bins (->> (day-msgs)
                  (map #(% "t"))
                  (map time-for)
                  (map #(interval first-time %))
                  (map in-seconds)
                  (partition 2 1)
                  (map (fn [[a b]] (- b a)))
                  (hist-values 0 max-t-sec 100))]
    (i3d3 "plot6" {:data [{:type "bars"
                       :bins bins
                       :color "#3b6c9d"
                       :range [0 max-t-sec]}]
               :size [700, 250]
               :yscale "log"
               :xlabel "Times between messages, seconds",
               :ylabel "Entries"})))

;; (delta-times)
;; <div class="plot" id="plot6"></div>

;; We assume most of the data we're sending is moni messages.  Verify
;; this:
;;
;;     (println (message-type-frequencies))
;; =>
;;
;;     {moni 93904,
;;      user-alert 68,
;;      alert 6,
;;      rundata 6,
;;      comment 7}
(defn message-type-frequencies []
  (->> (day-msgs)
       (filter #(= (% "prio") 1))
       (map #(% "cmd"))
       frequencies
       println))


;; What are the top ten most common varnames monitored at priority 1?
;; To do this we would like a function which would take a set of
;; key/value pairs (a map), sort the values, and find the keys which
;; yield the largest values.
(defn unroll-map-sorted [m]
  (let [values (-> m vals set sort reverse)]
    (for [v values]
            [v (for [k (keys m) :when (= (m k) v)] k)])))


;; Our most common monitored scalar quantities are therefore:
(defn common-prio-1-monis []
  (->> (day-msgs)
       (filter #(= (% "prio") 1))
       (filter #(= (% "cmd") "moni"))
       (map #((% "payload") "varname"))
       frequencies
       unroll-map-sorted
       (take 10)))

;;     (pprint (common-prio-1-monis))
;; =>
;;
;;     ([80774 ("state")]
;;      [1436 ("physicsEvents")]
;;      [290 ("run_update")]
;;      [144 ("wxconditions")]
;;      [143
;;       ("expectedDOMs"
;;        "total_ratelc"
;;        "activeDOMs"
;;        "LBMOverflows"
;;        "total_rate")]
;;      [98
;;       ("minTemp1"
;;        "minTemp2"
;;        "minTemp3"
;;        "minTemp6"
;;        "maxTemp1"
;;        "airFlow2"
;;        "maxTemp2"
;;        "maxTemp3")]
;;      [97
;;       ("HsWorker@sps-ithub10.sps"
;;        "HsWorker@sps-ithub11.sps"
;;        ;; ....
;;        "HsWorker@sps-ichub08.sps"
;;        "HsWorker@sps-ichub19.sps"
;;        "HsWorker@sps-ichub09.sps")]
;;      [96 ("HsPublisher@expcont.sps"
;;           "HsSender@2ndbuild.sps")]
;;      [95 ("maxTemp6")]
;;      [19 ("significance")])


;; `state` messages are BY FAR the highest volume of traffic.  (Matt
;; Newcomb determined this several months ago.)

;; Which service is sending the most `state` records? 

(defn biggest-state-reporters []
  (->> (day-msgs)
       (filter #(= (% "prio") 1))
       (filter #(= (% "cmd") "moni"))
       (filter #(= ((% "payload") "varname") "state"))
       (map #(% "service"))
       frequencies
       unroll-map-sorted
       (take 10)))

;;     (pprint (biggest-state-reporters))
;; =>
;;
;;     ([37053 ("OpticalFollowUp")]
;;      [36588 ("GammaFollowUp")]
;;      [479 ("TFRateMonitor" "PFFiltDispatch")]
;;      [478 ("I3DAQDispatch" "PFRawWriter")]
;;      [477
;;       ("PFFiltWriter"
;;        "PFServer1"
;;        "PFServer2"
;;        "PFServer3"
;;        "PFServer4"
;;        "DB")]
;;      [318 ("pdaq")]
;;      [288 ("I3MoniDomMon" "sndaq")]
;;      [287
;;       ("I3MoniPhysA"
;;        "uptimer"
;;        "I3MoniDomTcal"
;;        "I3MoniDomSn"
;;        "I3MoniMover")]
;;      [28 ("livecontrol")])

;; Holy multimessenger, OFU/GFU are totally spamming ITS with their
;; state reporting.  How frequently are those being reported?

(defn deltats [s]
  (->> s
       (partition 2 1)
       (map (fn [[a b]] (interval a b)))))

(let [maxdt 200
      bins (->> (day-msgs)
                (filter #(= (% "prio") 1))
                (filter #(= (% "cmd") "moni"))
                (filter #(= (% "service") "OpticalFollowUp"))
                (filter #(= ((% "payload") "varname") "state"))
                (map (comp time-for #(% "t")))
                delta-ts
                (map in-seconds)
                (hist-values 0 maxdt 1000))]
  (i3d3 "plot7" {:data [{:type "bars"
                         :bins bins
                         :color "#3b6c9d"
                         :range [0 maxdt]}]
                 :size [700, 250]
                 :yscale "log"
                 :xlabel "Times between OFU status messages, seconds",
                 :ylabel "Entries"}))

;; (delta-times)
;; <div class="plot" id="plot7"></div>

;; This bimodal distribution suggests something goofy.  We can inspect
;; the times by writing them back out to a file...
(defn write-ofu-state-times []
  (->> (day-msgs)
       (filter #(= (% "prio") 1))
       (filter #(= (% "cmd") "moni"))
       (filter #(= (% "service") "OpticalFollowUp"))
       (filter #(= ((% "payload") "varname") "state"))
       (map (comp #(% "t")))
       (#(with-out-str (pprint %)))
       (spit "/tmp/ofutimes")))

;; Sometimes they look like this, i.e. once every 3 minutes...
 "2013-11-20 23:00:57.297997"
 "2013-11-20 23:03:58.387704"
 "2013-11-20 23:06:58.671330"
 "2013-11-20 23:09:58.722348"
 "2013-11-20 23:13:00.788604"
 "2013-11-20 23:16:03.245657"
 "2013-11-20 23:19:04.784654"
 "2013-11-20 23:22:04.845620"
 "2013-11-20 23:25:05.534559"
 "2013-11-20 23:28:07.422556"
 "2013-11-20 23:31:08.565761"
 "2013-11-20 23:34:09.676526"

;; ... and sometimes like this (~ 1 Hz).
 "2013-11-20 17:53:12.213043"
 "2013-11-20 17:53:12.727890"
 "2013-11-20 17:53:14.443550"
 "2013-11-20 17:53:14.957570"
 "2013-11-20 17:53:16.621265"
 "2013-11-20 17:53:17.185204"
 "2013-11-20 17:53:18.849326"
 "2013-11-20 17:53:19.363238"
 "2013-11-20 17:53:21.077113"
 "2013-11-20 17:53:21.590343"
 "2013-11-20 17:53:23.253939"
 "2013-11-20 17:53:23.818978"
 "2013-11-20 17:53:25.482208"
 "2013-11-20 17:53:25.997032"
 "2013-11-20 17:53:27.676178"
 "2013-11-20 17:53:28.241808"


;; Time dependence of time difference:
(defn time-difference-vs-t []
  (let [t0 (->> (day-msgs)
                (first)
                (#(% "t"))
                time-for)
        points (->> (day-msgs)
                    (filter #(= (% "prio") 1))
                    (filter #(= (% "cmd") "moni"))
                    (filter #(= (% "service") "OpticalFollowUp"))
                    (filter #(= ((% "payload") "varname") "state"))
                    (map #(% "t"))
                    (partition 2 1)
                    (take-nth 10) ;; so the plot doesn't get too huge
                    (map (fn [[s1 s2]]
                           ;; time since start:
                           {:x (in-seconds (interval t0 (time-for s1)))
                            ;; time spread:
                            :y (in-seconds (interval (time-for s1) (time-for s2)))})))]
    (i3d3 "plot8" {:data [{:type "points"
                           :values points
                           :color "#3b6c9d"
                           :size 1.5}]
                   :size [700, 500]
                   :xlabel "t (seconds)",
                   :ylabel "delta-t"})))


;; <div class="plot" id="plot8"></div>


;; # Getting more files to look at
;;
;; Here we use Hugo Duncan's `clj-ssh` library to get a list of
;; available files.  First ssh into `pub` and run `find` to get the
;; file list, saving this locally to `/tmp/filelist` so we don't have
;; to pester `pub` repeatedly. 
;;
(defn remote-fetch-prio-3-files! []
  (->> (ssh "pub.icecube.wisc.edu"
            "find /data/exp/IceCube/2013/internal-system/I3Live/ -name '*Prio3*'")
       json/json-str
       (spit "/tmp/filelist")))

;; This file list then gives us what we have to play with.  This is
;; all the data we have for the year!
;;
(def prio3-files
  (-> "/tmp/filelist"
      slurp
      json/read-str
      (get "out")
      clojure.string/split-lines
      sort))

(take 10 prio3-files)
;; =>
["/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_000250786385.tar.gz"
 "/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_001112656039.tar.gz"
 "/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_001945146971.tar.gz"
 "/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_001959580018.tar.gz"
 "/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_002820262486.tar.gz"
 "/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_003641634600.tar.gz"
 "/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_004507961834.tar.gz"
 "/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_005329257959.tar.gz"
 "/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_010205844637.tar.gz"
 "/data/exp/IceCube/2013/internal-system/I3Live/0101/I3Live-Msgs-Prio3_20130101_011040940357.tar.gz"]

;; If we want files for a given day:
(defn file-dir-for-date [mm dd]
  (format 
   "/data/exp/IceCube/2013/internal-system/I3Live/%02d%02d/" mm dd))

(defn files-for-day [m d]
  (filter #(.contains % (file-dir-for-date m d)) prio3-files))

;; Let's get files for Oct. 10.  `clj-ssh` provides an interface to
;; `sftp`.
(defn get-day-files []
  (let [mm 10 dd 10
        dayfiles (files-for-day mm dd)
        mmdd-dir (format "/tmp/%02d%02d" mm dd)]
    (-> mmdd-dir clojure.java.io/file .mkdir)
    (doseq [f dayfiles]
      (println mmdd-dir f)
      (sftp "pub.icecube.wisc.edu" :get f mmdd-dir))))

;; <!-- This has to be at the bottom for the time being. -->
;; <script src="local.js"></script>
