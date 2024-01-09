package net.povstalec.stellarview.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.povstalec.stellarview.StellarView;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
	@Shadow
	@Nullable
	private ClientLevel level;

	@Shadow
	private int ticks;

	@Inject(method = "renderClouds", at = @At("HEAD"), cancellable = true)
	private void renderCustomClouds(PoseStack pPoseStack, Matrix4f pProjectionMatrix, float pPartialTick, double pCamX, double pCamY, double pCamZ, CallbackInfo ci) {
		if (BuiltinDimensionTypes.OVERWORLD.equals(level.dimensionTypeId()) && StellarView.overworld.renderClouds(level, ticks, pPartialTick, pPoseStack, pCamX, pCamY, pCamZ, pProjectionMatrix))
			ci.cancel();
	}

	@Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
	private void renderCustomSky(PoseStack pPoseStack, Matrix4f pProjectionMatrix, float pPartialTick, Camera pCamera, boolean pIsFoggy, Runnable pSkyFogSetup, CallbackInfo ci) {
		if (BuiltinDimensionTypes.OVERWORLD.equals(level.dimensionTypeId()) && StellarView.overworld.renderSky(level, ticks, pPartialTick, pPoseStack, pCamera, pProjectionMatrix, pIsFoggy, pSkyFogSetup))
			ci.cancel();
	}

	@Inject(method = "renderSnowAndRain", at = @At("HEAD"), cancellable = true)
	private void renderCustomWeather(LightTexture pLightTexture, float pPartialTick, double pCamX, double pCamY, double pCamZ, CallbackInfo ci) {
		if (BuiltinDimensionTypes.OVERWORLD.equals(level.dimensionTypeId()) && StellarView.overworld.renderSnowAndRain(level, ticks, pPartialTick, pLightTexture, pCamX, pCamY, pCamZ))
			ci.cancel();
	}
}
