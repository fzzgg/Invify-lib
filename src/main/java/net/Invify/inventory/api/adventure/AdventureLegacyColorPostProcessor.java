package net.Invify.inventory.api.adventure;

import net.Invify.inventory.util.AdventureUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public final class AdventureLegacyColorPostProcessor implements UnaryOperator<Component> {

    private static final TextReplacementConfig LEGACY_REPLACEMENT_CONFIG = TextReplacementConfig.builder()
            .match(Pattern.compile(".*"))
            .replacement((matchResult, builder) -> AdventureUtil.parseText(matchResult.group()))
            .build();

    @Override
    public Component apply(Component component) {
        return component.replaceText(LEGACY_REPLACEMENT_CONFIG);
    }

}