#!/usr/bin/env bash
# Cleanup resources

source ./resourcesConfig.sh

function main() {
  local directory
  for directory in \
    "blockstates" \
    "lang" \
    "models" \
    "recipes" \
    "textures"; do

    find "${__RESOURCES}/${directory}" -delete 2>/dev/null
  done
}

##### Script start #####

main "${@}" # Run self
