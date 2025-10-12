package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seedu.address.model.person.timesheet.PersonTimesheet;
import seedu.address.model.person.timesheet.WeekIndex;

public class TimesheetGridTest {

    private TimesheetGrid grid;
    private PersonTimesheet timesheet;

    @BeforeAll
    static void initJfxRuntime() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // Toolkit already started
        }
        Platform.setImplicitExit(false);
    }


    @BeforeEach
    public void setUp() {
        WeekIndex weekIndex = new WeekIndex(LocalDate.of(2025, 10, 13)); // Monday
        timesheet = new PersonTimesheet("P001", weekIndex);
        grid = new TimesheetGrid(timesheet);
    }

    @Test
    public void grid_hasExpectedNumberOfCells() {
        long rectCount = grid.getChildren().stream()
                .filter(node -> node instanceof Rectangle)
                .count();
        assertEquals(7 * WeekIndex.BINS_PER_DAY, rectCount);
    }

    @Test
    public void initialGrid_reflectsTimesheetMask() {
        grid.getChildren().stream()
                .filter(node -> node instanceof Rectangle)
                .map(node -> (Rectangle) node)
                .forEach(rect -> assertEquals(Color.TRANSPARENT, rect.getFill()));
    }

    @Test
    public void clickingCell_togglesMaskAndColor() {
        Rectangle cell = (Rectangle) grid.getChildren().stream()
                .filter(node -> node instanceof Rectangle)
                .findFirst()
                .orElseThrow();
        int index = 0;

        assertFalse(timesheet.getMask().get(index));
        assertEquals(Color.TRANSPARENT, cell.getFill());

        Platform.runLater(() -> cell.getOnMouseClicked().handle(null));

        // Wait a short moment for FX thread
        sleep(100);
        assertTrue(timesheet.getMask().get(index));
        assertEquals(Color.LIGHTGREEN, cell.getFill());
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }
}
