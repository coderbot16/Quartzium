#!/bin/bash
# Purpose: Combine the overlays and colored noise into flattened images

# [Initialization]
IN_TEXTURES=./textures/baseline
OUT_TEXTURES=./textures/blocks
OUT_BLOCKSTATES=./blockstates
OUT_MODELS_BLOCK=./models/block
OUT_MODELS_ITEM=./models/item
OUT_LANG=./lang
MODID="projecty"
BASE_NAME="xychronite"

mkdir -p $OUT_TEXTURES
mkdir -p $OUT_BLOCKSTATES
mkdir -p $OUT_MODELS_BLOCK
mkdir -p $OUT_MODELS_ITEM
mkdir -p $OUT_LANG

# Color Variable Setup

flavors=(ore bricks block plate platform shield)

declare -A colors
colors["white"]="rgb(255,255,255)"
colors["orange"]="rgb(100,50,0)"
colors["magenta"]="rgb(100,0,100)"
colors["light_blue"]="rgb(0,0,100)"
colors["yellow"]="rgb(100,100,0)"
colors["lime"]="rgb(0,100,0)"
colors["pink"]="rgb(100,0,0)"
colors["gray"]="rgb(0,0,0)"
colors["light_gray"]="rgb(0,0,0)"
colors["cyan"]="rgb(50,100,100)"
colors["purple"]="rgb(100,0,100)"
colors["blue"]="rgb(0,0,100)"
colors["brown"]="rgb(150,75,0)"
colors["green"]="rgb(0,100,0)"
colors["red"]="rgb(100,0,0)"
colors["black"]="rgb(0,0,0)"

declare -A gamma
gamma["white"]="1.0"
gamma["orange"]="1.0"
gamma["magenta"]="1.2"
gamma["light_blue"]="1.2"
gamma["yellow"]="0.7"
gamma["lime"]="1.2"
gamma["pink"]="1.2"
gamma["gray"]="0.4"
gamma["light_gray"]="0.7"
gamma["cyan"]="1.2"
gamma["purple"]="0.7"
gamma["blue"]="0.7"
gamma["brown"]="0.7"
gamma["green"]="0.7"
gamma["red"]="0.7"
gamma["black"]="0.2"

# [Texture Generation]

for color in ${!colors[@]}
do
  for flavor in ${flavors[*]}
  do
    # NB: Minecraft doesn't handle grayscale well, make sure the images are in RGB.

  	convert $IN_TEXTURES/noise.png -fill "${colors[$color]}" -tint 100 -gamma ${gamma[$color]} \
  			$IN_TEXTURES/overlay_"$flavor".png \
  			-composite -define png:color-type=2 -format png $OUT_TEXTURES/"$flavor"_"$color".png
  done
done

# [Block State Generation]

for color in ${!colors[@]}
do
  for flavor in ${flavors[*]}
  do
  	NAME=$color"_"$BASE_NAME"_"$flavor;
  	echo "{ \"variants\": {\"normal\": {\"model\": \""$MODID":"$NAME"\"} } }" > $OUT_BLOCKSTATES/$NAME.json
  done
done

# [Model Generation]

for color in ${!colors[@]}
do
  for flavor in ${flavors[*]}
  do
  	NAME=$color"_"$BASE_NAME"_"$flavor;

  	echo "{ \"parent\": \"minecraft:block/cube_all\", \"textures\": { \"all\": \""$MODID":blocks/"$flavor"_"$color"\" } }" > $OUT_MODELS_BLOCK/$NAME.json
  	echo "{ \"parent\": \""$MODID":block/"$NAME"\" }" > $OUT_MODELS_ITEM/$NAME.json
  done
done

# [Language Generation]

echo "itemGroup.projecty=ProjectY" > $OUT_LANG/en_us.lang

for color in ${!colors[@]}
do
  for flavor in ${flavors[*]}
  do
  	NAME=$color"_"$BASE_NAME"_"$flavor;
  	echo "tile.$MODID.$NAME.name=${color^} ${BASE_NAME^} ${flavor^}" >> $OUT_LANG/en_us.lang
  done
done

# TODO: Recipes, items, etc
