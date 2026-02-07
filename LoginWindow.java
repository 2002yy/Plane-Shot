import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private UserManager userManager; // 添加UserManager实例
    private JSlider settingSlider; // 滑动条

    public LoginWindow(UserManager userManager) { // 传入UserManager实例
        setTitle("开飞机当然要抢红包");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

try {
    java.net.URL iconUrl = getClass().getResource("/images/icon.ico");
    if (iconUrl != null) {
        Image icon = Toolkit.getDefaultToolkit().getImage(iconUrl);
        // 异步加载，需要等待或检查尺寸
        if (icon.getWidth(null) > 0) {
            setIconImage(icon);
            System.out.println("✅ 图标加载成功！");
        } else {
            System.err.println("⚠️ 图标尚未加载完成或无效");
        }
    } else {
        System.err.println("❌ 找不到图标资源: /images/icon.ico");
    }
} catch (Exception e) {
    e.printStackTrace();
}
        // 创建标签和文本字段
        JLabel usernameLabel = new JLabel("用户名:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("密码:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        // 创建登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // 创建注册按钮
        JButton registerButton = new JButton("注册");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        // 创建忘记密码按钮
        JButton forgotPasswordButton = new JButton("忘记密码");
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forgotPassword();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(forgotPasswordButton, gbc);

       // 创建滑动条
       JLabel sliderLabel = new JLabel("你要抢到多少钱？:(10万-360万)");
       sliderLabel.setForeground(Color.BLACK); // 设置标签颜色为黑色
       gbc.gridx = 0;
       gbc.gridy = 5;
       add(sliderLabel, gbc);

        settingSlider = new JSlider(JSlider.HORIZONTAL, 10, 360, 50); // 最小值10，最大值360，默认值50
        settingSlider.setMajorTickSpacing(50);
        settingSlider.setMinorTickSpacing(10);
        settingSlider.setPaintTicks(true);
        settingSlider.setPaintLabels(true);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // 占据两列
        add(settingSlider, gbc);

        // 设置窗口居中
        setLocationRelativeTo(null);
// 添加提示信息（联系作者）
        JLabel contactLabel = new JLabel("<html><font color='gray'>联系作者： <a href='https://github.com/2002yy'>https://github.com/2002yy</a></font></html>");
        contactLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contactLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contactLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/2002yy"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "无法打开浏览器", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(contactLabel, gbc);

        setLocationRelativeTo(null);
        this.userManager = userManager; // 初始化UserManager
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // 使用UserManager进行登录验证
        if (userManager.login(username, password)) {
            // 登录成功，关闭登录窗口，打开关卡选择窗口
            dispose();
            int settingValue = settingSlider.getValue(); // 获取滑动条的值
            new LevelSelectionWindow(settingValue); // 打开关卡选择窗口
        } else {
            // 登录失败，显示错误消息
            JOptionPane.showMessageDialog(this, "用户名或密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void register() {
        String username = JOptionPane.showInputDialog(this, "请输入新的用户名:", "注册", JOptionPane.PLAIN_MESSAGE);
        String password = JOptionPane.showInputDialog(this, "请输入密码:", "注册", JOptionPane.PLAIN_MESSAGE);
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            userManager.register(username, password);
        }
    }

    private void forgotPassword() {
        JOptionPane.showMessageDialog(this, "请联系管理员重置密码。", "忘记密码", JOptionPane.INFORMATION_MESSAGE);
    }
}