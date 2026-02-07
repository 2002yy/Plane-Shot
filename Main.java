import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow(new UserManager());
            loginWindow.setVisible(true);
        });
    }
}
