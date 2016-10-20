import java.util.Arrays;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import com.caffeine.utils.Platform;

public class PlatformTest {

    @Test
    public void testPlatformDetectsOS(){
        String os = System.getProperty("os.name").split("\\s+")[0];

        switch(os){
            case "Linux":
                assertTrue(Platform.isLinux());
                break;
            case "Mac":
                assertTrue(Platform.isMacOSX());
                break;
            case "Windows":
                assertTrue(Platform.isWindows());
                break;
            default:
                fail("Platform could not match your Operating System.");
                break;
        }
    }

    @Test
    public void testPlatformDetectsBitWidth(){
        String arch = System.getProperty("os.arch");

        ArrayList<String> possible_64 = new ArrayList<String>();
        possible_64.add("x64");
        possible_64.add("amd64");
        possible_64.add("i686");
        ArrayList<String> possible_32 = new ArrayList<String>();
        possible_32.add("x32");
        possible_32.add("i386");

        if (possible_64.contains(arch)){ assertTrue(Platform.is64bit()); }
        else if (possible_32.contains(arch)){ assertTrue(Platform.is32bit()); }
        else { fail("Platform could not match your Architecture Bit Width"); }
    }

    @Test
    public void testPlatformStringIsCorrect(){
        String os = System.getProperty("os.name").split("\\s+")[0].toLowerCase();
        String arch = System.getProperty("os.arch");

        ArrayList<String> possible_64 = new ArrayList<String>();
        possible_64.add("x64");
        possible_64.add("amd64");
        possible_64.add("i686");
        ArrayList<String> possible_32 = new ArrayList<String>();
        possible_32.add("x32");
        possible_32.add("i386");

        if (possible_64.contains(arch)){ arch = "x64"; }
        else if (possible_64.contains(arch)){ arch = "x32"; }
        else { arch = "null"; }

        String plat = os + "_" + arch;

        assertEquals(plat, Platform.getPlatformString());
    }
}
