// First-Party Imports
import javax.swing.*;
import java.awt.Frame;

// Third-Party Imports
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.assertj.swing.core.*;
import org.assertj.swing.fixture.*;
import org.assertj.swing.exception.*;
import org.assertj.swing.core.matcher.JButtonMatcher.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.finder.JOptionPaneFinder.findOptionPane;

// Local Imports
import com.caffeine.Chess;
import com.caffeine.view.*;
import com.caffeine.logic.*;

/* ============================================================================
                            View Layer Tests
============================================================================ */
public class ViewTest {

    @Test // Sanity Check -- Can AssertJ be used?
    public void testAssertJIntegrated(){
        Object o = new Object();
        org.assertj.core.api.Assertions.assertThat("test")
            .as("Generic test for AssertJ integration", o)
            .isEqualTo("test");
    }
	
	
	//BoardSquare unit tests
	
	@Test
	public void testSetBackgroundColor(){
		boolean isLight = true;
		BoardSquare testSquare = new BoardSquare();
		testSquare.setBackgroundColor(isLight);
		assertTrue(testSquare.isLightSquare());
		isLight = false;
		testSquare.setBackgroundColor(isLight);
		assertFalse(testSquare.isLightSquare());	
	}

	@Test
	public void testHasPiece(){
		BoardSquare testSquare = new BoardSquare();
		testSquare.setPiece(null);
		assertFalse(testSquare.hasPiece());
	}
	
	@Test
	public void testGetPiece(){
		BoardSquare testSquare = new BoardSquare();
		Piece testPiece = new Piece("test", "test", 0, 0);
		testSquare.setPiece(testPiece);
		assertEquals(testPiece, testSquare.getPiece());
	}
	
	@Test
	public void testRemovePiece(){
		BoardSquare testSquare = new BoardSquare();
		Piece testPiece = new Piece("test", "test", 0, 0);
		testSquare.setPiece(testPiece);
		testSquare.removePiece();
		assertNull(testSquare.getPiece());
	}

}