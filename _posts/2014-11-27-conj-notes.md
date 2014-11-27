---
layout: post
title: "Clojure/conj 2014 Notes"
description: "The View from the Clojure Center of Gravity"
category: clojure
tags: [clojure]
---
{% include JB/setup %}

# Summary

This was my fifth Clojure conference.  I&rsquo;ve become accustomed to
having my mind blown, being humbled, entertained, charmed, and
inspired, and coming away from each conference with a bunch of new
tools and tricks to try.

This conj was no exception.  Changes this year:

-   Greater diversity.  This is a welcome shift, though still far from
    optimal.
-   Videos from talks went online within hours.  This is also a big
    improvement over some of the previous conferences.
-   I knew and recognized more people.
-   I understood more of the content.
-   I came with colleagues &#x2013; a very welcome change.

As always I came away with a renewed appreciation for the intellectual
depth and the warmth of this community.  The first talk by Paul
deGrandis I found especially noteworthy.  The work he, Tim Baldridge,
and others at Cognitect did for Consumer Reports showed the power of
the Clojure language, philosophy and ecosystem.  They created not just
a single Web service or data import mechanism or rich reporting client
but, in each case, developed data-driven (declarative) *frameworks*
for creating those things, in less than a month per framework, with
only two developers.  I&rsquo;ve been assuming that doing things well with
Clojure takes more time initially but pays off in the long run due to
the benefits which comes from focusing on simplicity.  Now I still
believe in the latter claim (benefits of simplicity) but have in hand
a vivid counterexample for the former (speed of development).
I was also particularly impressed that they spent *half* their time on
design.  I think the talk (see links below) is well worth watching and
contemplating.

**Thanks to my employer [OpinionLab](http://opinionlab.com)** for sending me to the conj, and for
letting me post these notes on my personal blog!  By the way, [we&rsquo;re
hiring](http://www.opinionlab.com/functional-software-developer/).

**Notes**

What follows are my own terse notes (quotes, links, examples, things
to try or think about), ordered by talk, in case someone else might
find them helpful.

<div id="table-of-contents">
<h2>Table of Contents</h2>
<div id="text-table-of-contents">
<ul>
<li><a href="#sec-1">1. Summary</a></li>
<li><a href="#sec-2">2. Notes</a>
<ul>
<li><a href="#sec-2-1">2.1. Paul deGrandis - Data &rarr; Services and Apps</a></li>
<li><a href="#sec-2-2">2.2. Anna Pawlicka - OM for Visualization</a></li>
<li><a href="#sec-2-3">2.3. Colin Fleming - Cursive</a></li>
<li><a href="#sec-2-4">2.4. Steve Miner</a></li>
<li><a href="#sec-2-5">2.5. Ghadi Shayban</a></li>
<li><a href="#sec-2-6">2.6. Bozhidar Batsov - History of Clojure Tooling for Emacs</a></li>
<li><a href="#sec-2-7">2.7. Steven Yi</a></li>
<li><a href="#sec-2-8">2.8. Rich Hickey &#x2013; Inside Transducers</a></li>
<li><a href="#sec-2-9">2.9. UNSESSION: Bozhidar Batsov</a></li>
<li><a href="#sec-2-10">2.10. Nathan Herzing &amp; Chris Shea &#x2013; voting infrastructure</a></li>
<li><a href="#sec-2-11">2.11. Michał Marczyk&#x2026;</a></li>
<li><a href="#sec-2-12">2.12. Julian Gamble &#x2013; <code>core.async</code></a></li>
<li><a href="#sec-2-13">2.13. Jeanine Adkisson &#x2013; Variants are not Unions</a></li>
<li><a href="#sec-2-14">2.14. Lucas Cavalcanti &amp; Edward Wible &#x2013; Exploring four hidden superpowers of Datomic</a></li>
<li><a href="#sec-2-15">2.15. Lightning talks</a></li>
<li><a href="#sec-2-16">2.16. NightCode&#x2026; and games. &#x2013; Zach Oakes</a></li>
<li><a href="#sec-2-17">2.17. Glenn Vanderberg &#x2013; Algorithms of TeX&#x2026; in Clojure (Cló)</a></li>
<li><a href="#sec-2-18">2.18. Zach Tellman/Factual &#x2013; ABC (Always Be Composing)</a></li>
<li><a href="#sec-2-19">2.19. Building a Data Pipeline with Clojure and Kafka &#x2013; David Pick (Braintree)</a></li>
<li><a href="#sec-2-20">2.20. Ashton Kemerling &#x2013; Generative Integration Testing</a></li>
<li><a href="#sec-2-21">2.21. Stewardship: the Sobering Parts – Brian Goetz</a></li>
</ul>
</li>
</ul>
</div>
</div>

# Notes

## Paul deGrandis - Data &rarr; Services and Apps

*Amazing talk.*

Video link: <https://www.youtube.com/watch?v=BNkYYYyfF48>

Slides: <https://github.com/ohpauleez/data-driven-slides>

Free the data!  (From RDBMSs)

Prototype services in **hours** instead of days/weeks.

Services only do a handful of things.  This can be abstracted away (service of services).

&ldquo;VASE&rdquo; - stored in Datomic; based on Pedestal

&ldquo;**We spent half our time in design.**&rdquo;

-   Superpowers - Clojure/ClojureScript/Datomic/core.logic/&#x2026;
-   Power in constraints and simplicity.
-   Combinations multiply impact.
-   Holistic exponential value combination
-   Be exploratory; think holistically
-   Write it all down.
-   Constrain the design space.
-   Think critically and SLOWLY.
-   Visualize outcomes and possibilities.

## Anna Pawlicka - OM for Visualization

Dimple.js - higher level lib on top of D3

http-kit and sente - websockets

sente has core.async as well &#x2013; &ldquo;just works&rdquo;

## Colin Fleming - Cursive

Cursive plugin for IntelliJ continues to improve.

Text is the wrong level of abstraction for working w/ our code.

Type less &#x2013; let the tools do more.

&ldquo;I object to doing things that computers can do.&rdquo; &#x2013; Colin Shivers

Determining if a macro expansion terminates is the halting problem.

&ldquo;You should never have to see your namespace declaration.  I hate that
thing.&rdquo;

Text is a *serialization* protocol for our programs.

For Emacs, look at `clj-refactor`.

## Steve Miner

&ldquo;Herbert&rdquo; schema generator (*very* $$\alpha$$) &rarr; test.check
property expressions

    (hg/generator '[(:= V [int+]) (in V)])

replaces

    (gen/bind (gen/not-empty (gen/vector gen/int))
      #(gen/tuple (gen/return %) (gen/elements %)))

Can also use regexps, e.g. `(str #"fo*bar+")`

&ldquo;Regex&rsquo;s are probably the most powerful DSL in history.&rdquo;

Use Herbert both for test.check generators AND validation.

## Ghadi Shayban

Nashorn

### Truffle and Graal

Rewriting nodes of ASTs based on optimistic typing

Inlining like macroexpansion

TruffleC - crazy project: inline trees of C in trees of Ruby and vice
versa.

Clojure PersistentVector implements $$\approx 20$$ interfaces(!).

Why even run Java bytecode?  (Future tools may make bytecode less relevent.)

&ldquo;Low-level stuff is fun.&rdquo;

## Bozhidar Batsov - History of Clojure Tooling for Emacs

CIDER maintainer.  Dark knight of the order of Emacs.

&ldquo;You are the architect of the structure you want, built on a
foundation [Emacs].&rdquo;

CIDER: 85 contributors v. 1 contributor for Cursive.

### TODO Try sexp-fu, paxedit, rainbow-identifiers, refactor-nrepl.

## Steven Yi

Music &#x2013; `Pink` and `Score`.  Overtone wraps Supercollider; Pink and
Score replace those.

Linking space and time.

Lock-free audio functions.

Filters/delay lines require state.

## Rich Hickey &#x2013; Inside Transducers

Transduce = &ldquo;lead across.&rdquo;  `map`, `filter`, `take`, `mapcat`,
`partition-by` now all *return* transducers.

Transducer takes and return 3 arity functions; arity-0 flows through
to wrapped fn.  Arity-1 is used for completion, if none flow through.
Arity-2 is reducing stop &#x2013; you can morph input, call nested (or not),
expand, etc.

Map:

    (defn map
      ([f]
         (fn [rf]
           (fn
             ([] (rf))
             ([result] (rf result))         ;; call reducing fn
             ([result input]
                (rf result (f input)))))))  ;; ACTUAL map operation

Filter:

    (defn filter
      ([pred]
         (fn [rf]
           (fn
             ([] (rf))
             ([result] (rf result))  ;; call reducing fn
             ([result input]
                (if (pred input)     ;; Again, the meat
                  (rf result input)
                  result))))))

`take` requires state (`volatile!`).

Mapcat is simple composition:

    (defn mapcat
      ([f] (comp (map f) cat)))

where `cat` is a transducer which concatenates the contents of each
input into the reduction.  Expands input into many inputs in the next
stage.  It is semantically opposite to a reduction.

`partition-by` builds up intermediate collections and returns the last
during the 1-arity (complete) phase if given a finite input.

    (defn transduce
      ([xform f coll] (transduce xform f (f) coll))
      ([xform f init coll]
        (let [xf (xform f)  ;; transformation of reducing step
              ret (reduce coll xf init)]
          ;; completion:
          (xf ret))))

Transducers are &ldquo;pushy,&rdquo; rather than lazy.  Especially at completion.

`educe` &#x2013; to *lead out.* Lead back, lead across, lead out.  Return an
&ldquo;eduction&rdquo; which is the reduction without actually carrying it out.

Good for inexpensive work that you&rsquo;re not ready to use yet.  Still
composes with subsequent transductions.

Parallel transducers are coming (like reducers).

### More `core.async`:

Channels will be deref-able with blocking semantics (or timeout).

&ldquo;Promises are cool, but channels are cooler.&rdquo;  Promise semantics
coming to channels as &ldquo;promise channels&rdquo;.  Write once, read many.
Always write-ready.  All readers will unblock together.

Don&rsquo;t do a lot of work in channel transducers (they block the
channel); use *pipelines* instead.  Parallel with user control
of number of threads.  But can&rsquo;t parallelize stateful transducers.

[We can make fruitful use of `core.async` and transducers at
work&#x2026; best to start understanding them now.]

## UNSESSION: Bozhidar Batsov

### `CIDER` Demo &#x2013; new features

1.  Now don&rsquo;t have to evaluate namespace.
    Use `:init-ns` option to `:repl-options`.
    
    Look at `*nrepl-messages*` (recently upgraded) if problems.

2.  `cider-undef` undefines a var.

3.  `cider-refresh` does Stuart Sierra trick for reloading namespaces.
    &ldquo;30 years from now people will still be using Emacs&#x2026; but not IntelliJ.&rdquo;

4.  `M-.` can show Java code now (and presents a selection of which class to look at).

5.  `C-h m` show *all* keybindings in scope.

6.  `C-h f` show keybindings for mode.

7.  `C-c C-d` show JavaDoc (browser not needed).

8.  `smex` (on top of IDO).  Suggest recent commands.

9.  **Look at `ac-cider`** for autocomplete.  &ldquo;This is Emacs &#x2013;
    anything is possible.&rdquo;  &ldquo;I have been drinking a lot of Cider
    while fixing `CIDER` bugs&#x2026; it&rsquo;s a problem that kind of fixes
    itself.&rdquo;
    
    See also `compliment` Clojure completion library (Clojure only).

10. Grimoire integration (in-editor or in-browser).

11. Tracing functionality `cider-var-toggle-trace` (`C-c M-t v`).

12. Choose recent file?

13. `C-u C-c C-z`? change namespace in REPL.

14. `C-c M-o` clears everything.

15. `cider-browse-ns` &#x2013; list of all namespaces, explorable.

16. Can open (browse) jar files from Emacs!!!

17. `dash.el` Clojure-idiomatic Elisp.

18. `cider-inspect` function for introspecting data structures/objects.

19. **Awesome package from Ustun**: `https://github.com/ustun/emacs-friends` for launching
    just about anything from inside Emacs.

20. `helm-imenu` for inventory of / searching in current namespace.

&ldquo;If you know Clojure, learning Emacs Lisp is very easy.&rdquo;

## Nathan Herzing & Chris Shea &#x2013; voting infrastructure

Voting infrastructure &#x2013; Turbovote

Architecture:

    USPS | FTP frontend | REDIS | API <> Datomic
                                  API | frontend

Cool interactive Om/core.async/Datomic example.

### TODO Take another look at Pedestal

[Learned from Ustun Ozgur &#x2013; `git log -p` &#x2013; awesome for delving into
change details.  See also
`http://www.philandstuff.com/2014/02/09/git-pickaxe.html` ].

## Michał Marczyk&#x2026;

&#x2026; Ported PDS&rsquo;s to ClojureScript.

`2r100111` legal syntax for an integer :-)

Could we do better than `core.rrb-vector` (radix search) for
performance?  Probably not; but adding requirements until data
structure isn&rsquo;t optimal; then, optimize again.  E.g. concatenate, slice,
reverse.

`data.avl` &#x2013; AVL tree (`http://en.wikipedia.org/wiki/AVL_tree`) &#x2013;
like red-black tree; lookup / insert / delete all $$O(\log n)$$.
Lookup, insert, delete &ldquo;universally known.&rdquo;

&ldquo;Well-known&rdquo; operations: join, split, range.

## Julian Gamble &#x2013; `core.async`

Paradigms of `core.async`

Forthcoming Clojure Recipes book

`core.async` is about:

-   Queues in your application; asynchronous communication.
-   Making your program simpler to reason about.
-   &ldquo;[In Lisp], you can extend the compiler simply by shipping the library.&rdquo;
-   Modeling time as reading from a queue

Example:

    go block >! my-q <! second go block
    go block | my-q | second go block

-   10k &ldquo;processes&rdquo; in browser (TBaldridge)
-   David Nolen &ldquo;render pipeline&rdquo; example &#x2013; guarantees queues don&rsquo;t back up.
-   JS event queue&#x2013;handler puts event on queue; event loop processes these.
-   Parallelism is not concurrency &#x2013; in concurrency, there is shared state.
-   Rich Hickey ant demo &#x2013; multiple threads (agents) against one data structure
    -   previously impossible in the browser
    -   demo: no async version (performs poorly)
    -   add `core.async` for evaporation; modest improvement
    -   adopt Nolen minecraft demo (`.cljs` animations) &#x2013; imperative
        style loops / `aset`&rsquo;s.
-   Locks and callbacks don&rsquo;t scale.

## Jeanine Adkisson &#x2013; Variants are not Unions

*Nice talk*.

`@jneen` language developer

-   Clojure &#x2013; &ldquo;a viable alternative to Ruby.&rdquo;
-   Variants a pattern from typed languages.
-   Values are nice.  No one is going to come around and change them.
-   Variants are tagged data.
    `[:tagged data]`
-   Destructuring via `core.match`.
-   Book store example
    -   Antipattern: maps or database tables populated with missing fields.  Makes her sad.
    -   `{:address nil, :email nil, :store-id nil}` *will* show up in your run time.
-   Maps model `and` by putting data together:
    
        {:a 1
         ;; and
         :b 2}
-   Model OR:
    
        {:address "a"
         ;; or
         :email "b"}
-   Variants have multiple constructors and single destructors.
-   Core.typed can check for things that can go wrong:
    
        (defalias MyVariant
          (U [(Value :tag1) String]
             [(Value :tag2) Sym]
             [(Value :tag3) ...]))

-   Herbert schemas can also support variants:
    
        '(or [:tag1 str]
             [:tag2 sym]
             [:tag3 ...])

-   Recursive variants and (untyped) &lambda; calculus
-   `hiccup` uses recursive variants to model HTML
-   Erlang does this stuff well also.
-   Closed Variants restrict creation of new tags
-   Variant examples:
    -   Loop variant where control decision made elsewhere
    -   result variant.  `[:ok val]` and `[:err message]`.
    -   Datomic stores variants efficiently (e.g., `:db.variant/type :xx/order`)
        -   Polymorphism straightforward
        -   Schema example
-   Variants are everywhere (begging to get out)
    
    Especially in shallow subclass heirarchies
-   *This idiom has a long pedigree from typed languages*.  Use it.

## Lucas Cavalcanti & Edward Wible &#x2013; Exploring four hidden superpowers of Datomic

`@lucascs` São Paulo and remote.

Why build a bank from scratch in Brazil using Clojure and Datomic?

Banks are slow to adapt to new realities.

Barriers for entry are substantial.

Datomic &#x2013; is an alien spaceship.

### Superpowers

1.  Audit trail.  Metaphor is like Git.
2.  Authorization: Queries which traverse graph of relationships.
    -   recursive rule definitions
    -   queries using such rules
    -   makes ownership/permissioning simple(r)
3.  HTTP Cache &#x2013; `Last-Modified` header &rarr; 304 Not Modified
4.  Mobile Sync &#x2013; pass update URL w/ timestamp in response header
    -   `d/history` gives ALL facts, including retracted ones.
5.  *bonus* Future DBs: Can create future (simulation) data and
    &ldquo;include&rdquo; it with a DB.
6.  *bonus* Testing: Make virtual database populated with test variables.
7.  *bonus* Schema Extension: Transact **schema itself** into Datomic (migrations)
    `:pii` example.
8.  *non-feature* &#x2013; sharding reads.  Currently one transactors; will
    scale as needed for availability.  &ldquo;Canary shard.&rdquo;  Shard-specific
    peer cache.  ACID transactions inter-shard.
9.  *bonus* DB Aggregation.  Give Data Science dept. a read-only copy
    of DBs using their own CPUs.  Knowing when things happened is
    crucial for doing machine learning (e.g., testing predictions).

&ldquo;*We never take our system down.*&rdquo;

## Lightning talks

-   Chris Oakman&rsquo;s friend
    -   Pros <http://pteroattack.com/>
        -   CLJS needs a website and an easier compiler tool
        -   `cljs.info` &larr; **look at this**
        -   More helpful compiler UI
-   Reid Draper - finding simple failing test cases with `test.check`

## NightCode&#x2026; and games. &#x2013; Zach Oakes

Game Development is a gateway drug for programming, and for Clojure.
So a solid game development story is good for the ecosystem.

Mainstream game development environments well-suited for &ldquo;big&rdquo; games.
But for indie games and &ldquo;art games,&rdquo; artistic merit and interesting
gameplay are the differentiator.  2D is more abstract and better for
some things.

(Check out Lone Survivor (2012))

### Tools for Indie Games

1.  Hosted languages.  Frameworks like unity (CLR) and libGDX (JVM)
    very popular for indie developers. &ldquo;I&rsquo;m afraid of the $$z$$-axis.&rdquo;
    
    `play-clj` *experimental*
    
    Desktop, &ldquo;iOS&rdquo; and Android in one project.
    
    Transforming entities is most of what you do.
    
    Entities:
    
    -   **Particle effect editor** &rarr; LibGDX entity
    -   Texture
    -   Shape
    
    Teaching problem: modify an existing game.

2.  Functional and logic programming
    
    Uncommon in game development.  Entity Component Systems
    
    Papers to read:
    
    -   A logical approach to building dungeons (roguelike games)
    -   Game Dialogue as Expressions in First-Order Logic
    
    Time rewinding is simple &#x2013; built into `clj-play` (awesome demo).

3.  Interactivity (REPL)
    
    Biggest selling point for Clojure game development.  Esp. important
    for **art**.  (Ebert: &ldquo;Games can&rsquo;t be art.&rdquo;)
    
    &ldquo;I don&rsquo;t think art has to make sense.  If it didn&rsquo;t, David Lynch
    wouldn&rsquo;t be a famous director.&rdquo;
    
    &ldquo;I really love unfinished games&#x2026; they avoid a lot of common game
    tropes&#x2026; like the game has an end.&rdquo;

## Glenn Vanderberg &#x2013; Algorithms of TeX&#x2026; in Clojure (Cló)

IBM 650 Knuth &ldquo;An Appreciation from the Field.&rdquo;

3 Goals of TeX (&ldquo;the greatest yak shave in history&rdquo;)

-   Utility
-   Discovery
-   Education

&ldquo;Breaking Paragraphs into Lines&rdquo; &#x2013; Knuth paper

TCP/IP flag day 1 Jan. 1983

John Lions commentary on the Unix OS

Showpiece of literate programming.

Matz quote (see photo).

Reading TeX code is opening a time capsule of another era.
&ldquo;The last hurrah of procedural structured programming.&rdquo;

`seque->` parallel threading macro (!)

Study a modern implementation to compare with the original.

Impedence mismatch between Clojure and TeX.  How much compatibility
is needed?  Is this a problem in Clojure?  In TeX?  How much of the
quirks in TeX are intrinsic?

Embrace mutation when appropriate.

Java NIO scatter/gather &rarr; TeX font metric files.

The Gifts of 30 Years &#x2013; &ldquo;It&rsquo;s 2014 and we&rsquo;re still using&#x2026;.&rdquo;

## Zach Tellman/Factual &#x2013; ABC (Always Be Composing)

WebGL - Sierpinski triangles

Macros are not our most composable abstraction

Deferring invocation allows one to do more with it.  But functions are
somewhat &ldquo;opaque&rdquo; to examination.  Data is better.  Prefer:

    Data > Functions > Macros

in terms of generality.

However, for execution,

    Data < Functions < Macros

Taxonomies &#x2013; value of periodic table (&ldquo;gaps&rdquo;).

Abstractions take us further from a particular problem, towards more
generality.  This is a balance.  E.g. transducers &#x2013; they are a
*specific* kind of composition, and do not compose w/ e.g. `sort`.
They are not easy for beginners to wrap their heads around.

Composing data &#x2013; example of mean, variance, &#x2026;. and data sets whose
mean and variance are all the same.

&ldquo;Premature *specialization* is the root of all evil.&rdquo;

ztellman/automat &#x2013; &ldquo;better automata through combinators&rdquo;

[Beautiful automata / Kleene-y slides here]

&ldquo;We are side-effecting the world.&rdquo;  Backpressure in TCP/IP &c.;
structural in most implementations (queues, core.async, &#x2026;).
But &ldquo;causality doesn&rsquo;t have to be structural.&rdquo;

ztellman/manifold &#x2014; streams vs. deferreds; like channels and
promise-channels in core.async.  Beautifully composable; higher-level
abstraction than core.async.  `let-flow` &#x2013; data flow in complex DAGs
similar to Prismatic&rsquo;s `graph`.

Code is data, but code is a very narrow subset of data; code&rsquo;s
semantics are fixed; data&rsquo;s is arbitrary.

Tradeoffs &#x2013; how many affordances can we give to users?

What are the different forces at play in these design questions?
These decisions can make the ecosystem flourish or wither.

## Building a Data Pipeline with Clojure and Kafka &#x2013; David Pick (Braintree)

Amazon Redshift (data warehouse)

Keeping track of deleted data is very hard to do.  E.g. extra columns
(`created_at`, `updated_at`).

Data pipeline + search/reporting infrastructure [sound familiar?]

-   First attempt: SQL powered.  Slow and limited.
-   Harvest PostgreSQL change log?
    
        DB | ..... | Redshift
                   | Elasticsearch
    
    But Redshift dies frequently.
-   PGQ\_ async. processing of live transactions.  Triggers on CRUD.
    When transaction fails, queue not populated.
    ACID and fast.  So:
    
        PGQ | Redshift
            | Elasticsearch
-   Apache **Kafka**.  pub/sub w/ multiple nodes in between (high avail.).
    *Partitions* (ordered) shard *topics* (not necessarily ordered).
    Can keep data for e.g. 1 month. 300k msgs/sec rates.
    Can re-wind Kafka streams in case a job dies.
    
    Pipeline:
    
        shards
             0 | datastream  | redshift loader
                             | ES loader
               | eventstream | ES loader
             1 ...
             2 ...
-   Why Clojure? Why not Ruby?  (Braintree is mostly a Ruby shop.)
    -   JVM &#x2013; ES, Kafka client, etc. already there.
    -   Concurrency &#x2013; architecture is inherently concurrent, and Ruby
        sucks at this.
    -   Abstractions &#x2013; *this is the main point*
        -   Infinite sequences (this is the primary abstraction of a data
            pipeline)
        -   Error handling
            -   First try &#x2013; threads
            -   Second try &#x2013; agents.  They can handle errors, but they don&rsquo;t
                talk to each other&#x2026; not the right abstraction.
            -   3rd try &#x2013; `core.async` &#x2013; single `go` block per merchant.
                
                    datastream  |             | 0 |
                    eventstream | distributor | 1 | Elasticsearch
                                              | 2 |
                
                But, had race conditions and callback-like semantics.
            -   Actor semantics turned out to be a good fit.
                -   Can think about single merchant at a time.
                -   Can put backpressure
                -   Avoid races by notification
                -   Pulsar/quasar provided semantics for this
    -   Lessons learned:
        -   GC is problematic
        -   Use G1GC
        -   Tune your heap size &#x2013; smaller minimizes pause times (Clojure
            objects tend to be needed for a short time)
        -   Used JMX for almost everything
        -   Default configs for ES, etc. are bad for production
        -   **Use a model that avoids race conditions**

## Ashton Kemerling &#x2013; Generative Integration Testing

Pivotal Labs &#x2013; Pivotal Tracker

How do we make software more reliable?  Software either needs to get
simpler, and/or better unit and integration tests.

52k LOC Ruby.  12k+ RSpec examples &#x2026;

Time, creativity, edge/ordering cases, emergent behavior: these are
all causes of bugs which creep into production.

Downside of generative testing: test duration; reduced assertion power.

GT is helpful for Web apps because it&rsquo;s hard to simulate many
different sequence of user actions, and harder to find the minimal
sequence which introduces a failure.

Tools:

-   Clojure
-   test.check
-   clj-webdriver
-   JDBC or similar.  Make sure queries are fast!
-   clj-http (optional)

Before tests

-   copy/setup database state
-   setup all browsers

Before run

-   restore DB state
-   refresh browsers and clear caches
-   generate actions (generators) &#x2013; what the user will do
-   execute actions (action runners)
-   assert (assertion engines)

Actions are composable and atomic.  e.g., drag-drop, click, &#x2026;.  They
must completely describe all test behavior.

Don&rsquo;t worry about eliminating impossible sequences from your test
cases.

Multimethods very helpful for describing actions.

At a higher level, *strategies* separate context from action.  Can be
parallel or serial. They include setup and assertions.

Make invalid actions a no-op.

Assertions are trickier in GT.  Can&rsquo;t depend on input data.  Must be
true every time.

Examples (from Pivotal Tracker):

-   changes should synchronize
-   client/server state parity
-   presence of alerts and error dialogs
-   no crashes

Assertions can be slow, and this will reduce number of runs you can do.

*Demo, via video.*

## Stewardship: the Sobering Parts – Brian Goetz

&ldquo;Blub Language Architect&rdquo;

&ldquo;You have your brushes and your colors&#x2026; go paint paradise, and then,
in you go!&rdquo;

Pragmatism &#x2013; &ldquo;there is no *good*, there is only *good for*.&rdquo; &#x2013; Yoda

COBOL was an able *predator* for the problem *ecosystem* of the mid
1960s. `ALTER` statement.

Steward: *A person employed to manage another&rsquo;s property, especially a
large house or estate.*

**How much change, how fast?**

-   Better safe than sorry
-   Compatibility
-   Don&rsquo;t alienate users

vs.

-   Adapt to change
-   Fix mistakes.

Put more eggs in fewer baskets &#x2013; invest in changes w/ high
cost/benefit ratio.  &ldquo;That&rsquo;s not where I want to expend my complexity
budget.&rdquo;

Ford: practical cars for ordinary people.

&ldquo;Every feature is one step towards collapsing in your own complexity.&rdquo;

&ldquo;Developers overestimate the importance of code, and underestimate the
importance of stable programs.&rdquo;

Central park == USD $$\frac{1}{2} 10^{12}$$.  Based on trust.  Breaking
changes are like building in Central Park.

Evolution of &lambda; in Java 8.  The obvious choice may not be the one
you want.  Java&rsquo;s `ALTER` statement is only one bad decision away.  **If
you don&rsquo;t know the right thing to do, at the very least, do nothing.**

Hardware: memory access has gotten more expensive and less
predictable.  Cache-miss can equal 1000 arithmetic instructions.
Future JVM: support for values!  Implicit immutability,
perf. improvement and predictability.  &ldquo;Codes like a class, behaves
like an int.&rdquo;  Non-Java languages will see the biggest benefits.

Tail recursion not supported due to a somewhat bone-headed decision.
This may get fixed.