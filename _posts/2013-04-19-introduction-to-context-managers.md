---
layout: post
title: "Introduction to Context Managers in Python"
description: ""
category:
tags: ["python"]
draft: true
---
{% include JB/setup %}


Context managers are a way of allocating and releasing some sort of
resource exactly where you need it (readers who are very familiar with
context managers may want to skip this section). The simplest example
is file access:

{% highlight python %}

with file("/tmp/foo", "w") as foo:
    print >> foo, "Hello!"

{% endhighlight %}

This is essentially equivalent to:

{% highlight python %}

foo = file("/tmp/foo", "w")
try:
    print >> foo, "Hello!"
finally:
    foo.close()

{% endhighlight %}

Locks are another example.  Given:

{% highlight python %}

import threading    
lock = threading.Lock()

{% endhighlight %}

then

{% highlight python %}

with lock:
    my_list.append(item)

{% endhighlight %}

replaces the more verbose:

{% highlight python %}

lock.acquire()
try:
    my_list.append(item)
finally:        
    lock.release()

{% endhighlight %}

In each case a bit of boilerplate is eliminated, and the "context" of the
file or the lock is acquired, used, and released cleanly. The cool
thing about context managers, however, is that you can easily make
your own. The simplest way is with the `contextlib` library. Here is a
context manager which you could use to time our
threads-vs-processes testing, above:

{% highlight python %}

import contextlib
import time

@contextlib.contextmanager
def time_print(task_name):
    t = time.time()
    try:
        yield
    finally:
        print task_name, "took", time.time() - t, "seconds."

with time_print("processes"):
    [doproc() for _ in range(500)]

# processes took 15.236166954 seconds.

with time_print("threads"):
    [dothread() for _ in range(500)]

# threads took 0.11357998848 seconds.

{% endhighlight %}
