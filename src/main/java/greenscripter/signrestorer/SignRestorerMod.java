package greenscripter.signrestorer;

import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import greenscripter.signrestorer.data.SignData;
import greenscripter.signrestorer.data.SignRestorerConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class SignRestorerMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("sign-restorer");
	public static Map<String, SignData> signData = null;
	public static boolean enabled = false;
	private static KeyBinding stateChangeKeybind;

	@Override
	public void onInitialize() {
		SignRestorerConfig.loadConfig();

		reloadSignData();

		stateChangeKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Toggle Sign Restorer", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
				GLFW.GLFW_KEY_B, // The keycode of the key
				"Sign Restorer" // The translation key of the keybinding's category.
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (stateChangeKeybind.wasPressed()) {
				enabled = !enabled;
				client.player.sendMessage(Text.literal("§7 Sign Restorer is now " + (enabled ? "enabled" : "disabled")), false);
			}
		});
	}

	public static void reloadSignData() {
		signData = new HashMap<>();

		String fileData = null;
		try {
			Gson gson = new Gson();
			fileData = fetchFile();
			SignData[] signDataJson = gson.fromJson(fileData, SignData[].class);

			for (SignData s : signDataJson) {
				if (s == null) continue;
				signData.put(s.coordinates, s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String fetchFile() throws IOException {
		BufferedReader breader;
		if (SignRestorerConfig.config.file != null) {
			breader = new BufferedReader(new FileReader(SignRestorerConfig.config.file));
		} else {
			URL url = new URL(SignRestorerConfig.config.url);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setConnectTimeout(1000);
			urlConnection.setReadTimeout(1000);
			breader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		}
		String output = "";
		String line;
		while ((line = breader.readLine()) != null) {
			output += line;
		}

		return output;
	}
}
