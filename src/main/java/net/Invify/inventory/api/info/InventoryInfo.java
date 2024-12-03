package net.Invify.inventory.api.info;

import net.Invify.inventory.api.size.InvifiInventorySize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to provide metadata for custom inventories.
 * This annotation can be used on inventory classes to specify details
 * like the inventory size, display name, and whether the click events should be cancelled.
 */
@Target(value = ElementType.TYPE) // The annotation can be applied to types (i.e., classes).
@Retention(RetentionPolicy.RUNTIME) // This annotation will be available at runtime.
public @interface InventoryInfo {

    /**
     * Specifies the size of the inventory.
     * Default is `InvifiInventorySize.THREE_LINE`, which corresponds to a 27-slot inventory.
     */
    InvifiInventorySize size() default InvifiInventorySize.THREE_LINE;

    /**
     * Specifies the display name of the inventory.
     * Default is "Hey, you forgot to change the name!" as a placeholder message.
     */
    String displayName() default "Hey, you forgot to change the name!";

    /**
     * Specifies whether to cancel the click event inside the inventory.
     * If true, clicking inside the inventory will be blocked.
     * Default is true.
     */
    boolean cancelClickEvent() default true;
}
