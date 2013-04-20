---
layout: post
title: "Introduction to Context Managers in Python"
description: ""
category:
tags: [python, testing]
draft:
---
{% include JB/setup %}

*This is the third of a
[series](/2013/04/18/thoughts-on-integration-testing/) of posts
loosely associated with integration testing, mostly focused on Python.*

Context managers are a way of allocating and releasing some sort of
resource exactly where you need it. The simplest example is file
access:

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

In each case a bit of boilerplate is eliminated, and the "context" of
the file or the lock is acquired, used, and released cleanly.  

Context managers are a common idiom in Lisp, where they are usually defined
using macros (examples are `with-open` and `with-out-str`
in Clojure and `with-open-file` and `with-output-to-string` in Common
Lisp). 

Python, not having macros, must include context managers as part of
the language. Since 2.5, it does so, providing an easy mechanism
for rolling your own. Though the default, 'low level' way to make a context manager
is to make a class which follows the [context management
protocol](http://docs.python.org/2/library/stdtypes.html#typecontextmanager),
by implementing `__enter__` and `__exit__` methods, the simplest way
is using the `contextmanager` decorator from the `contextlib` library,
and invoking `yield` in your context manager function in between the
setup and teardown steps. 

Here is a context manager which you could use to time our
[threads-vs-processes
testing](/2013/04/19/processes-vs-threads-for-testing/), discussed
previously:

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

## Composition

Context managers can be composed very nicely.  While you can certainly do the following,

{% highlight python %}

with a(x, y) as A:
    with b(z) as B:
        # Do stuff

{% endhighlight %}

in Python 2.7 or above, the following also works:

{% highlight python %}

with a(x, y) as A, b(z) as B:
    # Do stuff

{% endhighlight %}

with Python 2.6, use `contextlib.nested` does *almost* the same thing:

{% highlight python %}

with contextlib.nested(a(x, y), b(z)) as (A, B):
    # Do the same stuff

{% endhighlight %}

the difference being that with the 2.7+ syntax, you can use the value
yielded from the first context manager as the argument to the second
(e.g., `with a(x, y) as A, b(A) as C:...`).
  
If multiple contexts occur together repeatedly, you can also roll them together
into a new context manager:

{% highlight python %}

import contextlib

@contextlib.contextmanager
def c(x, y, z):
    with a(x, y) as A, b(z) as B:
        yield (A, B) 

with c(x, y, z) as C:  # C == (A, B)
    # Do that same old stuff
 
{% endhighlight %}

What does all this have to do with testing? I have found that for
complex integration tests where there is a lot of setup and teardown,
context managers provide a helpful pattern for making compact, simple
code, by putting the "context" (state) needed for any given test
*close to where it is actually needed* (and not everywhere else).
Careful isolation of state is an important aspect of functional
programming. 

More on the use of context managers in actual integration tests in the next post.
