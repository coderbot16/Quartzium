#!/bin/bash
# Purpose: Combine the overlays and colored noise into flattened images

# [Initialization]
IN_TEXTURES=./textures/baseline
OUT_TEXTURES=./textures/blocks
OUT_BLOCKSTATES=./blockstates
OUT_MODELS_BLOCK=./models/block
OUT_MODELS_ITEM=./models/item
OUT_LANG=./lang
OPTIMIZE=false
MODID="projecty"
PREFIX="xychronite"

mkdir -p $OUT_TEXTURES
mkdir -p $OUT_BLOCKSTATES
mkdir -p $OUT_MODELS_BLOCK
mkdir -p $OUT_MODELS_ITEM
mkdir -p $OUT_LANG

# [Texture Generation]

colors=(red green blue light dark)
flavors=(ore bricks storage plate platform shield)

for color in ${colors[*]}
do
  for flavor in ${flavors[*]}
  do
    # NB: Minecraft doesn't handle grayscale well, make sure the images are in RGB.
  	convert   $IN_TEXTURES/noise_"$color".png $IN_TEXTURES/overlay_"$flavor".png -composite -define png:color-type=2 -format png $OUT_TEXTURES/"$flavor"_"$color".png
  done
done

# [Texture Optimization]
#if $OPTIMIZE
#then
  #for image in $OUT_TEXTURES/*.png; do
  	#optipng -quiet $image
  #done
#fi

# [Block State Generation]

for color in ${colors[*]}
do
  for flavor in ${flavors[*]}
  do
  	NAME="$PREFIX"_"$flavor"_"$color";
  	echo "{ \"variants\": {\"normal\": {\"model\": \""$MODID":"$NAME"\"} } }" > $OUT_BLOCKSTATES/$NAME.json
  done
done

# [Model Generation]

for color in ${colors[*]}
do
  for flavor in ${flavors[*]}
  do
  	NAME="$PREFIX"_"$flavor"_"$color";

  	echo "{ \"parent\": \"minecraft:block/cube_all\", \"textures\": { \"all\": \""$MODID":blocks/"$flavor"_"$color"\" } }" > $OUT_MODELS_BLOCK/$NAME.json
  	echo "{ \"parent\": \""$MODID":block/"$NAME"\" }" > $OUT_MODELS_ITEM/$NAME.json
  done
done

# [Language Generation]

echo "itemGroup.projecty=ProjectY" > $OUT_LANG/en_us.lang

for color in ${colors[*]}
do
  for flavor in ${flavors[*]}
  do
  	NAME="$PREFIX"_"$flavor"_"$color";
  	echo "tile.$MODID.$NAME.name=${color^} ${PREFIX^} ${flavor^}" >> $OUT_LANG/en_us.lang
  done
done

# TODO: Recipes, items, etc