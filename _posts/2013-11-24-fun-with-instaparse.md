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
Meier](http://gigasquidsoftware.com)'s presentation, which combined
storytelling, live coding, philosophy, the history of computing, and flying
robotic drones. She used the relatively new [Instaparse
library](https://github.com/Engelberg/instaparse) to create her own
language to explore something called "Speech Acts," which I won't go
into here (but do catch the video of her talk when it goes up).

I don't have a classic CS background, but have long been a programming
language enthusiast. About two years ago I had the opportunity to take
the first ["write a compiler in Python"
class](http://dabeaz.blogspot.com/2012/01/compiler-experiment-begins.html), which was
offered by [David Beazley](http://dabeaz.com) here in Chicago. His
[PLY](http://www.dabeaz.com/ply/index.html) library is a great way to
get started with implementing compilers for old or new languages in
Python, and the relative ease of doing so, compared with classic C
implementations described in the infamous [Dragon
Book](http://www.amazon.com/Compilers-Principles-Techniques-Alfred-Aho/dp/0201100886),
inspired me to do some further [experimentation of my
own](https://github.com/eigenhombre/PyClojure).

With this background, and inspired by Carin's talk, I have been
waiting for an opportunity to try out Instaparse, which is getting
great press in the Clojure world. When the need arose this weekend to
read in Python configuration files into a Clojure program, I decided
the time was ripe. I also wanted to document the journey using some
form of [literate
programming](http://en.wikipedia.org/wiki/Literate_programming). A
library called [Marginalia](https://github.com/gdeer81/marginalia)
(Michael Fogus *et. al.*) made this pretty easy.

The results are
[here](http://eigenhombre.com/semi-literate-programming/parsepy.html),
as well as on [GitHub](https://github.com/eigenhombre/parsepy). My
impression, after doing this project in just a few hours, are (1) that
literate programming is great fun; and (2) that Instaparse sets a new
standard for power and expressiveness when converting structured text
into abstract syntax trees. If you have a DSL or some other
text-based, formal language you want to parse, and you are either
literate in Clojure or interested in becoming so, Instaparse would be
a great tool to check out.
