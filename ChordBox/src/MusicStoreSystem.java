// src/MusicStoreSystem.java
import gui.MusicStoreGUI;
import utils.DatabaseUtil;

public class MusicStoreSystem {
    public static void main(String[] args) {
        // Initialize the database
        DatabaseUtil.initializeInventoryDatabase();
        DatabaseUtil.initializeTransactionsDatabase();

        // Start the GUI
        MusicStoreGUI.main(args);
    }
}
