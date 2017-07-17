#########################################################################
# File Name :    run.sh
# Author :       unasm
# mail :         doujm@jiedaibao.com
# Last_Modified: 2017-07-14 01:38:05
#########################################################################
#!/bin/bash

mvn package -Pdev -DskipTests
#mvn package -Pdev 
java -cp target/ext-processor-sample-0.1.jar cn.sensorsdata.sample.Main
#java -cp target/ext-processor-sample-0.1.jar cn.sensorsdata.sample.SampleExtProcessor
