package lib;

import java.util.*;

public class Algorithm {

    public static List<Simpul> search(Board board, int algorithm) {
        PriorityQueue<Simpul> queue = new PriorityQueue<>(Comparator.comparingInt(Simpul::getCost));
        Set<Board> visited = new HashSet<>();
        Map<Board, Integer> costMap = new HashMap<>();
        Map<Simpul, Simpul> parent = new HashMap<>();

        if (board.isWin()) {
            List<Simpul> result = new ArrayList<>();
            result.add(new Simpul(board, '-', 0, -1));
            return result; // Already solved
        }

        int cost = 0; // dummy
        List<Simpul> firstNeighbors = board.getNeighbors();
        for (Simpul neighbor : firstNeighbors) {
            Board neighborBoard = neighbor.getBoard();
            if (!visited.contains(neighborBoard)) {
                neighbor.setCost(cost++); // dummy

                queue.add(neighbor);
                visited.add(neighborBoard);
                costMap.put(neighborBoard, cost);
                // hanya menunjukkan dari langkah pertama (ga nunjukin lagi kondisi awal)
                parent.put(neighbor, null);
            }
        }

        while (!queue.isEmpty()) {
            Simpul currentSimpul = queue.poll();
            Board currentBoard = currentSimpul.getBoard();

            // debugging
            // System.out.println("Initial Board:");
            // currentBoard.displayBoard();
            // currentBoard.displayPiece();

            // Check if the exit is reached
            if (currentBoard.isWin()) {
                // Add the exit state to the result
                return reconstructPath(parent, currentSimpul);
            }

            visited.add(currentBoard);

            // Generate next states
            List<Simpul> neighbors = currentBoard.getNeighbors();
            for (Simpul neighbor : neighbors) {
                Board neighborBoard = neighbor.getBoard();

                // kalau board sudah pernah diperiksa, maka cost saat ini pasti lebih besar, bisa langsung di skip
                if (!visited.contains(neighborBoard)) {
                    int newCost = costMap.get(currentBoard) + 1; // dummy

                    // !costMap.containsKey(neighborBoard) hanya guard untuk memastikan costMap.get(neighborBoard) tidak null
                    if (!costMap.containsKey(neighborBoard) || newCost < costMap.get(neighborBoard)) {
                        neighbor.setCost(newCost);
                        
                        queue.add(neighbor);
                        visited.add(neighborBoard);
                        costMap.put(neighborBoard, newCost);
                        parent.put(neighbor, currentSimpul);
                    }
                }
            }
        }

        // tidak ditemukan solusi
        return Collections.emptyList();
    }

    private static List<Simpul> reconstructPath(Map<Simpul, Simpul> parent, Simpul exit) {
        LinkedList<Simpul> path = new LinkedList<Simpul>();
        while (exit != null) {
            path.addFirst(exit);
            exit = parent.get(exit);
        }
        return path;
    }
}
