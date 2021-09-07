#!/bin/bash

mvn clean package
cp /Users/rick/yodean/workspace2/ac/target/ac-0.0.1-SNAPSHOT.jar /Users/rick/Documents/
cd /Users/rick/Documents/
java -jar ac-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev