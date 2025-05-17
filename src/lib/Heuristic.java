package lib;

public class Heuristic {
    // menghitung jarak blok ke pintu keluar
    public static int manhattanDistance(Board b, char id){
        Piece pieceP = b.getPiece(id);
        int[][] locs = pieceP.getLocation();
        int minDistance=Integer.MAX_VALUE;

        int exitX = b.getExitLoc()[1];
        int exitY = b.getExitLoc()[0];
        if (exitY == -1) {
            exitY = 0;
        } else if (exitY == b.getHeight()) {
            exitY = b.getHeight() - 1;
        }

        if (exitX == -1) {
            exitX = 0;
        } else if (exitX == b.getWidth()) {
            exitX = b.getWidth() - 1;
        }

        for(int[] loc : locs){
            int x = loc[1];
            int y = loc[0];
            int distance = Math.abs(x - exitX) + Math.abs(y - exitY);
            if (distance<minDistance){
                minDistance=distance;
            }
        }
        return minDistance;
    }
}
