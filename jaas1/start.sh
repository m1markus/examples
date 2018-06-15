#!/bin/bash

echo "hello"


##     policiy file....
##
##     permission java.security.AllPermission;
##

java -cp ./target/jaas1-1.0-SNAPSHOT.jar        \
 -Djava.security.manager                        \
 -Djava.security.policy==sampleacn.policy        \
 -Djava.security.auth.login.config=jaas.config  \
 ch.m1m.examples.SampleAcn
