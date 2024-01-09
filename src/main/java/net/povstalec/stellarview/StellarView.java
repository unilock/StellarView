package net.povstalec.stellarview;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.config.ModConfig;
import net.povstalec.stellarview.client.render.level.StellarViewOverworldEffects;
import net.povstalec.stellarview.common.config.OverworldConfig;
import net.povstalec.stellarview.common.config.StellarViewConfig;
import net.povstalec.stellarview.common.init.EventInit;
import net.povstalec.stellarview.common.util.KeyBindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StellarView implements ClientModInitializer
{
	public static final String MODID = "stellarview";
    
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    
    public static StellarViewOverworldEffects overworld;

	@Override
	public void onInitializeClient()
	{
		ForgeConfigRegistry.INSTANCE.register(MODID, ModConfig.Type.CLIENT, StellarViewConfig.CLIENT_CONFIG, MODID + "-client.toml");

		ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
			StellarView.overworld = new StellarViewOverworldEffects();
		});

		KeyBindingHelper.registerKeyBinding(KeyBindings.OPEN_CONFIG_KEY);
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> updateGalaxies());

		EventInit.init();
    }
    
    public static void updateGalaxies()
    {
		if (Minecraft.getInstance().isSameThread())
    		overworld.MILKY_WAY.setStarBuffer(OverworldConfig.milky_way_x.get(), OverworldConfig.milky_way_y.get(), OverworldConfig.milky_way_z.get(), 0, 0, 0);
    }
}
