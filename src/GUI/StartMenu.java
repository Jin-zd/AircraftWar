package GUI;

import edu.hitsz.DAO.RankDaolmpl;
import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

public class StartMenu {
    private JPanel mainPanel;
    private JPanel modelPanel;
    private JPanel soundPanel;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;
    private JComboBox soundBox;
    private JLabel soundLabel;

    public StartMenu() {
        easyButton.addActionListener(new BackGroundAction());
        normalButton.addActionListener(new BackGroundAction());
        hardButton.addActionListener(new BackGroundAction());
    }

    private void changeBackground(@NotNull String model) {
        try {
            switch (model) {
                case "简单模式":
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
                    Game.gameModel = "Easy";
                    break;
                case "普通模式":
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
                    Game.gameModel = "Normal";
                    break;
                case "困难模式":
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
                    Game.gameModel = "Hard";
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class BackGroundAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            changeBackground(event.getActionCommand());
            Game game = new Game();
            CardLayoutGUI.cardPanel.add(game);
            CardLayoutGUI.cardLayout.next(CardLayoutGUI.cardPanel);
            game.action();
        }
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartMenu");
        frame.setContentPane(new StartMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}