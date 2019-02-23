package net.coderbot.projecty.color;

import net.minecraft.block.material.MapColor;

public enum Color {
	WHITE(MapColor.SNOW),
	ORANGE(MapColor.ADOBE),
	MAGENTA(MapColor.MAGENTA),
	LIGHT_BLUE(MapColor.LIGHT_BLUE),
	YELLOW(MapColor.YELLOW),
	LIME(MapColor.LIME),
	PINK(MapColor.PINK),
	GRAY(MapColor.GRAY),
	LIGHT_GRAY(MapColor.SILVER),
	CYAN(MapColor.CYAN),
	PURPLE(MapColor.PURPLE),
	BLUE(MapColor.BLUE),
	BROWN(MapColor.BROWN),
	GREEN(MapColor.GREEN),
	RED(MapColor.RED),
	BLACK(MapColor.BLACK);

	private MapColor mapColor;
	private static final Color[] VALUES = values();

	Color(MapColor mapColor) {
		this.mapColor = mapColor;
	}

	public String getName() {
		return this.name().toLowerCase();
	}

	public MapColor getColor() {
		return this.mapColor;
	}

	public static Color fromOrdinal(int index) {
		return VALUES[index];
	}
}
