package com.chordbox;

import com.chordbox.gui.MusicStoreGUI;
import com.chordbox.utils.DatabaseUtil;

public class MusicStoreSystem {
    public static void main(String[] args) {
        // Initialize the database
        DatabaseUtil.initializeInventoryDatabase();
        DatabaseUtil.initializeTransactionsDatabase();

        // Start the GUI
        MusicStoreGUI.main(args);
    }
}
