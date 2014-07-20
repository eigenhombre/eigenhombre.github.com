---
layout: post
title: "A Worfklow: TDD, RDD and DDD"
description: ""
category: clojure
tags: [clojure, testing, "literate programming"]
draft: true
---
{% include JB/setup %}

Having [discussed my Emacs setup](/clojure/2014/07/05/emacs-customization-for-clojure/) for Clojure, I now present my "ideal" workflow, with subsequent discussion.  But first, let me motivate things with a few questions:


<ol type="a">
<li>How do you preserve the ability to make improvements without fear of breaking things?</li>
<li>What process best facilitates careful thinking about design and implementation?</li>
<li>How do you communicate your intent to future maintainers (including future versions of yourself)?</li>
<li>How do you experiment with potential approaches and solve low-level tactical problems as quickly as possible?</li>
<li>Since "simplicity is a prerequisite for reliability" (Dijkstra), how do you arrive at simple designs and implementations?</li>
</ol>


The answer to (a) is, of course, by having good test coverage; and the simplest way I know to have good test coverage is by doing Test-Driven Development (TDD).  However, my experience with TDD is that it doesn't always help much with the other points on the list (though it helps a bit with (b), (c) and (e)).  In particular, [Rich Hickey points out](http://www.infoq.com/presentations/Simple-Made-Easy) that TDD is not a substitute for *thinking* about the problem at hand.

As an aid for thinking, I find writing to be invaluable, so a minimal sort of [literate programming](http://en.wikipedia.org/wiki/Literate_programming) has increasingly become a part of my workflow, at least for hard problems.

#### Worfklow

Now for the workflow proper.  Given the following tools:

* Emacs + Cider REPL
* [Midje](https://github.com/marick/Midje) running continuously (`:autotest` mode)
* [Marginalia](https://github.com/gdeer81/marginalia) running continuously, via [`conttest`](https://github.com/eigenhombre/continuous-testing-helper),

then my workflow, in its [Platonic Form](http://en.wikipedia.org/wiki/Theory_of_Forms), is:

1. **Is the path forward clear enough to write the next failing test?** If not, go to step 2.  If it is, go to step 3.
1. **[Think](https://www.youtube.com/watch?v=f84n5oFoZBc)** and **write** (see below) about the problem.  Go to 1.
2. **Write the next failing test**.  This test, when made to pass, should represent the smallest "natural" increase of functionality.
1. **Is it clear how to make the test pass?**  If not, go to step 5. If it is, write the *simplest* "production" code which  makes all tests pass. Go to 6.
1. **Think, write, and conduct REPL experiments.**  Go to 4.
1. **Is the code as clean, clear, and simple as possible?**  If so, go to step 7.  If not, **refactor**, continuously making sure tests continue to pass with every change.  Go to 6.
1. Review the Marginalia docs.  **Is the intent of the code clear?**  If so, go to step 1.  If not, **write some more**. Go to 7.

"Writing" in each case above refers to updating comments and docstrings, as described in a subsequent post on Literate Programming.

Here are the above steps as a flow chart:
<a href="/images/workflow.png"><img src="/images/workflow.png" alt="Workflow, as a flow chart" width="75%"></a>

The workflow presented above is a somewhat idealized version of what I actually manage to pull off during any given coding session.  It is essentially the <span style="color:red">red</span>-<span style="color:green">green</span>-<span style="color:blue">refactor</span> of traditional test-driven development, with the explicit addition of REPL experimentation ("REPL-driven development," or RDD) and continuous writing of documentation ("documentation-driven development," or DDD) as a way of steering the effort and clarifying the approach.

A lot has been written about TDD and its advantages or drawbacks.  What I want to emphasize is how the addition of REPL experimentation and the emphasis on writing complement TDD, essentially covering all the bases (a)-(e), above.  While I've been combining unit tests and the REPL for some time, the emphasis on writing is new to me, and I am excited about it.

While I don't always follow each of the above steps to the letter, the harder the problem, the more closely I will tend to follow this plan, with one further modification: I am willing to wipe the slate clean and start over if a new understanding shows the current path to be unworkable, or to be leading to unneeded complexity.

The next few posts attack specifics about testing and writing, giving what I personally have found most effective (so far), and elaborating on helpful aspects of each.

How does your preferred workflow differ from the above?
