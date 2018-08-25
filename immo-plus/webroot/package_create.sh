#!/bin/bash

echo "creating package..."

TAR_NAME=iq_homepage.tar.gz

tar cvfz $TAR_NAME img/vcard_mrs_back.png       \
                   img/vcard_mrs_front.png      \
                   img/photo_mrs_profil.jpg     \
                   img/immoQuickLogo.png        \
                   img/piktogrammehaus.png      \
                   img/gimp-hurry-1920x1278.png \
                   google783f5c3e55fb8f7c.html  \
                   vcard_mrs.html               \
                   iq_sandra_mueller_vcard.vcf  \
                   cdown.html                   \
                   iq2.html                     \
                   *.ttf                        \
                   favicon.png 

scp $TAR_NAME axmue@m1m.ch:/tmp/
