package seedu.address.ui;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import seedu.address.model.person.timesheet.PersonTimesheet;
import seedu.address.model.person.timesheet.WeekIndex;

import java.time.LocalDateTime;

public class TimesheetGrid extends GridPane {
    private final PersonTimesheet timesheet;
    private final int slotsPerDay = WeekIndex.BINS_PER_DAY; // e.g. 48 for 30-min slots
    private final int days = 7;

    public TimesheetGrid(PersonTimesheet timesheet) {
        this.timesheet = timesheet;
        drawGrid();
    }

    private void drawGrid() {
        for (int day = 0; day < days; day++) {
            for (int slot = 0; slot < slotsPerDay; slot++) {
                int index = day * slotsPerDay + slot;
                Rectangle cell = new Rectangle(20, 20);
                cell.setFill(timesheet.getMask().get(index) ? Color.LIGHTGREEN : Color.LIGHTCORAL);

                cell.setOnMouseClicked((MouseEvent e) -> {
                    boolean available = timesheet.getMask().get(index);
                    timesheet.getMask().set(index, !available);
                    cell.setFill(!available ? Color.LIGHTGREEN : Color.LIGHTCORAL);
                });

                this.add(cell, day, slot);
            }
        }
    }
}