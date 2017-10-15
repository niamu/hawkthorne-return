#!/bin/sh
#
# Build the Docker image
#

set -e

# Determine current git branch name
if [ -z "$CI_BUILD_REF_NAME" ]; then
    CI_BUILD_REF_NAME=$(git branch | grep "*" | cut -d " " -f 2)
fi

DOCKER_IMAGE=hawkthorne
VERSION=$(cat project.clj | grep "defproject" | sed -e "s/.*\"\(.*\)\"/\1/")
TAG=$CI_BUILD_REF_NAME-$VERSION

# Build the Docker image with the compiled Uberjar
docker build -t $DOCKER_IMAGE:$TAG -f Dockerfile .
docker tag $DOCKER_IMAGE:$TAG $DOCKER_IMAGE:latest
