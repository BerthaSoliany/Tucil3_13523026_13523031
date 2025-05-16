package lib;


public class Piece {
    private char id;
    private int[][] location; // atribut lokasi piece. sudah termasuk lebar dan tingginya
    private int width;
    private int height;
    // private boolean primaryPiece; // boolean untuk identifikasi primarypiece

    // constructor
    public Piece(char id, int[][] loc, int w, int h){
        
    }

    // getter setter
    public char getId(){return id;}
    public int[][] getLocation(){return location;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public void setId(char id){this.id=id;}
    public void setLocation(int[][] loc){location=loc;}
    public void setWidth(int w){width=w;}
    public void setHeight(int h){height=h;}


    public boolean isPrimaryPiece(){
        return id == 'P';
    }

}