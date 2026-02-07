import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.List;

// 定义飞机行为接口
interface PlaneBehavior {
   void move(int keyCode, boolean pressed); // 处理按键事件的方法
   void draw(Graphics g, int planeX, int planeY, BufferedImage planeImage); // 绘制飞机的方法
   int getDx(); // 获取水平移动量
   int getDy(); // 获取垂直移动量
   void reset(); // 重置飞机行为对象的状态
   int getScore(); // 获取当前得分
   void addScore(int points); // 增加得分
}

// 实现飞机行为的具体类
class DefaultPlaneBehavior implements PlaneBehavior {
   private final int PLANE_SPEED = 5; // 飞机移动速度
   private Set<Integer> pressedKeys = new HashSet<>(); // 存储当前按下的键
   private int score; // 当前得分
   private int dx; // 水平移动量
   private int dy; // 垂直移动量

   public DefaultPlaneBehavior() {
       this.score = 0; // 初始化得分为0
   }

   @Override
   public void move(int keyCode, boolean pressed) {
       if (pressed) {
           pressedKeys.add(keyCode);
       } else {
           pressedKeys.remove(keyCode);
       }
       updateMovement(); // 更新移动量
   }

   private void updateMovement() {
      dx = 0;
      dy = 0;
      if (pressedKeys.contains(KeyEvent.VK_LEFT)) dx -= PLANE_SPEED;
      if (pressedKeys.contains(KeyEvent.VK_RIGHT)) dx += PLANE_SPEED;
      if (pressedKeys.contains(KeyEvent.VK_UP)) dy -= PLANE_SPEED;
      if (pressedKeys.contains(KeyEvent.VK_DOWN)) dy += PLANE_SPEED;
  }

   @Override
   public void draw(Graphics g, int planeX, int planeY, BufferedImage planeImage) {
       g.drawImage(planeImage, planeX, planeY, null); // 绘制飞机图片
   }

   public int getDx() {
       return dx; // 获取水平移动量
   }

   public int getDy() {
       return dy; // 获取垂直移动量
   }

   public void reset() {
    pressedKeys.clear(); // 重置按键状态
    dx = 0; // 重置水平移动量
    dy = 0; // 重置垂直移动量
}

  @Override
  public int getScore() {
      return score; // 获取当前得分
  }

  @Override
  public void addScore(int points) {
      score += points; // 增加得分
  }
}

// 定义小怪行为接口
interface EnemyBehavior {
   void updatePosition(int width, int height); // 更新小怪的位置
   void draw(Graphics g, int enemyX, int enemyY, BufferedImage enemyImage); // 绘制小怪的方法
   int getX(); // 获取小怪的X坐标
   int getY(); // 获取小怪的Y坐标
   int getHealth(); // 获取小怪的血量
   void takeDamage(int damage); // 小怪受到伤害
   void reset(); // 重置小怪的行为对象的状态
}

// 实现小怪行为的具体类
class DefaultEnemyBehavior implements EnemyBehavior {
   private final int ENEMY_SPEED = 2; // 小怪移动速度
   private int enemyX;               // 小怪的X位置
   private int enemyY;               // 小怪的Y位置
   private int directionX = 1;       // 水平方向上的移动方向
   private int directionY = 1;       // 垂直方向上的移动方向
   private BufferedImage enemyImage; // 小怪图片
   private int health;               // 小怪血量

   public DefaultEnemyBehavior(int initialX, int initialY, BufferedImage enemyImage, int initialHealth) {
       this.enemyX = initialX;
       this.enemyY = initialY;
       this.enemyImage = enemyImage;
       this.health = initialHealth;
   }

   @Override
   public void updatePosition(int width, int height) {
       enemyX += directionX * ENEMY_SPEED;
       enemyY += directionY * ENEMY_SPEED;

       // 水平边界检测
       if (enemyX <= 0) {
           enemyX = 0;
           directionX *= -1; // 反转水平方向
       } else if (enemyX >= width - enemyImage.getWidth()) {
           enemyX = width - enemyImage.getWidth();
           directionX *= -1; // 反转水平方向
       }

       // 垂直边界检测
       if (enemyY <= 0) {
           enemyY = 0;
           directionY *= -1; // 反转垂直方向
       } else if (enemyY >= height - enemyImage.getHeight()) {
           enemyY = height - enemyImage.getHeight();
           directionY *= -1; // 反转垂直方向
       }
   }

   @Override
   public void draw(Graphics g, int enemyX, int enemyY, BufferedImage enemyImage) {
       g.drawImage(enemyImage, enemyX, enemyY, null); // 绘制小怪图片
   }

   public int getX() {
      return enemyX; // 获取小怪的X坐标
  }

  public int getY() {
      return enemyY; // 获取小怪的Y坐标
  }

  public int getHealth() {
      return health; // 获取小怪的血量
  }

  public void takeDamage(int damage) {
      health -= damage; // 小怪受到伤害
      if (health <= 0) {
          // 小怪死亡后可以进行一些操作，比如从敌人列表中移除
          System.out.println("Enemy died!");
      }
  }

   public void reset() {
       enemyX = 450;
       enemyY = 50;
       directionX = 1;
       directionY = 1; // 重置小怪的行为对象的状态
   }
}

public class GameWindow extends JFrame {
   private BufferedImage backgroundImage; // 背景图片
   private BufferedImage planeImage;      // 飞机图片
   private BufferedImage enemyImage;      // 小怪图片
   private int planeX = 450;             // 飞机的初始X位置
   private int planeY = 300;             // 飞机的初始Y位置

   private PlaneBehavior planeBehavior;   // 飞机行为对象
   private List<EnemyBehavior> enemies;   // 小怪列表
   private Random random;                 // 随机数生成器
   private javax.swing.Timer repaintTimer;// 定时器用于重绘窗口
   private final int TIMER_DELAY = 10;     // 定时器延迟（毫秒）
   private int level;                     // 当前关卡
   private int bo=1;                        // 当前小怪波次
   private int numberOfEnemies;           // 每关的小怪数量
   private int settingValue;              // 设置最大分数
   private boolean levelCompleted = false; // 标志变量，表示当前关卡是否已完成

    // 构造函数，用于初始化游戏窗口
    public GameWindow(int settingValue, int level) {
        this.settingValue = settingValue;
        this.level = level;
        switch (this.level) {
            case 1:
                this.numberOfEnemies = 3;
                break;
            case 2:
                this.numberOfEnemies = 5;
                break;
            case 3:
                this.numberOfEnemies = 10;
                break;
            default:
                this.numberOfEnemies = 3;
                break;
        }

        setTitle("你能抢多少？"); // 设置窗口标题
        setSize(1000, 600); // 设置窗口大小
        setLocationRelativeTo(null); // 设置窗口居中显示

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 防止默认关闭行为
        setResizable(false); // 设置窗口不可调整大小

        // 添加窗口监听器，处理关闭事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit the game?",
                        "Exit Game",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose(); // 关闭窗口
                    System.exit(0); // 退出程序
                }
            }
        });

        // 加载背景图片和飞机图片
        String imagePath = "images/bg.jpg"; // 路径，
        String planePath = "images/plane.jpg"; // 路径，
        String enemyPath = "images/red_package.jpg"; // 路径，
        try {
         imagePath = URLDecoder.decode(imagePath, "UTF-8");
         planePath = URLDecoder.decode(planePath, "UTF-8");
         enemyPath = URLDecoder.decode(enemyPath, "UTF-8");

         backgroundImage = ImageIO.read(new File(imagePath));
         planeImage = ImageIO.read(new File(planePath));
         enemyImage = ImageIO.read(new File(enemyPath));
        } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(this, "Error decoding image paths!", "Error", JOptionPane.ERROR_MESSAGE);
         System.exit(1);
        } catch (IOException e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(this, "Error loading images!", "Error", JOptionPane.ERROR_MESSAGE);
         System.exit(1);
        }
         
        planeBehavior = new DefaultPlaneBehavior(); // 初始化飞机行为对象
        enemies = new ArrayList<>();
        random = new Random();

        // 创建指定数量的小怪
        for (int i = 0; i < this.numberOfEnemies; i++) { // 根据传入的数量创建小怪
            int initialX = random.nextInt(getWidth() - enemyImage.getWidth());
            int initialY = random.nextInt(getHeight() - enemyImage.getHeight());
            enemies.add(new DefaultEnemyBehavior(initialX, initialY, enemyImage, 100)); // 初始血量为100
        }

        // 创建绘图面板并添加到窗口
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); // 绘制背景图片
                planeBehavior.draw(g, planeX, planeY, planeImage); // 绘制飞机
                for (EnemyBehavior enemy : enemies) {
                  enemy.draw(g, enemy.getX(), enemy.getY(), enemyImage); // 绘制每个小怪
              }
              // 显示当前得分
              g.setColor(Color.RED);
              g.setFont(new Font("Microsoft YaHei", Font.BOLD, 24)); // 使用支持中文的字体
              g.drawString("您已抢到：RMB: " + planeBehavior.getScore()+"￥", 10, 30);
            }
        };
        panel.setDoubleBuffered(true); // 开启双缓冲

        add(panel);

        // 添加键盘监听器，处理按键事件
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                planeBehavior.move(e.getKeyCode(), true); // 处理按键按下事件
            }

            @Override
            public void keyReleased(KeyEvent e) {
                planeBehavior.move(e.getKeyCode(), false); // 处理按键释放事件
            }
        });
        setFocusable(true);

        // 设置定时器来控制重绘
        repaintTimer = new Timer(TIMER_DELAY, e -> {
            planeX += planeBehavior.getDx(); // 更新飞机的X位置
            planeY += planeBehavior.getDy(); // 更新飞机的Y位置

            // 确保飞机不会移出窗口边界
            planeX = Math.max(0, Math.min(planeX, getWidth() - planeImage.getWidth()));
            planeY = Math.max(0, Math.min(planeY, getHeight() - planeImage.getHeight()));

            // 创建一个临时列表来存储需要移除的小怪
            List<EnemyBehavior> enemiesToRemove = new ArrayList<>();

            // 更新所有小怪的位置
            for (EnemyBehavior enemy : enemies) {
                enemy.updatePosition(getWidth(), getHeight());

                // 检查飞机是否与小怪碰撞
                Rectangle planeRect = new Rectangle(planeX, planeY, planeImage.getWidth(), planeImage.getHeight());
                Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), enemyImage.getWidth(), enemyImage.getHeight());

                if (planeRect.intersects(enemyRect)) {
                    // 飞机与小怪发生碰撞
                    ((DefaultEnemyBehavior) enemy).takeDamage(settingValue); // 小怪受到设置值点伤害
                    if (((DefaultEnemyBehavior) enemy).getHealth() <= 0) {
                        enemiesToRemove.add(enemy); // 标记需要移除的小怪
                        planeBehavior.addScore(10); // 增加玩家得分
                    }
                }
            }
            // 批量移除标记的小怪
            enemies.removeAll(enemiesToRemove);

            // 如果所有小怪都被消灭，则进入下一波或下一关
            if (enemies.isEmpty() && !levelCompleted) {
                if (bo < 3*level) {
                    JOptionPane.showMessageDialog(GameWindow.this, "恭喜！你完成了第 "+bo+" 波！", "Mission Complete", JOptionPane.INFORMATION_MESSAGE);
                    bo++;
                    numberOfEnemies=numberOfEnemies*2;
                    resetGame(numberOfEnemies);
                } else {
                    JOptionPane.showMessageDialog(GameWindow.this, "恭喜！你完成了第"+level+"关卡！", "You Win", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    levelCompleted = true; // 设置标志变量为 true，防止重复弹窗
                    // LevelSelectionWindow 是一个已存在的类，用于选择下一个级别
                     new LevelSelectionWindow(settingValue).setVisible(true);
                }
            }

            repaint(); // 重绘窗口
        });
        repaintTimer.start(); // 启动定时器

        setVisible(true); // 设置窗口可见
    }

    // 重置游戏状态的方法
    private void resetGame(int numberOfEnemies) {
        planeX = 450;
        planeY = 300;
        planeBehavior.reset(); // 重置飞机行为对象的状态
        for (EnemyBehavior enemy : enemies) {
            ((DefaultEnemyBehavior) enemy).reset(); // 重置每个小怪的行为对象的状态
        }
        enemies.clear(); // 清空小怪列表
        // 创建新的小怪
        for (int i = 0; i < numberOfEnemies; i++) { // 根据传入的数量创建小怪
            int initialX = random.nextInt(getWidth() - enemyImage.getWidth());
            int initialY = random.nextInt(getHeight() - enemyImage.getHeight());
            enemies.add(new DefaultEnemyBehavior(initialX, initialY, enemyImage, 100)); // 初始血量为100
        }
        repaint(); // 重绘窗口
    }
}
