---
layout: post
title: "Marginalia Hacks"
description: "Solutions for Notebook-style Literate Programming"
category: clojure
tags: [clojure, "literate programming"]
---
{% include JB/setup %}

**This is the sixth and final post in a
[series](/clojure/2014/07/03/an-advanced-clojure-workflow/) on my Clojure workflow.**

Here are some tricks I used to make [Marginalia](https://github.com/gdeer81/marginalia) work for me -- in particular, to support a sort of investigatory, notebook style of working. As always, your mileage may vary.

-----------

**Problem: I want to reorder my code snippets** to allow for more natural exposition.

**Solution**: As [discussed](/clojure/2014/08/02/communicating-with-humans), Marginalia does not provide reordering or interpolation of source code in the same way that Knuth's WEB does.  By default, `lein marg` processes all the Clojure source code in your project except in the `test` directory, presenting namespaces in alphabetical order.  The problem is exacerbated by the one-pass Clojure compiler, which expects everything to be declared before it is used.

I have been able to work around this to my satisfaction by specifying directories and/or files at the command line in the order I want them to appear.  For example, if I wanted both `src` and `test` files in my output, and if I wanted `src/myproject/core.clj` to appear first, I would say

    lein marg src/myproject/core.clj src test

If I wanted to reorder forms within `core.clj`, I could also just use Clojure's `declare` macro to forward-declare the vars at the top of the file.

-----------

**Problem: I want to see my Marginalia output as soon as I save my source code.**

**Solution**: It's nice to have quick feedback, so I use [conttest](https://github.com/eigenhombre/continuous-testing-helper) to run `lein marg`, plus some Applescript (or equivalent) to reload the output in the browser.

Example:

    conttest 'lein marg && osascript ~/bin/reload-browser.scpt file:///path/to/project/docs/uberdoc.html'

The Applescript `~/bin/reload-browser.scpt` is fairly simple, though you may have to adjust to suit your browser of choice:

    on run argv
        tell application "Google Chrome"
            set URL of active tab of first window to item 1 of argv
        end tell
    end run

Though it does take a few seconds, Marginalia not being a speed demon, one can get pretty quick visual feedback using this approach.

-------

**Problem: I want to show expressions and the results of their evaluation together**, à la [iPython Notebook](http://ipython.org/notebook.html), [Mathematica](http://www.wolfram.com/mathematica/), [Maple](http://en.wikipedia.org/wiki/Maple_software), [Gorilla REPL](https://github.com/JonyEpsilon/gorilla-repl), or [Session](https://github.com/kovasb/session).

**Solution**: For Clojure evaluation, I use the [cider-eval-last-sexp-and-append trick](/clojure/2014/07/05/emacs-customization-for-clojure/) I described in [my previous post on customizing Emacs](/clojure/2014/08/03/marginalia-hacks/).  This results in something like the following, in Emacs and in Marginalia:

<a href="/images/emacs-eval.png"><img src="/images/emacs-eval.png"/></a>
<a href="/images/marg-eval.png"><img src="/images/marg-eval.png"/></a>

Here I added a single quote (`'`) to keep the resulting form from throwing an exception when the buffer is recompiled.  This not quite iPython Notebook, but I find it gets me surprisingly far.  _And the source code remains completely usable as a whole, standalone program_.  This means I can combine notebook-style investigations directly in a working project without worrying how to get the code described in my "notes" into production.

-------

**Problem: I want nice looking math formulae** in my "notebook."

**Solution**: Use the [MathJax](http://www.mathjax.org/) JavaScript library.

As shown in the images from [the previous post](/clojure/2014/08/02/communicating-with-humans/), math can be typeset quite nicely with MathJax.  This can either be imported directly from a CDN, as follows:

{% highlight clojure %}
;; <script type="text/javascript"
;;  src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
;; </script>
{% endhighlight %}

(this being done directly in the Clojure source file, comments being rendered as Markdown, and therefore HTML).  Or, copy down the MathJax JavaScript source and use the `-j` argument to `lein marg`.

It took me a little time to figure out how to properly escape the TeX for inline math formulae.  The usual pattern in HTML is to use `\(` and `\)`, as follows:

    \(r^2 = x^2 + y^2\)

yielding $$r^2 = x^2 + y^2$$ inline.

For this to work correctly, Marginalia makes you add another `\`:

    \\(r^2 = x^2 + y^2\\)

For inset math formulae, the required `$$` is unchanged:

    $$r^2 = x^2 + y^2$$

Which gives:

$$r^2 = x^2 + y^2.$$

------

**Problem: I want to show graphs** along with my text, code, and mathematics.

**Solution**: Use a JavaScript plotting library, and a little Clojure to prepare the data.

This last hack is perhaps the most fun and the most "hacky" of the bunch.  One of the best features of notebook solutions like iPython Notebook is the ability to show graphs inline with the code that generates them.  This is not really in the wheelhouse of Marginalia, but since we can incorporate JavaScript (as seen above, for mathematics), we can leverage plotting libraries

