@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk-25.0.4.1"
echo JAVA_HOME=%JAVA_HOME%
"%JAVA_HOME%\bin\javac.exe" -version
call mvn.cmd test %*
