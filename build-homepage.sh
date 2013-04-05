#!/bin/sh

set -e

if [ "x$YUBICO_GITHUB_REPO" = "x" ]; then
  echo "please set YUBICO_GITHUB_REPO to the checked out location of the webpage repo"
  exit 1
elif [ ! -d $YUBICO_GITHUB_REPO/yubico-j ]; then
  echo "$YUBICO_GITHUB_REPO/yubico-j does not exist."
  exit 1
fi

asciidoc -s -o README.html README
cat $YUBICO_GITHUB_REPO/yubico-j/index.html.in README.html > $YUBICO_GITHUB_REPO/yubico-j/index.html
echo "</div></body></html>" >> $YUBICO_GITHUB_REPO/yubico-j/index.html
rm -f README.html

mvn javadoc:javadoc
rsync -a target/site/apidocs/ $YUBICO_GITHUB_REPO/yubico-j/apidocs/

cd $YUBICO_GITHUB_REPO/yubico-j
git add index.html
git add apidocs
git commit -m "updated javadocs and index.html"

echo "Webpage updated but not pushed."
exit 0
