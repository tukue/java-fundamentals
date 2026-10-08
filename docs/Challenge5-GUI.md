# Challenge 5 - Graphical User Interface

## Overview

Challenge 5 replaces the console interface of the Lexicon Cafe order system with a
desktop GUI built with JavaFX. The cashier can greet the customer, pick menu items,
choose a quantity, mark loyalty membership, place the order, and read the receipt -
all in one window, without a terminal.

The business logic is not rewritten. The GUI drives the same `Order` class the
console app uses, so prices, discounts, VAT, and receipt text are identical in both
versions.

## How to run

Requirements: JDK 25 (project compiles with `-source 25`) and Maven.

```bash
mvn javafx:run
```

Or in IntelliJ IDEA: run `CafeGuiLauncher`. No VM options or manual JavaFX SDK are
needed - the JavaFX jars come from Maven and the launcher class starts the toolkit
without module-path configuration.

## Window layout

```
+----------------------------------------------------------------------+
| LEXICON CAFE                                                         |
| Welcome! What is your name?          (updates to "Hi <name>! ...")   |
+----------------+------------------------------+----------------------+
| Menu           | Order                        | Receipt              |
|                |                              |                      |
| 1. Espresso .. | Customer name: [__________]  | ==================   |
| 2. Cappuccino .| Quantity:     [ 1 ^]         | LEXICON CAFE         |
| 3. Latte ......| [x] Loyalty member           | Customer  : ...      |
| 4. Croissant ..|                              | Item      : ...      |
| 5. Sandwich ...| [Place Order][New Customer]  | Subtotal  : ...      |
|                | [End of Day]                 | Discount  : ...      |
|                |                              | VAT       : ...      |
|                |                              | TOTAL     : ...      |
+----------------+------------------------------+----------------------+
| Customers served : 2          Total revenue : 180.88 SEK             |
+----------------------------------------------------------------------+
```

- **Header** - title and greeting. A listener on the name field switches between
  "Welcome! What is your name?" and "Hi <name>! Here is our menu:" as the cashier types.
- **Menu** - the five items with prices, reusing `CafeApp.MENU` as the single source
  of truth. Click an item to select it.
- **Order form** - name field, quantity spinner (constrained to 1-99, not editable),
  loyalty check box, and the three action buttons.
- **Receipt** - a read-only, monospaced text area showing exactly the text produced
  by `Order.formatReceipt()`.
- **Status bar** - customers served and total revenue for the day, updated after
  every placed order.

## Classes and responsibilities

| Class | Responsibility |
|---|---|
| `CafeGuiApp` | The UI. Builds the scene, wires event handlers, validates input, shows dialogs, keeps the daily totals. |
| `CafeGuiLauncher` | Entry point. Calls `Application.launch(CafeGuiApp.class)`. |
| `Order` | Domain logic (unchanged): fields, `calculateTotals()`, `formatReceipt()`. |
| `CafeApp` | Console version; its `MENU` array is shared with the GUI. |

`CafeGuiApp` methods stay single-purpose:

- `start()` - assemble the window and show it; no calculations.
- `createHeader()`, `createMenuSection()`, `createFormSection()`,
  `createReceiptSection()`, `createStatusBar()` - layout builders.
- `handlePlaceOrder()` - coordinates one order: validate, calculate, display, update stats.
- `createOrderFromForm()` - validation and `Order` construction; returns `null`
  when input is invalid so the handler stops early.
- `showError()` - error alert dialog.
- `handleNewCustomer()` - resets the form for the next customer.
- `handleEndOfDay()` - confirmation dialog, then the end-of-day report, then exit.

## Event-driven model vs. console flow

The console app is a linear script: `main()` prints, blocks on `Scanner.nextLine()`,
calculates, prints again, and loops. The program controls the order of events.

The GUI inverts this. `main()` only launches the toolkit. After `start()` builds the
window, nothing happens until the user acts:

- clicking *Place Order* fires `handlePlaceOrder()`
- clicking *New Customer* fires `handleNewCustomer()`
- clicking *End of Day* fires `handleEndOfDay()`
- typing in the name field fires the greeting listener

Any event can arrive at any time and in any order, so each handler must work on its
own and leave the application ready for the next one. The "workflow" of the program
is now spread across handlers instead of one top-to-bottom `main()`, and shared state
(customer count, revenue) lives in fields of `CafeGuiApp` instead of local variables.

## What is reused unchanged

- `Order.calculateTotals()` and every pricing rule (base price, member discount,
  bulk discount, VAT).
- `Order.formatReceipt()` - the receipt text in the GUI is byte-for-byte the text
  the console prints, so both interfaces stay consistent.
- `CafeApp.MENU` - one menu definition for both interfaces.
- The validation rules from Challenge 2: name required, item selected, quantity at
  least 1. Membership needs no text validation because a check box is either
  selected or not.

Only the input and output channels changed: controls instead of `Scanner`,
a text area instead of `System.out`.

## Business rules (same as the console version)

1. Base price = unit price x quantity.
2. Discount (only one applies, member takes priority):
   - loyalty member: 15% off the base price
   - no membership but order exceeds 150 SEK: 10% off
3. VAT of 12% is applied after the discount.
4. Total = subtotal - discount + VAT.

Example (member, 2 lattes): 80.00 - 12.00 discount + 8.16 VAT = **76.16 SEK**.

## Tests

```bash
mvn test -Dtest=OrderTest
```

`OrderTest` (8 tests) covers all discount combinations, VAT, and receipt output.
The GUI itself is verified manually: start it, place the three sample orders from
the workshop, and compare the receipt text with the console version.
