public class OutputDevice {
    public void writeMessage(String message) {
        System.out.println(message);
    }

    public <T> void printArray(T[] array){
        writeMessage("this is reached");

        for(int i = 0 ; i < array.length ; i++){
            writeMessage("array["+ i + "]=" + array[i]);
        }
    }
}
