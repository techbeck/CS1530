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

}
