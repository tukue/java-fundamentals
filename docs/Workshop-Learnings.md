# Workshop Lesson Summary — Café Order System

## 1. Keep logic independent of the interface
By putting everything in `Order` (total, discount, VAT, receipt), the same calculations power the console app and the JavaFX GUI. The interface only collects input and displays results.

## 2. Validate before you act
Both versions validate every input (item selection, quantity, name) and fail gracefully — the GUI's `createOrderFromForm()` returns `null` and shows an alert instead of crashing. Dead/duplicate validation (like the spinner `quantity < 1` check) got found and removed.

## 3. Understand your toolkit
- Console: `Scanner` + validation loops.
- GUI: event-driven — controls fire listeners/handlers, `Platform` threading rules, dialogs use `showAndWait()`, layouts (`BorderPane`/`GridPane`) need explicit `Hgrow`/`Vgrow`.

## 4. Test the real behavior
GUI tests drove actual controls on the FX thread with a recording subclass instead of real alerts, and reused a single window for speed — catching real regressions, not just happy paths.

## 5. Refactoring pays off
`printReceipt()` → `formatReceipt()` let the GUI reuse output byte-identical; `calculateTotals()` replaced four manual calls; consistent naming (`buildXxx`) and a single layout system kept the code clean.

## 6. CI catches what local runs miss
GitHub Actions + `xvfb-run` runs the JavaFX tests headlessly on JDK 25, and PR review comments (Amazon Q) became concrete fixes (e.g., unreachable quantity check).

## 7. Git hygiene
Feature branches + PRs for non-trivial work, small focused commits, rebasing on a moved `main`, and keeping refactor commits off `main` until reviewed.

## 8. Real-world debugging
WSLg won't screen-record app surfaces (verified via scene snapshots instead); two failing tests turned out to be real logic bugs — grades >100 weren't rejected, and the password checker missed a "letter" rule.