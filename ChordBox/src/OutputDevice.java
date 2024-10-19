public class OutputDevice {

    public void writeMessage(String message) {
        System.out.println(message);
    }
    

    // made it more modular such that accepts any data type, and prints the
    // elements.
    public <T> void printArray(T[] array){
        writeMessage("the array has the following:");

        for(int i = 0 ; i < array.length ; i++){
            writeMessage("array["+ i + "]=" + array[i]);
        }
    }
}
