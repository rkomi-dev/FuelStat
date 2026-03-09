#!/bin/bash

javac -d bin -cp "lib/*:src" src/it/unipv/posw/Main.java
java -cp "bin:lib/*" it.unipv.posw.Main
