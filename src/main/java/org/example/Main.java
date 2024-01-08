package org.example;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Good maindb = new Good("database/db.json");
        List<File> files = Jsondb.getJsonFiles("database/" + Jsondb.getToday());
        int cur_id;
        if (files != null) {
            cur_id = files.size();}else {
            cur_id = 0;
        }
        String todayDB_string = "database/" + Jsondb.getToday() + "/" + Integer.toString(cur_id) + ".json";
        Good cur_check = new Good(todayDB_string, maindb);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("------------------------------------------------");
            System.out.println("Please, enter your password or 'exit'");
            System.out.println("------------------------------------------------");
            String input_password = scanner.next();
            if (input_password.contentEquals("exit"))break;
            int password = Integer.parseInt(input_password);
            if (password == 1) {
                while (true) {
                    System.out.println("------------------------------------------------");
                    System.out.println("You are Commodity expert");
                    System.out.println("Choose your option:");
                    System.out.println("1 - Add new item");
                    System.out.println("2 - Change quantity of items");
                    System.out.println("3 - Exit");
                    int input = Integer.parseInt(scanner.next());
                    if (input == 1) {
                        System.out.println("------------------------------------------------");
                        System.out.println("Please, enter a name:");
                        String input_2 = scanner.next();
                        System.out.println("Please, enter a quantity:");
                        float input_3 = Float.parseFloat(scanner.next());
                        System.out.println("Please, enter a cost:");
                        float input_4 = Float.parseFloat(scanner.next());
                        maindb.add_good(new Item((int) maindb.list_of_goods.size(), (String) input_2, (float) input_3, (float) input_4));
                    }
                    if (input == 2) {
                        System.out.println("------------------------------------------------");
                        System.out.println("------------------------------------------------");
                        System.out.println("Please, enter a name:");
                        String input_2 = scanner.next();
                        System.out.println("Please, enter a quantity to add:");
                        float input_3 = Float.parseFloat(scanner.next());
                        maindb.changeQuantityInCur(input_2, input_3);
                    }
                    if (input == 3) {
                        break;
                    }
                }
            }
            if (password == 2) {
                while (true) {

                    System.out.println("------------------------------------------------");
                    System.out.println("Current check:");
                    for (Item q:cur_check.list_of_goods){
                        System.out.println(q.toString());
                    }
                    System.out.println("------------------------------------------------");
                    System.out.println("You are Cashier");
                    System.out.println("Choose your option:");
                    System.out.println("1 - Add product");
                    System.out.println("2 - Change quantity of product");
                    System.out.println("3 - Close check");
                    System.out.println("4 - Exit");
                    int input = Integer.parseInt(scanner.next());
                    if (input == 1) {
                        System.out.println("Please, enter a product:");
                        String input1 = scanner.next();
                        System.out.println("Please, enter a quantity of a product:");
                        float input2 = Float.parseFloat(scanner.next());
                        Item temp = maindb.findItem(input1);
                        cur_check.add_good(new Item(temp.getId(), temp.getName(), input2, temp.getCost()));
                        maindb.changeQuantityInCur(temp.getName(), input2 * (-1));
                    }
                    if (input == 2) {
                        System.out.println("Please, enter a product:");
                        String input1 = scanner.next();
                        System.out.println("Please, enter amount of product:");
                        float input2 = Float.parseFloat(scanner.next());
                        cur_check.changeQuantityInCur(input1, input2);
                        maindb.changeQuantityInCur(input1, input2 * (-1));
                    }
                    if (input == 3) {
                        cur_id++;
                        todayDB_string = "database/" + Jsondb.getToday() + "/" + Integer.toString(cur_id) + ".json";
                        cur_check = new Good(todayDB_string, maindb);
                    }
                    if (input == 4) {break;}
                }
            }
            if (password == 3) {
                while (true) {
                    System.out.println("------------------------------------------------");
                    System.out.println("You are Senior cashier");
                    System.out.println("Choose your option:");
                    System.out.println("1 - Cancel a check");
                    System.out.println("2 - Cancel the item in the check");
                    System.out.println("3 - Make X/Z reports");
                    System.out.println("4 - Exit");
                    int input = Integer.parseInt(scanner.next());
                    if (input == 1) {
                        cur_check.delete();
                        cur_check = new Good(todayDB_string, maindb);
                        System.out.println("File deleted");
                    }
                    if (input == 2) {
                        System.out.println("Please, enter a product:");
                        String input1 = scanner.next();
                        cur_check.deleteFromCheck(input1);
                    }
                    if (input == 3) {
                        System.out.println("X/Z report:");
                        for(int i = 0; i < cur_id; i++){
                            String fname = "database/" + Jsondb.getToday() + "/" + Integer.toString(i) + ".json";
                            Good im = new Good(fname);
                            for (Item q:im.list_of_goods){
                                System.out.println(q.toString());
                            }
                        }
                    }
                    if (input == 4) {break;}

                }
            }
        }

    }
}