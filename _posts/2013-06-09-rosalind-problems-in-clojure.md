---
layout: post
title: "Rosalind Problems in Clojure"
description: ""
category: 
tags: [clojure, python, bioinformatics, macros]
---
{% include JB/setup %}


This weekend I've been having a lot of fun working the Bioinformatics
problems from [Rosalind](http://rosalind.info/). Most people work them
in Python, but so far they have been very amenable to Clojure except
where BioPython libraries are used for access to online databases. The
problems have been straightforward so far but I have enjoyed the
elegance and brevity that Clojure lends the solutions. 

In particular, I like this short translator 
[from RNA sequences to proteins](http://rosalind.info/problems/prot/):

{% highlight clojure %}

;;; Translating RNA into Protein

(defmacro deftable [tname & rest]
  `(def ~tname (apply hash-map '(~@rest))))

(deftable proteins
  UUU F      CUU L      AUU I      GUU V
  UUC F      CUC L      AUC I      GUC V
  UUA L      CUA L      AUA I      GUA V
  UUG L      CUG L      AUG M      GUG V
  UCU S      CCU P      ACU T      GCU A
  UCC S      CCC P      ACC T      GCC A
  UCA S      CCA P      ACA T      GCA A
  UCG S      CCG P      ACG T      GCG A
  UAU Y      CAU H      AAU N      GAU D
  UAC Y      CAC H      AAC N      GAC D
  UAA Stop   CAA Q      AAA K      GAA E
  UAG Stop   CAG Q      AAG K      GAG E
  UGU C      CGU R      AGU S      GGU G
  UGC C      CGC R      AGC S      GGC G
  UGA Stop   CGA R      AGA R      GGA G
  UGG W      CGG R      AGG R      GGG G) 

(defn to-protein [s]
  (->> s
       (partition 3)
       (map (partial apply str))
       (map symbol)
       (map proteins)
       (take-while #(not= % 'Stop))
       (apply str)))

{% endhighlight %}

The body of the `proteins` table is literally cut-and-pasted from [the
problem page](http://rosalind.info/problems/prot/) (click on "RNA
codon table"). I think it's a good example of using macros to provide
a little bit of syntactic sugar to make the code just a little more
readable and elegant.

This and my other solutions so far are [up on GitHub](https://github.com/eigenhombre/rosalind).
