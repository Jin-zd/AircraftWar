package GUI;

import edu.hitsz.DAO.RankDaolmpl;
import edu.hitsz.application.Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class EndMenu {
    private JPanel mainPanel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private JLabel gameModel;

    private final DefaultTableModel tableModel;

    public EndMenu(int score) throws IOException {
        RankDaolmpl rankDaolmpl = new RankDaolmpl();
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");

        String user = JOptionPane.showInputDialog("游戏结束，您的得分是 %d，请输入用户名记录得分：".formatted(score));
        if (!Objects.isNull(user)) {
            try {
                rankDaolmpl.addRecord(user, String.valueOf(score), formatter.format(now));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String[] tableColumn = new String[]{"用户名", "得分", "日期"};
        String[][] tableData = rankDaolmpl.getSortRecordsArray();
        tableModel = new DefaultTableModel(tableData, tableColumn);
        scoreTable.setModel(tableModel);
        scrollPane.setViewportView(scoreTable);

        gameModel.setText(Game.gameModel);


        deleteButton.addActionListener(event -> {
            int selectedRow = scoreTable.getSelectedRow();
            int result = JOptionPane.showConfirmDialog(deleteButton, "确定删除该条记录？");
            if (selectedRow != -1 && result == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                try {
                    rankDaolmpl.deleteRecord(selectedRow);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("EndMenu");
        frame.setContentPane(new EndMenu(10).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
