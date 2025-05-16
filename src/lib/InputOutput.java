package lib;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class InputOutput {
    public static void readFile(String fileName, Board b) {
        try {
            // BARIS X KOLOM
            int A,B,N;

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringTokenizer st = new StringTokenizer(br.readLine());

            if (st.countTokens()!=2){
                br.close();
                throw new IOException("Format ukuran papan tidak valid.");
            }

            try {
                A = Integer.parseInt(st.nextToken());
                B = Integer.parseInt(st.nextToken());
            } catch (NumberFormatException e) {
                br.close();
                throw new IOException("Format salah: A dan B harus berupa angka.");
            }
            b.setWidth(B);
            b.setHeight(A);

            System.out.println("A:"+A+" B:"+B); // debugging
            
            if (A<=0||B<=0){
                br.close();
                throw new IOException("Dimensi papan tidak valid.");
            }

            N = Integer.parseInt(br.readLine());
            b.setTotalPieces(N);

            System.out.println("Total piece selain primary piece: "+N); // debugging
            
            char[][] board = new char[A][B];
            int[] exit = {-1,-1};

            List<String> lines = new ArrayList<>();
            String line;
            while ((line=br.readLine())!=null){
                lines.add(line);
            }

            int row = 0;
            for (int i=0;i<lines.size();i++){
                String l = lines.get(i);
                // l = l.replaceAll(" ", "");
                if (l.trim().equals("K")){
                    // K di plg atas, exit={-1,j}
                    if (i == 0) {
                        exit[0] = -1;
                        exit[1] = l.indexOf('K');
                    } else if (i == lines.size() - 1) { // K plg bawah, exit={B,j}
                        exit[0] = A;
                        exit[1] = l.indexOf('K');
                    } 
                    continue;
                }

                l = l.replaceAll(" ","");

                if (l.length()==B+1){
                    // K plg kiri, exit={row,-1}
                    if (l.charAt(0)=='K'){
                        exit[0] = row;
                        exit[1] = -1;
                        l=lines.get(i).substring(1);
                    } else if (l.charAt(l.length()-1)=='K'){ // K plg kanan, exit={row,B}
                        exit[0] = row;
                        exit[1] = B;
                        l = l.substring(0, B);
                    }
                }

                if (row<A){
                    board[row++] = l.substring(0, B).toCharArray();
                }
            }

            if (exit[0] == -1 && exit[1] == -1) {
                br.close();
                throw new IOException("Pintu keluar (K) tidak ditemukan atau tidak valid.");
            }

            b.setExitLoc(exit);
            b.setBoard(board);


            Map<Character, List<int[]>> pieceMap = new HashMap<>();
            for (int i = 0; i < A; i++) {
                for (int j = 0; j < B; j++) {
                    char c = board[i][j];
                    if (c != '.') {
                        pieceMap.putIfAbsent(c, new ArrayList<>());
                        pieceMap.get(c).add(new int[]{i, j});
                    }
                }
            }

            Piece[] pieces = new Piece[pieceMap.size()];
            int idx = 0;
            for (Map.Entry<Character, List<int[]>> entry : pieceMap.entrySet()) {
                char id = entry.getKey();
                List<int[]> locs = entry.getValue();

                int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
                int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
                for (int[] p : locs) {
                    minX = Math.min(minX, p[0]);
                    maxX = Math.max(maxX, p[0]);
                    minY = Math.min(minY, p[1]);
                    maxY = Math.max(maxY, p[1]);
                }

                int height = maxX - minX + 1;
                int width = maxY - minY + 1;

                int[][] loc = new int[locs.size()][2];
                for (int i = 0; i < locs.size(); i++) {
                    loc[i][0] = locs.get(i)[0];
                    loc[i][1] = locs.get(i)[1];
                }

                Piece piece = new Piece(id, loc, width, height);
                pieces[idx++] = piece;
            }

            b.setPieces(pieces);
            br.close();
            if (pieces.length < N+1){
                throw new IOException("Jumlah piece tidak sesuai dengan input.");
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file "+fileName);
            e.printStackTrace();
        }

    }

    // TODO : tentuin penulisan output geraknya kemana [piece]-[arah gerak]
    public static void writeFile(String fileName, Board b){
        try{
            FileWriter save = new FileWriter(fileName+"_solution");
            save.write("Step to step solution for "+fileName+"\n\n"); // fileName adalah fileName input
            save.write("Board Dimension: "+b.getHeight()+" x "+b.getWidth()+"\n");
            save.write("Total pieces beside P: "+b.getTotalPieces()+"\n");
            save.write("\nTime spent: "+"\n"); // getTime dari algo
            save.write("Total move made: "+"\n"); // getTotalMove dari algo
            // save.write("Initial Board\n");
            for (int k=0;k<b.getTotalStep();k++){
                save.write("Step-"+k+"\n");
                if (b.getExitLoc()[0]==-1){
                    for (int i=0;i<b.getWidth();i++){
                        if (b.getExitLoc()[1]==i){
                            save.write("K");
                        } else {
                            save.write(" ");
                        }
                    }
                    save.write("");
                    for (int i = 0; i < b.getHeight(); i++) {
                        for (int j = 0; j < b.getWidth(); j++) {
                            save.write(b.getBoard()[i][j]);
                        }
                        save.write("");
                    }
                } else if (b.getExitLoc()[0]==b.getHeight()){
                    for (int i = 0; i < b.getHeight(); i++) {
                        for (int j = 0; j < b.getWidth(); j++) {
                            save.write(b.getBoard()[i][j]);
                        }
                        save.write("");
                    }
                    for (int i=0;i<b.getWidth();i++){
                        if (b.getExitLoc()[1]==i){
                            save.write("K");
                        } else {
                            save.write(" ");
                        }
                    }
                    save.write("");
                } else if (b.getExitLoc()[1] == -1) {
                    for (int i = 0; i < b.getHeight(); i++) {
                        if (i == b.getExitLoc()[0]) {
                            save.write("K");
                        } else {
                            save.write(" ");
                        }
                        for (int j = 0; j < b.getWidth(); j++) {
                            save.write(b.getBoard()[i][j]);
                        }
                        save.write("");
                    }
                } else if (b.getExitLoc()[1] == b.getWidth()) {
                    for (int i = 0; i < b.getHeight(); i++) {
                        for (int j = 0; j < b.getWidth(); j++) {
                            save.write(b.getBoard()[i][j]);
                        }
                        if (i == b.getExitLoc()[0]) {
                            save.write("K");
                        } else {
                            save.write(" ");
                        }
                        save.write("");
                    }
                }
            }
            
            save.close();
        } catch (IOException e){
            System.out.println("Error writing file");
            e.printStackTrace();
        }
    }

    
}
