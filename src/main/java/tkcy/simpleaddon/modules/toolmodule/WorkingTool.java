package tkcy.simpleaddon.modules.toolmodule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Acts as a simple marker to retrieve any class related to this feature
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface WorkingTool {}
