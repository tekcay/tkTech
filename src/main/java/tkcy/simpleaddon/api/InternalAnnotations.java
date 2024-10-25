package tkcy.simpleaddon.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class InternalAnnotations {

    @Retention(value = RetentionPolicy.CLASS)
    @Target(value = ElementType.METHOD)
    public @interface NotBreakingBug {

        String value();
    }

    @Retention(value = RetentionPolicy.CLASS)
    @Target(value = ElementType.METHOD)
    public @interface WarningCanBreak {

        String value();
    }
}
