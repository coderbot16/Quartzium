package net.coderbot.projecty.client;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.vecmath.Matrix4f;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SimpleBakedModelBright implements IBakedModel {
	protected IBakedModel base;

	private List<BakedQuad> allQuads;
	private EnumMap<EnumFacing, List<BakedQuad>> quads;

	public SimpleBakedModelBright(IBakedModel base, Set<String> textures) {
		this.base = base;

		this.quads = new EnumMap<>(EnumFacing.class);
		for(EnumFacing facing: EnumFacing.values()) {
			List<BakedQuad> faceQuads = new ArrayList<>(base.getQuads(null, facing, 0));

			Brightener.brighten(faceQuads, textures);
			this.quads.put(facing, faceQuads);
		}

		allQuads = new ArrayList<>(base.getQuads(null, null, 0));
		Brightener.brighten(allQuads, textures);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		return side == null ? allQuads : quads.get(side);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return base.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return base.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return base.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return base.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return base.getOverrides();
	}

	@Override
	@SuppressWarnings("deprecation")
	public ItemCameraTransforms getItemCameraTransforms() {
		return base.getItemCameraTransforms();
	}

	@Override
	public boolean isAmbientOcclusion(IBlockState state) {
		return base.isAmbientOcclusion(state);
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		return Pair.of(this, base.handlePerspective(cameraTransformType).getRight());
	}
}
