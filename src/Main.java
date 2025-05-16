import java.io.IOException;
import lib.*;

public class Main {
    public static void main(String[] args) {
        String filename = args[0];
        Board b = new Board();

        InputOutput.readFile(filename,b);

        b.printBoardWithExit();
    }
}
