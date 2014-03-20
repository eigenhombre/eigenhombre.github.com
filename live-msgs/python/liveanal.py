#!/usr/bin/env python

import os
import toolz
import json

toolz.frequencies


def messages_from_file(fname):
    with file(fname) as ff:
        return json.loads(ff.read())

def gen():
    for fname in os.listdir("../resources"):
        if not fname.startswith("I3Live"):
            continue
        for m in messages_from_file("../resources/" + fname):
            yield m["service"]


print toolz.frequencies(gen())

print "OK"
