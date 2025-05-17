// import java.io.IOException;
import java.io.File;
import java.io.IOException;
// import java.io.IOException;
import java.util.Scanner;
import java.util.List;
// import java.util.ArrayList;

// import javax.swing.text.html.StyleSheet;

import lib.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nHalooo!\nSelamat datang di program penyelesaian Rush Hour!");
        try(Scanner sc = new Scanner(System.in)){
            System.out.println("Masukkan nama file (ex.txt) atau 'exit' untuk keluar: ");
            String fileName = sc.nextLine();
            if (fileName.equals("exit")){System.out.println("Bye bye~");System.exit(1);}
            while (fileName.isEmpty() || !fileName.contains(".txt")) {
                System.out.println("Masukkan nama file (ex.txt) atau 'exit' untuk keluar: ");
                fileName = sc.nextLine();
                if (fileName.equals("exit")){System.out.println("Bye bye~");System.exit(1);}

            }

            File file = new File("test\\"+fileName);

            while(!file.exists()) {
                System.out.println("Tidak ada file dengan nama "+fileName+" di folder test.\nMasukkan nama file (ex.txt): ");
                fileName = sc.nextLine();
                file = new File("test\\"+fileName);
            }
            try{
                Board b = new Board();
                InputOutput.readFile(fileName, b);
            
                // b.displayBoard();

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
                System.out.println("4. Keluar");
                System.out.println("Pilihan: ");            
                int pilihan=0;
                pilihan = Integer.parseInt(sc.nextLine());

                while (pilihan<1 || pilihan>4) {
                    System.out.println("Pilihan tidak valid");
                    System.out.print("Pilihan: ");
                    pilihan = Integer.parseInt(sc.nextLine());
                }

                // bisa ditaro disini juga buat itung waktunya
                switch (pilihan) {
                    case 1:
                        
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Bye bye~");
                        break;
                }
                long startTime = System.nanoTime();
                List<Simpul> output = Algorithm.search(b, pilihan);
                long endTime = System.nanoTime();
                long duration = ((endTime-startTime)/1_000_000);
                InputOutput.printOutput(b, output);

                InputOutput.writeFile(fileName, b, output, duration);
            } catch (IOException e) {
                main(args);
            }
        }
    }
}
// javac -d bin -sourcepath src src/Main.java
// java -cp bin Main