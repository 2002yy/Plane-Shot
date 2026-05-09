+---------------------+
|     LoginWindow      |
+---------------------+
| - usernameField: JTextField |
| - passwordField: JPasswordField |
| - userManager: UserManager    |
+---------------------+
| + login(): void                    |
| + register(): void                 |
| + forgotPassword(): void           |
+---------------------+

+---------------------+
|   LevelSelectionWindow |
+---------------------+
| - settingValue: int |
+---------------------+
| + startGame(level: int): void       |
+---------------------+

+---------------------+
|      GameWindow      |
+---------------------+
| - backgroundImage: BufferedImage |
| - planeImage: BufferedImage      |
| - enemyImage: BufferedImage      |
| - planeBehavior: PlaneBehavior   |
| - enemies: List<EnemyBehavior>   |
| - random: Random                |
| - repaintTimer: Timer           |
| - level: int                   |
| - bo: int                      |
| - numberOfEnemies: int         |
| - settingValue: int            |
| - levelCompleted: boolean      |
+---------------------+
| + updatePosition(width: int, height: int): void |
| + draw(g: Graphics, x: int, y: int, image: BufferedImage): void |
| + resetGame(numberOfEnemies: int): void               |
+---------------------+

+---------------------+
|   PlaneBehavior      |
+---------------------+
| + move(keyCode: int, pressed: boolean): void |
| + draw(g: Graphics, planeX: int, planeY: int, planeImage: BufferedImage): void |
| + getDx(): int                           |
| + getDy(): int                           |
| + reset(): void                          |
| + getScore(): int                        |
| + addScore(points: int): void            |
+---------------------+

+---------------------+
| DefaultPlaneBehavior|
+---------------------+
| - PLANE_SPEED: int  |
| - pressedKeys: Set<Integer> |
| - score: int        |
| - dx: int           |
| - dy: int           |
+---------------------+
| + move(keyCode: int, pressed: boolean): void |
| + updateMovement(): void                    |
+---------------------+

+---------------------+
|   EnemyBehavior      |
+---------------------+
| + updatePosition(width: int, height: int): void |
| + draw(g: Graphics, enemyX: int, enemyY: int, enemyImage: BufferedImage): void |
| + getX(): int                                  |
| + getY(): int                                  |
| + getHealth(): int                             |
| + takeDamage(damage: int): void                |
| + reset(): void                                |
+---------------------+

+---------------------+
| DefaultEnemyBehavior|
+---------------------+
| - ENEMY_SPEED: int  |
| - enemyX: int       |
| - enemyY: int       |
| - directionX: int   |
| - directionY: int   |
| - enemyImage: BufferedImage |
| - health: int       |
+---------------------+
| + updatePosition(width: int, height: int): void |
| + takeDamage(damage: int): void                |
+---------------------+

+---------------------+
|     UserManager      |
+---------------------+
| - userDatabase: HashMap<String, String> |
| - FILE_NAME: String |
+---------------------+
| + register(username: String, password: String): void |
| + login(username: String, password: String): boolean |
| + changePassword(username: String, newPassword: String): void |
| + loadDatabaseFromFile(): void                       |
| + saveDatabaseToFile(): void                         |
+---------------------+