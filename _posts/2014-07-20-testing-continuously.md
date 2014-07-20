---
layout: post
title: "Testing, Continuously"
description: ""
category: clojure
tags: [clojure, testing]
draft: true
---
{% include JB/setup %}

**This is the fourth post in a [series](/clojure/2014/07/03/an-advanced-clojure-workflow/) about my current Clojure workflow.**

In [my last post](/clojure/2014/07/20/a-worfklow-tdd-rdd-and-ddd/), I laid out a workflow based on TDD but with the addition of "literate programming" (writing prose interleaved with code) and experimentation at the REPL.  Here I dive a bit deeper into my test setup.

Even before I started developing in Clojure full time, I [discovered](/testing/2012/03/31/ontinuous-testing-in-python-clojure-and-blub/) that creating a setup that provides near-instant test feedback made me more efficient as a developer.  I now need and expect to be able to run all, or at least the most relevant, tests every time I hit the "Save" button on any file in my project, and to see the results within "a few" (preferrably, less than 3-4) seconds.  Some examples of systems which provide this are:

1. Ruby-based [Guard](https://github.com/guard/guard#readme), commonly used in the Rails community;
2. [Conttest](https://github.com/eigenhombre/continuous-testing-helper), a language-agnostic Python-based test runner I wrote;
3. in the Clojure sphere:
    a. [Midje](https://github.com/marick/Midje), using the `:autotest` option;
    b. [Expectations](https://github.com/jaycfields/expectations), using the [autoexpect plugin](https://github.com/jakemcc/lein-autoexpect) for Leiningen;
    c. [Speclj](https://github.com/slagyr/speclj)
    d. `clojure.test`, with [quickie](https://github.com/jakepearson/quickie) (and possibly other) plugins
    e. ...
4. ....

I used to use Expectations; now I use Midje since I like its rich DSL and the ability to develop functionality bottom-up or [top-down](https://github.com/marick/Midje/wiki/The-idea-behind-top-down-development).

In Midje, using the `lein midje` plugin, the following will run all tests in a project, and then re-run them more or less instantly whenever you save a file with changes:

    $ lein midje :autotest

Since I run this so often, I define a Leiningen alias as follows:

    $ lein autotest

by updating the project file `project.clj` as follows:

    :aliases {"autotest" ["midje" ":autotest"]}

Since the Midje dependency is only needed during development, and not in production, it is added to the `:dev` profile in the project file only:

    :profiles {:dev {:dependencies [[midje "1.5.1"]]}}

Running your tests hundreds of times per day not only reduces debugging time (you generally can figure out exactly what you broke much easier when the deltas to the code are small), but they also help build knowledge of what parts of the code run slowly.  If the tests start taking longer than a few seconds to run, I like to give some thought to what could be improved -- either I am testing at too high of a level (e.g. hitting the database, starting/stopping subprocesses, etc.) or I have made some questionable assumptions about performance somewhere.

Sometimes, however, despite best efforts, the tests still take longer than a few seconds to run.  At this point I start annotating tests with metadata (Clojure) or decorators (Python) indicating that those tests should be skipped during autotesting (I still run the full test suite before committing (usually) or pushing to master (always)).  In Midje, this is done as follows:

    (fact "I'm a slow test." :slow
       ...)

Updating our alias skips the slow tests:

    :aliases {"autotest" ["midje" ":autotest" ":filter -slow"]}

If I'm working on a slow test at the moment, I omit the `:slow` tag until I'm done with the feature in question.  In this way, I can continue to get the most feedback in real time as possible -- which helps me develop quality code efficiently.

In the next post, we'll switch gears and talk about literate programming with Marginalia.
