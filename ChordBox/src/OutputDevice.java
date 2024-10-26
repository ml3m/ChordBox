public class OutputDevice {
    public void writeMessage(String message) {
        System.out.println(message);
    }

    public <T> void printArray(T[] array) {
        writeMessage("Array elements:");
        for (int i = 0; i < array.length; i++) {
            writeMessage("Element [" + i + "] = " + array[i]);
        }
    }
}
