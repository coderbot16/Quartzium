#!/usr/bin/env bash
# Purpose: Combine the overlays and colored noise into flattened images

# [Initialization]
declare __IN_TEXTURES='./textures/baseline'
declare __OUT_TEXTURES='./textures/blocks'
declare __OUT_BLOCKSTATES='./blockstates'
declare __IN_MODELS='./models/baseline'
declare __OUT_MODELS_BLOCK='./models/block'
declare __OUT_MODELS_ITEM='./models/item'
declare __OUT_LANG='./lang'
declare __MODID='projecty'
declare __BASE_NAME='xychronite'

# Color Variable Setup

declare -a __FLAVORS_NAME=(
  'ore'
  'bricks'
  'block'
  'plate'
  'platform'
  'shield'
  'crystal'
)

declare -a __FLAVORS_CRYSTAL=(
  'false'
  'false'
  'false'
  'false'
  'false'
  'false'
  'true'
)

declare -i __FLAVORS_COUNT="${#__FLAVORS_NAME[@]}"

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
  for ((__flavor_id = 0; __flavor_id < __FLAVORS_COUNT; __flavor_id += 1)); do
    declare __flavor="${__FLAVORS_NAME[${__flavor_id}]}"
    declare __color="${__COLORS_NAME[${__color_id}]}"
    declare __rgb="${__COLORS_RGB[${__color_id}]}"
    declare __gamma="${__COLORS_GAMMA[${__color_id}]}"
    declare __crystal="${__FLAVORS_CRYSTAL[${__flavor_id}]}"

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
    if [[ "${__crystal}" != "true" ]]
    then
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
    else
      declare __model_ref="${__MODID}:${__NAME}.obj"
      cat >"${__blockstate_file}" <<EOF
{
	"variants": {
		"facing=up": {
			"model": "${__model_ref}"
		},
		"facing=down": {
			"model": "${__model_ref}"
		},
		"facing=east": {
			"model": "${__model_ref}"
		},
		"facing=west": {
			"model": "${__model_ref}"
		},
		"facing=south": {
			"model": "${__model_ref}"
		},
		"facing=north": {
			"model": "${__model_ref}"
		}
	}
}
EOF
    fi

    # [Model Generation]
    if [[ "${__crystal}" != "true" ]]
    then
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
    else
      declare __block_model_file="${__OUT_MODELS_BLOCK}/${__NAME}.obj"
      declare __block_material_file="${__OUT_MODELS_BLOCK}/${__NAME}.mtl"
      declare __texture_ref="${__MODID}\:blocks\/${__flavor}_${__color}"
      mkdir -p "$(dirname "${__block_model_file}")"
      sed "s/mtllib icosahedron\.mtl/mtllib ${__NAME}\.mtl/" < "${__IN_MODELS}/icosahedron.obj" > ${__block_model_file}
      sed "s/noise\.png/${__texture_ref}/" < "${__IN_MODELS}/icosahedron.mtl" > ${__block_material_file}

      declare __item_model_file="${__OUT_MODELS_ITEM}/${__NAME}.obj"
      declare __item_material_file="${__OUT_MODELS_ITEM}/${__NAME}.mtl"
      mkdir -p "$(dirname "${__item_model_file}")"
      sed "s/mtllib icosahedron\.mtl/mtllib ${__NAME}\.mtl/" < "${__IN_MODELS}/icosahedron_inventory.obj" > ${__item_model_file}
      cp ${__block_material_file} ${__item_material_file}
    fi

    # [Language Generation]
    declare lang_key="tile.${__MODID}.${__NAME}.name"
    declare lang_value_raw="$(echo -n ${__color} | sed "s/_/ /") ${__BASE_NAME} ${__flavor}"
    declare lang_value=$(echo -n $lang_value_raw | tr [A-Z] [a-z] | sed -e 's/^./\U&/g; s/ ./\U&/g')
    echo >>"${__LANG_FILE}" "${lang_key}=${lang_value}"
  done
done

# TODO: Recipes
