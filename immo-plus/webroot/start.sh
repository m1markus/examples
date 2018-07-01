#!/bin/bash

if [ "$1" == "" ]; then
    PORT=8080
else
    PORT="$1"
fi

#python http.py 127.0.0.1:9000

python http.py $PORT

## this is for python 2.7
#python -m SimpleHTTPServer $PORT
