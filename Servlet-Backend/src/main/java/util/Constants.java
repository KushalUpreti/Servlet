package util;

import java.util.Map;
import java.util.Set;

public class Constants {
    public static final Set<String> ALLOWED_PATHS = Set.of("", "/auth", "/guest");
    public static final Map<String, String> env = System.getenv();
    public static final Set<String> ALLOWED_ORIGINS = Set.of("http://localhost:3000", "http://localhost:4200");
}
