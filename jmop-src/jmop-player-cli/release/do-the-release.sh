#!/bin/bash
# prepares the release archive
# run from the jmop-player-cli module folder

# the version
VERSION="3.0.0-alpha"

# the dir name
RELEASE_DIR=release/jmop

# the zip file name
RELEASE_ZIP=release/jmop-$VERSION.zip

###########################################################

# clean the previous
echo "Cleaning ..."
rm -r $RELEASE_DIR $RELEASE_ZIP

# prepare the release dir
echo "Preparing ..."
mkdir $RELEASE_DIR

# build
echo "Building ..."
mvn clean package -DskipTests
#mvn package

# copy the binaries to release dir
echo "Copying the binaries ..."
cp    target/jmop-player-cli-*.jar  $RELEASE_DIR/jmop-cli.jar
cp -r target/libs/                  $RELEASE_DIR
cp    release/jmopcli.sh            $RELEASE_DIR
cp    release/jmopcli.BAT           $RELEASE_DIR

# zip it up
echo "Packing it up ..."
zip -r $RELEASE_ZIP $RELEASE_DIR

echo "JMOP $VERSION done! See the $RELEASE_ZIP file"


