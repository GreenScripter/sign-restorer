package greenscripter.signrestorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class SignRestorerMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("sign-restorer");
	public static List<Map<String, SignData>> signData = null;
	public static boolean enabled = false;
	private static KeyBinding stateChangeKeybind;

	@Override
	public void onInitialize() {
		try {
			reloadSignData();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		stateChangeKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding("Change Sign Restorer State", // The translation key of the keybinding's name
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

	public static void reloadSignData() throws IOException {
		String fileData = null;
		try {
			Gson gson = new Gson();
			fileData = fetchFile();
			signData = gson.fromJson(fileData, new TypeToken<ArrayList<Map<String, SignData>>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String fetchFile() throws IOException {
		URL url = new URL("https://raw.githubusercontent.com/barrulik/sign-restorer-data/main/signData.json");
		URLConnection urlConnection = url.openConnection();
		urlConnection.setConnectTimeout(1000);
		urlConnection.setReadTimeout(1000);
		BufferedReader breader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

		String output = "";
		String line;
		while ((line = breader.readLine()) != null) {
			output += line;
		}

		return output;
	}
}
