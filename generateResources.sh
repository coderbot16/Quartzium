#!/usr/bin/env bash
# Purpose: Combine the overlays and colored noise into flattened images
# TODO: Recipes, items, etc

source './bashlib/strings.sh' # The strings utility library

##### Global constants

source './resourcesConfig.sh' # Configuration of the generated resources

function main() {

  # Prepare the resources directories
  create_resources_directories

  # Initialize language file
  generate_language

  local -i color_index
  local flavor
  local rgb
  local gamma
  for ((color_index = 0; color_index < __COLORS_COUNT; color_index += 1)); do
    for flavor in "${__FLAVORS[@]}"; do

      # Generate texture
      generate_texture "${color_index}" "${flavor}" "0.0" || exit 1

      # Generate blockstate
      generate_blockstate "${color_index}" "${flavor}" || exit 1

      # Generate models
      generate_models "${color_index}" "${flavor}" || exit 1

      # [Language Generation]
      generate_display_name "${color_index}" "${flavor}" || exit 1
    done

    # << Crystals are a special case, and are generated separately. >>

    # Generate texture
    generate_texture "${color_index}" "crystal" "0.0" || exit 1

    # Generate blockstate
    generate_crystal_blockstate "${color_index}" || exit 1

    # Generate models
    generate_crystal_models "${color_index}" || exit 1

    # [Language Generation]
    generate_display_name "${color_index}" "crystal" || exit 1

    # << Generate Lamp >>
    generate_texture "${color_index}" "inverted_lamp" "0.0" || exit 1
    generate_texture "${color_index}" "lamp" "0.55" || exit 1

    # Generate blockstate
    generate_lamp_blockstate "${color_index}" "lamp" "inverted_lamp" "lamp" || exit 1
    generate_lamp_blockstate "${color_index}" "inverted_lamp" "lamp" "inverted_lamp" || exit 1

    # Generate models
    generate_models "${color_index}" "inverted_lamp" || exit 1
    generate_models "${color_index}" "lamp" || exit 1

    # [Language Generation]
    generate_display_name "${color_index}" "lamp" || exit 1
    generate_display_name "${color_index}" "inverted_lamp" || exit 1
  done
}

# Creates the resources directories if they don't exist
# @globals
# __OUT_TEXTURES
# __OUT_BLOCKSTATES
# __OUT_MODELS_BLOCK
# __OUT_MODELS_ITEM
# __OUT_LANG
# @product
# Directories created if missing

function create_resources_directories() {
  local directory
  for directory in \
    "${__OUT_TEXTURES}" \
    "${__OUT_BLOCKSTATES}" \
    "${__OUT_MODELS_BLOCK}" \
    "${__OUT_MODELS_ITEM}" \
    "${__OUT_LANG}"; do
    mkdir -p "${directory}"
  done
}

# Generate the en_us language file
# @globals
# __LANG_FILE: The language file path
# __MODID: The mod id
# @product
# An initialized en_us language file
function generate_language() {
  # NB: $(strings::capitalize "${__MODID}") doesn't handle camel case
  echo >"${__LANG_FILE}" "itemGroup.${__MODID}=ProjectY"
}

# Generates a texture, by compositing a colored noise with a flavor overlay
# @globals
# __COLORS_GAMMA: The Gamma adjustement per color array
# __COLORS_NAME: The colors name array
# __COLORS_RGB: The RGB definition per color array
# __IN_TEXTURES: The path of the noise and flavors overlay
# __OUT_TEXTURES: the output path of textures
# @params
# 1: color_index: The color index
# 2: flavor: The flavor of block
# 3: gamma_tweak: A value to subtract from the set gamma value (used for lamp textures)
# @product
# Creates a texture file in __OUT_TEXTURES
# @return
# ?: >0: on failure
function generate_texture() {
  # Both parameters are required
  [[ ${#} -eq 3 ]] || return 1
  local -ri color_index="${1}"
  local -r flavor="${2}"
  local -r gamma_tweak="${3:-0.0}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r rgb="${__COLORS_RGB[${color_index}]}"
  local -r gamma_base="${__COLORS_GAMMA[${color_index}]}"
  local -r noise_file="${__IN_TEXTURES}/noise.png"
  local -r overlay_file="${__IN_TEXTURES}/overlay_${flavor}.png"
  local -r texture_file="${__OUT_TEXTURES}/${flavor}_${color_name}.png"

  local gamma
  gamma="$(bc <<<"a=${gamma_base}; b=${gamma_tweak}; if(a > b) a-b else 0.2")"

  # NB: Minecraft doesn't handle grayscale well, make sure the images are in RGB.
  convert \
    "${noise_file}" \
    -fill "${rgb}" \
    -tint 100 \
    -gamma "${gamma}" \
    "${overlay_file}" \
    -composite \
    -define png:color-type=2 \
    -format png \
    "${texture_file}"
}

# Generates a blockstate json file for a given color index and flavor
# @globals
# __BASE_NAME: The registry base name
# __COLORS_NAME: The colors name array
# __MODID: The mod id
# __OUT_BLOCKSTATES: The path of the blockstates
# @params
# 1: color_index: The color index
# 2: flavor: The flavor of block/item
# @product
# Creates a blockstate json file in __OUT_BLOCKSTATES
# @return
# ?: >0: on failure
function generate_blockstate() {
  # Both parameters are required
  [[ ${#} -eq 2 ]] || return 1

  local -ri color_index="${1}"
  local -r flavor="${2}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_${flavor}"
  local -r blockstate_file="${__OUT_BLOCKSTATES}/${registry_name}.json"
  local -r model_ref="${__MODID}:${registry_name}"

  # [Block State Generation]
  cat >"${blockstate_file}" <<EOF
{
"variants": {
  "normal": {
    "model": "${model_ref}"
    }
  }
}
EOF
}

# Generates a crystal blockstate json file for a given color index
# @globals
# __BASE_NAME: The registry base name
# __COLORS_NAME: The colors name array
# __MODID: The mod id
# __OUT_BLOCKSTATES: The path of the blockstates
# @params
# 1: color_index: The color index
# @product
# Creates a blockstate json file in __OUT_BLOCKSTATES
# @return
# ?: >0: on failure
function generate_crystal_blockstate() {
  # The parameter is required
  [[ ${#} -eq 1 ]] || return 1

  local -ri color_index="${1}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_crystal"
  local -r blockstate_file="${__OUT_BLOCKSTATES}/${registry_name}.json"
  local -r model_ref="${__MODID}:${registry_name}.obj"

  # [Block State Generation]
  cat >"${blockstate_file}" <<EOF
{
	"forge_marker": 1,
	"variants": {
		"facing=up": {
			"model": "${model_ref}"
		},
		"facing=down": {
			"model": "${model_ref}",
			"x": 180
		},
		"facing=east": {
			"model": "${model_ref}",
			"x": 90,
			"y": 90
		},
		"facing=west": {
			"model": "${model_ref}",
			"x": 90,
			"y": 270
		},
		"facing=south": {
			"model": "${model_ref}",
			"x": 90,
			"y": 180
		},
		"facing=north": {
			"model": "${model_ref}",
			"x": 90
		},
		"inventory": {
			"model": "${model_ref}",
			"transform": {
				"gui": {
					"translation": [0, 0.297483, 0],
					"rotation": [30, 225, 0],
					"scale": [1, 1, 1]
				},
				"ground": {
					"translation": [0, 0.297483, 0],
					"rotation": [0, 0, 0],
					"scale": [0.5, 0.5, 0.5]
				},
				"fixed": {
					"translation": [0, 0.297483, 0],
					"rotation": [0, 0, 0],
					"scale": [1, 1, 1]
				},
				"thirdperson_righthand": {
					"translation": [0, 0.15, 0],
					"rotation": [0, 45, 0],
					"scale": [0.625, 0.625, 0.625]
				},
				"thirdperson_lefthand":	{
					"translation": [0, 0.15, 0],
					"rotation": [0, 225, 0],
					"scale": [0.625, 0.625, 0.625]
				},
				"firstperson_righthand": {
					"translation": [0, 0.15, 0],
					"rotation": [0, 45, 0],
					"scale": [0.6, 0.6, 0.6]
				},
				"firstperson_lefthand": {
					"translation": [0, 0.15, 0],
					"rotation": [0, 225, 0],
					"scale": [0.6, 0.6, 0.6]
				}
			}
		}
	}
}
EOF
}

# Generates a lamp blockstate json file for a given color index
# @globals
# __BASE_NAME: The registry base name
# __COLORS_NAME: The colors name array
# __MODID: The mod id
# __OUT_BLOCKSTATES: The path of the blockstates
# @params
# 1: color_index: The color index
# 2: flavor: The flavor of block/item
# 3: lit: Name of lit model
# 4: unlit: Name of unlit model
# @product
# Creates a blockstate json file in __OUT_BLOCKSTATES
# @return
# ?: >0: on failure
function generate_lamp_blockstate() {
  # All parameters are required
  [[ ${#} -eq 4 ]] || return 1

  local -ri color_index="${1}"
  local -r flavor="${2}"
  local -r lit="${3}"
  local -r unlit="${4}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_${flavor}"

  local -r blockstate_file="${__OUT_BLOCKSTATES}/${registry_name}.json"

  local -r model_lit_ref="${__MODID}:${color_name}_${__BASE_NAME}_${lit}"
  local -r model_ref="${__MODID}:${color_name}_${__BASE_NAME}_${unlit}"

  # [Block State Generation]
  cat >"${blockstate_file}" <<EOF
{
  "variants": {
    "lit=true": {
      "model": "${model_lit_ref}"
    },
    "lit=false": {
      "model": "${model_ref}"
    }
  }
}
EOF
}

# Generates block and item model json files for a given color index and flavor
# @globals
# __BASE_NAME: The registry base name
# __COLORS_NAME: The colors name array
# __MODID: The mod id
# __OUT_MODELS_BLOCK: The path of the block models
# __OUT_MODELS_ITEM: The path of the item models
# @params
# 1: color_index: The color index
# 2: flavor: The flavor of block/item
# @product
# Creates a block json model file in __OUT_MODELS_BLOCK
# Creates an item json model file in __OUT_MODELS_ITEM
# @return
# ?: >0: on failurefunction generate_models() {
function generate_models() {
  # Both parameters are required
  [[ ${#} -eq 2 ]] || return 1
  local -ri color_index="${1}"
  local -r flavor="${2}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_${flavor}"

  local -r block_model_file="${__OUT_MODELS_BLOCK}/${registry_name}.json"
  local -r texture_ref="${__MODID}:blocks/${flavor}_${color_name}"
  cat >"${block_model_file}" <<EOF
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "${texture_ref}"
  }
}
EOF

  local -r item_model_file="${__OUT_MODELS_ITEM}/${registry_name}.json"
  local -r parent_ref="${__MODID}:block/${registry_name}"
  cat >"${item_model_file}" <<EOF
{
  "parent": "${parent_ref}"
}
EOF
}

# Generates crystal block and item model json files for a given color index
# @globals
# __BASE_NAME: The registry base name
# __COLORS_NAME: The colors name array
# __MODID: The mod id
# __OUT_MODELS_BLOCK: The path of the block models
# __OUT_MODELS_ITEM: The path of the item models
# @params
# 1: color_index: The color index
# @product
# Creates a block json model file in __OUT_MODELS_BLOCK
# Creates an item json model file in __OUT_MODELS_ITEM
# @return
# ?: >0: on failurefunction generate_models() {
function generate_crystal_models() {
  # The parameter is required
  [[ ${#} -eq 1 ]] || return 1
  local -ri color_index="${1}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_crystal"

  local -r block_model_file="${__OUT_MODELS_BLOCK}/${registry_name}.obj"
  local -r block_material_file="${__OUT_MODELS_BLOCK}/${registry_name}.mtl"
  local -r texture_ref="${__MODID}:blocks/crystal_${color_name}"

  local obj_model obj_material
  obj_model="$(cat "${__IN_MODELS_BLOCK}/icosahedron.obj")"
  echo >"${block_model_file}" "${obj_model/mtllib icosahedron.mtl/mtllib ${registry_name}.mtl}"
  obj_material="$(cat "${__IN_MODELS_BLOCK}/icosahedron.mtl")"
  echo >"${block_material_file}" "${obj_material/noise.png/${texture_ref}}"

  local -r item_model_file="${__OUT_MODELS_ITEM}/${registry_name}.json"
  cat >"${item_model_file}" <<EOF
{
  "forge_marker": 1
}
EOF
}

# Generates display-name entry into the language file
# for the given color index and flavor
# @globals
# __BASE_NAME: The registry base name
# __COLORS_NAME: The colors name array
# __LANG_FILE: The language file path
# __MODID: The mod id
# @params
# 1: color_index: The color index
# 2: flavor: The flavor of block/item
# @product
# adds a registry-key=display-name entry into the language file
# @return
# ?: >0: on failurefunction generate_models() {
function generate_display_name() {
  # Both parameters are required
  [[ ${#} -eq 2 ]] || return 1
  local -ri color_index="${1}"
  local -r flavor="${2}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_${flavor}"

  local -r lang_key="tile.${__MODID}.${registry_name}.name"
  local -r lang_value="$(strings::capitalize_snake "${color_name}") $(strings::capitalize "${__BASE_NAME}") $(strings::capitalize_snake "${flavor}")"

  echo >>"${__LANG_FILE}" "${lang_key}=${lang_value}"
}

##### Script start #####

main "$@" # Run self
