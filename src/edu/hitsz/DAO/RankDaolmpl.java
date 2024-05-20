package edu.hitsz.DAO;

import edu.hitsz.game.GameTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class RankDaolmpl implements RankDao {

    private File record;
    private final List<Record> recordsList;

    public RankDaolmpl() {
        switch (GameTemplate.gameModel) {
            case "Easy":
                record = new File("src/edu/hitsz/DAO/EasyRecord.txt");
                break;
            case "Normal":
                record = new File("src/edu/hitsz/DAO/NormalRecord.txt");
                break;
            case "Hard":
                record = new File("src/edu/hitsz/DAO/HardRecord.txt");
                break;
        }
        recordsList = new ArrayList<>();
    }

    @Override
    public List<String> getAllRecords() throws IOException {
        return Files.readAllLines(record.toPath());
    }

    @Override
    public void addRecord(String user, String score, String date) throws IOException {
        Files.write(record.toPath(), (user + "," + score + "," + date + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    @Override
    public void deleteRecord(String user, String date) throws IOException {
        for (int i = 0; i < recordsList.size(); i++) {
            if (recordsList.get(i).getUser().equals(user) && recordsList.get(i).getDate().equals(date)) {
                deleteRecord(i);
                break;
            }
        }
    }

    @Override
    public void deleteRecord(int row) throws IOException {
        rankSort();
        recordsList.remove(row);
        Files.write(record.toPath(), new byte[0]);
        for (Record record : recordsList) {
            addRecord(record.getUser(), String.valueOf(record.getScore()), record.getDate());
        }
    }


    @Override
    public void rankSort() {
        recordsList.sort((x, y) -> y.getScore() - x.getScore());
    }

    @Override
    public void printRanks() throws IOException {
        List<String> records = getAllRecords();
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

    @Override
    public String[][] getSortRecordsArray() throws IOException {
        if (recordsList.isEmpty()) {
            List<String> records = getAllRecords();
            for (String record : records) {
                String[] split = record.split(",");
                recordsList.add(new Record(split[0], Integer.parseInt(split[1]), split[2]));
            }
        }
        rankSort();

        int length = recordsList.size();
        String[][] sortRecords = new String[length][];
        for (int i = 0; i < length; i++) {
            sortRecords[i] = ((i + 1) + "," + recordsList.get(i).toString()).split(",");
        }
        return sortRecords;
    }
}
