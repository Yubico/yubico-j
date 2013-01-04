#!/bin/sh

set -e

STAGING_DIR=`mktemp -d '/tmp/yubico-j-doc.XXXXXX'`
asciidoc -s -o $STAGING_DIR/README.html README

mvn javadoc:javadoc
cp -r target/site/apidocs $STAGING_DIR/apidocs

git checkout gh-pages
cat index.html.in $STAGING_DIR/README.html > index.html
echo "</div></body></html>" >> index.html

rm -rf apidocs
cp -r $STAGING_DIR/apidocs apidocs
rm -rf $STAGING_DIR

git add index.html
git commit -m "updated page with new README"
git add apidocs
git commit -m "updated javadocs"
git checkout master
