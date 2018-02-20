#!/bin/bash

if grep -q "SNAPSHOT" version.sbt
then
    echo "Snapshot Release just local"
else
    sbt -Dbintray.user=$BINTRAY_USER -Dbintray.pass=$BINTRAY_PASSWORD publish
fi