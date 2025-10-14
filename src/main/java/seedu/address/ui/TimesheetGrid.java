package seedu.address.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seedu.address.model.person.timesheet.PersonTimesheet;
import seedu.address.model.person.timesheet.WeekIndex;

/**
 * Displays the timetable for user
 */
public class TimesheetGrid extends GridPane {
    private final PersonTimesheet timesheet;
    private final int slotsPerDay = WeekIndex.BINS_PER_DAY; // 48 for 30-min slots
    private final int days = 7;
    private final String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

    /**
     * Constructor for timesheet grid object
     * @param timesheet
     */
    public TimesheetGrid(PersonTimesheet timesheet) {
        this.timesheet = timesheet;
        drawHeaders();
        drawGrid();
    }

    /**
     * Draw lines for the user to see slots more clearly
     */
    private void drawHeaders() {
        // Day headers (Mon-Sun)
        for (int day = 0; day < days; day++) {
            Label dayLabel = new Label(dayNames[day]);
            dayLabel.setAlignment(Pos.CENTER);
            this.add(dayLabel, day + 1, 0); // Shift right by 1 for time slot column
        }
        // Time slot headers (00:00 to 24:00)
        for (int slot = 0; slot < slotsPerDay; slot++) {
            int hour = (slot * WeekIndex.MINUTES_PER_BIN) / 60;
            int minute = (slot * WeekIndex.MINUTES_PER_BIN) % 60;
            String timeLabelStr = String.format("%02d:%02d", hour, minute);
            Label timeLabel = new Label(timeLabelStr);
            timeLabel.setAlignment(Pos.CENTER_RIGHT);
            this.add(timeLabel, 0, slot + 1); // First column, below header
        }
    }

    private void drawGrid() {
        for (int day = 0; day < days; day++) {
            for (int slot = 0; slot < slotsPerDay; slot++) {
                int index = day * slotsPerDay + slot;
                Rectangle cell = new Rectangle(20, 20);
                cell.setFill(timesheet.getMask().get(index) ? Color.LIGHTGREEN : Color.TRANSPARENT);

                cell.setOnMouseClicked((MouseEvent e) -> {
                    boolean available = timesheet.getMask().get(index);
                    timesheet.getMask().set(index, !available);
                    cell.setFill(timesheet.getMask().get(index) ? Color.LIGHTGREEN : Color.TRANSPARENT);
                });

                this.add(cell, day + 1, slot + 1); // Shift right by 1 for time slot column
            }
        }
    }
}
