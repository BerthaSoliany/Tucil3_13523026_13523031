package lib;

public class Simpul {
    Board board;
    char idPiece;
    int moveValue;
    int cost;

    // constructor
    public Simpul(Board board, char idPiece, int moveValue, int cost) {
        this.board = board;
        this.idPiece = idPiece;
        this.moveValue = moveValue;
        this.cost = cost;
    }

    // getters
    public Board getBoard() {
        return board;
    }
    public char getIdPiece() {
        return idPiece;
    }
    public int getMoveValue() {
        return moveValue;
    }
    public int getCost() {
        return cost;
    }

    // setters
    public void setBoard(Board board) {
        this.board = board;
    }
    public void setIdPiece(char idPiece) {
        this.idPiece = idPiece;
    }
    public void setMoveValue(int moveValue) {
        this.moveValue = moveValue;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
}
