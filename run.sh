#!/bin/sh

if [ ! -d classes ]; then
    mkdir classes
fi
javac -cp ./controlP5.jar:./core.jar:. src/main/*.java -d ./classes
java -cp ./controlP5.jar:./core.jar:./classes main.Main
