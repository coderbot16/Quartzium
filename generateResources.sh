#!/usr/bin/env bash
# Purpose: Combine the overlays and colored noise into flattened images
# TODO: Recipes, items, etc
source './bashlib/strings.sh' # The strings utility library

##### Global constants

source './resourcesConfig.sh' # Configuration for the generated resources

function main() {

  # Prepare the resources directories
  create_resources_directories

  # Initialize language file
  generate_language

  # Generate colorized noise
  generate_base_textures

  # Copy overlay textures
  copy_overlay_textures

  local -i color_index
  local -i flavor_index
  local flavor flavor_gamma_tweak flavor_model flavor_blockstate
  local rgb
  local gamma
  for ((color_index = 0; color_index < __COLORS_COUNT; color_index += 1)); do
    for ((flavor_index = 0; flavor_index < __FLAVORS_COUNT; flavor_index += 1)); do

      flavor="${__FLAVORS[flavor_index]}"
      flavor_model="${__FLAVORS_MODEL[flavor_index]}"
      flavor_blockstate="${__FLAVORS_BLOCKSTATE[flavor_index]}"
      flavor_gamma_tweak="${__FLAVORS_GAMMA_TWEAK[flavor_index]}"

      # Generate texture
      # generate_texture "${color_index}" "${flavor}" "${flavor_gamma_tweak}"

      # Generate models
      "generate_${flavor_model}_model" "${color_index}" "${flavor}"

      # Generate blockstate
      "generate_${flavor_blockstate}_blockstate" "${color_index}" "${flavor}"

      # [Language Generation]
      generate_display_name "${color_index}" "${flavor}"
    done

    # Sort the language file so it loads faster
    sort -o "${__LANG_FILE}" "${__LANG_FILE}"
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
    "${__OUT_TEXTURES_BLOCK}" \
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
  echo >"${__LANG_FILE}" "itemGroup.${__MODID}=Quartzium"
}

function copy_overlay_textures() {
	for ((flavor_index = 0; flavor_index < __FLAVORS_COUNT; flavor_index += 1)); do
		local flavor="${__FLAVORS[flavor_index]}"
		local overlay_file="${__IN_TEXTURES}/overlay_${flavor}.png"
		local texture_file="${__OUT_TEXTURES_BLOCK}/overlay_${flavor}.png"

		cp ${overlay_file} ${texture_file}
	done
}

function generate_base_textures() {
	for ((color_index = 0; color_index < __COLORS_COUNT; color_index += 1)); do
		generate_noise_texture "${color_index}" "" "0.0"
		generate_noise_texture "${color_index}" "_dark" "0.7"
	done
}

# Generates a colored noise texture from a white variant
# @globals
# __COLORS_GAMMA: The Gamma adjustement per color array
# __COLORS_NAME: The colors name array
# __COLORS_RGB: The RGB definition per color array
# __IN_TEXTURES: The path of the noise and flavors overlay
# __OUT_TEXTURES: the output path of textures
# @params
# 1: color_index: The color index
# 2: variant: A name to use as the suffix for the texture name
# 3: gamma_tweak: A value to subtract from the set gamma value (used for lamp textures)
# @product
# Creates a texture file in __OUT_TEXTURES
# @return
# ?: >0: on failure
function generate_noise_texture() {
  # Both parameters are required
  [[ ${#} -eq 3 ]] || return 1
  local -ri color_index="${1}"
  local -r variant="${2}"
  local -r gamma_tweak="${3:-0.0}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r rgb="${__COLORS_RGB[${color_index}]}"
  local -r gamma_base="${__COLORS_GAMMA[${color_index}]}"
  local -r noise_file="${__IN_TEXTURES}/noise.png"
  local -r texture_file="${__OUT_TEXTURES_BLOCK}/noise_${color_name}${variant}.png"

  local gamma
  gamma="$(bc <<<"a=${gamma_base}; b=${gamma_tweak}; if(a > b) a-b else 0.2")"

  # NB: Minecraft doesn't handle grayscale well, make sure the images are in RGB.
  convert \
    "${noise_file}" \
    -fill "${rgb}" \
    -tint 100 \
    -gamma "${gamma}" \
    -define png:color-type=2 \
    -format png \
    "${texture_file}"
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
  local -r texture_file="${__OUT_TEXTURES_BLOCK}/${flavor}_${color_name}.png"

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
function generate_cube_blockstate() {
  # Both parameters are required
  [[ ${#} -eq 2 ]] || return 1

  local -ri color_index="${1}"
  local -r flavor="${2}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_${flavor}"

  # [Block State Generation]
  (
    # shellcheck disable=SC2030,SC2031 # These variables are isolated
    export MODEL="${__MODID}:${registry_name}"

    envsubst \
      <"${__IN_BLOCKSTATES}/cube.json" \
      >"${__OUT_BLOCKSTATES}/${registry_name}.json"
  )
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
function generate_icosahedron_blockstate() {
  # The parameter is required
  [[ ${#} -ge 1 ]] || return 1

  local -ri color_index="${1}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_crystal"

  # [Block State Generation]
  (
    # shellcheck disable=SC2030,SC2031 # These variables are isolated

    local -a in_models=("${__IN_MODELS_BLOCK}/icosahedron/"*.obj)
    local -a MODELS
    local -i models_count="${#in_models[@]}"
    local -i models_index

    for ((models_index = 0; models_index < models_count; models_index += 1)); do
      MODELS+=("${__MODID}:${registry_name}/model_${models_index}.obj")
    done

    # envsubst does not handle array, so use distinct variables for entries
    export \
      MODEL_0="${MODELS[0]}" \
      MODEL_1="${MODELS[1]}" \
      MODEL_2="${MODELS[2]}" \
      MODEL_3="${MODELS[3]}"

    envsubst \
      <"${__IN_BLOCKSTATES}/icosahedron.json" \
      >"${__OUT_BLOCKSTATES}/${registry_name}.json"
  )
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
  [[ ${#} -ge 2 ]] || return 1

  local -ri color_index="${1}"
  local -r flavor="${2}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_${flavor}"

  # [Block State Generation]
  (
    # shellcheck disable=SC2030,SC2031 # These variables are isolated
    export \
      MODEL="${__MODID}:${color_name}_${__BASE_NAME}_lamp" \
      MODEL_LIT="${__MODID}:${color_name}_${__BASE_NAME}_inverted_lamp"

    envsubst \
      <"${__IN_BLOCKSTATES}/${flavor}.json" \
      >"${__OUT_BLOCKSTATES}/${registry_name}.json"
  )
}

# Generates cube block and item model json files for a given color index and flavor
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
# ?: >0: on failure
function generate_cube_dark_model() {
  # Both parameters are required
  [[ ${#} -eq 2 ]] || return 1
  local -ri color_index="${1}"
  local -r flavor="${2}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_${flavor}"

  # Generate Block model
  (
    # shellcheck disable=SC2030,SC2031 # These variables are isolated
    export \
      TEXTURE_NOISE="${__MODID}:blocks/noise_${color_name}" \
      TEXTURE_OVERLAY="${__MODID}:blocks/overlay_${flavor}"

    envsubst \
      <"${__IN_MODELS_BLOCK}/cube_dark.json" \
      >"${__OUT_MODELS_BLOCK}/${registry_name}.json"
  )

  # Generate Item model
  (
    # shellcheck disable=SC2030,SC2031 # These variables are isolated
    export \
      PARENT="${__MODID}:block/${registry_name}"

    envsubst \
      <"${__IN_MODELS_ITEM}/cube_dark.json" \
      >"${__OUT_MODELS_ITEM}/${registry_name}.json"
  )
}

# Generates cube block and item model json files for a given color index and flavor
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
# ?: >0: on failure
function generate_cube_model() {
  # Both parameters are required
  [[ ${#} -eq 2 ]] || return 1
  local -ri color_index="${1}"
  local -r flavor="${2}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_${flavor}"

  # Generate Block model
  (
    # shellcheck disable=SC2030,SC2031 # These variables are isolated
    export \
      TEXTURE_NOISE="${__MODID}:blocks/noise_${color_name}" \
      TEXTURE_OVERLAY="${__MODID}:blocks/overlay_${flavor}"

    envsubst \
      <"${__IN_MODELS_BLOCK}/cube.json" \
      >"${__OUT_MODELS_BLOCK}/${registry_name}.json"
  )

  # Generate Item model
  (
    # shellcheck disable=SC2030,SC2031 # These variables are isolated
    export \
      PARENT="${__MODID}:block/${registry_name}"

    envsubst \
      <"${__IN_MODELS_ITEM}/cube.json" \
      >"${__OUT_MODELS_ITEM}/${registry_name}.json"
  )
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
# ?: >0: on failure
function generate_icosahedron_model() {
  # The parameter is required
  [[ ${#} -eq 2 ]] || return 1
  local -ri color_index="${1}"

  local -r color_name="${__COLORS_NAME[${color_index}]}"
  local -r registry_name="${color_name}_${__BASE_NAME}_crystal"
  mkdir -p "${__OUT_MODELS_BLOCK}/${registry_name}"

  # Generate icosahedron Wavefront obj models
  (
    # shellcheck disable=SC2030,SC2031 # These variables are isolated
    export TEXTURE="${__MODID}:blocks/noise_${color_name}"

    local -a models=("${__IN_MODELS_BLOCK}/icosahedron/"*.obj)
    local -i models_count="${#models[@]}"
    local -i models_index
    for ((models_index = 0; models_index < models_count; models_index += 1)); do

      export MTL="model_${models_index}.mtl"

      envsubst \
        <"${__IN_MODELS_BLOCK}/icosahedron/$(basename "${models[models_index]}")" \
        >"${__OUT_MODELS_BLOCK}/${registry_name}/model_${models_index}.obj"

      envsubst \
        <"${__IN_MODELS_BLOCK}/icosahedron/model_${models_index}.mtl" \
        >"${__OUT_MODELS_BLOCK}/${registry_name}/model_${models_index}.mtl"
    done
  )

  # Generates icosahedron item json model
  cat \
    <"${__IN_MODELS_ITEM}/icosahedron.json" \
    >"${__OUT_MODELS_ITEM}/${registry_name}.json"
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

main "${@}" # Run self
