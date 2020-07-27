package make.it.work;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for Elevator assignment.
 */
public class ElevatorTest
{

    /**
     * Example Test, just for you to understand the assignment.
     */
    @Test
    public void testUp() {
        final int[][] queues = {
                new int[0], // G
                new int[0], // 1
                new int[]{5, 5, 5}, // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
        };
        final int[] result = Elevator.goElevatorGo(queues, 5);
        assertArrayEquals(new int[]{0, 2, 5, 0}, result);
    }

    @Test
    public void testDown() {
        final int[][] queues = {
                new int[0], // G
                new int[0], // 1
                new int[]{1, 1}, // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
        };
        final int[] result = Elevator.goElevatorGo(queues, 5);
        assertArrayEquals(new int[]{0, 2, 1, 0}, result);
    }

    @Test
    public void testUpAndUp() {
        final int[][] queues = {
                new int[0], // G
                new int[]{3}, // 1
                new int[]{4}, // 2
                new int[0], // 3
                new int[]{5}, // 4
                new int[0], // 5
                new int[0], // 6
        };
        final int[] result = Elevator.goElevatorGo(queues, 5);
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 0}, result);
    }

    @Test
    public void testDownAndDown() {
        final int[][] queues = {
                new int[0], // G
                new int[]{0}, // 1
                new int[0], // 2
                new int[0], // 3
                new int[]{2}, // 4
                new int[]{3}, // 5
                new int[0], // 6
        };
        final int[] result = Elevator.goElevatorGo(queues, 5);
        assertArrayEquals(new int[]{0, 5, 4, 3, 2, 1, 0}, result);
    }
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void testEmptyPassengerQueueToStartWith() {
        final int[][] queues = {
                new int[0], // G
                new int[0], // 1
                new int[0], // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
        };
        final int[] result = Elevator.goElevatorGo(queues,5);
        assertArrayEquals(new int[]{0}, result);
    }

    @Test
    public void testPeopleOnlyInGroundFloor_WithinCapacity() {
        final int[][] queues = {
                new int[]{6, 5, 4, 3}, // G
                new int[0], // 1
                new int[0], // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
        };
        final int[] result = Elevator.goElevatorGo(queues,5);
        assertArrayEquals(new int[]{0, 3, 4, 5, 6, 0}, result);
    }

    @Test
    public void testPeopleOnlyInGroundFloor_ExceedsCapacity() {
        final int[][] queues = {
                new int[]{5, 5, 4, 3, 2, 5}, // G
                new int[0], // 1
                new int[0], // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[0], // 6
        };
        final int[] result = Elevator.goElevatorGo(queues,5);
        assertArrayEquals(new int[]{0, 2, 3, 4, 5, 0, 5, 0}, result);
    }

    @Test
    public void testPeopleOnlyInTopMostFloor_WithinCapacity() {
        final int[][] queues = {
                new int[0], // G
                new int[0], // 1
                new int[0], // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[]{5, 4, 3}, // 6
        };
        final int[] result = Elevator.goElevatorGo(queues, 5);
        assertArrayEquals(new int[]{0, 6, 5, 4, 3, 0}, result);
    }

    @Test
    public void testPeopleOnlyInTopMostFloor_ExceedsCapacity() {
        final int[][] queues = {
                new int[0], // G
                new int[0], // 1
                new int[0], // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[]{5, 4, 3, 2, 1, 0}, // 6
        };
        final int[] result = Elevator.goElevatorGo(queues,5);
        assertArrayEquals(new int[]{0, 6, 5, 4, 3, 2, 1, 6, 0}, result);
    }

    @Test
    public void testRandomFloorsRandomPeople_withinCapacity() {

        final int[][] queues = {
                new int[]{6}, // G
                new int[0], // 1
                new int[]{3, 1}, // 2
                new int[0], // 3
                new int[0], // 4
                new int[0], // 5
                new int[]{5}, // 6
        };

        final int[] result = Elevator.goElevatorGo(queues, 5);
        assertArrayEquals(new int[]{0, 2, 3, 6, 5, 2, 1, 0}, result);
    }

    @Test
    public void testRandomFloorsRandomPeople_ExceedsCapacity() {

        final int[][] queues = {
                new int[]{6}, // G
                new int[0], // 1
                new int[]{3, 1}, // 2
                new int[]{6, 5, 5}, // 3
                new int[]{6, 6}, // 4
                new int[0], // 5
                new int[]{5}, // 6
        };

        final int[] result = Elevator.goElevatorGo(queues, 5);
        assertArrayEquals(new int[]{0, 2, 3, 4, 5, 6, 5, 2, 1, 4, 6, 0}, result);
    }






}
