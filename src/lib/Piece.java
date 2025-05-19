package lib;

import java.util.Objects;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Piece {
    private char id;
    private int[][] location; // atribut lokasi piece.
    private int width;
    private int height;

    // constructor
    public Piece(char id, int[][] loc, int w, int h){
        this.id = id;
        this.location = loc;
        this.width = w;
        this.height = h;
    }

    // copy constructor
    public Piece(Piece piece){
        this.id = piece.id;
        this.location = new int[piece.location.length][piece.location[0].length];
        for (int i = 0; i < piece.location.length; i++) {
            this.location[i] = piece.location[i].clone();
        }
        this.width = piece.width;
        this.height = piece.height;
    }

    // getter setter
    public char getId(){return id;}
    public int[][] getLocation(){return location;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getLength(){return width*height;}

    public void setId(char id){this.id=id;}
    public void setLocation(int[][] loc){location=loc;}
    public void setWidth(int w){width=w;}
    public void setHeight(int h){height=h;}

    public void printPiece(){
        System.out.println("Piece ID: "+id);
        System.out.println("Piece Location: ");
        for (int i = 0; i < location.length; i++) {
            System.out.print("("+location[i][0]+", "+location[i][1]+") ");
        }
        System.out.println();
        System.out.println("Piece Width: "+width);
        System.out.println("Piece Height: "+height);
        System.out.println("Piece Orientation: "+ getOrientation());
    }

    public boolean isPrimaryPiece(){
        return id == 'P';
    }

    public char getOrientation(){
        if (width == 1) {
            return 'v';
        }
        return 'h';
    }

    public void movePiece(int change) {
        if (getOrientation() == 'h') {
            for (int i = 0; i < width; i++) {
                location[i][1] += change;
            }
        } else {
            for (int i = 0; i < height; i++) {
                location[i][0] += change;
            }
        }
    }

    public List<Piece> possiblePieceMoves(List<Integer> possibleMoves) {
        List<Piece> possiblePieces = new ArrayList<>();
        for (int move : possibleMoves) {
            Piece newPiece = new Piece(this);
            newPiece.movePiece(move);
            possiblePieces.add(newPiece);
        }
        return possiblePieces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece other = (Piece) o;
        return id == other.id &&
               width == other.width &&
               height == other.height &&
               Arrays.deepEquals(location, other.location);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, width, height);
        result = 31 * result + Arrays.deepHashCode(location);
        return result;
    }
}