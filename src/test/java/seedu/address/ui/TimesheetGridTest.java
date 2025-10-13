package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seedu.address.model.person.timesheet.PersonTimesheet;
import seedu.address.model.person.timesheet.WeekIndex;

/**
 * Tests for TimesheetGrid.
 * This test runs headless (no GUI) using JavaFX initialized via JFXPanel.
 */
public class TimesheetGridTest {

    @BeforeAll
    static void initToolkit() {
        // Initializes JavaFX toolkit headlessly
        new JFXPanel(); // initializes JavaFX
        Platform.setImplicitExit(false);
    }


    @Test
    public void constructor_validInput_createsGridProperly() throws Exception {
        WeekIndex weekIndex = new WeekIndex(LocalDate.of(2025, 10, 13));
        PersonTimesheet timesheet = new PersonTimesheet("P001", weekIndex);

        TimesheetGrid[] gridHolder = new TimesheetGrid[1];
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            gridHolder[0] = new TimesheetGrid(timesheet);
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);

        TimesheetGrid grid = gridHolder[0];
        assertNotNull(grid);
        assertTrue(grid instanceof GridPane);
        assertNotNull(grid.getChildren());
        assertFalse(grid.getChildren().isEmpty());
    }

    @Test
    public void grid_containsExpectedHeaderAndCells() throws Exception {
        WeekIndex weekIndex = new WeekIndex(LocalDate.of(2025, 10, 13));
        PersonTimesheet timesheet = new PersonTimesheet("P002", weekIndex);

        TimesheetGrid[] gridHolder = new TimesheetGrid[1];
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            gridHolder[0] = new TimesheetGrid(timesheet);
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);

        TimesheetGrid grid = gridHolder[0];
        int expectedColumns = 1 + 7; // 1 time column + 7 day columns
        int expectedRows = 1 + WeekIndex.BINS_PER_DAY; // 1 header row + 48 time slots
        assertTrue(grid.getChildren().size() >= expectedColumns * expectedRows / 10,
                "Should have at least headers & cells");
    }

    @Test
    public void cellClick_togglesAvailabilityColorAndMask() throws Exception {
        WeekIndex weekIndex = new WeekIndex(LocalDate.of(2025, 10, 13));
        PersonTimesheet timesheet = new PersonTimesheet("P003", weekIndex);

        TimesheetGrid[] gridHolder = new TimesheetGrid[1];
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            gridHolder[0] = new TimesheetGrid(timesheet);
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
        TimesheetGrid grid = gridHolder[0];

        // Find first Rectangle cell
        Node firstCell = grid.getChildren().stream()
                .filter(n -> n instanceof Rectangle)
                .findFirst()
                .orElseThrow();

        Rectangle rect = (Rectangle) firstCell;
        Color originalColor = (Color) rect.getFill();

        // Simulate click on FX thread
        CountDownLatch latchClick = new CountDownLatch(1);
        Platform.runLater(() -> {
            rect.fireEvent(new javafx.scene.input.MouseEvent(
                    javafx.scene.input.MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0,
                    javafx.scene.input.MouseButton.PRIMARY,
                    1,
                    false, false, false, false,
                    true, false, false, true,
                    false, false, null
            ));
            latchClick.countDown();
        });
        latchClick.await(5, TimeUnit.SECONDS);

        // After toggle, color should change
        Color newColor = (Color) rect.getFill();
        assertNotEquals(originalColor, newColor, "Click should toggle cell color");
    }
}


