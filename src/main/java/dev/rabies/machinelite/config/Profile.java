package dev.rabies.machinelite.config;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.utils.IMC;

import java.io.*;
import java.util.ArrayList;

public class Profile {

    private final File data;

    public Profile() {
        File directory = new File(IMC.mc.gameDir, MachineLite.CLIENT_NAME);
        if (!directory.exists()) directory.mkdir();

        data = new File(directory, "data.txt");
        if (data.exists()) return;
        try {
            data.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveFile() {
        ArrayList<String> toSaves = new ArrayList<>();
        MachineLite.getModuleManager().getModules().forEach(module -> toSaves.add(String.format("Module:%s:%s:%s", module.getName(), module.getKeyCode(), module.isEnabled())));

        try {
            PrintWriter pw = new PrintWriter(this.data);
            toSaves.forEach(pw::println);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() throws Exception {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(this.data));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();

        for (String s : lines) {
            String[] split = s.split(":");

            if (s.startsWith("Module:") && split.length > 3) {
                Module module = MachineLite.getModuleManager().getModuleByString(split[1]);
                if (module != null) {
                    module.setKeyCode(Integer.parseInt(split[2]));

                    if (Boolean.parseBoolean(split[3]) && !module.isEnabled()) {
                        module.setEnable();
                    }
                }
            }
        }
    }
}
