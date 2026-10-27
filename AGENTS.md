# AGENTS.md

JavaFX 21 desktop Craps game. Single Maven module, Java language level **9** (`pom.xml`), JUnit 5. UI text is Spanish.

## Commands

```bash
mvn clean javafx:run     # run the app (pom's default-cli execution)
mvn test                 # compile + run tests
mvn -Dtest=SomeTest test # single test class
mvn -Dtest=SomeTest#method test
```

- The committed `./mvnw` is **broken** in this checkout: `.mvn/wrapper/maven-wrapper.properties` and the wrapper JAR are absent. Use a system/IntelliJ-bundled `mvn`, or restore the wrapper files before relying on `./mvnw`.
- There are currently no test sources; `src/test/java` does not exist. Add tests under `src/test/java` and Surefire's default `*Test` naming applies.
- The JavaFX plugin hardcodes `mainClass` as `com.example.crapsgame262/com.example.crapsgame262.HelloApplication` (module/class format). `Launcher` exists but is not used by `javafx:run`; keep the module and class names in sync with the pom if renaming.

## Architecture

- Entrypoint: `HelloApplication.start()` opens the singleton `views.WelcomeView`. On "Iniciar", `WelcomeController` creates a `Player`, gets the singleton `GameView`, injects the player into `GameController`, shows it, then hides the welcome window.
- Package layout under `com.example.crapsgame262`: `views` (Stages, each a lazy singleton via `getInstance()`), `controllers` (FXML controllers), `models` (`Dice`, `Player`, `AlertBox`), plus `Launcher`/`HelloApplication`.
- Each view loads its FXML from an **absolute** resource path, e.g. `/com/example/crapsgame262/game-view.fxml`; keep FXML and images in the mirrored path under `src/main/resources`. Dice images are `images/dices/dice1.png` … `dice6.png` and are resolved by `Dice.getImagePath()` from the rolled value.
- `module-info.java` must `opens` every package containing an FXML controller to `javafx.fxml` (currently the root package and `controllers`). New controller packages need this or FXMLLoader will fail at runtime.
- Views cache their controller instance; controller fields are wired with `@FXML`. `GameView` is a reused Stage, so don't create new `GameView` instances directly—use `getInstance()`.

## Conventions / gotchas

- FXML handlers use `onMouseClicked`, not `onAction`, so handler methods take `MouseEvent`.
- Because the pom targets Java 9, avoid `var` (10+), records, text blocks, and other newer syntax in main/test code.
- `GameController.setCurrentPlayer` assumes `nicknameLabel` is already injected; call it only after `FXMLLoader.load()`.
- `.idea/` is partially tracked; do not commit IDE-local files. Build output goes to `target/` (ignored).
