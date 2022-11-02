@echo off
title kayakedu-marketing-service!
echo Welcome to kayakedu scripting.

IF "%1"=="start" (
    ECHO start kayakedu-marketing-service
    start "kayakedu-marketing-service" java -Dspring.config.location="D:/kayakedu-marketing-service/application.yaml" -jar kayakedu-marketing-services.jar
) ELSE IF "%1"=="stop" (
    ECHO stop kayakedu-marketing-service
    TASKKILL /FI "WINDOWTITLE eq kayakedu-marketing-service"
) ELSE (
    ECHO please, use "run.bat start" or "run.bat stop"
)
exit