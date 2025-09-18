#!/bin/bash

set -e

mvn clean package -Dprotoc.skip=true -Dmaven.test.skip=true -U
cd web/target
if [[ -f ~/script/aliyun/207-scp-aliyun.sh ]];then
  sh ~/script/aliyun/207-scp-aliyun.sh htdong.jar
else
  zip htdong.zip htdong.jar
  curl -F 'file=@htdong.zip' -H "uploadTmpToken: ${UPLOAD_TMP_TOKEN}" http://${ALIYUN_HOST}:9961/htdong/upload/tmp
fi

