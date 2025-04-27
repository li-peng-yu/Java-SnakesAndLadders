package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Set;

public class GridPaneBoard {

    private static final int SIZE = 10;
    private static final int CELL_SIZE = 60;

    private static final Set<Integer> LADDERS = Set.of(4, 14, 28, 47, 62, 88);
    private static final Set<Integer> SNAKES = Set.of(17, 54, 64, 87, 93, 95);

    public static GridPane createBoardGrid() {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(false); // 改为“无线条”，更现代

        int number = 100;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int actualCol = (row % 2 == 0) ? SIZE - 1 - col : col;
                int cellNumber = number--;

                StackPane cell = new StackPane();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);

                // 样式逻辑
                String bgColor;
                if (LADDERS.contains(cellNumber)) {
                    bgColor = "#aed581"; // 明亮的绿
                } else if (SNAKES.contains(cellNumber)) {
                    bgColor = "#ef9a9a"; // 柔和的红
                } else {
                    bgColor = ((row + col) % 2 == 0) ? "#f1f8e9" : "#c8e6c9"; // 柔绿配色
                }

                cell.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: #bdbdbd; -fx-border-width: 0.5;");

                Label label = new Label(Integer.toString(cellNumber));
                label.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
                label.setTextFill(Color.web("#424242"));
                label.setEffect(new DropShadow(1, Color.web("#b0bec5")));

                StackPane.setAlignment(label, Pos.TOP_LEFT);
                StackPane.setMargin(label, new Insets(4));
                cell.getChildren().add(label);

                // hover 效果（可选）
                String defaultStyle = cell.getStyle();
                cell.setOnMouseEntered(e -> cell.setStyle("-fx-background-color: #fff59d; -fx-border-color: black;"));
                cell.setOnMouseExited(e -> cell.setStyle(defaultStyle));

                grid.add(cell, actualCol, row);
            }
        }

        return grid;
    }
}
