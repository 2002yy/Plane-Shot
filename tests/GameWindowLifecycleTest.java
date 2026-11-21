import java.awt.Window;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Runs under a virtual display, in a disposable working directory.
 * Uses the real Swing event loop to catch nested-modal timer reentrancy.
 */
public final class GameWindowLifecycleTest {
    private static Object get(Object owner, String name) {
        try {
            Field field = owner.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(owner);
        } catch (ReflectiveOperationException ex) {
            throw new AssertionError(ex);
        }
    }

    private static void set(Object owner, String name, Object value) {
        try {
            Field field = owner.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(owner, value);
        } catch (ReflectiveOperationException ex) {
            throw new AssertionError(ex);
        }
    }

    private static void edt(Runnable task) throws Exception {
        SwingUtilities.invokeAndWait(task);
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static boolean await(BooleanSupplier condition, String description) throws Exception {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(8);
        while (System.nanoTime() < deadline) {
            boolean[] result = new boolean[1];
            edt(() -> result[0] = condition.getAsBoolean());
            if (result[0]) return true;
            Thread.sleep(20);
        }
        throw new AssertionError("Timed out: " + description);
    }

    private static JDialog visibleDialog(String title) {
        for (Window window : Window.getWindows()) {
            if (window instanceof JDialog && window.isShowing()
                    && title.equals(((JDialog) window).getTitle())) {
                return (JDialog) window;
            }
        }
        return null;
    }

    private static void fakeAssets() throws Exception {
        Path images = Path.of("images");
        require(!Files.exists(images), "Run this test from a fresh temporary directory");
        Files.createDirectories(images);
        BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_RGB);
        for (String name : List.of("bg.jpg", "plane.jpg", "red_package.jpg")) {
            ImageIO.write(image, "PNG", images.resolve(name).toFile());
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        fakeAssets();
        GameWindow[] game = new GameWindow[1];
        try {
            edt(() -> game[0] = new GameWindow(50, 1));
            Timer timer = (Timer) get(game[0], "repaintTimer");
            require(timer.isRunning(), "Game timer should start while the level is active");

            edt(() -> ((List<?>) get(game[0], "enemies")).clear());
            await(() -> visibleDialog("Mission Complete") != null, "first wave completion");
            require(!timer.isRunning(), "Timer must stop while a wave dialog is visible");
            Thread.sleep(120);
            edt(() -> {
                int visible = 0;
                for (Window w : Window.getWindows()) {
                    if (w instanceof JDialog && w.isShowing()) visible++;
                }
                require(visible == 1, "Timer must not stack multiple completion dialogs");
                visibleDialog("Mission Complete").dispose();
            });

            await(() -> (Integer) get(game[0], "bo") == 2
                    && !((List<?>) get(game[0], "enemies")).isEmpty()
                    && timer.isRunning(), "next wave resumes ticking");

            edt(() -> {
                set(game[0], "bo", 3);
                ((List<?>) get(game[0], "enemies")).clear();
            });
            await(() -> visibleDialog("You Win") != null, "last wave completion");
            require(!timer.isRunning(), "Timer must stop while the final dialog is visible");
            edt(() -> visibleDialog("You Win").dispose());
            await(() -> !game[0].isDisplayable() && !timer.isRunning(), "win disposes and stops timer");

            edt(() -> game[0] = new GameWindow(50, 2));
            Timer second = (Timer) get(game[0], "repaintTimer");
            edt(() -> game[0].dispose());
            require(!second.isRunning(), "Explicit dispose must stop the timer");
            System.out.println("PASS: Swing modal transitions and window timer disposal");
        } finally {
            edt(() -> {
                for (Window w : Window.getWindows()) w.dispose();
            });
        }
    }
}
