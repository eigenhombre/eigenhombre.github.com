---
layout: post
title: "Processes vs. Threads for Integration Testing"
description: ""
category:
tags: ["python", "testing", "concurrency"]
---
{% include JB/setup %}


This post continues a series of posts about integration testing in
Python. Introductory post is
[here](/2013/04/18/thoughts-on-integration-testing/).

Python has become notorious for being somewhat problematic when it
comes to concurrency. The language supports threads, but performance
is known to be dismal in some cases. In particular, [as David Beazley
discovered](http://www.dabeaz.com/GIL/), when I/O-bound and CPU-bound
threads run in the same program, the GIL performs very badly (worse
than if you ran the tasks one after the other on a single thread/CPU).
Alternative approaches which have become popular are to use
asynchronous or event-driven programming techniques
([Twisted](http://twistedmatrix.com/trac/),
[asyncore](http://docs.python.org/2/library/asyncore.html)), or to
make heavy use of the `multiprocessing` module.

These wrinkles aside, there are situations where threads in Python are still
very useful.  One of these is integration testing of distributed
systems.

Imagine you have a distributed system with some 30 (or 3, or 300)
software components running on some cluster. These programs might
interact with a database, with a user via command line or a GUI (Web
or otherwise), and with each other via a messaging or RPC protocol
(XML-RPC, ZeroMQ, ...).

While low-level (functional, unit) testing will perhaps be the bulk of
your tests, integration tests are important to make sure all the
programs talk to each other as they should. Like your other tests, you
want to automate these. And they should run as fast a possible to
optimize the feedback cycle during development.

A straightforward way to test these components is to run them all in separate programs (locally or distributed).  However, you'll get much better performance from your tests if you run them as local threads:

{% highlight python %}

# in ipython:

import threading, subprocess

doproc = lambda: subprocess.Popen(["python", "-c", "'a=1'"], 
                                  stdout=subprocess.PIPE).communicate()

def dothread():
    def run():
        a = 1
    th = threading.Thread(target=run)
    th.start()
    th.join()

time junk = [doproc() for _ in range(500)]

# CPU times: user 0.18 s, sys: 1.81 s, total: 1.98 s
# Wall time: 14.30 s

time junk = [dothread() for _ in range(500)]

# CPU times: user 0.09 s, sys: 0.05 s, total: 0.14 s
# Wall time: 0.16 s

{% endhighlight %}

That's a factor of about a hundred -- something you will most
definitely notice in your test suite.

Another advantage of this approach is that, when you write your code
so that it can be run as a thread, you can put as many or few of these
threads in actual processes (programs) as you'd like. In other words,
the coupling of processes to components is loosened. Your automated tests will
force you to implement clean shutdown semantics for each thread
(otherwise your test program will likely not terminate without manual
interruption).

Finally, it's much easier to interrogate the state of each component
when it's running as a thread, than it is to query a subprocess (via
e.g. RPC). This greatly simplifies the assertions you have to make in
your integration tests.

In a future post we will explore the use of context managers for
organizing setup and teardown of complex tests cases involving
multiple components.
