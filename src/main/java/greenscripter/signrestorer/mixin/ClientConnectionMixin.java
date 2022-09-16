package greenscripter.signrestorer.mixin;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import greenscripter.signrestorer.SignRestorerMod;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ClientConnectionMixin {

	@Inject(method = "onChatMessage", at = @At("RETURN"), cancellable = true)
	public void onChatMessage(final ChatMessageC2SPacket packet, CallbackInfo ci) throws IOException {

		String text = ((ChatMessageC2SPacket) packet).chatMessage();
		if (text.equals("&signsreload") || text.equals("&reloadsigns")) {
			SignRestorerMod.reloadSignData();
			ci.cancel();
		}
	}
}
