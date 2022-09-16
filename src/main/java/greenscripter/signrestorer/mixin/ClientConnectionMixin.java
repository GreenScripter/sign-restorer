package greenscripter.signrestorer.mixin;

import java.util.concurrent.Future;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import greenscripter.signrestorer.SignRestorerMod;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {
	
	@Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", cancellable = true)
	public void send(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo ci) {
		if (packet instanceof ChatMessageC2SPacket) {
			String text = ((ChatMessageC2SPacket) packet).getChatMessage();
			if (text.equals("&signsreload") || text.equals("&reloadsigns")) {
				SignRestorerMod.reloadSignData();
				ci.cancel();
			}
		}
	}
	
}
