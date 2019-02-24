#!/bin/env bash
# Purpose: Combine the overlays and colored noise into flattened images

# [Initialization]
declare __IN_TEXTURES='./textures/baseline'
declare __OUT_TEXTURES='./textures/blocks'
declare __OUT_BLOCKSTATES='./blockstates'
declare __OUT_MODELS_BLOCK='./models/block'
declare __OUT_MODELS_ITEM='./models/item'
declare __OUT_LANG='./lang'
declare __MODID='projecty'
declare __BASE_NAME='xychronite'

# Color Variable Setup

declare -a __FLAVORS=(
  'ore'
  'bricks'
  'block'
  'plate'
  'platform'
  'shield'
)

declare -a __COLORS_NAME=(
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

declare -i __COLORS_COUNT="${#__COLORS_NAME[@]}"

declare -a __COLORS_RGB=(
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

declare -a __COLORS_GAMMA=(
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

# Initialize language file
declare __LANG_FILE="${__OUT_LANG}/en_us.lang"
mkdir -p "$(dirname "${__LANG_FILE}")"
echo >"${__LANG_FILE}" "itemGroup.projecty=ProjectY"

declare -i __color_id
declare __flavor
for ((__color_id = 0; __color_id < __COLORS_COUNT; __color_id += 1)); do
  for __flavor in "${__FLAVORS[@]}"; do
    declare __color="${__COLORS_NAME[${__color_id}]}"
    declare __rgb="${__COLORS_RGB[${__color_id}]}"
    declare __gamma="${__COLORS_GAMMA[${__color_id}]}"

    # [Texture Generation]
    # NB: Minecraft doesn't handle grayscale well, make sure the images are in RGB.
    declare __noise_file="${__IN_TEXTURES}/noise.png"
    declare __overlay_file="${__IN_TEXTURES}/overlay_${__flavor}.png"
    declare __texture_file="${__OUT_TEXTURES}/${__flavor}_${__color}.png"
    mkdir -p "$(dirname "${__texture_file}")"
    convert "${__noise_file}" \
      -fill "${__rgb}" \
      -tint 100 \
      -gamma "${__gamma}" \
      "${__overlay_file}" \
      -composite \
      -define png:color-type=2 \
      -format png "${__texture_file}"

    declare __NAME="${__color}_${__BASE_NAME}_${__flavor}"

    # [Block State Generation]
    declare __blockstate_file="${__OUT_BLOCKSTATES}/${__NAME}.json"
    mkdir -p "$(dirname "${__blockstate_file}")"
    declare __model_ref="${__MODID}:${__NAME}"
    cat >"${__blockstate_file}" <<EOF
{
  "variants": {
    "normal": {
      "model": "${__model_ref}"
    }
  }
}
EOF

    # [Model Generation]
    declare __block_model_file="${__OUT_MODELS_BLOCK}/${__NAME}.json"
    mkdir -p "$(dirname "${__block_model_file}")"
    declare __texture_ref="${__MODID}:blocks/${__flavor}_${__color}"
    cat >"${__block_model_file}" <<EOF
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "${__texture_ref}"
  }
}
EOF

    declare __item_model_file="${__OUT_MODELS_ITEM}/${__NAME}.json"
    mkdir -p "$(dirname "${__item_model_file}")"
    declare __parent_ref="${__MODID}:block/${__NAME}"
    cat >"${__item_model_file}" <<EOF
{
  "parent": "${__parent_ref}"
}
EOF

    # [Language Generation]
    declare lang_key="${__MODID}.${__NAME}.name"
    declare lang_value
    lang_value="$(
      # Upper-case first letter of __color
      echo -n "${__color:0:1}" | tr '[:lower:]' '[:upper:]'
      # Keep remaining of __color unchanged
      echo -n "${__color:1}"
    ) ${__BASE_NAME} ${__flavor}"
    echo >>"${__LANG_FILE}" "${lang_key}=${lang_value}"
  done
done

# TODO: Recipes, items, etc
