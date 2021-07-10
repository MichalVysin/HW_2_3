package com.company;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        String[] field = new String[]{"A", "b", "C", "De", "fF", "gHiJK", "LmN"};

        File file1 = new File("priklad2.txt");
        File file2 = new File("priklad3_1.txt");
        File file3 = new File("priklad3_2.txt");

        List<File> files = new ArrayList<>();
        files.add(file2);
        files.add(file3);


        // 1)
        countUpperCase(field);

        // 2)
        countCharsAtLineWithoutNumbers(file1);

        // 3)
        countAverageFromFiles(files);

        //4)
        countAllNumbersBetweenAAndBByTheirOwnValue(1,7);
    }

    public static void countUpperCase(String[] text) {

        int numberOfUpperCase = (int) Arrays.stream(text)
                .max(Comparator.comparing(String::length))
                .get()
                .chars()
                .filter(Character::isUpperCase)
                .count();
        System.out.println("Pocet kapitalek v nejdelsim retezci: " + numberOfUpperCase);
    }

    public static void countCharsAtLineWithoutNumbers(File file) throws IOException {

        int numberOfChars = 0;

        Pattern pattern = Pattern.compile("(.)*(\\d)(.)*");

        try (
                BufferedReader bufferedReader =
                     new BufferedReader(
                             new FileReader(file))) {

            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {

                if (!pattern.matcher(readLine).matches()) {

                    Stream<String> inputStream = Stream.of(readLine);

                    numberOfChars = (int) inputStream
                            .flatMap(str -> Arrays.stream(str.chars().mapToObj(c -> (char) c).toArray(Character[]::new)))
                            .count();
                    System.out.println("Pocet znaku na radku neobsahujici cislici: " + numberOfChars);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void countAverageFromFiles(List<File> files) throws FileNotFoundException {

        List<Double> inputStreamList = new ArrayList<>();
        Double result = 0.0;

        for (File file : files) {
            if (file.isFile()) {

                try (
                        Scanner scanner =
                                new Scanner(
                                        new BufferedReader(
                                                new FileReader(file)))) {

                    while (scanner.hasNextDouble()) {

                        inputStreamList.add(scanner.nextDouble());

                        Stream<Double> inputStream = inputStreamList.stream();

                        result = inputStream.collect(Collectors.averagingDouble(Double::doubleValue));


                    }

                }
            }
        }
        System.out.println("Prumer: " + (String.format("%.2f", result)));
    }

    public static void countAllNumbersBetweenAAndBByTheirOwnValue(int a, int b) {

        List<Integer> inputStreamList = new ArrayList<>();

        for (int i = a; i <= b; i++) {

            inputStreamList.add(i);
        }

        Integer result = inputStreamList
                .stream()
                .map(number -> number * number)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Soucet druhych mocnin mezi A a B (vcetne): " + result);

        List<Integer> resultList = inputStreamList
                .stream()
                .map(number -> number * number)
                .collect(Collectors.toList());

        System.out.println("Druhe mocniny jednotlivych cisel mezi A a B (vcetne): " + resultList);
    }


}
