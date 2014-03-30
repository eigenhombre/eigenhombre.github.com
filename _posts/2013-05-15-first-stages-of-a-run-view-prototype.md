---
layout: post
title: "Early Prototypes of a Zoomable Run Viewer for IceCube"
description: ""
category: 
tags: [python, javascript, django, icecube, neutrino, d3]
draft: true
---
{% include JB/setup %}

## Day 1

I have long wanted to create a zoomable, pannable Web-based view in 
[IceCube Live](http://npxdesigns.com/projects/icecube-live/) which
would show everything of relevance which happened in the IceCube
Neutrino Detector at a wide variety of timescales. This is something which was
discussed at various times but which never managed to make it into the
development schedule.

Recently, however, I discovered [D3.js](http://d3js.org), which makes
some crazy awesome visualizations fairly easy; so I spent yesterday
creating a first version of what I wanted: a UI tool which displays
IceCube runs (periods of data collection; simulated, in this case, and
shown in green) at a variety of time scales.

There are many things to improve -- runs that overlap two time bars
don't display correctly; zooming and panning is somewhat jagged; and,
of course, there is a lot more information to add to the display. But
it already shows the direction I want to go and I was pleased at what
I was able to arrive at with only a day's worth of development.

The implementation uses D3.js and jQuery (JavaScript) on the client,
and Django (Python) with MySQL on the backend. Next steps are to fix
display of runs that wrap from one time bar to the next, and to make
the Ajax calls back to the server more efficient so that information
is only fetched as needed (right now there is a lot of inefficient
re-fetching of information). Then I'll overlay more information like
run numbers, graphs of data rates, and allow specific display elements
to link to other pages inside of IceCube Live. Also IceCube has very
high uptime so any gaps should probably be shown in red.

In the video zooming is being done with the mousewheel and panning is
done by clicking and dragging.

**Update** - Day 2 added, below.

<iframe src="http://player.vimeo.com/video/66215209" width="600" height="400" frameborder="0">xxx</iframe> 
<p><a href="http://vimeo.com/66215209">Prototype time-based view for IceCube Live</a> from <a href="http://vimeo.com/eigenhombre">John Jacobsen</a> on <a href="http://vimeo.com">Vimeo</a>.</p>

## Day 2

Though a shorter work day today, I managed make some more progress on the prototype:

1. Fixed wrapping of runs from one time band to the next

2. Zoom in and out more or less from whatever run is currently at the cursor (only works well when number of bands doesn't change)

3. Allow panning in Y (whole time bands) as well as X direction

4. Code cleanup

The results are shown here:

<iframe src="http://player.vimeo.com/video/66295924" width="600" height="400" frameborder="0">xxx</iframe> 
<p><a href="http://vimeo.com/66295924">Day 2 - IceCube Live time-based run view prototype</a> from <a href="http://vimeo.com/eigenhombre">John Jacobsen</a> on <a href="http://vimeo.com">Vimeo</a>.</p>


