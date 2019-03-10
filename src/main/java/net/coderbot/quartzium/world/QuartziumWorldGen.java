package net.coderbot.quartzium.world;

import net.coderbot.quartzium.QuartziumConfig;
import net.coderbot.quartzium.color.PentaColor;
import net.coderbot.quartzium.init.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class QuartziumWorldGen implements IWorldGenerator {
	private static final WorldGenMinable WHITE = new WorldGenMinable(ModBlocks.ORE.get(PentaColor.WHITE).getDefaultState(), QuartziumConfig.ORES.whiteSize);
	private static final WorldGenMinable BLUE = new WorldGenMinable(ModBlocks.ORE.get(PentaColor.BLUE).getDefaultState(), QuartziumConfig.ORES.blueSize);
	private static final WorldGenMinable GREEN = new WorldGenMinable(ModBlocks.ORE.get(PentaColor.GREEN).getDefaultState(), QuartziumConfig.ORES.greenSize);
	private static final WorldGenMinable RED = new WorldGenMinable(ModBlocks.ORE.get(PentaColor.RED).getDefaultState(), QuartziumConfig.ORES.redSize);
	private static final WorldGenMinable BLACK = new WorldGenMinable(ModBlocks.ORE.get(PentaColor.RED).getDefaultState(), QuartziumConfig.ORES.blackSize);

	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		ore(random, chunkX, chunkZ, world, WHITE, QuartziumConfig.ORES.whiteCount);
		ore(random, chunkX, chunkZ, world, BLUE, QuartziumConfig.ORES.blueCount);
		ore(random, chunkX, chunkZ, world, GREEN, QuartziumConfig.ORES.greenCount);
		ore(random, chunkX, chunkZ, world, RED, QuartziumConfig.ORES.redCount);
		ore(random, chunkX, chunkZ, world, BLACK, QuartziumConfig.ORES.blackCount);
	}

	public void ore(Random random, int chunkX, int chunkZ, World world, WorldGenMinable ore, int count) {
		for(int i = 0; i < count; i++) {
			int x = chunkX * 16 + random.nextInt(16);
			int y = random.nextInt(64);
			int z = chunkZ * 16 + random.nextInt(16);

			ore.generate(world, random, new BlockPos(x, y, z));
		}
	}
}
