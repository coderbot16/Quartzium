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

	public <R> ColorMap<R> map(Function<T, R> mapper) {
		return new ColorMap<>(color -> mapper.apply(get(color)));
	}
}
