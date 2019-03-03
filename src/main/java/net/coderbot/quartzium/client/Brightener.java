package net.coderbot.quartzium.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexLighterFlat;

import java.util.List;
import java.util.Set;

public class Brightener {
	private static final VertexFormat ITEM_LIT = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);
	private static final float FULLBRIGHT = 0.007F;

	public static void brighten(List<BakedQuad> quads, Set<String> textures) {
		for(int i = 0; i < quads.size(); i++) {
			BakedQuad quad = quads.get(i);

			if(quad.getSprite().getIconName().contains("noise")) {
				quads.set(i, brightenQuad(quad));
			}
		}
	}

	private static BakedQuad brightenQuad(BakedQuad quad) {
		VertexFormat format = quad.getFormat();

		if(format == DefaultVertexFormats.ITEM) {
			format = ITEM_LIT;
		} else if (format != DefaultVertexFormats.BLOCK && !format.hasUvOffset(1)) {
			format = new VertexFormat(format);
			format.addElement(DefaultVertexFormats.TEX_2S);
		}

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);

		VertexLighterFlat lighter = new VertexLighterFlat(Minecraft.getMinecraft().getBlockColors()) {
			@Override
			public void setQuadTint(int tint) {}

			@Override
			protected void updateLightmap(float[] normal, float[] lightmap, float x, float y, float z) {
				lightmap[0] = FULLBRIGHT;
				lightmap[1] = FULLBRIGHT;
			}
		};

		lighter.setParent(builder);
		quad.pipe(lighter);

		builder.setQuadTint(quad.getTintIndex());
		builder.setQuadOrientation(quad.getFace());
		builder.setTexture(quad.getSprite());
		builder.setApplyDiffuseLighting(false);

		return builder.build();
	}
}
