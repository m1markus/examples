#!/bin/bash

if [ "$1" == "" ]; then
    PORT=8080
else
    PORT="$1"
fi

## this is for python 2.7
python -m SimpleHTTPServer $PORT
