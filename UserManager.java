import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private HashMap<String, String> userDatabase = new HashMap<>();
    private final String FILE_NAME = "userDatabase.txt";

    // 构造函数，加载用户数据
    public UserManager() {
        loadDatabaseFromFile();
    }

    public void register(String username, String password) {
        if (userDatabase.containsKey(username)) {
            System.out.println("注册失败，用户名已存在。");
        } else {
            userDatabase.put(username, password);
            saveDatabaseToFile();
            System.out.println("注册成功！");
        }
    }

    public boolean login(String username, String password) {
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            System.out.println("登录成功！");
            return true;
        } else {
            System.out.println("登录失败，用户名或密码错误。");
            return false;
        }
    }

    public void changePassword(String username, String newPassword) {
        if (userDatabase.containsKey(username)) {
            userDatabase.put(username, newPassword);
            saveDatabaseToFile();
            System.out.println("密码修改成功！");
        } else {
            System.out.println("密码修改失败，用户不存在。");
        }
    }

    public void printAllUserInfo() {
        if (userDatabase.containsKey("admin")) {
            for (Map.Entry<String, String> entry : userDatabase.entrySet()) {
                System.out.println("用户名: " + entry.getKey() + ", 密码: " + entry.getValue());
            }
        } else {
            System.out.println("没有管理员权限。");
        }
    }

    // 加载用户数据库文件
    public void loadDatabaseFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    userDatabase.put(data[0], data[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("无法加载用户数据库文件。");
            e.printStackTrace();
        }
    }

    // 保存用户数据库文件
    public void saveDatabaseToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, String> entry : userDatabase.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("无法保存用户数据库文件。");
            e.printStackTrace();
        }
    }
}
