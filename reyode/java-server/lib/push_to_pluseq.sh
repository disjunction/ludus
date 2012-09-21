#!/bin/sh
TARGET_PATH=root@indeco.com.ua:/var/lib/tomcat5/webapps/reyad/WEB-INF/lib/
scp tay.jar $TARGET_PATH
scp reyad.jar $TARGET_PATH
scp quizzer.jar $TARGET_PATH
scp quizzer.reyad.jar $TARGET_PATH
