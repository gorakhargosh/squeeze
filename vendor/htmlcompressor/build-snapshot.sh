#!/bin/sh
mvn clean package javadoc:jar javadoc:test-jar source:jar source:test-jar site:jar assembly:single deploy