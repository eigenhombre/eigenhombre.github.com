---
layout: post
title: "Validating the Genome Decoder"
description: ""
category: 
tags: [clojure, genomics, bioinformatics]
---
{% include JB/setup %}

Today we'll validate the genome decoder we described yesterday, once
again with our friend the yeast *Saccharomyces cerevisiae* (you may
want to enjoy a slice of freshly-baked bread and a stein of Pilsner
with this post).

We are aided in this case by the availability of the SacCer3 genome in
both 2bit and FASTA formats. We can get [the FASTA
version](http://hgdownload-test.cse.ucsc.edu/goldenPath/sacCer3/bigZips/chromFa.tar.gz)
in [the same place we got the 2bit
file](http://hgdownload-test.cse.ucsc.edu/goldenPath/sacCer3/bigZips/):

    mkdir /tmp/sacCer3_fasta
    cd /tmp/sacCer3_fasta
    wget http://hgdownload-test.cse.ucsc.edu/goldenPath/sacCer3/bigZips/\
    chromFa.tar.gz
    tar xvzf chromFa.tar.gz

There is one FASTA file per sequence, starting something like this:

    >chrI
    CCACACCACACCCACACACCCACACACCACACCACACACCACACCACACC
    CACACACACACATCCTAACACTACCCTAACACAGCCCTAATCTAACCCTG
    GCCAACCTGTCTCTCAACTTACCCTCCATTACCCTGCCTCCACTCGTTAC
    CCTGTCCCATTCAACCATACCACTCCGAACCACCATCCATCCCTCTACTT
    ACTACCACTCACCCACCGTTACCCTCCAATTACCCATATCCAACCCACTG
    CCACTTACCCTACCATTACCCTACCATCCACCATGACCTACTCACCATAC

Back in the REPL, we can now spit out our own copy in the same format
(carrying over `yeast` and other functions and vars from the previous
post).

First we need a new directory for the FASTA files we'll generate:

{% highlight clojure %}
(.mkdir (clojure.java.io/file "/tmp/decoded"))
{% endhighlight %}

Then we convert the keywords in our sequence to strings:

{% highlight clojure %}

(defn genome-str
  "
  Convert e.g. [:A :G :T :C] to \"AGTC\"
  "
  [s]
  (->> s
       (map name)
       (apply str)))
{% endhighlight %}

A simple function will spit out the files, given a seq:

{% highlight clojure %}
(defn write-seq 
  "
  Write a (potentially very long) sequence of lines to a text file
  "
  [filename s]
  (with-open [wrt (clojure.java.io/writer filename)]
    (doseq [x s]
      (.write wrt (str x "\n")))))
{% endhighlight %}

And now for the actual converter:

{% highlight clojure %}
(doseq [{:keys [name dna-offset dna-size]} (sequence-headers yeast)]
  (let [fname (str "/tmp/decoded/" name ".fa")]
    (write-seq fname
               (cons (str ">" name)
                     (->> (genome-sequence yeast dna-offset dna-size)
                          (partition-all 50)
                          (map genome-str))))))
{% endhighlight %}

Did it work?

    cd /tmp/sacCer3_fasta
    for f in *.fa; do diff $f /tmp/decoded/$f; done

No output -- it succeeded! This builds more confidence that we didn't
screw anything up in the decoder. (There are also a few other
hard-coded unit tests in `test_core.clj`.)

Comparing file sizes, the FASTA files are about 4 times larger than
the original 2bit file. If you tar and compress both versions, the
FASTA files are still about 30% larger. It is left as as an exercise
to the reader (with some spare hard disk) to do the same comparison
with the human genome. (Though larger, the FASTA files would clearly
be simpler to work with, and these days 4 GB isn't too terribly much
data; nevertheless, we'll continue to use the 2bit file and decoder
for our explorations.)

Now we are ready to begin playing with the actual data -- starting in
the next post.

