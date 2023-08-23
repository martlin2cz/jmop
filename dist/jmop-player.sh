#!/bin/bash
# Runs JMOP player on unix-like systems

java -Dlog4j2.configurationFile=log4j2.xml -jar jmop-player.jar $@

exit $?
