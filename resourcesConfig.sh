#!/usr/bin/env bash
# shellcheck disable=SC2034 # These variables are meant to be sourced and not used here

declare -i __resourcesConfig_sh

if [[ ${__resourcesConfig_sh:-0} -gt 0 ]]; then
  # do not include more than once
  exit
fi

# [Initialization]
declare -r __MODID='projecty'
declare -r __TEMPLATES="./src/main/templates/assets/${__MODID}"
declare -r __IN_BLOCKSTATES="${__TEMPLATES}/blockstates"
declare -r __IN_LANG="${__TEMPLATES}/lang"
declare -r __IN_MODELS_BLOCK="${__TEMPLATES}/models/block"
declare -r __IN_MODELS_ITEM="${__TEMPLATES}/models/item"
declare -r __IN_RECIPES="${__TEMPLATES}/recipes"
declare -r __IN_TEXTURES="${__TEMPLATES}/textures/block"
declare -r __RESOURCES="./src/main/resources/assets/${__MODID}"
declare -r __OUT_BLOCKSTATES="${__RESOURCES}/blockstates"
declare -r __OUT_LANG="${__RESOURCES}/lang"
declare -r __OUT_MODELS_BLOCK="${__RESOURCES}/models/block"
declare -r __OUT_MODELS_ITEM="${__RESOURCES}/models/item"
declare -r __OUT_TEXTURES_BLOCK="${__RESOURCES}/textures/blocks"
declare -r __OUT_RECIPES="${__RESOURCES}/recipes"
declare -r __LANG_FILE="${__OUT_LANG}/en_us.lang"
declare -r __BASE_NAME='xychronite'

# Flavor constants
declare -ra __FLAVORS=(
  'ore'
  'bricks'
  'block'
  'plate'
  'platform'
  'shield'
  'engineering_bricks'
  'lamp'
  'inverted_lamp'
  'crystal'
)
declare -ri __FLAVORS_COUNT="${#__FLAVORS[@]}"

declare -ra __FLAVORS_GAMMA_TWEAK=(
  '0.0'
  '0.0'
  '0.0'
  '0.0'
  '0.0'
  '0.0'
  '0.0'
  '0.0'
  '0.0'
  '0.55'
)

declare -ra __FLAVORS_MODEL=(
  'cube'
  'cube'
  'cube'
  'cube'
  'cube'
  'cube'
  'cube'
  'cube'
  'cube'
  'icosahedron'
)

declare -ra __FLAVORS_BLOCKSTATE=(
  'cube'
  'cube'
  'cube'
  'cube'
  'cube'
  'cube'
  'cube'
  'lamp'
  'lamp'
  'icosahedron'
)

# Color constants Setup
declare -ra __COLORS_NAME=(
  'white'
  'orange'
  'magenta'
  'light_blue'
  'yellow'
  'lime'
  'pink'
  'gray'
  'light_gray'
  'cyan'
  'purple'
  'blue'
  'brown'
  'green'
  'red'
  'black'
)
declare -ri __COLORS_COUNT="${#__COLORS_NAME[@]}"

declare -ra __COLORS_CONV=(
  'orange'
  'magenta'
  'light_blue'
  'yellow'
  'lime'
  'pink'
  'gray'
  'light_gray'
  'cyan'
  'purple'
  'brown'
)
declare -ri __COLORS_CONV_COUNT="${#__COLORS_CONV[@]}"

declare -ra __COLORS_BASE=(
  'red'
  'purple'
  'blue'
  'red'
  'green'
  'red'
  'black'
  'gray'
  'blue'
  'blue'
  'orange'
)

declare -ra __COLORS_MIXIN=(
  'yellow'
  'white'
  'white'
  'green'
  'white'
  'white'
  'white'
  'white'
  'green'
  'red'
  'black'
)

declare -ra __COLORS_RGB=(
  'rgb(255,255,255)'
  'rgb(100,50,0)'
  'rgb(100,0,100)'
  'rgb(0,0,100)'
  'rgb(100,100,0)'
  'rgb(0,100,0)'
  'rgb(100,0,0)'
  'rgb(0,0,0)'
  'rgb(0,0,0)'
  'rgb(50,100,100)'
  'rgb(100,0,100)'
  'rgb(0,0,100)'
  'rgb(150,75,0)'
  'rgb(0,100,0)'
  'rgb(100,0,0)'
  'rgb(0,0,0)'
)

declare -ra __COLORS_GAMMA=(
  '1.0'
  '1.0'
  '1.2'
  '1.2'
  '0.7'
  '1.2'
  '1.2'
  '0.4'
  '0.7'
  '1.2'
  '0.7'
  '0.7'
  '0.7'
  '0.7'
  '0.7'
  '0.2'
)

if [[ ${BASH_SOURCE[0]} != "${0}" ]]; then
  __resourcesConfig_sh=1
else
  (
    cat >&2 <<EOF
${BASH_SOURCE[0]} is containing global setings for resources generation.
It is not meant to be run stand-alone.
To load these settings into your script, simply do
source ${BASH_SOURCE[0]}
EOF
  )
  exit 1
fi
