package com.caffeine.logic;

import java.util.ArrayList;
import java.util.HashMap;

import com.caffeine.logic.State;

public class FileManager {

    public FileManager(){;} // Trivial Default Constructor

    public class PGNBuilder {
        // Instantiate as new, set properties, then create with `save()` method.

        // Only Instantiate, do not initialize. Makes checking easier.
        private HashMap<String, String> sevenTagRoster;
        private HashMap<String, String> optionalTags;
        private String filePath;
        private String fenString;
        private ArrayList<String> moveHistory;

        public PGNBuilder(){;} // Trivial Default Constructor

        // public boolean save(){ return false; } // Should raise many exceptions
    }

    public class StateLoader {
        // Instantiate as new, set properties, then load with `load()` method.

        // private State newState = new State();

        public StateLoader(){;} // Trivial Default Constructor

        // public boolean load(){ return false; } // Should raise exceptions if ANYTHING doesn't work.


    }
}
