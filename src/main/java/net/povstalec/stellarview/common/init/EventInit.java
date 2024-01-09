package net.povstalec.stellarview.common.init;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.povstalec.stellarview.client.screens.config.ConfigScreen;
import net.povstalec.stellarview.common.util.KeyBindings;

public class EventInit
{
	public static void init() {
		ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
			if(KeyBindings.OPEN_CONFIG_KEY.consumeClick())
				minecraft.setScreen(new ConfigScreen(null));
		});
	}
}
