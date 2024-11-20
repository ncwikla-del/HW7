/******************************************************************
 *
 *   Nick Cwikla / COMP272-001
 *
 *   This java file contains the problem solutions for the methods selectionSort,
 *   mergeSortDivisibleByKFirst, asteroidsDestroyed, and numRescueCanoes methods.
 *
 ********************************************************************/


import java.util.Arrays;



public class ProblemSolutions {

    /**
     * Method SelectionSort
     *
     * This method performs a selection sort. This file will be spot checked,
     * so ENSURE you are performing a Selection Sort!
     *
     * This is an in-place sorting operation that has two function signatures. This
     * allows the second parameter to be optional, and if not provided, defaults to an
     * ascending sort. If the second parameter is provided and is false, a descending
     * sort is performed.
     *
     * @param values        - int[] array to be sorted.
     * @param ascending     - if true,method performs an ascending sort, else descending.
     *                        There are two method signatures allowing this parameter
     *                        to not be passed and defaulting to 'true (or ascending sort).
     */

    public  void selectionSort(int[] values) {
        selectionSort(values, true);
    }

    public static void selectionSort(int[] values, boolean ascending ) {
       // Acquire the length of the array.
        int n = values.length;
       // Go through all elements present in the array.
        for (int i = 0; i < n - 1; i++) {
           // Initialize the selected index as current index.
            int selectIndex = i;
           // Go through and find the elements that are smaller or larger.
            for (int j = i +1; j < n; j++) {
                // selectIndex is updated for sorting order.
                if (ascending ? values[j] < values[selectIndex] : values[j] > values[selectIndex]) {
                    selectIndex = j;
                }
            }
           // First element in the unsorted portion is swapped with smallest/largest element.
            int temp = values[selectIndex];
           // Current element moved to selected index.
            values[selectIndex] = values[i];
           // Put the selected element in the correct position.
            values[i] = temp;

        }

    } // End class selectionSort


    /**
     *  Method mergeSortDivisibleByKFirst
     *
     *  This method will perform a merge sort algorithm. However, all numbers
     *  that are divisible by the argument 'k', are returned first in the sorted
     *  list. Example:
     *        values = { 10, 3, 25, 8, 6 }, k = 5
     *        Sorted result should be --> { 10, 25, 3, 6, 8 }
     *
     *        values = { 30, 45, 22, 9, 18, 39, 6, 12 }, k = 6
     *        Sorted result should be --> { 30, 18, 6, 12, 9, 22, 39, 45 }
     *
     * As shown above, this is a normal merge sort operation, except for the numbers
     * divisible by 'k' are first in the sequence.
     *
     * @param values    - input array to sort per definition above
     * @param k         - value k, such that all numbers divisible by this value are first
     */

    public void mergeSortDivisibleByKFirst(int[] values, int k) {

        // Protect against bad input values
        if (k == 0)  return;
        if (values.length <= 1)  return;

        mergeSortDivisibleByKFirst(values, k, 0, values.length-1);
    }

    private void mergeSortDivisibleByKFirst(int[] values, int k, int left, int right) {

        if (left >= right)
            return;

        int mid = left + (right - left) / 2;
        mergeSortDivisibleByKFirst(values, k, left, mid);
        mergeSortDivisibleByKFirst(values, k, mid + 1, right);
        mergeDivisbleByKFirst(values, k, left, mid, right);
    }

    /*
     * The merging portion of the merge sort, divisible by k first
     */

    private void mergeDivisbleByKFirst(int arr[], int k, int left, int mid, int right)
    {
       // Create a temporary array for merging.
        int[] temp = new int[right - left + 1];
       // Initialize pointers for left, right, and temp array.
        int i = left, j = mid + 1, k1 = 0;
        // Merging of elements, divisible by k first.
        while (i <= mid && j <= right) {
            if (arr[i] % k == 0) {
               // Insert element from left when divisible by k.
                temp[k1++] = arr[i++];
            } else if (arr[j] % k == 0) {
               // Insert element from right when divisible by k.
                temp[k1++] = arr[j++];
            } else if (arr[i] < arr[j]) {
               // Insert the smaller element that is found on left.
                temp[k1++] = arr[i++];
            } else {
               // Otherwise, insert the smaller element from the right.
                temp[k1++] = arr[j++];
            }
        }
        // Insert the remaining elements from the left.
        while (i <= mid) {
            temp[k1++] = arr[i++];
        }
       // Insert the remaining elements from the right.
        while (j <= right) {
            temp[k1++] = arr[j++];
        }
        // Contents in merged temp are then copied over to the original array.
        System.arraycopy(temp, 0, arr, left, temp.length);

    }


    /**
     * Method asteroidsDestroyed
     *
     * You are given an integer 'mass', which represents the original mass of a planet.
     * You are further given an integer array 'asteroids', where asteroids[i] is the mass
     * of the ith asteroid.
     *
     * You can arrange for the planet to collide with the asteroids in any arbitrary order.
     * If the mass of the planet is greater than or equal to the mass of the asteroid, the
     * asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the
     * planet is destroyed.
     *
     * Return true if possible for all asteroids to be destroyed. Otherwise, return false.
     *
     * Example 1:
     *   Input: mass = 10, asteroids = [3,9,19,5,21]
     *   Output: true
     *
     * Explanation: One way to order the asteroids is [9,19,5,3,21]:
     * - The planet collides with the asteroid with a mass of 9. New planet mass: 10 + 9 = 19
     * - The planet collides with the asteroid with a mass of 19. New planet mass: 19 + 19 = 38
     * - The planet collides with the asteroid with a mass of 5. New planet mass: 38 + 5 = 43
     * - The planet collides with the asteroid with a mass of 3. New planet mass: 43 + 3 = 46
     * - The planet collides with the asteroid with a mass of 21. New planet mass: 46 + 21 = 67
     * All asteroids are destroyed.
     *
     * Example 2:
     *   Input: mass = 5, asteroids = [4,9,23,4]
     *   Output: false
     *
     * Explanation:
     * The planet cannot ever gain enough mass to destroy the asteroid with a mass of 23.
     * After the planet destroys the other asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22.
     * This is less than 23, so a collision would not destroy the last asteroid.
     *
     * Constraints:
     *     1 <= mass <= 105
     *     1 <= asteroids.length <= 105
     *     1 <= asteroids[i] <= 105
     *
     * @param mass          - integer value representing the mass of the planet
     * @param asteroids     - integer array of the mass of asteroids
     * @return              - return true if all asteroids destroyed, else false.
     */

    public static boolean asteroidsDestroyed(int mass, int[] asteroids) {
       // Assume that every asteroid can be destroyed.
        boolean destroyed = true;
       // The planet's mass is initialized.
        int totalMass = mass;
       // Go through each asteroid in the array.
        for(int asteroid : asteroids){
           // Make sure planet's mass is enough for asteroid destruction.
            if(totalMass >= asteroid){
               //  Asteroids mass added to planet mass.
                totalMass += asteroid;
            } else{
               // If planet's mass is not enough, destruction is false.
                destroyed = false;
            }
        }
       // Results returned whether asteroids were destroyed.
        return destroyed;


    }


    /**
     * Method numRescueSleds
     *
     * You are given an array people where people[i] is the weight of the ith person,
     * and an infinite number of rescue sleds where each sled can carry a maximum weight
     * of limit. Each sled carries at most two people at the same time, provided the
     * sum of the weight of those people is at most limit. Return the minimum number
     * of rescue sleds to carry every given person.
     *
     * Example 1:
     *    Input: people = [1,2], limit = 3
     *    Output: 1
     *    Explanation: 1 sled (1, 2)
     *
     * Example 2:
     *    Input: people = [3,2,2,1], limit = 3
     *    Output: 3
     *    Explanation: 3 sleds (1, 2), (2) and (3)
     *
     * Example 3:
     *    Input: people = [3,5,3,4], limit = 5
     *    Output: 4
     *    Explanation: 4 sleds (3), (3), (4), (5)
     *
     * @param people    - an array of weights for people that need to go in a sled
     * @param limit     - the weight limit per sled
     * @return          - the minimum number of rescue sleds required to hold all people
     */

    public static int numRescueSleds(int[] people, int limit) {
       // 0 is returned if there are no people.
        if (people.length == 0)
            return 0;
       // People array is sorted in ascending order.
        Arrays.sort(people);
       // Initialize the sled counter.
        int sleds = 0;
       // Pointer for lightest person within weight limit.
        int left = 0;
       // Pointer for heaviest person within weight limit.
        int right = people.length - 1;
       // Iterate through all the people.
        while (left <= right) {
           // Make sure lightest and heaviest can be together in the sled.
            if(people[left] + people[right] <= limit) {
               // Add the lightest person.
                left++;
               // Add the heaviest person.
                right--;
               // Increment the sled counter;
                sleds++;
            } else {
               // If sled can't be shared, only heaviest is on sled.
                right--;
               // Increment the sled counter for single sledder.
                sleds++;
            }
        }
       // Total sleds used is returned.
        return sleds;

    }

} // End Class ProblemSolutions

