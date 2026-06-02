# Plane-Shot Interview Notes

## 一句话介绍

Plane-Shot 是一款基于 Java 17 + Swing 的 2D 飞机射击桌面游戏，内置账号系统、多关卡选择，通过 jpackage 打包为便携式分发包。

## 技术亮点

- 纯 Java Swing / AWT 开发，无第三方依赖
- MVC 风格分层：UI（LoginWindow/GameWindow） → 行为（PlaneBehavior/EnemyBehavior） → 数据（UserManager）
- 账号注册/登录系统，本地文件持久化
- 实时碰撞检测与游戏循环（Swing Timer）
- jpackage 打包为本机应用，不写注册表，解压即玩
- 多关卡选择（不同敌机数量与难度配置）

## 定位说明

本项目是早期 Java 桌面开发练习作品，展示 GUI 编程、事件驱动和打包分发的基本能力。相比其他项目，此仓库更偏向入门阶段的工程实践记录。
