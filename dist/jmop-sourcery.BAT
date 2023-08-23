@echo off
REM Runs JMOP sourcery on DOS or Windows systems

java -Dlog4j2.configurationFile=log4j2.xml -jar jmop-sourcery.jar %*

EXIT
