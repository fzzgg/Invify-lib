package net.Invify.inventory.api.size;

import lombok.Getter;

/**
 * Enum representing different predefined inventory sizes.
 * Each size corresponds to a different number of slots and some may include specific colored border slots.
 */

public enum InvifyInventorySize {

    // Different inventory sizes with a name, total slot count, and specific border slots.
    ONE_LINE("ONE_LINE", 9),       // 9 slots (1 row)
    TWO_LINE("TWO_LINE", 18),      // 18 slots (2 rows)
    THREE_LINE("THREE_LINE", 27),  // 27 slots (3 rows)
    FOUR_LINE("FOUR_LINE", 36),    // 36 slots (4 rows)
    FIVE_LINE("FIVE_LINE", 45),    // 45 slots (5 rows)
    SIX_LINE("SIX_LINE", 54);      // 54 slots (6 rows)

    // Name of the inventory size (e.g., "ONE_LINE", "TWO_LINE").
    private final String name;

    // Total number of slots for this inventory size.
    @Getter
    private final int size;

    // An array representing the border slots that might be used for decoration or special purpose.

    InvifyInventorySize(String name, int size) {
        this.name = name;
        this.size = size;

    }

    /**
     * Fits the given number of slots to the closest predefined inventory size.
     *
     * @param slots The number of slots to fit.
     * @return The closest inventory size based on the provided number of slots.
     */
    public static InvifyInventorySize fit(int slots) {
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
    public static InvifyInventorySize of(String name) {
        for (InvifyInventorySize invifyInventorySize : values()) {
            if (invifyInventorySize.name.equals(name)) {
                return invifyInventorySize; // Return the matching inventory size.
            }
        }
        return null; // Return null if no matching size is found.
    }

}
