package greenscripter.signrestorer.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import greenscripter.signrestorer.SignRestorerMod;
import greenscripter.signrestorer.data.SignData;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.util.math.BlockPos;

@Mixin(SignEditScreen.class)
public abstract class SignEditScreenMixin {

	@Shadow
	@Final
	private String[] text;

	@Shadow
	@Final
	private SignBlockEntity sign;

	@Inject(at = { @At("HEAD") }, method = { "init()V" })
	private void onInit(CallbackInfo ci) {
		if (!SignRestorerMod.enabled) return;

		BlockPos pos = sign.getPos();
		SignData data = SignRestorerMod.signData.get(pos.getX() + " " + pos.getY() + " " + pos.getZ());
		if (data == null) return;

		for (int i = 0; i < 4; i++)
			text[i] = data.text[i];

		finishEditing();
	}

	@Shadow
	private void finishEditing() {

	}
}
