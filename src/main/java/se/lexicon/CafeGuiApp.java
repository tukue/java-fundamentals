package se.lexicon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CafeGuiApp extends Application {

    final TextField nameField = new TextField();
    final Spinner<Integer> quantitySpinner =
            new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1));
    final CheckBox memberBox = new CheckBox("Loyalty member");
    final ListView<String> menuList = new ListView<>();
    final TextArea receiptArea = new TextArea();
    final Label greetingLabel = new Label("Welcome! What is your name?");
    final Label statusLabel = new Label();

    private int customersServed;
    private double totalRevenue;

    @Override
    public void start(Stage stage) {
        configureControls();

        var root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setTop(createHeader());
        root.setCenter(createCenter());
        root.setBottom(createStatusBar());

        var scene = new Scene(root, 960, 640);
        stage.setTitle("Lexicon Cafe");
        stage.setScene(scene);
        stage.show();
    }

    private void configureControls() {
        quantitySpinner.setEditable(false);
        quantitySpinner.setPrefWidth(80);

        receiptArea.setEditable(false);
        receiptArea.setWrapText(false);
        receiptArea.setFont(Font.font("Monospaced", 13));
        receiptArea.setPromptText("Receipt will appear here");

        for (var item : CafeApp.MENU) {
            menuList.getItems().add(
                    String.format("%s. %-17s%.2f SEK", item[0], item[1], Double.parseDouble(item[2])));
        }
        menuList.setPrefWidth(280);

        nameField.textProperty().addListener((obs, oldValue, newValue) -> {
            var name = newValue.trim();
            greetingLabel.setText(name.isEmpty()
                    ? "Welcome! What is your name?"
                    : "Hi " + name + "! Here is our menu:");
        });

        updateStatus();
    }

    private VBox createHeader() {
        var title = new Label("LEXICON CAFE");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        greetingLabel.setStyle("-fx-font-size: 14px;");
        return new VBox(5, title, greetingLabel);
    }

    private HBox createCenter() {
        var formSection = createFormSection();
        var center = new HBox(20, createMenuSection(), formSection, createReceiptSection());
        HBox.setHgrow(formSection, Priority.ALWAYS);
        return center;
    }

    private VBox createMenuSection() {
        var label = sectionLabel("Menu");
        var section = new VBox(5, label, menuList);
        VBox.setVgrow(menuList, Priority.ALWAYS);
        return section;
    }

    private VBox createFormSection() {
        var label = sectionLabel("Order");

        var grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        nameField.setPrefWidth(220);
        GridPane.setHgrow(nameField, Priority.ALWAYS);

        grid.add(new Label("Customer name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(quantitySpinner, 1, 1);
        grid.add(memberBox, 1, 2);

        var placeOrderBtn = new Button("Place Order");
        placeOrderBtn.setDefaultButton(true);
        placeOrderBtn.setOnAction(event -> handlePlaceOrder());

        var newCustomerBtn = new Button("New Customer");
        newCustomerBtn.setOnAction(event -> handleNewCustomer());

        var endOfDayBtn = new Button("End of Day");
        endOfDayBtn.setOnAction(event -> handleEndOfDay());

        var buttons = new HBox(10, placeOrderBtn, newCustomerBtn, endOfDayBtn);
        grid.add(buttons, 1, 3);

        return new VBox(10, label, grid);
    }

    private VBox createReceiptSection() {
        var label = sectionLabel("Receipt");
        var section = new VBox(5, label, receiptArea);
        VBox.setVgrow(receiptArea, Priority.ALWAYS);
        section.setPrefWidth(360);
        return section;
    }

    private HBox createStatusBar() {
        statusLabel.setFont(Font.font("Monospaced", 13));
        var bar = new HBox(statusLabel);
        bar.setPadding(new Insets(8, 0, 0, 0));
        return bar;
    }

    private Label sectionLabel(String text) {
        var label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        return label;
    }

    void handlePlaceOrder() {
        var order = createOrderFromForm();
        if (order == null) {
            return;
        }

        order.calculateTotals();
        receiptArea.setText(order.formatReceipt());

        customersServed++;
        totalRevenue += order.getTotal();
        updateStatus();
    }

    void handleNewCustomer() {
        nameField.clear();
        menuList.getSelectionModel().clearSelection();
        quantitySpinner.getValueFactory().setValue(1);
        memberBox.setSelected(false);
        receiptArea.clear();
        nameField.requestFocus();
    }

    private void handleEndOfDay() {
        var confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("End of Day");
        confirm.setHeaderText(null);
        confirm.setContentText("Close the cafe and print the end-of-day report?");
        confirm.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        var answer = confirm.showAndWait();
        if (answer.isEmpty() || answer.get() != ButtonType.YES) {
            return;
        }

        var report = String.format(
                "==============================%n"
                        + "      END OF DAY REPORT%n"
                        + "==============================%n"
                        + "Customers served : %d%n"
                        + "Total revenue    : %.2f SEK%n"
                        + "==============================",
                customersServed, totalRevenue);

        var dialog = new Alert(Alert.AlertType.INFORMATION, report);
        dialog.setTitle("End of Day");
        dialog.setHeaderText(null);
        dialog.showAndWait();

        Platform.exit();
    }

    Order createOrderFromForm() {
        var name = nameField.getText().trim();
        if (name.isEmpty()) {
            showError("Please enter the customer's name.");
            return null;
        }

        var index = menuList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            showError("Please select an item from the menu.");
            return null;
        }

        var item = CafeApp.MENU[index];
        var order = new Order();
        order.setCustomerName(name);
        order.setItemName(item[1]);
        order.setUnitPrice(Double.parseDouble(item[2]));
        order.setQuantity(quantitySpinner.getValue());
        order.setMember(memberBox.isSelected());
        return order;
    }

    void showError(String message) {
        var alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void updateStatus() {
        statusLabel.setText(String.format(
                "Customers served : %d          Total revenue : %.2f SEK",
                customersServed, totalRevenue));
    }
}
