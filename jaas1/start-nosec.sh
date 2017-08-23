#!/bin/bash

echo "hello"

java -cp ./target/jaas1-1.0-SNAPSHOT.jar        \
 -Djava.security.auth.login.config=jaas.config  \
 ch.m1m.examples.SampleAcn
