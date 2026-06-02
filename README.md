# Plane-Shot — 飞机射击小游戏

> 2D 飞机射击小游戏 · Java Swing 怀旧风

---
> 
> Portfolio note: this is an early Java desktop practice project.
> It is kept for GUI, Java 17 and packaging history, not as a main job-seeking project.
> 
> > 作品集说明：这是早期 Java 桌面 GUI 练习项目，保留用于展示 Java 17、Swing/AWT 和 jpackage 打包记录，不作为核心求职项目。


## 1. 📌 项目一句话定位

**Plane-Shot** 是一款基于 Java 17 + Swing 开发的 2D 桌面飞机射击小游戏。玩家通过键盘方向键控制飞机移动，躲避或击落敌机并收集红包来获取分数。项目内置账号登录/注册系统、多关卡选择，并已通过 `jpackage` 打包为便携式分发包，解压即玩，无需安装 JRE。

该仓库是作者早期的 Java 桌面开发练习项目，作为 GUI 编程与打包实践的记录而保留。

---

## 2. 🖼️ Screenshots / Demo

![游戏截图](https://github.com/user-attachments/assets/0b15012f-722c-4092-a7ee-d4ae44ef4557)

![关卡选择](https://github.com/user-attachments/assets/336d448c-ab48-4316-8d3f-a88d749043cf)

> 截图展示登录界面与游戏主界面。更多游戏内截图待补充。

**如何运行查看效果：**

1. 前往 [Releases](https://github.com/2002yy/Plane-Shot/releases) 下载 `PlaneShot_v1.0.zip`
2. 解压后双击 `game.jar`（需要 Java 17+ 环境）

---

## 3. ⚡ Tech Highlights

### Backend / Core

| 技术 | 版本 | 用途 |
|------|------|------|
| Java | 17 | 开发语言 |
| Swing / AWT | — | GUI 框架：窗口、事件、渲染 |
| jpackage | — | 打包为本机可执行程序（无需 JRE） |

### 主要特性

- 纯 Java Swing 开发的完整桌面应用，无第三方依赖
- 账号登录/注册系统，用户数据通过 `userDatabase.txt` 本地持久化
- 多关卡选择（不同敌机数量），游戏循环与菜单分离
- 方向键（↑↓←→）控制飞机移动，实时碰撞检测
- 支持找回密码功能
- `jpackage` 打包为单文件 zip 分发，不写注册表

---

## 4. 🏗️ Architecture / Code Map

### 架构简介

采用经典的 MVC 风格分层，各窗口独立为 JFrame，通过类之间的直接引用来串联：

- **Main** — 应用入口，`SwingUtilities.invokeLater` 启动登录窗口
- **LoginWindow** → **LevelSelectionWindow** → **GameWindow**：用户流程按线性路径推进
- **UserManager** 作为数据层，独立管理账号的注册、登录与持久化

### 类图

```
+---------------------+
|     LoginWindow      |
+---------------------+
| - usernameField: JTextField        |
| - passwordField: JPasswordField    |
| - userManager: UserManager         |
+---------------------+
| + login(): void                    |
| + register(): void                 |
| + forgotPassword(): void           |
+---------------------+

+-------------------------+
|   LevelSelectionWindow  |
+-------------------------+
| - settingValue: int               |
+-------------------------+
| + startGame(level: int): void     |
+-------------------------+

+---------------------+
|      GameWindow      |
+---------------------+
| - backgroundImage: BufferedImage  |
| - planeImage: BufferedImage       |
| - enemyImage: BufferedImage       |
| - planeBehavior: PlaneBehavior    |
| - enemies: List<EnemyBehavior>    |
| - random: Random                  |
| - repaintTimer: Timer             |
| - level: int                      |
| - bo: int                         |
| - numberOfEnemies: int            |
| - settingValue: int               |
| - levelCompleted: boolean         |
+---------------------+
| + updatePosition(): void          |
| + draw(): void                    |
| + resetGame(): void               |
+---------------------+

+---------------------+
|   PlaneBehavior      |  <<interface>>
+---------------------+
| + move() : void                 |
| + draw() : void                 |
| + getDx() : int                 |
| + getDy() : int                 |
| + reset() : void                |
| + getScore() : int              |
| + addScore() : void             |
+---------------------+
           ^
           |
+-------------------------+
| DefaultPlaneBehavior    |
+-------------------------+
| - PLANE_SPEED: int = 5           |
| - pressedKeys: Set<Integer>      |
| - score: int                     |
| - dx, dy: int                    |
+-------------------------+
| + move() : void                  |
| + updateMovement() : void        |
+-------------------------+

+---------------------+
|   EnemyBehavior      |  <<interface>>
+---------------------+
| + updatePosition() : void        |
| + draw() : void                  |
| + getX() : int                   |
| + getY() : int                   |
| + getHealth() : int              |
| + takeDamage() : void            |
| + reset() : void                 |
+---------------------+
           ^
           |
+-------------------------+
| DefaultEnemyBehavior   |
+-------------------------+
| - ENEMY_SPEED: int               |
| - enemyX, enemyY: int            |
| - directionX, directionY: int    |
| - enemyImage: BufferedImage      |
| - health: int                    |
+-------------------------+
| + updatePosition() : void        |
| + takeDamage() : void            |
+-------------------------+

+---------------------+
|     UserManager      |
+---------------------+
| - userDatabase: HashMap<String, String> |
| - FILE_NAME: String (userDatabase.txt)  |
+---------------------+
| + register() : void              |
| + login() : boolean              |
| + changePassword() : void        |
| + loadDatabaseFromFile() : void  |
| + saveDatabaseToFile() : void    |
+---------------------+
```

### 项目文件结构

```
├── Main.java                   # 程序入口，启动登录窗口
├── GameWindow.java             # 游戏主窗口：渲染、碰撞检测、计时器
│   ├── PlaneBehavior           # 飞机行为接口
│   ├── DefaultPlaneBehavior    # 默认飞机移动实现（速度 5px/tick）
│   ├── EnemyBehavior           # 敌机/红包行为接口
│   └── DefaultEnemyBehavior    # 默认敌机行为实现
├── LoginWindow.java            # 登录/注册/找回密码 UI
├── LevelSelectionWindow.java   # 关卡选择界面
├── UserManager.java            # 账号管理 & 持久化（userDatabase.txt）
├── images/
│   ├── bg.jpg                  # 游戏背景图
│   ├── plane.jpg               # 飞机贴图
│   ├── red_package.jpg         # 红包/道具贴图
│   └── icon.ico                # 应用图标
├── class-diagram.md            # 类图文档
├── LICENSE                     # MIT License
└── README.md                   # 本文件
```

---

## 5. 🚀 Quick Start

### 方式一：直接下载运行（推荐）

1. 前往 [Releases](https://github.com/2002yy/Plane-Shot/releases) 下载 `PlaneShot_v1.0.zip`
2. 解压到任意目录
3. 双击 `game.jar` 启动（确保已安装 Java 17+）

### 方式二：从源码构建

```bash
# 1. 克隆仓库
git clone https://github.com/2002yy/Plane-Shot.git
cd Plane-Shot

# 2. 编译
javac *.java

# 3. 打包为可执行 Jar
jar cvfe game.jar Main *.class

# 4. （可选）生成本机应用镜像（无需 JRE）
jpackage --type app-image \
         --name "PlaneShot" \
         --input . \
         --main-jar game.jar \
         --main-class Main \
         --icon images/icon.ico

# 5. 运行
java -jar game.jar
```

### 默认账号

| 用户名 | 密码 |
|--------|------|
| admin | 2002 |

也可以在登录界面自行注册新账号。

### 操作控制

| 按键 | 功能 |
|------|------|
| ↑ ↓ ← → | 控制飞机移动 |

---

## 6. 🧪 Testing / CI

当前项目 **暂未集成单元测试** 与 CI 流水线。

- 测试方式：手动运行验证
- CI：待补充

---

## 7. 📦 Release / Download

项目已发布 v1.0 版本，提供便携式 zip 分发包。

- **Releases 页面**：https://github.com/2002yy/Plane-Shot/releases
- **下载文件**：`PlaneShot_v1.0.zip`
- **运行要求**：Java 17+（打包版本已内置 JRE，不需要独立安装）
- **特点**：单文件 zip，解压即玩，不写注册表

### 构建安装包

```bash
# 如果需要对分发包签名或定制，可扩展 jpackage 参数
jpackage --type exe      \
         --name PlaneShot \
         --input .        \
         --main-jar game.jar \
         --main-class Main    \
         --icon images/icon.ico
```

---

## 8. 🗺️ Roadmap

### ✅ 已实现

- [x] 方向键控制飞机移动
- [x] 游戏画面实时渲染（Swing Timer）
- [x] 玩家账号登录 / 注册 / 找回密码
- [x] 账号数据本地文件持久化（`userDatabase.txt`）
- [x] 多关卡选择
- [x] 计分系统
- [x] 碰撞检测与敌机 AI（随机移动）
- [x] `jpackage` 打包为本机应用
- [x] 单文件 zip 分发

### 🚧 计划中

- [ ] 音效与背景音乐
- [ ] 关卡分数排行榜
- [ ] 道具系统（加速、护盾、子弹升级）
- [ ] 移动端触控适配
- [ ] 国际化（i18n）支持
- [ ] 单元测试与 CI 集成
- [ ] 更丰富的敌机种类与 Boss 战

---

---

## 8.5 🔧 Possible Improvements

- Replace local text file user storage with SQLite.
- Add unit tests for UserManager.
- Add release checksum verification.
- Replace non-original assets before redistribution.

## 9. 📄 License / Notes

### MIT License

版权所有 (c) 2026 张元垚（2002yy）

本软件采用 MIT 许可证授权。详情请参见 [LICENSE](./LICENSE) 文件。

### 声明

- 本项目中使用的游戏素材（图片等）并非原创。
- **在重新分发前，请确保您拥有使用这些素材的合法权利。**
- 这是作者早期的 Java 桌面开发练习项目，部分实现可能不够完善，仅供学习参考。

### 相关链接

- GitHub 仓库：https://github.com/2002yy/Plane-Shot
- 作者：张元垚（2002yy）
