@echo on & setlocal enabledelayedexpansion
cls

robocopy src/ bin/ *.fxml /E

javac -cp src -d bin/ src/main/MainGraph.java

java -ea -cp bin main.MainGraph
