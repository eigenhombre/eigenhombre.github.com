---
layout: post
title: "Emacs Customization for Clojure"
description: ""
category: clojure
tags: [clojure]
---
{% include JB/setup %}

**Synopsis**: *I talk about the value of paredit in Emacs and show a trick which allows you to insert the result of any given Clojure expression directly underneath that expression.*

As I said in the [first post](/2014/07/03/an-advanced-clojure-workflow/), a good set of tools can make a big difference in productivity and enjoyment while working in any language, and Clojure is certainly no exception.  The most important tool in your toolbox, regardless of language, is your editor. Editing Lisp code in particular is much more natural with the right editor setup.

I have been using Emacs since the 1990s, though I still consider myself a novice (Emacs is that way).  Though [good](http://lighttable.com) [alternatives](http://code.google.com/p/counterclockwise/) exist, [over half of the Clojure community has adopted Emacs](http://cemerick.com/2013/11/18/results-of-the-2013-state-of-clojure-clojurescript-survey/) despite its lack of polish and its Himalayan learning curve.  Emacs is massively customizable, with hundreds of plugins available, and can be extended to just about any degree using [its own flavor of Lisp](http://en.wikipedia.org/wiki/Emacs_Lisp).

While I'll give a few Emacs configuration tips below, I don't give a complete recipe for customizing Emacs here.  [My Emacs configuration file](https://github.com/eigenhombre/emacs-config/) is on GitHub if you are interested. (Thanks to [Matthew Wampler-Doty](https://github.com/xcthulhu/config-files) for contributing portions of this file.)

### Paredit

Though it takes a little getting used to, I started using [paredit mode](http://emacsrocks.com/e14.html) exclusively a year or so and it has made a huge difference, not only for my productivity with the language, but also in my enjoyment and appreciation of Lisp in general.

In paredit, the units of code are not lines of text or sequences of characters, but *s-expressions* (lists).  In other words, paredit gives you the ability to easily manipulate the data structures that your code is built out of.  With various key combinations, you can kill expressions, split them, join them, expell ("barf") or absorb ("slurp") neighboring expressions, and so on.  This excellent [cheat sheet](https://github.com/joelittlejohn/paredit-cheatsheet) covers the whole range of commands and keybindings.

I think that it wasn't until I started using paredit that I really got used to all those parentheses.  Now editing non-Lisp code feels unnatural to me, since I don't have the ability to sling blocks of code about with the same ease without those parentheses steering my editor.  And, though it's hard to give a specific reason for this, working directly with trees of Lisp expressions gives me a feeling of somehow being closer to the essence of computation.

### Cider

An even more significant element of my workflow is provided by [Cider](https://github.com/clojure-emacs/cider), which integrates the Clojure REPL directly with Emacs.  The upshot of this kind of integration is that one can *evaluate Clojure code directly from the editor* with a single key combination and see the results instantly.

I bind my own key combinations to the common Cider commands to make them as quick to execute as possible:

    (add-hook 'clojure-mode-hook
       '(lambda ()
          (paredit-mode 1)
          ;; Actual keyboard bindings follow:

Start Cider REPL with `control-o-j`:

          (define-key clojure-mode-map (kbd "C-o j") 'cider-jack-in)

**Restart** Cider REPL with `control-o-J` (in case of dirty JVM, etc.):

          (define-key clojure-mode-map (kbd "C-o J") 'cider-restart)

Use `⌘-i` to evaluate previous expression and display result in minibuffer (I am on a Mac):

          (define-key clojure-mode-map (kbd "s-i") 'cider-eval-last-sexp)

Rather than showing in minibuffer, use `control-o y` to insert the result DIRECTLY IN THE CODE:

          (define-key clojure-mode-map (kbd "C-o y") 'cider-eval-last-sexp-and-append)))

The last line requires the definition of `cider-eval-last-sexp-and-append` to be placed earlier in `init.el`:

{% highlight clojure %}

    ;; Append result of evaluating previous expression (Clojure):
    (defun cider-eval-last-sexp-and-append ()
      "Evaluate the expression preceding point and append result."
      (interactive)
      (let ((last-sexp (cider-last-sexp)))
	;; we have to be sure the evaluation won't result in an error
	(cider-eval-and-get-value last-sexp)
	(with-current-buffer (current-buffer)
	  (insert ";;=>"))
	(cider-interactive-eval-print last-sexp)))

{% endhighlight %}

As an example of using `cider-eval-last-sexp-and-append`, if I have the following Clojure code:

{% highlight clojure %}

    (range 100)
    |

{% endhighlight %}

with the position of my cursor denoted by |, and I type `control-o y`, I get:

{% highlight clojure %}

    (range 100)
    ;;=>
    (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99)

{% endhighlight %}

To see a video of this trick in action, check out [this demonstration video](https://vimeo.com/99980843).  I find that using `cider-eval-last-sexp-and-append` feels a little like working with the InstaREPL in [Light Table](http://www.lighttable.com/), except that here you can actually preserve and edit the results of expressions rather than just viewing them inline.  This is especially helpful for the style of semi-literate programming I will describe in a coming post.

In addition to `cider-eval-last-sexp-and-append`, `cider-pprint-eval-last-sexp` (bound to `C-c C-p`) evaluates the previous Clojure expression and pretty-prints it into a separate buffer (thanks to Michael Nygard for [pointing out this trick](http://stackoverflow.com/questions/24232346/resize-cider-minibuffer-for-evaluated-expressions)).  Note that the [source code to cider-mode](https://github.com/clojure-emacs/cider/blob/master/cider-mode.el) is not too hard to follow -- `cider-eval-last-sexp-and-append` came out of studying and adapting functions from there.

Other Emacs plugins which I use and recommend are:

* [clojure-mode](https://github.com/clojure-emacs/clojure-mode), which provides syntax coloring, indentation rules, and source code navigation;
* [rainbow-delimiters](https://github.com/jlr/rainbow-delimiters), which colorizes parentheses according to their nesting depth; and,
* [auto-complete](https://github.com/auto-complete/auto-complete), which provides IDE-like auto-completion.

To install Emacs packages, e.g. paredit, `M-x install-package<RET>paredit`.  Or, steal the code from [my `init.el` file](https://github.com/eigenhombre/emacs-config/blob/master/init.el#L35) which installs and updates multiple packages at once.

Conference talks and online videos are a great way to find Emacs tricks to steal.  For example, I've seen people run unit tests from inside Emacs and display failures inline with the code, which is a very cool trick, though not exactly what I want.  The best way to increase your Emacs-fu is to sit down with a grizzled Emacs guru and compare notes and techniques.  I wish I had more opportunities to do this.

In the next post, we'll talk about test-driven development and how "RDD" (REPL-driven development) and TDD enhance and complement each other.
