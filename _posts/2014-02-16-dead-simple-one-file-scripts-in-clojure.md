---
layout: post
title: "Dead simple one file scripts in Clojure"
description: ""
category: 
tags: [clojure]
---
{% include JB/setup %}

Back in the dark twilight days of the old millenium, I was a Perl
programmer. I wrote Perl code so often that eventually I wrote a
Perl script, `perlscript`, to generate scripts -- i.e. to create files
with the correct
[shebang](http://en.wikipedia.org/wiki/Shebang_(Unix)) preamble,
permissions, `use strict` declaration, add my name and copyright info,
etc.

Then came Python, and I did the same thing with `pyscript`.

As of this year I'm developing more or less exclusively in Clojure.
One thing that irritated me a bit when I started with the language was
the need to create a whole Leiningen project just to do some simple
task.

That irritation is gone with
[lein-exec](https://github.com/kumarshantanu/lein-exec), a Leiningen
plugin which lets one create single-file scripts and execute them
without need of a `project.clj`, `src` directory, etc.

However, I still wanted to be able to spin out new scripts quickly. So
I wrote a Leiningen template
[lein-script](https://github.com/eigenhombre/lein-script) which does
this:

    $ lein new script foo
    Generating stand-alone script "foo".
    $ ./foo
    Welcome to foo!

Pushed to Clojars and GitHub. (You need to update `.lein/profiles.clj`
as shown in the GitHub project for this to work.)

