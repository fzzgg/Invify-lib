package net.Invify.inventory.util;

import net.Invify.inventory.InvifyAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class AdventureUtil {

    // Legacy serializer to handle color codes from older Minecraft versions
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
            .character('§')  // Define the color code character
            .hexColors()  // Enable support for hex color codes
            .useUnusualXRepeatedCharacterHexFormat()  // Use the unusual format for hex colors
            .build();

    // Private constructor to prevent instantiation of this utility class
    private AdventureUtil() {
        throw new UnsupportedOperationException("This class is not designed for instantiation.");
    }

    /**
     * Creates a colored component using the Minimessage system.
     *
     * @param text The text to be colorized.
     * @return A Component representing the colorized text.
     */
    public static Component colorize(String text) {
        return InvifyAPI.getInstance().getMinimessage().deserialize(text);
    }

    /**
     * Parses the input string into a Component using legacy Minecraft color codes.
     *
     * @param input The input string containing Minecraft color codes.
     * @return A Component representing the input string.
     */
    public static Component parseText(String input) {
        return LEGACY_SERIALIZER.deserialize(input);
    }

    /**
     * Extracts plain text from a Component, removing any formatting and colors.
     *
     * @param component The Component to extract plain text from.
     * @return A string containing the plain text.
     */
    public static String extractPlainText(Component component) {
        StringBuilder result = new StringBuilder();

        processComponent(component, result);  // Process the component recursively

        return result.toString();
    }

    /**
     * Helper method to recursively process a Component and append its content to the result.
     *
     * @param component The Component being processed.
     * @param result    The StringBuilder where the plain text will be appended.
     */
    private static void processComponent(Component component, StringBuilder result) {
        if (component instanceof TextComponent textComponent) {
            result.append(textComponent.content());  // Append the content of the text component
        }

        // Recursively process child components
        component.children().forEach(child -> processComponent(child, result));
    }
}
