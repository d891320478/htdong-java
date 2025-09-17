#!/bin/bash

set -e

mvn clean package -Dprotoc.skip=true -Dmaven.test.skip=true -U
cd web/target
zip htdong.zip htdong.jar
curl -F 'file=@htdong.zip' -H "uploadTmpToken: ${UPLOAD_TMP_TOKEN}" http://${ALIYUN_HOST}:9961/htdong/upload/tmp

