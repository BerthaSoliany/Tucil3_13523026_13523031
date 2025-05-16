package lib;


import java.util.Arrays;

public class Board {
    private int width; // lebar papan
    private int height; // tinggi papan
    private int totalPieces; // total pieces selain primary piece
    private int[] exitLoc; // pintu keluar
    private char[][] board; // untuk memetakan pieces (mainly untuk kebutuhan output dan cek apakah keluar dr board atau enggak)
    private Piece[] pieces; // 
    private int totalStep=0; // untuk menghitung banyak step. ini jadi static oke jg sih

    public Board(){}
    
    public Board(int width, int height, Piece[] p, int[][] e, int[][] pp){
        // constructor untuk board awal
    }

    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getTotalPieces(){return totalPieces;}
    public int[] getExitLoc(){return exitLoc;}
    public char[][] getBoard(){return board;}
    public Piece[] getPieces(){return pieces;}
    public int getTotalStep(){return totalStep;}
    
    public void setWidth(int w){width=w;}
    public void setHeight(int h){height=h;}
    public void setTotalPieces(int p){totalPieces=p;}
    public void setExitLoc(int[] loc){exitLoc=loc;}
    public void setBoard(char[][] b){board=b;}
    public void setPieces(Piece[] p){pieces=p;}
    public void setTotalStep(int s){totalStep=s;}

    public void totalStepIncrement(){totalStep++;}

    public void printBoard(){
        System.out.println(Arrays.deepToString(board));
    }

    public void displayBoard() {
        if (exitLoc[0]==-1){
            for (int i=0;i<width;i++){
                if (exitLoc[1]==i){
                    System.out.print("K");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
        } else if (exitLoc[0]==height){
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
            for (int i=0;i<width;i++){
                if (exitLoc[1]==i){
                    System.out.print("K");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        } else if (exitLoc[1] == -1) {
            for (int i = 0; i < height; i++) {
                if (i == exitLoc[0]) {
                    System.out.print("K");
                } else {
                    System.out.print(" ");
                }
                for (int j = 0; j < width; j++) {
                    System.out.print(board[i][j]);
                }
                System.out.println();
            }
        } else if (exitLoc[1] == width) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(board[i][j]);
                }
                if (i == exitLoc[0]) {
                    System.out.print("K");
                } else {
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }

    public void displayPiece(){
        Piece[] pieces = getPieces();
        if (pieces == null || pieces.length == 0) {
            System.out.println("No pieces to display.");
            return;
        }

        for (Piece p : pieces) {
            System.out.println("Piece ID: " + p.getId());
            System.out.println("Dimensions: " + p.getHeight() + " x " + p.getWidth());
            System.out.println("Locations:");
            for (int[] loc : p.getLocation()) {
                System.out.println("  (" + loc[0] + ", " + loc[1] + ")");
            }
            System.out.println();
        }
    }

    // fungsi2 di bawah ini bisa dipindah ke algo.java. yg lbh nyaman yg mana aja

    // mungkin geraknya satu2 aja yg diambil
    // tp yg ditampilin adalah keseluruhan gerakan untuk satu piece itu
    public void movePiece(Piece piece, int[][] move){
        // piece bergerak sejauh move 
    }

    public boolean isValidMove(char id, int[][] move){
        // boolean untuk mengecek kevalidan gerekan
        // mengecek kosong atau tidak
        // mengecek keluar dari board atau tidak
        return true;
    }

    public boolean isWin(Piece primPiece){
        // cek menang atau enggak di setiap move
        // menang == satu bagian aja dr piece menyentuh K atau bagian depannya (kanan/kiri/atas/bawah) kosong sampe ke K
        return true;
    }

}
