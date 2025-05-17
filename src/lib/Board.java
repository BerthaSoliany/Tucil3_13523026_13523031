package lib;


import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Board {
    private int width; // lebar papan
    private int height; // tinggi papan
    private int totalPieces; // total pieces selain primary piece
    private int[] exitLoc; // pintu keluar
    private char[][] board; // untuk memetakan pieces (mainly untuk kebutuhan output dan cek apakah keluar dr board atau enggak)
    private Piece[] pieces; // 
    private int totalStep=0; // untuk menghitung banyak step. ini jadi static oke jg sih

    public Board(){}

    // copy constructor
    public Board(Board b){
        this.width = b.width;
        this.height = b.height;
        this.totalPieces = b.totalPieces;
        this.exitLoc = b.exitLoc.clone();
        this.board = new char[height][width];
        for (int i = 0; i < height; i++) {
            this.board[i] = b.board[i].clone();
        }
        this.pieces = new Piece[b.pieces.length];
        for (int i = 0; i < b.pieces.length; i++) {
            this.pieces[i] = new Piece(b.pieces[i]);
        }
    }

    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getTotalPieces(){return totalPieces;}
    public int[] getExitLoc(){return exitLoc;}
    public char[][] getBoard(){return board;}
    public Piece[] getPieces(){return pieces;}
    public int getTotalStep(){return totalStep;}
    public Piece getPiece(char id){
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getId() == id) {
                return pieces[i];
            }
        }
        return null;
    }
    
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
        System.out.println();
    }

    public void displayBoardForOutput(char id) {
        String reset = "\u001B[0m";
        String blueBg = "\u001B[44m";

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
                    if (board[i][j] == id) {
                        System.out.print(blueBg + board[i][j] + reset);
                    } else {
                        System.out.print(board[i][j]);
                    }
                }
                System.out.println();
            }
        } else if (exitLoc[0]==height){
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (board[i][j] == id) {
                        System.out.print(blueBg + board[i][j] + reset);
                    } else {
                        System.out.print(board[i][j]);
                    }                
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
                    if (board[i][j] == id) {
                        System.out.print(blueBg + board[i][j] + reset);
                    } else {
                        System.out.print(board[i][j]);
                    }   
                }
                System.out.println();
            }
        } else if (exitLoc[1] == width) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (board[i][j] == id) {
                        System.out.print(blueBg + board[i][j] + reset);
                    } else {
                        System.out.print(board[i][j]);
                    }   
                }
                if (i == exitLoc[0]) {
                    System.out.print("K");
                } else {
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public void displayPiece(){
        Piece[] pieces = getPieces();
        if (pieces == null || pieces.length == 0) {
            System.out.println("Board tidak mempunyai piece.");
            return;
        }

        for (Piece p : pieces) {
            System.out.println("ID piece: " + p.getId());
            System.out.println("Dimensi piece: " + p.getHeight() + " x " + p.getWidth());
            System.out.println("Lokasi:");
            for (int[] loc : p.getLocation()) {
                System.out.println("  (" + loc[0] + ", " + loc[1] + ")");
            }
            System.out.println();
        }
    }

    public boolean isWin(){
        if (exitLoc[0]==-1){
            if (board[0][exitLoc[1]] == 'P'){
                return true;
            }
        } else if (exitLoc[0]==height){
            if (board[height-1][exitLoc[1]] == 'P'){
                return true;
            }
        } else if (exitLoc[1] == -1) {
            if (board[exitLoc[0]][0] == 'P'){
                return true;
            }
        } else if (exitLoc[1] == width) {
            if (board[exitLoc[0]][width-1] == 'P'){
                return true;
            }
        }
        return false;
    }

    public Piece removePiece(char id){
        // remove piece dari board
        Piece removedPiece = getPiece(id);
        for (int i = 0; i < removedPiece.getLocation().length; i++) {
            int x = removedPiece.getLocation()[i][0];
            int y = removedPiece.getLocation()[i][1];
            board[x][y] = '.';
        }

        return removedPiece;
    }

    public void addPiece(Piece piece){
        // add piece ke board
        char id = piece.getId();
        for (int i = 0; i < piece.getLocation().length; i++) {
            int x = piece.getLocation()[i][0];
            int y = piece.getLocation()[i][1];
            board[x][y] = id;
        }

        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getId() == piece.getId()) {
                pieces[i] = piece;
                break;
            }
        }
    }

    public List<Integer> checkPossibleMove(char id){
        // cek piece bisa bergerak kemana
        Piece piece = getPiece(id);
        int i = piece.getLocation()[0][0];
        int j = piece.getLocation()[0][1];
        int length = piece.getLength();
        char orientation = piece.getOrientation();
        List<Integer> possibleMoves = new ArrayList<>();
        int move;

        if (orientation == 'h') {
            // check left
            move = -1;
            while (j + move >= 0 && board[i][j + move] == '.') {
                possibleMoves.add(move);
                move--;
            }
            // check right
            move = 1;
            while (j + length - 1 + move < width && board[i][j + length - 1 + move] == '.') {
                possibleMoves.add(move);
                move++;
            }
        } else {
            // check up
            move = -1;
            while (i + move >= 0 && board[i + move][j] == '.') {
                possibleMoves.add(move);
                move--;
            }
            // check down
            move = 1;
            while (i + length - 1 + move < height && board[i + length - 1 + move][j] == '.') {
                possibleMoves.add(move);
                move++;
            }
        }
        
        return possibleMoves;
    }

    public List<Simpul> getNeighbors(){
        List<Simpul> neighbors = new ArrayList<>();
        for (Piece piece : pieces) {
            List<Integer> possibleMoves = checkPossibleMove(piece.getId());
            List<Piece> possiblePieces = piece.possiblePieceMoves(possibleMoves);
            for (Piece newPiece : possiblePieces) {
                Board newBoard = new Board(this);

                newBoard.removePiece(piece.getId());
                newBoard.addPiece(newPiece);

                // // debugging
                // try {
                //     System.out.println(piece.getId() + " awalnya di " + piece.getLocation()[0][0] + ", " + piece.getLocation()[0][1]);
                //     System.out.println(newPiece.getId() + " sekarang di " + newPiece.getLocation()[0][0] + ", " + newPiece.getLocation()[0][1]);
                //     newBoard.displayBoard();
                //     Thread.sleep(1000); // Jeda 1000 milidetik (1 detik)
                // } catch (Exception e) {
                //     Thread.currentThread().interrupt();
                //     System.err.println("Thread diinterupsi: " + e.getMessage());                
                // }

                int moveValue = 0;
                if (newPiece.getOrientation() == 'h') {
                    moveValue = newPiece.getLocation()[0][1] - piece.getLocation()[0][1];
                } else {
                    moveValue = newPiece.getLocation()[0][0] - piece.getLocation()[0][0];
                }

                neighbors.add(new Simpul(newBoard, newPiece.getId(), moveValue, -1));
            }
        }
        return neighbors;
    }
}
