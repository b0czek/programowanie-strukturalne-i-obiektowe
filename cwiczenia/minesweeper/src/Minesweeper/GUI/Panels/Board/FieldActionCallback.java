package Minesweeper.GUI.Panels.Board;

public interface FieldActionCallback {
    void fieldRevealed(Field field);
    void fieldFlagToggled(Field field);
}
