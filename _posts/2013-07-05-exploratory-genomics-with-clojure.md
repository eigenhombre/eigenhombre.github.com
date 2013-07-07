---
layout: post
title: "Exploratory Genomics with Clojure"
description: ""
category: 
tags: [clojure, genomics, bioinformatics]
---
{% include JB/setup %}

In Stanislaw Lem's [*His Master's
Voice*](http://en.wikipedia.org/wiki/His_Master's_Voice_novel),
humanity receives a "letter from the stars," in the form of regularly
repeating binary data encoded in a beam of extraterrestrial neutrinos.
The protagonists know nothing *a priori* about the content of the
letter. The plot unfolds as the message slowly, as a result of careful
study, yields a few (not many) of the signal's secrets.

What would you do if you received such a dataset? How would you study
it? What real world datasets are similarly mysterious?

As most readers will know, every living being contains a "message" of
sorts, encoded, not in base 2 (binary), but rather base 4: A, G, C, or
T, arranged in paired, sequential molecular strands called DNA (in
which every A is paired with a T, every C with a G). From 159,662 base
pairs for [the smallest known
genome](http://www.nature.com/news/2006/061009/full/news061009-10.html),
to 3.3 billion BPs for humans, to [149 billion
BPs](http://news.sciencemag.org/sciencenow/2010/10/scienceshot-biggest-genome-ever.html)
for the largest genome, that of *Paris japonica*, each organism
carries its own unique tome locked in its cells.

In the past ten years, the human genome [has been
sequenced](http://en.wikipedia.org/wiki/Human_Genome_Project) and
[made widely
available](http://en.wikipedia.org/wiki/Human_Genome_Projec). Though
downloadable in a variety of formats, the most compact seems to be the
'2bit' format (two bits per base pair): the genome in this format is
just under a gigabyte, uncompressed. Plenty of genomes of simpler
organisms are available for study as well.

I am not a genomicist or biologist, but am curious about the
properties of the "data" stored within us all. In the next few blog posts
I intend to investigate genomic data (human or otherwise) using
[Clojure](http://clojure.org), a modern Lisp developed for the JVM. Clojure's strengths
are simplicity, concurrency, expressiveness and speed -- assets which
will be helpful as we tackle a few simple analytical tasks aimed at
understanding some of the properties of life's very own "Big Data."

__Next__: [A Two Bit Decoder](/2013/07/06/a-two-bit-decoder/)
