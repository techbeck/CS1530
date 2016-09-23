
import org.junit.Test;
import static org.junit.Assert.*;

import com.caffeine.World;

public class WorldTest {

    @Test
    public void testWorldReturn(){
        assertEquals(World.returnsOne(), 1);
    }
}