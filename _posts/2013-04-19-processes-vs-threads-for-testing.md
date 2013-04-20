---
layout: post
title: "Processes vs. Threads for Integration Testing"
description: ""
category:
tags: ["python", "testing", "concurrency"]
---
{% include JB/setup %}


*This post continues a series of posts about integration testing in
Python. Introductory post is
[here](/2013/04/18/thoughts-on-integration-testing/).*

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
want to automate these. And they should run as fast as possible to
optimize the feedback cycle during development.

A straightforward way to test these components is to run them all in
separate programs (locally or distributed), each in their own process.
However, you're likely to get much better performance from your many
short-running tests if you run the components as local threads in a
single process. The components running in these threads would, of
course, still talk to each other via RPC, ZeroMQ, etc., same as if
they were processes. But for short tests the setup and teardown for
threads is much faster. The most trivial example (assigning a value to
a variabled) shows the difference dramatically:

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
so that it can be run as threads, you can put as many or few of these
threads in actual processes (programs) as you'd like. In other words,
the coupling of processes to components is loosened. Your automated tests will
force you to implement clean shutdown semantics for each thread
(otherwise your test program will likely not terminate without manual
interruption).

Finally, it's much easier to interrogate the state of each component
when it's running as a thread, than it is to query a subprocess (via
e.g. RPC). This greatly simplifies the assertions you have to make in
your integration tests, since you don't have to send a message of some
sort via RPC or message queues -- you can just query variables.

I found, for the automated tests in IceCube Live, that making
components that could be instantiated in threads (for testing) or in
processes (for production) greatly sped up my test suite and
simplified the actual tests quite a bit. I should note that, prior to
release, there is still a final integration test done on a mirror test
system which simulates actual data collection and makes sure IceCube
Live can play along with other systems. The mirroring, however, is not
exact, since the actual detector elements we deployed at South Pole
are expensive and rely on a billion tons of crystal-clear ice to work
as intended.

In future posts we will explore the use of context managers for
organizing setup and teardown of complex tests cases involving
multiple components.

Next: [Introduction to Context Managers](/2013/04/20/introduction-to-context-managers/).
