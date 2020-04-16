#!/bin/sh
# set_env.sh

set -e

cmd="$@"

FILE_FINAL=/etc/nginx/conf.d/default.conf
FILE_TMP=$FILE_FINAL.tmp

echo 'do all sed commands'
sed -i -- "s|{{ environment }}|${PORTAL_ENVIRONMENT}|g" $FILE_TMP

echo 'set final file from tmp'
mv $FILE_TMP $FILE_FINAL

echo ${cmd}
echo 'starting nginx'
exec ${cmd}
