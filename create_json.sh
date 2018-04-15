#!/usr/bin/env bash

osmfilter /tmp/Dortmund_nodes.osm --keep="$1" --drop="$2" --ignore-dependencies --drop-relations | \
 osmconvert --csv="@lon @lat name" - | \
 sort -k3 | uniq -f2 | \
 sed -e "s/\"/\\\\\"/g" \
     -e "s/\([^\t]*\)\t\([^\t]*\)\t\(.*\)/\{\"lon\":\1,\"lat\":\2,\"name\":\"\3\"\}/g" | \
 jq '[.]' | jq -s -c 'add' | sed -e "s/^/{ \"words\" :/" -e "s/\$/}/"
