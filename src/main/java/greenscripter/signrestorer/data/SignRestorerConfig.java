package greenscripter.signrestorer.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import net.fabricmc.loader.api.FabricLoader;

public class SignRestorerConfig {

	public static SignRestorerConfig config;

	public static void loadConfig() {
		File f = new File(FabricLoader.getInstance().getConfigDir().toFile(), "sign-restorer.json");
		try {
			config = new Gson().fromJson(new FileReader(f), SignRestorerConfig.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			config = new SignRestorerConfig();
			String newFile = new GsonBuilder().setPrettyPrinting().create().toJson(config);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
				writer.write(newFile);
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public String url = "https://raw.githubusercontent.com/GreenScripter/sign-restorer/master/signData.json";
	public String file = null;
}
