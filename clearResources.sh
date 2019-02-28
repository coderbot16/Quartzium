#!/usr/bin/env bash

source ./resourcesConfig.sh

find "${__RESOURCES}/blockstates" -delete 2>/dev/null
find "${__RESOURCES}/lang" -delete 2>/dev/null
find "${__RESOURCES}/models/block" -delete 2>/dev/null
find "${__RESOURCES}/models/item" -delete 2>/dev/null
find "${__RESOURCES}/recipes" -delete 2>/dev/null
find "${__RESOURCES}/textures/blocks" -delete 2>/dev/null
