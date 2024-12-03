package net.Invify.inventory.api.adventure;

import java.util.function.UnaryOperator;

public final class AdventureLegacyColorPreProcessor implements UnaryOperator<String> {

    @Override
    public String apply(String component) {
        return component.replace("§", "&");
    }

}