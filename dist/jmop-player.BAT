@echo off
REM Runs JMOP player on DOS or Windows systems

java -Dlog4j2.configurationFile=log4j2.xml -jar jmop-player.jar %*

EXIT
