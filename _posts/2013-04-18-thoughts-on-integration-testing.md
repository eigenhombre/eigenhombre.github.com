---
layout: post
title: "Thoughts on Integration Testing"
description: ""
category: 
tags: [testing, python]
---
{% include JB/setup %}

![Satellite uplink at South Pole](/images/5403111969_44e4335f43_b_sm.jpg)

*Satellite uplink at South Pole.  The remoteness of and lack of bandwidth at the site makes reliability an even greater concern.*

**This is the first post in a series on integration testing**, mostly
 relating to automated tests written in **Python**.

For the past few years, I have been working on a large control and
monitoring system for my clients on [the IceCube Neutrino
Observatory](http://icecube.wisc.edu) at the geographic South Pole. As
the lead developer for this control system, known as [IceCube
Live](http://www.npxdesigns.com/projects/icecube-live/), I am partly
responsible for the overall uptime of this expensive (US$270M)
detector, so reliability matters (and, if the detector goes down, I
might get a phone call from Antarctica in the middle of the night...).
Furthermore, with a few hundred features implemented in the code base,
several developers touching the code, and constantly evolving
requirements, the test coverage has to be good in order to allow
changes to be made to the system with any sort of confidence. IceCube
Live was developed using [TDD](http://en.wikipedia.org/wiki/Test-driven_development)
from the beginning, and my ideas about tests have evolved as the
project grew from a small prototype into a critical system for the
project. I'd like to present some of these ideas here in the hope that
someone else might find them useful.

There are many taxonomies of tests. Here we will discuss automated
tests only. A simple scheme is to consider a continuum ranging from
*unit tests* (testing purely isolated functions or components) to
full-scale *integration tests* (where you put many things together and
exercise their collective functionality). Unit tests are your first
line of defense -- they are usually the easiest to reason about and to
write. It's generally much easier to isolate and understand a bug
exposed by a unit test than a large integration test, simply because
there are fewer places where the problem could have occurred.

Nonetheless, in larger, distributed systems, the interactions between
parts can dominate the overall complexity, and integration testing
becomes critical: subsystems that are well-tested in
isolation may still not talk to each other correctly.

Writing integration tests well can be a challenge. The tests should run
as quickly as possible, to encourage their frequent use during
development. Proliferation of boilerplate code can also become
problematic, because so many things are being combined together, with
slight variation.

Over the next few days/weeks, I plan to post a series of sort posts on
a few patterns I've come to make heavy use of while developing
automated integration tests for IceCube.

Next: [Processes vs. Threads for Integration Testing](/2013/04/19/processes-vs-threads-for-testing/)

Other posts in this series:

- [Introduction to Context Managers in Python](/2013/04/20/introduction-to-context-managers/)

*Thanks to [Matt Rocklin](http://matthewrocklin.com) for feedback on the material in this series*.
