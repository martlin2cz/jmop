@echo off
REM Runs JMOP on DOS or Windows systems

REM Specify the root directory (or optionally override by command line param)
SET ROOT_DIR=%userprofile%\music\JMOP

REM do not touch following lines!
IF NOT EXIST "%ROOT_DIR%" mkdir "%ROOT_DIR%"
java -jar jmop-jwd.jar -dir "%ROOT_DIR%" %*

EXIT
