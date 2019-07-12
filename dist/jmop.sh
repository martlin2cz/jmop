#!/bin/bash
# Runs JMOP on unix-like systems

# Specify the root directory (or optionally override by command line param)
ROOT_DIR=~/music/JMOP

# do not touch following lines!
mkdir -p "$ROOT_DIR"
java -jar jmop-jwd.jar -dir "$ROOT_DIR" $@

exit 0
