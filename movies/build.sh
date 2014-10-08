#!/bin/bash

readonly JAR_NAME='movies-pojo'
readonly PATH_JBOSS_MODULE=${PATH_JBOSS_MODULE:-'../jdv-6.0.0.git/modules/system/layers/base/org/jdg/6.2/'}

mvn -o clean install
cp target/${JAR_NAME}*.jar "${PATH_JBOSS_MODULE}"
