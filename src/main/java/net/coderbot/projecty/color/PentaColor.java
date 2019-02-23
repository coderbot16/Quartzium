package net.coderbot.projecty.color;

import net.minecraft.block.material.MapColor;

public enum PentaColor {
	WHITE(MapColor.SNOW),
	BLUE(MapColor.BLUE),
	GREEN(MapColor.GREEN),
	RED(MapColor.RED),
	BLACK(MapColor.BLACK);

	private MapColor mapColor;
	private static final PentaColor[] VALUES = values();

	PentaColor(MapColor mapColor) {
		this.mapColor = mapColor;
	}

	public String getName() {
		return this.name().toLowerCase();
	}

	public MapColor getColor() {
		return this.mapColor;
	}

	public static PentaColor fromOrdinal(int index) {
		return VALUES[index];
	}
}
