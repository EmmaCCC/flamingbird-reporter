package cn.flamingbird.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class HostEnvironment {

    private static final String UNKNOWN = "unknown";
    private static final String DEV = "dev";
    private static final String TEST = "test";
    private static final String PROD = "prod";

    private String profile;

    private String applicationName;

    public HostEnvironment(String profile, String applicationName) {
        this.profile = profile == null ? UNKNOWN : profile;
        this.applicationName = applicationName == null ? UNKNOWN : applicationName;
    }

    public boolean isDevelopment() {
        return profile.contains(DEV);
    }

    public boolean isTest() {
        return profile.contains(TEST);
    }

    public boolean isProduction() {
        return profile.contains(PROD);
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getActiveProfile() {
        return profile;
    }

    public String getHostName() {
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            return hostName;
        } catch (UnknownHostException e) {
            return UNKNOWN;
        }
    }
}
