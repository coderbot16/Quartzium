package net.coderbot.quartzium.color;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class PentaColorMap<T> {
	private Object[] map;

	public PentaColorMap(Function<PentaColor, T> filler) {
		map = new Object[5];

		for(int i = 0; i<5; i++) {
			map[i] = filler.apply(PentaColor.fromOrdinal(i));
		}
	}

	@SuppressWarnings("unchecked")
	public T get(PentaColor color) {
		return (T)map[color.ordinal()];
	}

	public void set(PentaColor color, T value) {
		map[color.ordinal()] = value;
	}

	@SuppressWarnings("unchecked")
	public void forEach(BiConsumer<PentaColor, T> consumer) {
		for(int i = 0; i<5; i++) {
			PentaColor color = PentaColor.fromOrdinal(i);
			consumer.accept(color, (T)map[i]);
		}
	}

	public <R> PentaColorMap<R> map(Function<T, R> mapper) {
		return new PentaColorMap<>(color -> mapper.apply(get(color)));
	}
}
