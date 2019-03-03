package net.coderbot.quartzium.color;

import net.minecraft.block.material.MapColor;

public enum PentaColor {
	WHITE(MapColor.SNOW, Color.WHITE),
	BLUE(MapColor.BLUE, Color.BLUE),
	GREEN(MapColor.GREEN, Color.GREEN),
	RED(MapColor.RED, Color.RED),
	BLACK(MapColor.BLACK, Color.BLACK);

	private MapColor mapColor;
	private Color full;
	private static final PentaColor[] VALUES = values();

	PentaColor(MapColor mapColor, Color full) {
		this.mapColor = mapColor;
		this.full = full;
	}

	public String getName() {
		return this.name().toLowerCase();
	}

	public MapColor getColor() {
		return this.mapColor;
	}

	public Color getFullColor() {
		return this.full;
	}

	public static PentaColor fromOrdinal(int index) {
		return VALUES[index];
	}
}
