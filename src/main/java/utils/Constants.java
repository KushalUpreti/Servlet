package utils;

import java.util.Map;
import java.util.Set;

public class Constants {
    public static final Set<String> ALLOWED_PATHS = Set.of("", "/auth");
    public static final Map<String, String> env = System.getenv();
}
