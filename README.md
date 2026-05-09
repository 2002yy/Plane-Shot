# 🛩️ Plane Shot — 飞机移动抢红包游戏

> 一款怀旧风 2D 飞机抢红包小游戏，**无需安装 Java，解压即玩！**  
> 控制战机收集红包，体验流畅操作与像素乐趣！

<img width="494" height="492" alt="游戏截图" src="https://github.com/user-attachments/assets/0b15012f-722c-4092-a7ee-d4ae44ef4557" />
<img width="1235" height="734" alt="局部截取_20260207_175554" src="https://github.com/user-attachments/assets/336d448c-ab48-4316-8d3f-a88d749043cf" />

---

## ✅ 为什么选择 Plane Shot？

- 🚀 **零依赖**：已内嵌 JRE，**无需安装 Java 或任何运行库**
- 💾 **绿色软件**：不写注册表、不创建系统文件，删除即卸载
- 👤 **账号系统**：支持登录/注册，保存你的战绩
- 🎯 **多关卡挑战**：难度递增，越玩越上瘾
- 📦 **单文件分发**：整个游戏打包为一个压缩包，方便分享

---

## ▶️ 如何运行？

### 🎮 方式一：直接下载（需要 Java）
1. 前往 [Releases 页面](https://github.com/2002yy/Plane-Shot/releases) 下载最新 `PlaneShot_v1.0.zip`
2. 解压到任意文件夹
3. 确保已安装 [Java 17+](https://adoptium.net/)，双击 `game.jar` 或终端运行：
```bash
java -jar game.jar
```

### 📦 方式二：开发者自行打包
见下方 [从源码构建](#-开发者从源码构建)

---

## 🕹️ 操作说明

| 按键       | 功能         |
|------------|--------------|
| 方向键 ↑↓←→ | 移动飞机     |

**目标**：移动飞机，收集红包（🧧），越多金额越高！

**账号说明**：首次运行自动创建 `userDatabase.txt`。默认账号 `admin` / `2002`，可自行注册。

---

## 📂 项目结构

```
├── Main.java                 # 游戏入口，启动登录
├── GameWindow.java           # 主游戏窗口、渲染、控制
├── LoginWindow.java          # 登录/注册界面
├── LevelSelectionWindow.java # 关卡选择
├── UserManager.java          # 账号管理（读写 userDatabase.txt）
├── bg.jpg                    # 游戏背景图
├── plane.jpg                 # 飞机素材
├── red_package.jpg           # 红包素材（小）
├── red package.jpg           # 红包素材（大）
├── icon.ico                  # 应用图标
└── 类图.md                   # UML 类结构图
```

---

## 🛠️ 开发者：从源码构建

> 普通用户无需操作以下步骤！

### 环境要求
- JDK 17+（推荐 [Eclipse Temurin](https://adoptium.net/)）

### 构建步骤
```bash
# 1. 编译源码（.java 文件在根目录）
javac *.java

# 2. 打包 JAR
jar cvfe game.jar Main *.class

# 3. 生成绿色 EXE
jpackage --type app-image --name "PlaneShot" --input . --main-jar game.jar --main-class Main
```
生成结果：`PlaneShot/` 文件夹，可直接压缩分发。

### 项目结构
```
├── Main.java              # 游戏入口
├── GameWindow.java         # 主游戏窗口
├── LoginWindow.java        # 登录界面
├── LevelSelectionWindow.java # 关卡选择
├── UserManager.java        # 账号管理（自动创建 userDatabase.txt）
├── bg.jpg / plane.jpg      # 游戏素材图片
├── icon.ico                # 应用图标
└── 类图.md                 # 类结构图
```

---

## 📄 许可证

本项目采用 MIT License。  
注：游戏素材（图片等）若非原创，请确保你有合法使用权。

## ❤️ 致谢

- 使用 Java 17 + jpackage 打包为独立应用
- 灵感来自经典街机射击游戏
- Made with ☕ | 解压即玩 · 无需配置 · 纯粹乐趣
