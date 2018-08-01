package com.colobu.rpcx.config;

import java.util.regex.Pattern;

public class Constants {

    public static final String VERSION_KEY = "version";

    public static final String GROUP_KEY = "group";


    public static final String BACKUP_KEY = "backup";

    public static final String DEFAULT_KEY_PREFIX = "default.";


    public static final Pattern COMMA_SPLIT_PATTERN = Pattern
            .compile("\\s*[,]+\\s*");

    public static final String INTERFACE_KEY = "interface";


    public static final String LOCALHOST_KEY = "localhost";

    public static final String ANYHOST_VALUE = "0.0.0.0";

    public static final String ANYHOST_KEY = "anyhost";

    public static final String ASYNC_KEY = "async";

    public static final String RETURN_KEY = "return";


    public static final String $ECHO = "$echo";


    public static final String TOKEN_KEY = "token";

    public static final String TIMEOUT_KEY = "timeout";

    public static final String PATH_KEY = "path";

    public static final String CACHE_KEY = "cache";

    public static final String COMMA_SEPARATOR = ",";

    public static final String TPS_LIMIT_RATE_KEY = "tps";

    public static final String TPS_LIMIT_INTERVAL_KEY = "tps.interval";

    public static final long DEFAULT_TPS_LIMIT_INTERVAL = 60 * 1000;

}