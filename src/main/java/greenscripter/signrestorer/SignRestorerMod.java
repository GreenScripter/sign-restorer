package greenscripter.signrestorer;

import java.util.HashMap;
import java.util.Map;

import java.io.File;
import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.fabricmc.api.ModInitializer;

public class SignRestorerMod implements ModInitializer {
	
	public static Map<String, SignData> signData = new HashMap<>();
	
	@Override
	public void onInitialize() {
		reloadSignData();
	}
	
	public static void reloadSignData() {
		File f = new File("signdata.json");
		String fileData;
		try {
			fileData = Files.lines(f.toPath()).reduce((s1, s2) -> s1 + " " + s2).orElse(null);
			Gson gson = new Gson();
			signData = gson.fromJson(fileData, new TypeToken<Map<String, SignData>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
