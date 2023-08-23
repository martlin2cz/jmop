#!/bin/bash

# build

#SKIP_TESTS="-DskipTests"
SKIP_TESTS=""

cd jmop-src/
mvn clean install $SKIP_TESTS

# preparing the jmop-player
cd jmop-player-cli/
mvn compile assembly:single $SKIP_TESTS

cp --verbose target/jmop-player-cli-*-jar-with-dependencies.jar ../../dist/jmop-player.jar
cd ../

# preparing te jmop-sourcery
cd jmop-sourcery-app/
mvn compile assembly:single $SKIP_TESTS

cp --verbose target/jmop-sourcery-app-*-jar-with-dependencies.jar ../../dist/jmop-sourcery.jar
cd ../
cd ../

# just package the README along
cp --verbose README.md dist/

# done?
echo ok?

