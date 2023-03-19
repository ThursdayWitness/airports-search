import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Program{

    public static void main(String[] args){

        if(args.length != 1 )
            throw new IllegalStateException("Пожалуйста, введите номер колонки для поиска");
        var data = Read();
        Scanner scannerObj = new Scanner(System.in);
        while(true)
        {
            System.out.println("Введите искомое значение.\nЛибо команду \"!quit\" для завершения работы.");
            var input = scannerObj.nextLine().trim();
            if(input.startsWith("!")){
                if(input.equals("!quit")) return;
                System.out.println("Команда не распознана");
            } else
                Search(input, data, Integer.parseInt(args[0])-1);
        }
    }

    public static void Search(String searchQuery, ArrayList<String[]> data, int column)
    {
        var result = new ArrayList<String>();
        Pattern pattern = Pattern.compile("^\"?"+searchQuery+".*", Pattern.UNICODE_CASE);
        long startTime = System.currentTimeMillis();
        var matcher = pattern.matcher("");
        for(var line : data){
            matcher.reset(line[column]);
            if (matcher.find()) {
                result.add(matcher.group()+Arrays.toString(line));
            }
        }
        long endTime = System.currentTimeMillis();
        Collections.sort(result);
        result.forEach(System.out::println);
        System.out.println("\nКоличество найденных строк: " + result.size() +
                "\nВремя, затраченное на поиск: " + (endTime-startTime) + " мс");
    }

    public static ArrayList<String[]> Read()
    {
        var data = new ArrayList<String[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/airports.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}

