package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Good {
    List<Item> list_of_goods = null;
    String filename;
    Good main_list;
    protected Good(){

    }
    public Good(String filename) throws IOException, ParseException {
        this.filename = filename;
        this.list_of_goods = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filename)) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("list");
            for(int q = 0; q < jsonArray.size(); q++) {
                JSONObject i = (JSONObject) jsonArray.get(q);
                list_of_goods.add(new Item((int)(long) i.get("id"), (String) i.get("name"), (float)(double) i.get("quantity"), (float)(double) i.get("cost")));
            }
        }catch (IOException e) {
            System.out.println("Error reading file " + filename);
        } catch (org.json.simple.parser.ParseException e) {
            System.out.println("Error parsing JSON file " + filename);
        }

    }
    public Good(String filename, Good main_list) throws IOException, ParseException {
        this.main_list = main_list;
        this.filename = filename;
        this.list_of_goods = new ArrayList<Item>();

        File db_goods = new File(filename);

        if (!db_goods.exists()) {
            db_goods.createNewFile();

        } else {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(filename);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("list");
            for(int q = 0; q < jsonArray.size(); q++) {
                JSONObject i = (JSONObject) jsonArray.get(q);
                list_of_goods.add(new Item((int)(long) i.get("id"), (String) i.get("name"), (float)(double) i.get("quantity"), (float)(double) i.get("cost")));
            }
        }
    }

    private void update_goods() throws IOException {
        Jsondb.updateItems(filename, list_of_goods);
    }
    protected void add_good(Item a) throws IOException {
        list_of_goods.add(a);
        update_goods();
    }

    public void deleteFromCheck(String id) throws IOException {
        for(Item cur_item:list_of_goods){
            if (Integer.toString(cur_item.getId()).equals(id) || Objects.equals(cur_item.getName(), id)){
                this.list_of_goods.remove(cur_item);
                cur_item.addToQuantity(main_list.list_of_goods.get(cur_item.getId()).getQuantity());
                main_list.list_of_goods.set(cur_item.getId(),cur_item);
                main_list.update_goods();
                this.update_goods();
            }
        }
    }

    public Item findItem(String id){
        for(Item cur_item: list_of_goods){
            if (Integer.toString(cur_item.getId()).equals(id) || Objects.equals(cur_item.getName(), id)){
                return cur_item;
            }
        }
        return null;
    }

    public void changeQuantityInCur(String id, float amount) throws IOException {
        for(Item cur_item: list_of_goods){
            if (Integer.toString(cur_item.getId()).equals(id) || Objects.equals(cur_item.getName(), id)){
                cur_item.addToQuantity(amount);
                list_of_goods.set(cur_item.getId(),cur_item);
                this.update_goods();
            }
        }
    }

    public void delete(){
        String jsonFileName = filename;
        File jsonFile = new File(jsonFileName);

        if (jsonFile.exists()) {
            try {
                Files.delete(Paths.get(jsonFileName));
                System.out.println("Check file deleted successfully.");
            } catch (IOException e) {
                System.out.println("Error deleting check: " + e.getMessage());
            }
        } else {
            System.out.println("Check not found.");
        }
        this.delete();
    }
}
