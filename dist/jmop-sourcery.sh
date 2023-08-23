#!/bin/bash
# Runs JMOP sourcery on unix-like systems

java -Dlog4j2.configurationFile=log4j2.xml -jar jmop-sourcery.jar $@

exit $?
