package edu.hitsz.DAO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class RankDaolmpl implements RankDao {

    private final File record;
    private final List<Record> recordsList;

    public RankDaolmpl() {
        record = new File("src/edu/hitsz/DAO/record.txt");
        recordsList = new ArrayList<>();
    }

    @Override
    public List<String> getAllRecords() throws IOException {
        return Files.readAllLines(record.toPath());
    }

    @Override
    public void addRecord(String user, String score, String date) throws IOException {
        Files.write(record.toPath(), (user + "," + score + "," + date +  "\n").getBytes(), StandardOpenOption.APPEND);
    }

    @Override
    public void deleteRecord(String user, String date) {

    }

    @Override
    public void rankSort() {
        recordsList.sort((x, y)-> y.getScore() - x.getScore());
    }

    @Override
    public void printRanks() throws IOException {
        List<String> records = getAllRecords();
        System.out.println(records);
        for (String record : records) {
            String[] split = record.split(",");
            recordsList.add(new Record(split[0], Integer.parseInt(split[1]), split[2]));
        }

        rankSort();
        System.out.println("*************************************");
        System.out.println("              得分排行榜               ");
        System.out.println("*************************************");
        for (Record record : recordsList) {
            System.out.println(record);
        }

    }
}
