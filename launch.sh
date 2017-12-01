#!/usr/bin/bash

curdir="$(dirname $0)"
LD_LIBRARY_PATH=$curdir/jni java -jar predator-main-1.0-SNAPSHOT.jar
