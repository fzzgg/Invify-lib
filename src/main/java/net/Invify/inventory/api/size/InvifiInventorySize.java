package net.Invify.inventory.api.size;

/**
 * Enum representing different predefined inventory sizes.
 * Each size corresponds to a different number of slots and some may include specific colored border slots.
 */

public enum InvifiInventorySize {

    // Different inventory sizes with a name, total slot count, and specific border slots.
    ONE_LINE("ONE_LINE", 9, new int[0]), // 9 slots (1 row)
    TWO_LINE("TWO_LINE", 18, new int[0]), // 18 slots (2 rows)
    THREE_LINE("THREE_LINE", 27, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 24, 25, 26}), // 27 slots (3 rows)
    FOUR_LINE("FOUR_LINE", 36, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 27, 28, 29, 30, 31, 32, 33, 34, 35}), // 36 slots (4 rows)
    FIVE_LINE("FIVE_LINE", 45, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 36, 37, 38, 39, 40, 41, 42, 43, 44}), // 45 slots (5 rows)
    SIX_LINE("SIX_LINE", 54, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53}); // 54 slots (6 rows)

    // Name of the inventory size (e.g., "ONE_LINE", "TWO_LINE").
    private final String name;

    // Total number of slots for this inventory size.
    private final int size;

    // An array representing the border slots that might be used for decoration or special purpose.

    InvifiInventorySize(String name, int size, int[] coloredBorderSlots) {
        this.name = name;
        this.size = size;

    }

    /**
     * Fits the given number of slots to the closest predefined inventory size.
     *
     * @param slots The number of slots to fit.
     * @return The closest inventory size based on the provided number of slots.
     */
    public static InvifiInventorySize fit(int slots) {
        if (slots < 10) {
            return ONE_LINE; // Return ONE_LINE if there are fewer than 10 slots.
        } else if (slots < 19) {
            return TWO_LINE; // Return TWO_LINE for 10-18 slots.
        } else if (slots < 28) {
            return THREE_LINE; // Return THREE_LINE for 19-27 slots.
        } else if (slots < 37) {
            return FOUR_LINE; // Return FOUR_LINE for 28-36 slots.
        } else if (slots < 46) {
            return FIVE_LINE; // Return FIVE_LINE for 37-45 slots.
        }

        return SIX_LINE; // Return SIX_LINE for 46 or more slots.
    }

    /**
     * Finds an inventory size by its name.
     *
     * @param name The name of the inventory size (e.g., "ONE_LINE").
     * @return The corresponding inventory size or null if not found.
     */
    public static InvifiInventorySize of(String name) {
        for (InvifiInventorySize invifiInventorySize : values()) {
            if (invifiInventorySize.name.equals(name)) {
                return invifiInventorySize; // Return the matching inventory size.
            }
        }
        return null; // Return null if no matching size is found.
    }

    public int getSize() {
        return size;
    }
}
