package GUI;

import edu.hitsz.application.ImageManager;
import edu.hitsz.game.EasyGame;
import edu.hitsz.game.GameTemplate;
import edu.hitsz.game.HardGame;
import edu.hitsz.game.NormalGame;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

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
        soundBox.addActionListener(e -> {
            String selectedOption = (String) soundBox.getSelectedItem();
            GameTemplate.bgmOn = Objects.equals(selectedOption, "是");
        });
    }

    private GameTemplate chooseModel(@NotNull String model) {
        try {
            switch (model) {
                case "简单模式"-> {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
                    GameTemplate.gameModel = "Easy";
                    return new EasyGame();
                }
                case "普通模式"-> {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
                    GameTemplate.gameModel = "Normal";
                    return new NormalGame();
                }
                case "困难模式"-> {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
                    GameTemplate.gameModel = "Hard";
                    return new HardGame();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new EasyGame();
    }

    private class BackGroundAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            GameTemplate game = chooseModel(event.getActionCommand());
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