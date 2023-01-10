package util;

import java.util.Map;
import java.util.Set;

public class Constants {
    public static final Set<String> ALLOWED_PATHS = Set.of("", "/auth", "/guest");
    public static final Map<String, String> env = System.getenv();
}
