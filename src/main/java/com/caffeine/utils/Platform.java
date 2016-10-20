package com.caffeine.utils;

public final class Platform {
    // A static package with convenient shorthand methods to determine a
    // system's platform.


    private static String operatingSystem = System.getProperty("os.name");
    private static String architecture = System.getProperty("os.arch");

    // Get Operating System
    // TODO: Improve this logic, it's terrible.
    public static boolean isLinux(){
        return (operatingSystem.equals("Linux"));
    }

    public static boolean isMacOSX(){
        return (operatingSystem.equals("Mac OS X"));
    }

    public static boolean isWindows(){
        return (operatingSystem.contains("Windows"));
    }


    // Get Architecture
    public static boolean is32bit(){
        if (architecture.equals("x86") || architecture.equals("i386")){
            return true;
        }
        return false;
    }

    public static boolean is64bit(){
        if (architecture.equals("x64") || architecture.equals("amd64")){
            return true;
        }
        return false;
    }


    // Get Platform as String
    public static String getPlatformString(){
        if (Platform.isLinux() && Platform.is64bit()){ return "linux_x64"; }
        if (Platform.isLinux() && Platform.is32bit()){ return "linux_x32"; }
        if (Platform.isMacOSX() && Platform.is64bit()){ return "osx_x64"; }
        if (Platform.isMacOSX() && Platform.is32bit()){ return "osx_x32";}
        if (Platform.isWindows() && Platform.is64bit()){ return "win_x64"; }
        if (Platform.isWindows() && Platform.is32bit()){ return "win_x32"; }
        else { return "null"; }
    }
}
