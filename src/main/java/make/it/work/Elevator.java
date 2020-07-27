package make.it.work;

/**
 Synopsis

 A multi-floor building has a Elevator in it.

 People are queued on different floors waiting for the Elevator.

 Some people want to go up. Some people want to go down.

 The floor they want to go to is represented by a number (i.e. when they enter the Elevator this is the button they will press)

 BEFORE (people waiting in queues)               AFTER (people at their destinations)
 +--+                                          +--+
 /----------------|  |----------------\        /----------------|  |----------------\
 10|                |  | 1,4,3,2        |      10|             10 |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 9|                |  | 1,10,2         |       9|                |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 8|                |  |                |       8|                |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 7|                |  | 3,6,4,5,6      |       7|                |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 6|                |  |                |       6|          6,6,6 |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 5|                |  |                |       5|            5,5 |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 4|                |  | 0,0,0          |       4|          4,4,4 |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 3|                |  |                |       3|            3,3 |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 2|                |  | 4              |       2|          2,2,2 |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 1|                |  | 6,5,2          |       1|            1,1 |  |                |
 |----------------|  |----------------|        |----------------|  |----------------|
 G|                |  |                |       G|          0,0,0 |  |                |
 |====================================|        |====================================|

 Rules
 Elevator Rules

 The Elevator only goes up or down!
 Each floor has both UP and DOWN Elevator-call buttons (except top and ground floors which have only DOWN and UP respectively)
 The Elevator never changes direction until there are no more people wanting to get on/off in the direction it is already travelling
 When empty the Elevator tries to be smart. For example,
 If it was going up then it may continue up to collect the highest floor person wanting to go down
 If it was going down then it may continue down to collect the lowest floor person wanting to go up
 The Elevator has a maximum capacity of people
 When called, the Elevator will stop at a floor even if it is full, although unless somebody gets off nobody else can get on!
 If the elevator is empty, and no people are waiting, then it will return to the ground floor

 People Rules

 People are in "queues" that represent their order of arrival to wait for the Elevator
 All people can press the UP/DOWN Elevator-call buttons
 Only people going the same direction as the Elevator may enter it
 Entry is according to the "queue" order, but those unable to enter do not block those behind them that can
 If a person is unable to enter a full Elevator, they will press the UP/DOWN Elevator-call button again after it has departed without them

 Kata Task

 Get all the people to the floors they want to go to while obeying the Elevator rules and the People rules
 Return a list of all floors that the Elevator stopped at (in the order visited!)

 NOTE: The Elevator always starts on the ground floor (and people waiting on the ground floor may enter immediately)
 I/O
 Input

 queues a list of queues of people for all floors of the building.
 The height of the building varies
 0 = the ground floor
 Not all floors have queues
 Queue index [0] is the "head" of the queue
 Numbers indicate which floor the person wants go to
 capacity maximum number of people allowed in the elevator

 Parameter validation - All input parameters can be assumed OK. No need to check for things like:

 People wanting to go to floors that do not exist
 People wanting to take the Elevator to the floor they are already on
 Buildings with < 2 floors
 Basements

 Output

 A list of all floors that the Elevator stopped at (in the order visited!)

 Example

 Refer to the example test cases.

 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Calculates the list of floors in order an elevator visits.
 *
 * @author Proma Chowdhury
 * @version 1.0
 */

public class Elevator {

    public static final int CAPACITY = 5;

    public static void main(String... args) {

        final int[][] queues = {
                new int[0], // G
                new int[]{2}, // 1
                new int[]{1, 3, 0}, // 2
                new int[]{2}, // 3

        };

        int[] arrayInt = goElevatorGo(queues, CAPACITY);
        Arrays.stream(arrayInt).forEach(System.out::println);
    }

    /** Prints the floors visited by the lift in order.
     * @param queues List of people waiting for lift at certain floors.
     * @param capacity The maximum capacity of the lift.
     * @return An array of Floors visited in order by the lift
     */
    public static int[] goElevatorGo(final int[][] queues, final int capacity) {
        ArrayList<ArrayList<Integer>> floorAndPeopleList = createFloorAndPeopleList(queues);
//        if (isQueuesAreEmpty(floorAndPeopleList)) {
//            return new int[]{0};
//        }
        return calculateListOfVisitedFloors(floorAndPeopleList, capacity).stream().mapToInt(i -> i).toArray();
    }

    /**
     * Computes the floors visited by the lift in order.
     *
     * @param floorAndPeopleList List of people waiting for lift at certain floors.
     * @param capacity           The maximum capacity of the lift.
     * @return A list of Floors visited in order by the lift
     */
    private static ArrayList<Integer> calculateListOfVisitedFloors(ArrayList<ArrayList<Integer>> floorAndPeopleList, int capacity) {

        //Initialize the lift--begins
        ArrayList<Integer> listOfFloors = new ArrayList<>();
        int totalNumberOfFloors = floorAndPeopleList.size();
        ArrayList<Integer> personsInTheLift = new ArrayList<>(capacity);
        int currentFloor = 0;
        int nextFloor;
        boolean isGoingUp = true;
        //Initialize the lift--ends

        if (!floorAndPeopleList.get(currentFloor).isEmpty()) { //pick people from ground floor --begins
            pushPersons(floorAndPeopleList.get(currentFloor), currentFloor, personsInTheLift, capacity, isGoingUp);
        }
        listOfFloors.add(0); //pick people from ground floor --ends
        while (true) {
            nextFloor = getNearestCall(floorAndPeopleList, totalNumberOfFloors, currentFloor, isGoingUp);
            if (personsInTheLift.isEmpty()) {
                if (nextFloor == -1) {
                    if (isQueuesAreEmpty(floorAndPeopleList)) {
                        break;
                    }

                    currentFloor = isGoingUp ? totalNumberOfFloors : -1;//people may be waiting to get down or up so scan from above
                    //or below resp.

                    isGoingUp = !isGoingUp; //reverse direction

                    continue;

                }
                currentFloor = nextFloor;
                pushPersons(floorAndPeopleList.get(currentFloor), currentFloor, personsInTheLift, capacity, isGoingUp);
                if (listOfFloors.get(listOfFloors.size() - 1) != currentFloor) {
                    listOfFloors.add(currentFloor);
                }
            } else {
                int nextNeeded = getNearestNeededFloor(personsInTheLift, isGoingUp);
                if (nextFloor == -1) {
                    currentFloor = nextNeeded;
                    popPersons(personsInTheLift, currentFloor);
                    pushPersons(floorAndPeopleList.get(currentFloor), currentFloor, personsInTheLift, capacity, isGoingUp);
                    listOfFloors.add(currentFloor);
                } else {

                    if (isGoingUp) {

                        currentFloor = nextNeeded < nextFloor ? nextNeeded : nextFloor;

                    } else {

                        currentFloor = nextNeeded > nextFloor ? nextNeeded : nextFloor;
                    }

                    popPersons(personsInTheLift, currentFloor);
                    pushPersons(floorAndPeopleList.get(currentFloor), currentFloor, personsInTheLift, capacity, isGoingUp);
                    listOfFloors.add(currentFloor);
                }
            }
        }
        if (listOfFloors.get(listOfFloors.size() - 1) != 0) {
            listOfFloors.add(0);
        }

        return listOfFloors;
    }


    /**
     * Get the nearest floor called from the current floor where someone is waiting for
     * lift depending on direction.
     *
     * @param floorAndPeopleList  List of people waiting for lift at certain floors.
     * @param totalNumberOfFloors The maximum no of floor to which the lift can go to
     * @param currentFloor        The current Floor
     * @param isGoingUp           A flag indicating if the lift is going up or down
     * @return The nearest floor where someone is waiting to go either up or down depending on direction
     */
    private static int getNearestCall(ArrayList<ArrayList<Integer>> floorAndPeopleList, int totalNumberOfFloors, int currentFloor, boolean isGoingUp) {

        if (isGoingUp) {

            //Get the nearest floor from current floor where someone wants to go up
            for (int i = currentFloor + 1; i < totalNumberOfFloors; i++) {
                if (floorAndPeopleList.get(i).size() != 0) {
                    if (nearestCallAvailable(floorAndPeopleList, i, isGoingUp)) {
                        return i;
                    }

                }
            }
        } else {
            //Get the nearest floor from current floor where someone wants to go down
            for (int i = currentFloor - 1; i >= 0; i--) {
                if (floorAndPeopleList.get(i).size() != 0) {
                    if (nearestCallAvailable(floorAndPeopleList, i, isGoingUp)) {
                        return i;
                    }
                }
            }
        }
        //if there is no one waiting for lift at all or
        //if lift is going up and there are ppl only waiting to come down or
        //if lift is going down and there are ppl only waiting to come up or
        //then return 1

        return -1;

    }

    private static boolean nearestCallAvailable(ArrayList<ArrayList<Integer>> floorAndPeopleList, int i, boolean isGoingUp) {

        return isGoingUp ? floorAndPeopleList.get(i).stream().anyMatch(person -> person > i) :
                floorAndPeopleList.get(i).stream().anyMatch(person -> person < i);

    }

    /**
     * Computes the nextNeededFloor of the people on the lift based on the direction.
     *
     * @param personsInTheLift List of people already in the lift waiting to be dropped.
     * @param isGoingUp        A flag indicating if the lift is going up or down.
     * @return The nearest floor where the lift has to stop depending on people in the lift and direction.
     */
    private static int getNearestNeededFloor(ArrayList<Integer> personsInTheLift, boolean isGoingUp) {
        int nearest;
        if (isGoingUp) {
            nearest = Collections.min(personsInTheLift);
        } else {
            nearest = Collections.max(personsInTheLift);
        }

        return nearest;
    }

    /**
     * Pushes people into the lift if there is still capacity and removes them from the
     * waiting list of people on the floor.
     *
     * @param currentQueueOnFloor List of people waiting at a floor for the lift.
     * @param currentFloor        The current Floor
     * @param personsInTheLift    A list of people already in the Lift
     * @param capacity            The maximum number of people the lift can hold
     * @param isGoingUp           A flag indicating if the lift is going up or down.
     */
    private static void pushPersons(ArrayList<Integer> currentQueueOnFloor, int currentFloor,
                                    ArrayList<Integer> personsInTheLift, int capacity, boolean isGoingUp) {

        if (currentQueueOnFloor.isEmpty()) {
            return;
        }

        int index = 0;
        while (index < currentQueueOnFloor.size()) {
            if (personsInTheLift.size() < capacity) {
                if (isGoingUp) {

                    if (currentQueueOnFloor.get(index) > currentFloor) {
                        personsInTheLift.add(currentQueueOnFloor.get(index));
                        currentQueueOnFloor.remove(index);

                    } else {
                        index++;
                    }

                } else {

                    if (currentQueueOnFloor.get(index) < currentFloor) {
                        personsInTheLift.add(currentQueueOnFloor.get(index));
                        currentQueueOnFloor.remove(index);

                    } else {
                        index++;
                    }

                }
            } else {
                break;
            }
        }
    }

    /**
     * Removes people from the lift to their destined floor.
     *
     * @param currentFloor     The floor at which the lift visits and performs operations
     * @param personsInTheLift A list of people already in the Lift
     */


    private static void popPersons(ArrayList<Integer> personsInTheLift, int currentFloor) {

        personsInTheLift.removeIf(person -> person == currentFloor);
    }

    /**
     * Checks if there are people waiting to be dropped to their destination by the lift.
     *
     * @param currentQueues The floor at which the lift visits and performs operations
     * @return true: if there are no more people waiting , false: if people are still waiting
     */
    private static boolean isQueuesAreEmpty(ArrayList<ArrayList<Integer>> currentQueues) {
        return currentQueues.stream().noneMatch((queue) -> (!queue.isEmpty()));
    }

    private static ArrayList<ArrayList<Integer>> createFloorAndPeopleList(int[][] queues) {
        ArrayList<ArrayList<Integer>> peopleAndFloorList = new ArrayList<>(queues.length);
        for (int[] queue : queues) {
            peopleAndFloorList.add((ArrayList<Integer>) Arrays.stream(queue).boxed().collect(Collectors.toList()));
        }
        return peopleAndFloorList;
    }


}
