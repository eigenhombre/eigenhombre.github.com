---
layout: post
title: "Dead simple scripts in Clojure"
description: ""
category: 
tags: [clojure]
---
{% include JB/setup %}

TL;DR: Some times you just want to do something and have one file of
source code for that task.
[`lein-script`](https://github.com/eigenhombre/lein-script) sets that
up for you in Clojure.

Back in the dark twilight days of the old millenium, after years of
programming C and FORTRAN, I discovered Perl. I loved how easy it
became to automate so many common tasks with just a few lines of code.
I wrote Perl modules so often that eventually I wrote a Perl script,
`perlscript`, to generate Perl scripts -- i.e. to create files with
the correct [shebang](http://bit.ly/1gQyETQ)
preamble, add the `use strict` declaration, set file permissions, add
my name and copyright info, etc. 

The sixty seconds or so of time saved for each script I made paid
for the minor cost of writing `perlscript` many times over. When I switched
to Python, I did the same thing with a script I called `pyscript`, and
I happily used that for awhile (until I started packaging everything
up with distutils).

As of this year I'm developing more or less exclusively in Clojure.
One thing that irritated me a bit when I started with the language was
the need to create a whole Leiningen project just to automate some simple
task.

That irritation went away recently when I discovered
[lein-exec](https://github.com/kumarshantanu/lein-exec), a Leiningen
plugin which lets one create single-file scripts and execute them
without need of a `project.clj`, source sub-directory, etc.

However, I still wanted to be able to spin out new scripts quickly. So
I wrote a Leiningen template
[lein-script](https://github.com/eigenhombre/lein-script) which does
this:

    $ lein new script foo
    Generating stand-alone script "foo".
    $ ./foo
    Welcome to foo!

The generated script contains a commented-out example dependency
declaration for `lein-exec` which shows how to introduce and use
external libraries. The process is surprisingly easy -- it even works
fine for me with Emacs and nREPL.

Pushed to [Clojars](https://clojars.org/lein-script) and
[GitHub](https://github.com/eigenhombre/lein-script). (You need to
update `.lein/profiles.clj` as shown in the GitHub project for this to
work.)
