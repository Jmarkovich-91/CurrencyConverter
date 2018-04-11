/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

/**
 * @author michael
 */
public class FlagImages {

    List<String> list = new ArrayList<>();

    public FlagImages() {
        //getFileData("resourced/Countries.txt");
        getFileData(0);
    }

    public List getList() {
        return list;
    }

    public void getFileData(int i) {
        list.removeAll(list);
        String fileName = "CountryList.txt";
        //Get file from resources folder
        int column = i;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                                
                String[] columnDetail = scanner.nextLine().split("\t", -1);
                list.add(columnDetail[column]);
            }
            scanner.close();            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder("");

        for (String country : list) {
            result.append(country).append("\n");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        FlagImages flagList = new FlagImages();
        System.out.println(flagList);
    }
}
