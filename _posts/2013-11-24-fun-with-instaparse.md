---
layout: post
title: "Fun with Instaparse"
description: ""
category: 
tags: [clojure, python]
---
{% include JB/setup %}

One of my favorite talks from this month's excellent
[Clojure/conj](http://clojure-conj.org/) was [Carin
Meier](http://gigasquid.github.io/)'s presentation, which combined
storytelling, live coding, philosophy, the history of computing, and
flying robotic drones. She used the relatively new [Instaparse
library](https://github.com/Engelberg/instaparse) to [create her own
language](http://gigasquidsoftware.com/wordpress/?p=689) to explore
something called "Speech Acts" (which I won't go into here, but do
catch the video of her talk when it goes up).

My university work was in physics (and art) rather than CS, but I have
long been interested in the implementation of programming languages,
even going so far as to write a simple parser for Lisp-style math
expressions in Pascal many years ago. Last year I had the opportunity
to take the first ["write a compiler in Python"
class](http://dabeaz.blogspot.com/2012/01/compiler-experiment-begins.html)
offered by [David Beazley](http://dabeaz.com) here in Chicago, in
which we implemented a subset of the Go language. His
[PLY](http://www.dabeaz.com/ply/index.html) library is a great way to
get started with implementing language parsers in Python, and the
relative ease of doing so, compared with classic C implementations
described in the infamous [Dragon
Book](http://www.amazon.com/Compilers-Principles-Techniques-Alfred-Aho/dp/0201100886),
inspired me to do some further [experimentation of my
own](https://github.com/eigenhombre/PyClojure).

With this background, and inspired by Carin's talk, I have been
waiting for an opportunity to try out Instaparse, which is getting
great press in the Clojure world. Instaparse takes a grammar as input
(in the form of a string), and gives you a parser in the language
specified by that grammar. It will also let you specify rules for
transforming the resulting tree into something your Clojure program
can use more directly (for example, by converting data types or
removing unneeded elements from the parse tree).

When the need arose this weekend to read in Python configuration files
into a Clojure program, I decided the time was ripe. I also wanted to
document the journey using some form of [literate
programming](http://en.wikipedia.org/wiki/Literate_programming). A
library called [Marginalia](https://github.com/gdeer81/marginalia)
(Michael Fogus *et. al.*) made this pretty easy.

The results are
[here](http://eigenhombre.com/semi-literate-programming/parsepy.html),
as well as on [GitHub](https://github.com/eigenhombre/parsepy).

My impressions, after doing this project in just a few hours, are that
(1) literate programming is great fun; and (2) Instaparse sets a new
standard for power and expressiveness when converting structured text
into abstract syntax trees. If you have a DSL or some other
text-based, formal language you want to parse, and you are either
literate in Clojure or interested in becoming so, Instaparse would be
a great tool to check out.
