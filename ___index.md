---
layout: page
title: Software
tagline: ... and other things that should be as simple as possible, but not simpler.
---
{% include JB/setup %}

![image](images/5269103637_e8e1d7e684_m.jpg)

Welcome to the technical blog of **John Jacobsen**, a software
developer currently living in Chicago, IL.

I have been a software developer since the late 1980s, mostly working
in the field of high energy physics and particle astrophysics. My
current specialty is developing distributed and/or Web-based
applications for scientific computing, though I have done a fair
amount of low level software engineering (Linux device drivers and
embedded systems) as well.

I like to write elegant, powerful, maintainable code. Generally this
means striving for [simplicity](http://bit.ly/simple-made-easy)
whenever possible. I also practice iterative, test-driven development
and fully-automated deployment workflows with frequent releases. I
like Python, Clojure, other Lisps, Git, Eclipse, Emacs, and many, many
more things. I'm also an on-again, off-again
[visual artist](http://johnj.com) (painting, drawing and photography).

The site for my [consulting business](http://npxdesigns.com) has more
details. I'm also on
[StackOverflow](http://stackoverflow.com/users/611752/johnj) and
[GitHub](http://github.com/eigenhombre).

<iframe src="http://ghbtns.com/github-btn.html?user=eigenhombre&type=follow"
  allowtransparency="true" frameborder="0" scrolling="0" width="150px" height="25px"></iframe>

Occasionally I will post about various side projects on the following
blog. I also "tweet about various obsessions, software or
otherwise":https://twitter.com/eigenhombre.

<a href="https://twitter.com/eigenhombre" class="twitter-follow-button" data-show-count="false">Follow @eigenhombre</a> 
<script src="//platform.twitter.com/widgets.js" type="text/javascript"></script>

## Blog Posts

{{ site.posts }}

<ul class="posts">
  {% for post in site.posts %}
    {% if post.draft != true %}
      <li>
        <span>{{ post.date | date_to_string }}</span> &raquo;
        <a href="{{ BASE_PATH }}{{ post.url }}">{{ post.title }}</a>
      </li>
    {% endif %}
  {% endfor %}
</ul>
