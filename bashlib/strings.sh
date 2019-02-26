#!/usr/bin/env bash

##### Strings utility library #####

# Capitalize the first letter of the string argument
# @params
# 1: The string to Capitalize
# @return
# &1: The capitalized string
# ?: >0 error
function strings::capitalize() {
  # The argument is required
  [[ ${#} -eq 1 ]] || return 1
  # Upper-case first letter
  echo -n "${1:0:1}" | tr '[:lower:]' '[:upper:]'
  # Keep remaining of string unchanged
  echo -n "${1:1}"
}

# Removes underscores from the string and then capitalizes all words
# @params
# 1: The snake_string to Capitalize
# @return
# &1: The Capitalized String
# ?: >0 error
function strings::capitalize_snake() {
  # The argument is required
  [[ ${#} -eq 1 ]] || return 1

  local complete=''
  local part
  # iterates _ separated words
  for part in ${1/_/ }; do
    # only add spaces between words
    if [ ! -z "${complete}" ]; then
      complete+=" "
    fi

    # build color display string by concatenating capitalized words
    complete+="$(strings::capitalize "${part}")"
  done

  echo -n "${complete}"
}

if [[ ${BASH_SOURCE[0]} == "${0}" ]]; then
  (
    declare functions
    functions=$(declare -F)
    cat >&2 <<EOF
${BASH_SOURCE[0]} is an utility library.
It needs to be sourced with:
. ${BASH_SOURCE[0]}
or
source ${BASH_SOURCE[0]}

Available functions:
====================
${functions//declare -f /}
EOF
  )
  exit 1
elif [ -z "${__strings_sh}" ]; then
  declare __strings_sh=1
else
  echo >&2 "${BASH_SOURCE[0]} sourced more than once!"
  exit 1
fi
