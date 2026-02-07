// src/LevelSelectionWindow.java
import javax.swing.*;
import java.awt.*;

public class LevelSelectionWindow extends JFrame {
    private int settingValue;

    public LevelSelectionWindow(int settingValue) {
        this.settingValue = settingValue;
        setTitle("选择关卡");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JButton level1Button = new JButton("关卡 1");
        level1Button.addActionListener(e -> startGame(1));
        add(level1Button);

        JButton level2Button = new JButton("关卡 2");
        level2Button.addActionListener(e -> startGame(2));
        add(level2Button);

        JButton level3Button = new JButton("关卡 3");
        level3Button.addActionListener(e -> startGame(3));
        add(level3Button);

        JButton backButton = new JButton("返回登录");
        backButton.addActionListener(e -> {
            dispose();
            new LoginWindow(new UserManager()).setVisible(true);
        });
        add(backButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGame(int level) {
        dispose();
        new GameWindow(settingValue, level);
    }
}