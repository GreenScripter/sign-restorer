package greenscripter.signrestorer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import greenscripter.signrestorer.SignRestorerMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

	@SuppressWarnings("resource")
	@Inject(at = @At("HEAD"), method = "sendChatMessage(Ljava/lang/String;Lnet/minecraft/text/Text;)V", cancellable = true)
	private void onSendChatMessage(String message, Text text, CallbackInfo ci) {
		if (message.equals("&reloadsigns") || message.equals("&signsreload")) {
			SignRestorerMod.reloadSignData();
			MinecraftClient.getInstance().player.sendMessage(Text.literal("§7 Reloaded sign data."), false);
			ci.cancel();
		}
	}
}
