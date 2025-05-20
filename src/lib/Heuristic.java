package lib;

import java.util.HashSet;
import java.util.Set;

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

    // menghitung jumlah piece yang menghalangi
    public static int blockCount(Board b) {
        Piece p = b.getPiece('P');
        int[][] locs = p.getLocation();
        int exitX = b.getExitLoc()[1];
        int exitY = b.getExitLoc()[0];
        char[][] board = b.getBoard();
        int width = b.getWidth();
        int height = b.getHeight();

        Set<Character> blockers = new HashSet<>();

        boolean isHorizontal = p.getHeight() == 1;

        if (isHorizontal) {
            int y = locs[0][1];

            int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
            for (int[] loc : locs) {
                minX = Math.min(minX, loc[0]);
                maxX = Math.max(maxX, loc[0]);
            }

            if (exitX == width) {
                for (int x = maxX + 1; x < width; x++) {
                    char c = board[y][x];
                    if (c != '.' && c != 'P') blockers.add(c);
                }
            } else if (exitX == -1) {
                for (int x = minX - 1; x >= 0; x--) {
                    char c = board[y][x];
                    if (c != '.' && c != 'P') blockers.add(c);
                }
            }
        } else {
            int x = locs[0][0];

            int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
            for (int[] loc : locs) {
                minY = Math.min(minY, loc[1]);
                maxY = Math.max(maxY, loc[1]);
            }

            if (exitY == height) {
                for (int y = maxY + 1; y < height; y++) {
                    char c = board[y][x];
                    if (c != '.' && c != 'P') blockers.add(c);
                }
            } else if (exitY == -1) {
                for (int y = minY - 1; y >= 0; y--) {
                    char c = board[y][x];
                    if (c != '.' && c != 'P') blockers.add(c);
                }
            }
        }

        // System.out.println("Jumlah blocker: " + blockers.size());
        return blockers.size();
    }

    public static int getChoosenHeuristic(Board b, int choice) {
        if (choice == 1) {
            return manhattanDistance(b, 'P');
        } else if (choice == 2) {
            return blockCount(b);
        } else if (choice == 3){
            return manhattanDistance(b, 'P') + blockCount(b);
        } else {
            return 0;
        }
    }
}
