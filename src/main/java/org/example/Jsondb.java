package org.example;

import org.json.simple.*;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Jsondb {

    public Jsondb(String todayDBString) {
        this.openJsonFile(todayDBString);
    }

    public static File openJsonFile(String filename){
        try {
            File db = new File(filename);
            db.getParentFile().mkdirs();
            db.createNewFile();
            return db;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void updateItems(String filename, List<Item> information) throws IOException {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Item i:information) {
            JSONObject jsonObjectTemp = new JSONObject();
            jsonObjectTemp.put("id", i.getId());
            jsonObjectTemp.put("name", i.getName());
            jsonObjectTemp.put("quantity", i.getQuantity());
            jsonObjectTemp.put("cost", i.getCost());
            jsonArray.add(jsonObjectTemp);
        }
        jsonObject.put("list", jsonArray);
        // Write the JSONObject to a JSON file
        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getToday() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String today = dtf.format(now);
        return today;
    }

    public static List<File> getJsonFiles(String directoryPath) {
        List<File> jsonFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            // Filter only .json files
            FilenameFilter jsonFilter = (dir, name) -> name.endsWith(".json");
            File[] files = directory.listFiles(jsonFilter);

            if (files != null) {
                for (File file : files) {
                    jsonFiles.add(file);
                }
            }
        }

        return jsonFiles;
    }
}
