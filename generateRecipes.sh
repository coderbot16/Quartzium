#!/usr/bin/env bash

declare -r __MODID="projecty"
declare -r __RESOURCES="./src/main/resources/assets/${__MODID}"
declare -r __IN_RECIPES="${__RESOURCES}/recipes_baseline"
declare -r __OUT_RECIPES="${__RESOURCES}/recipes"
declare -r __BASE_NAME='xychronite'

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

declare -ra __FLAVORS=(
#  'bricks'
  'block'
#  'plate'
#  'platform'
#  'shield'
)

declare -a __COLORS_CONV=(
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

declare -a __COLORS_BASE=(
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

declare -a __COLORS_MIXIN=(
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

function main() {
	mkdir -p "${__OUT_RECIPES}"

	for ((__i = 0; __i < 11; __i += 1)); do
		local target="${__COLORS_CONV[${__i}]}_${__BASE_NAME}_crystal"
		local base="${__COLORS_BASE[${__i}]}_${__BASE_NAME}_crystal"
		local mixin="${__COLORS_MIXIN[${__i}]}_${__BASE_NAME}_crystal"
		local filename="combine_${target}.json"
	
		sed "s/TARGET/${target}/;s/BASE/${base}/;s/MIXIN/${mixin}/" < "${__IN_RECIPES}/combine.json" > "${__OUT_RECIPES}/${filename}"
	done

	for flavor in "${__FLAVORS[@]}"; do
		for ((color_index = 0; color_index < __COLORS_COUNT; color_index += 1)); do
			local color=${__COLORS_NAME[${color_index}]}

			local target="${color}_${__BASE_NAME}_${flavor}"
			local replacement="s/@COLOR@/${color}/;s/@BASE@/${__BASE_NAME}/"

			sed "${replacement}" < "${__IN_RECIPES}/${flavor}.json" > "${__OUT_RECIPES}/${target}.json"
		done
	done
}

##### Script start #####

main "$@" # Run self