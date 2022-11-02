@echo off
title kayakedu-marketing-service!
echo Welcome to kayakedu scripting.

IF "%1"=="start" (
    ECHO start kayakedu-marketing-service
    start "kayakedu-marketing-service" java -Dspring.config.location="application.yaml" -jar kayakedu-marketing-services.jar
	exit
) ELSE IF "%1"=="stop" (
    ECHO stop kayakedu-marketing-service
    TASKKILL /FI "WINDOWTITLE eq kayakedu-marketing-service"
	exit
) ELSE (
    ECHO please, use "run.bat start" or "run.bat stop"
	pause
)
