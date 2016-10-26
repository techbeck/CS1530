// First-Party Imports
import org.junit.Test;
import static org.junit.Assert.*;

// Third-Party Imports
import com.jwarner.jockfish.JockfishEngine;
import static org.assertj.core.api.Assertions.*;

// Local Imports
import com.caffeine.Chess;

public class ViewTest {
	
	@Test
	public void testAssertJIntegrated(){
		Object o = new Object();
		assertThat("test").as("Generic test for AssertJ integration", o).isEqualTo("test");
	}
}
