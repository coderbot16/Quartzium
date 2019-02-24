package net.coderbot.projecty.color;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ColorMap<T> {
	private Object[] map;

	public ColorMap(Function<Color, T> filler) {
		map = new Object[16];

		for(int i = 0; i<16; i++) {
			map[i] = filler.apply(Color.fromOrdinal(i));
		}
	}

	@SuppressWarnings("unchecked")
	public T get(Color color) {
		return (T)map[color.ordinal()];
	}

	@SuppressWarnings("unchecked")
	public T get(PentaColor color) {
		return (T)map[color.getFullColor().ordinal()];
	}

	public void set(Color color, T value) {
		map[color.ordinal()] = value;
	}

	@SuppressWarnings("unchecked")
	public void forEach(BiConsumer<Color, T> consumer) {
		for(int i = 0; i<16; i++) {
			Color color = Color.fromOrdinal(i);
			consumer.accept(color, (T)map[i]);
		}
	}

	public void forEachPenta(BiConsumer<PentaColor, T> consumer) {
		for(int i = 0; i<5; i++) {
			PentaColor color = PentaColor.fromOrdinal(i);
			consumer.accept(color, get(color));
		}
	}

	public <R> ColorMap<R> map(Function<T, R> mapper) {
		return new ColorMap<>(color -> mapper.apply(get(color)));
	}
}
