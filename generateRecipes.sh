#!/usr/bin/env bash

source './resourcesConfig.sh' # Configuration of the generated resources

function main() {
  mkdir -p "${__OUT_RECIPES}"

  generate_crystal_combine_color_recipes
  generate_block_color_flavor_recipes
}

# Generates crystal color mix recipes
# @product
# recipes/combine_${target}.json
function generate_crystal_combine_color_recipes() {
  for ((i = 0; i < __COLORS_CONV_COUNT; i += 1)); do
    (
      # envsubst need environment varialbes
      # this () enclosed sub-shell isolates the exported environment variables
      # from the main environment
      # shellcheck disable=SC2030,SC2031 # These variables are isolated
      export \
        TARGET="${__COLORS_CONV[${i}]}_${__BASE_NAME}_crystal" \
        BASE="${__COLORS_BASE[${i}]}_${__BASE_NAME}_crystal" \
        MIXIN="${__COLORS_MIXIN[${i}]}_${__BASE_NAME}_crystal"
      envsubst \
        <"${__IN_RECIPES}/combine.json" \
        >"${__OUT_RECIPES}/combine_${TARGET}.json"
    )
  done
}

# Generates crystal color mix recipes
# @product
# recipes/combine_${target}.json
function generate_block_color_flavor_recipes() {
  for flavor in "${__FLAVORS[@]}"; do
    if [ -f "${__IN_RECIPES}/${flavor}.json" ]; then
      for color in "${__COLORS_NAME[@]}"; do
        (
          # shellcheck disable=SC2030,SC2031 # These variables are isolated
          export \
            COLOR="${color}" \
            BASE="${__BASE_NAME}"

          envsubst \
            <"${__IN_RECIPES}/${flavor}.json" \
            >"${__OUT_RECIPES}/${color}_${__BASE_NAME}_${flavor}.json"
        )
      done
    fi
  done
}
##### Script start #####

main "$@" # Run self
