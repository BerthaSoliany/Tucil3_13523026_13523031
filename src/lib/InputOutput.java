package lib;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class InputOutput {
    public static void readFile(String fileName, Board b) throws IOException{
        try {
            fileName = "test\\" + fileName;
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

            System.out.println("Total piece selain primary piece: "+N+"\n"); // debugging
            
            char[][] board = new char[A][B];
            int[] exit = {-1,-1};

            List<String> lines = new ArrayList<>();
            String line;
            while ((line=br.readLine())!=null){
                lines.add(line);
            }
            
            int panjang, lebar=-1; // untuk mengecek kevalidan panjang dan lebar papan

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
                    panjang = lines.size()-1;
                    if (panjang!=A){br.close();throw new IOException("Tinggi papan "+panjang+" tidak sesuai.");}
                    continue;
                }

                l = l.replaceAll(" ","");

                if (l.length()==B+1){
                    
                    // K plg kiri, exit={row,-1}
                    if (l.charAt(0)=='K'){
                        exit[0] = row;
                        exit[1] = -1;
                        l=lines.get(i).substring(1);

                        panjang = lines.size();
                        if (panjang!=A){br.close();throw new IOException("Tinggi papan "+panjang+" tidak sesuai.");}
                    } else if (l.charAt(l.length()-1)=='K'){ // K plg kanan, exit={row,B}
                        exit[0] = row;
                        exit[1] = B;
                        l = l.substring(0, B);

                        panjang = lines.size();
                        if (panjang!=A){br.close();throw new IOException("Tinggi papan "+panjang+" tidak sesuai.");}
                    }
                    lebar = l.length();
                    if (lebar != B){br.close();throw new IOException("Lebar papan "+lebar+" tidak sesuai.");}
                    
                } else {
                    lebar = l.length();
                    // System.out.println(lebar);

                    if (lebar != B){br.close();throw new IOException("Lebar papan "+lebar+" tidak sesuai.");}
                }

                if (row<A){
                    for (char c : l.toCharArray()){
                        if ((c<'A'||c>'Z') || c=='K'){
                            l = l.replace(c, '.');
                        }
                    }
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
                    if (c != '.' && (c >= 'A' || c<='Z') && c!='K') {
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

                int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
                int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
                for (int[] p : locs) {
                    minY = Math.min(minY, p[0]);
                    maxY = Math.max(maxY, p[0]);
                    minX = Math.min(minX, p[1]);
                    maxX = Math.max(maxX, p[1]);
                }

                int height = maxY - minY + 1;
                int width = maxX - minX + 1;

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
            if (pieces.length != N+1){
                throw new IOException("Jumlah piece "+pieces.length+" tidak sesuai dengan file input.");
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file "+fileName);
            e.printStackTrace();
            throw e;
        }

    }

    public static void writeFile(String fileName, Board awal, List<Simpul> output, long timeSpent){
        try{
            fileName = "test\\" + "solution_"+fileName;
            FileWriter save = new FileWriter(fileName);
            save.write("Solusi untuk "+fileName.substring(14)+"\n\n"); // fileName adalah fileName input
            save.write("Dimensi papan: "+awal.getHeight()+" x "+awal.getWidth()+"\n");
            save.write("Jumlah piece selain P: "+awal.getTotalPieces()+"\n");
            save.write("\nWaktu penyelesaian: "+timeSpent+" ns\n");
            save.write("Total gerakan yang dilakukan: "+output.size()+"\n\n");
            // print ke file untuk papan awal
            save.write("Papan Awal\n");
            if (awal.getExitLoc()[0]==-1){
                for (int i=0;i<awal.getWidth();i++){
                    if (awal.getExitLoc()[1]==i){
                        save.write("K");
                    } else {
                        save.write(" ");
                    }
                }
                save.write("\n");
                for (int i = 0; i < awal.getHeight(); i++) {
                    for (int j = 0; j < awal.getWidth(); j++) {
                        save.write(awal.getBoard()[i][j]);
                    }
                    save.write("\n");
                }
            } else if (awal.getExitLoc()[0]==awal.getHeight()){
                for (int i = 0; i < awal.getHeight(); i++) {
                    for (int j = 0; j < awal.getWidth(); j++) {
                        save.write(awal.getBoard()[i][j]);
                    }
                    save.write("\n");
                }
                for (int i=0;i<awal.getWidth();i++){
                    if (awal.getExitLoc()[1]==i){
                        save.write("K");
                    } else {
                        save.write(" ");
                    }
                }
                save.write("\n");
            } else if (awal.getExitLoc()[1] == -1) {
                for (int i = 0; i < awal.getHeight(); i++) {
                    if (i == awal.getExitLoc()[0]) {
                        save.write("K");
                    } else {
                        save.write(" ");
                    }
                    for (int j = 0; j < awal.getWidth(); j++) {
                        save.write(awal.getBoard()[i][j]);
                    }
                    save.write("\n");
                }
            } else if (awal.getExitLoc()[1] == awal.getWidth()) {
                for (int i = 0; i < awal.getHeight(); i++) {
                    for (int j = 0; j < awal.getWidth(); j++) {
                        save.write(awal.getBoard()[i][j]);
                    }
                    if (i == awal.getExitLoc()[0]) {
                        save.write("K");
                    } else {
                        save.write(" ");
                    }
                    save.write("\n");
                }
            }
            
            // print langkah2 ke file
            if (output.isEmpty()) {
                save.write("Tidak ditemukan solusi... :(");
                save.close();
                return;
            }

            if (output.get(0).getIdPiece()=='-'){
                save.write("Papan sudah dalam kondisi menang.");
                save.close();
                return;
            }

            int counter = 1;
            for (Simpul s : output) {
                save.write("\n");
                Piece piece = s.getBoard().getPiece(s.getIdPiece());
                
                String direction = null;
                if (piece.getOrientation() == 'h') {
                    if (s.getMoveValue() > 0) {
                        direction = "kanan";
                    } else if (s.getMoveValue() < 0) {
                        direction = "kiri";
                    }
                } else {
                    if (s.getMoveValue() > 0) {
                        direction = "bawah";
                    } else if (s.getMoveValue() < 0) {
                        direction = "atas";
                    }
                }

                save.write("Gerakan " + counter++ + ": " + s.getIdPiece() + " sebanyak " + Math.abs(s.getMoveValue()) + " petak ke " + direction);
                save.write("\n");
                if (s.getBoard().getExitLoc()[0]==-1){
                    for (int i=0;i<s.getBoard().getWidth();i++){
                        if (s.getBoard().getExitLoc()[1]==i){
                            save.write("K");
                        } else {
                            save.write(" ");
                        }
                    }
                    save.write("\n");
                    for (int i = 0; i < s.getBoard().getHeight(); i++) {
                        for (int j = 0; j < s.getBoard().getWidth(); j++) {
                            save.write(s.getBoard().getBoard()[i][j]);
                        }
                        save.write("\n");
                    }
                } else if (s.getBoard().getExitLoc()[0]==s.getBoard().getHeight()){
                    for (int i = 0; i < s.getBoard().getHeight(); i++) {
                        for (int j = 0; j < s.getBoard().getWidth(); j++) {
                            save.write(s.getBoard().getBoard()[i][j]);
                        }
                        save.write("\n");
                    }
                    for (int i=0;i<s.getBoard().getWidth();i++){
                        if (s.getBoard().getExitLoc()[1]==i){
                            save.write("K");
                        } else {
                            save.write(" ");
                        }
                    }
                    save.write("\n");
                } else if (s.getBoard().getExitLoc()[1] == -1) {
                    for (int i = 0; i < s.getBoard().getHeight(); i++) {
                        if (i == s.getBoard().getExitLoc()[0]) {
                            save.write("K");
                        } else {
                            save.write(" ");
                        }
                        for (int j = 0; j < s.getBoard().getWidth(); j++) {
                            save.write(s.getBoard().getBoard()[i][j]);
                        }
                        save.write("\n");
                    }
                } else if (s.getBoard().getExitLoc()[1] == s.getBoard().getWidth()) {
                    for (int i = 0; i < s.getBoard().getHeight(); i++) {
                        for (int j = 0; j < s.getBoard().getWidth(); j++) {
                            save.write(s.getBoard().getBoard()[i][j]);
                        }
                        if (i == s.getBoard().getExitLoc()[0]) {
                            save.write("K");
                        } else {
                            save.write(" ");
                        }
                        save.write("\n");
                    }
                }
            }
            save.close();
        } catch (IOException e){
            System.out.println("Terjadi kesalahan saat menulis file.");
            e.printStackTrace();
        }
    }

    public static void printOutput(Board awal, List<Simpul> output, long d) {
        System.out.println();
        System.out.println("\nWaktu penyelesaian: "+d+" ns\n");
        System.out.println("Total gerakan yang dilakukan: "+output.size()+"\n\n");
        System.out.println("Papan awal:");
        awal.displayBoard();

        if (output.isEmpty()) {
            System.out.println("Tidak ditemukan solusi... :(");
            return;
        }

        if (output.get(0).getIdPiece() == '-') {
            System.out.println("Papan sudah dalam kondisi menang.");
            return;
        }

        int counter = 1;
        for (Simpul s : output) {
            Board board = s.getBoard();
            Piece piece = s.getBoard().getPiece(s.getIdPiece());
            
            String direction = null;
            if (piece.getOrientation() == 'h') {
                if (s.getMoveValue() > 0) {
                    direction = "kanan";
                } else if (s.getMoveValue() < 0) {
                    direction = "kiri";
                }
            } else {
                if (s.getMoveValue() > 0) {
                    direction = "bawah";
                } else if (s.getMoveValue() < 0) {
                    direction = "atas";
                }
            }

            System.out.println("Gerakan " + counter++ + ": " + s.getIdPiece() + " sebanyak " + Math.abs(s.getMoveValue()) + " petak ke " + direction);
            board.displayBoardForOutput(s.getIdPiece());
        }
    }
    
}
