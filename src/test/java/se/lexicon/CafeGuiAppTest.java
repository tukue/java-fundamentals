package se.lexicon;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CafeGuiAppTest {

    private static final long FX_TIMEOUT = 10;

    private static RecordingApp app;
    private static Stage stage;

    @BeforeAll
    static void startToolkitAndBuildWindow() throws Exception {
        var latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        assertTrue(latch.await(FX_TIMEOUT, TimeUnit.SECONDS), "JavaFX toolkit failed to start");
        Platform.setImplicitExit(false);

        runFx(() -> {
            app = new RecordingApp();
            stage = new Stage();
            app.start(stage);
        });
    }

    @AfterAll
    static void closeWindowAndStopToolkit() throws Exception {
        runFx(() -> stage.close());
        Platform.exit();
    }

    @BeforeEach
    void resetApp() throws Exception {
        runFx(() -> {
            app.reset();
            app.lastError = null;
        });
    }

    private static void runFx(Runnable action) throws Exception {
        var exception = new AtomicReference<RuntimeException>();
        var executed = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable t) {
                exception.set(new RuntimeException(t));
            } finally {
                executed.countDown();
            }
        });
        assertTrue(executed.await(FX_TIMEOUT, TimeUnit.SECONDS), "FX action timed out");
        if (exception.get() != null) {
            throw exception.get();
        }
    }

    private static <T> T computeFx(Callable<T> action) throws Exception {
        var result = new AtomicReference<T>();
        runFx(() -> {
            try {
                result.set(action.call());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return result.get();
    }

    @Test
    void start_buildsWindowWithMenuAndGreeting() throws Exception {

        assertEquals(5, computeFx(() -> app.menuList.getItems().size()));
        assertEquals("Welcome! What is your name?", computeFx(() -> app.greetingLabel.getText()));
        assertTrue(computeFx(() -> app.menuList.getItems().get(2)).contains("Latte"));
        assertEquals("Customers served : 0          Total revenue : 0.00 SEK",
                computeFx(() -> app.statusLabel.getText()));
    }

    @Test
    void placeOrder_validMemberOrder_updatesReceiptAndStatus() throws Exception {

        runFx(() -> {
            app.nameField.setText("Test");
            app.menuList.getSelectionModel().select(2);
            app.quantitySpinner.getValueFactory().setValue(2);
            app.memberBox.setSelected(true);
            app.handlePlaceOrder();
        });

        var receipt = computeFx(() -> app.receiptArea.getText());
        assertTrue(receipt.contains("Customer  : Test"));
        assertTrue(receipt.contains("Item      : Latte x 2"));
        assertTrue(receipt.contains("Subtotal  : 80.00 SEK"));
        assertTrue(receipt.contains("Discount  : -12.00 SEK"));
        assertTrue(receipt.contains("VAT       : 8.16 SEK"));
        assertTrue(receipt.contains("TOTAL     : 76.16 SEK"));

        assertEquals("Customers served : 1          Total revenue : 76.16 SEK",
                computeFx(() -> app.statusLabel.getText()));
        assertEquals("Hi Test! Here is our menu:", computeFx(() -> app.greetingLabel.getText()));
    }

    @Test
    void placeOrder_emptyName_showsErrorAndNoOrder() throws Exception {

        runFx(() -> {
            app.menuList.getSelectionModel().select(0);
            app.handlePlaceOrder();
        });

        assertEquals("Please enter the customer's name.", computeFx(() -> app.lastError));
        assertTrue(computeFx(() -> app.receiptArea.getText()).isEmpty());
    }

    @Test
    void placeOrder_noItemSelected_showsError() throws Exception {

        runFx(() -> {
            app.nameField.setText("Bob");
            app.handlePlaceOrder();
        });

        assertEquals("Please select an item from the menu.", computeFx(() -> app.lastError));
        assertTrue(computeFx(() -> app.receiptArea.getText()).isEmpty());
    }

    @Test
    void newCustomer_resetsFormButKeepsTotals() throws Exception {

        runFx(() -> {
            app.nameField.setText("Test");
            app.menuList.getSelectionModel().select(2);
            app.quantitySpinner.getValueFactory().setValue(2);
            app.memberBox.setSelected(true);
            app.handlePlaceOrder();
            app.handleNewCustomer();
        });

        assertEquals("", computeFx(() -> app.receiptArea.getText()));
        assertEquals(1, computeFx(() -> app.quantitySpinner.getValue()));
        assertFalse(computeFx(() -> app.memberBox.isSelected()));
        assertEquals("Customers served : 1          Total revenue : 76.16 SEK",
                computeFx(() -> app.statusLabel.getText()));
    }

    private static class RecordingApp extends CafeGuiApp {
        String lastError;

        @Override
        void showError(String message) {
            lastError = message;
        }
    }
}