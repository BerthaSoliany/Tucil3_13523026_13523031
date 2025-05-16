// import java.io.IOException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import javax.swing.text.html.StyleSheet;

import lib.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello there!\nWelcome to Rush Hour Solution Program!");
        try(Scanner sc = new Scanner(System.in)){
            System.out.println("Please input your fileName (ex.txt): ");
            String fileName = sc.nextLine();

            while (fileName.isEmpty() || !fileName.contains(".txt")) {
                System.out.println("Please input your fileName (ex.txt): ");
                fileName = sc.nextLine();
            }

            File file = new File("test\\"+fileName);

            while(!file.exists()) {
                System.out.println("No file with such name in test folder.\nPlease input your fileName (ex.txt): ");
                fileName = sc.nextLine();
                file = new File("test\\"+fileName);
            }

            Board b = new Board();
            fileName = "test\\" + fileName;
            InputOutput.readFile(fileName, b);
            b.displayBoard();

            // // debugging
            // b.displayPiece();
            // List<Board> visited = b.getNeighbors();
            // for (Board board : visited) {
            //     board.displayBoard();
            // }

            System.out.println("Sekarang, pilih algoritma penyelesaian yang ingin kamu gunakan: ");
            System.out.println("1. Algoritma UCS");
            System.out.println("2. Algoritma Greedy Best First Search");
            System.out.println("3. Algoritma A*");
            System.out.println("Pilihan: ");            
            int pilihan=0;
            pilihan = Integer.parseInt(sc.nextLine());

            while (pilihan<1 || pilihan>3) {
                System.out.println("Pilihan tidak valid");
                System.out.print("Pilihan: ");
                pilihan = Integer.parseInt(sc.nextLine());
            }

            // bisa ditaro disini juga buat itung waktunya
            switch (pilihan) {
                case 1:
                    
                    break;
            
                default:
                    break;
            }

            List<Simpul> output = Algorithm.search(b, pilihan);
            InputOutput.printOutput(b, output);

            InputOutput.writeFile(fileName, b);
        }
    }
}
// javac -d bin -sourcepath src src/Main.java
// java -cp bin Main