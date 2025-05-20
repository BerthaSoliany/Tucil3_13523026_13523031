package lib;

import java.util.*;

public class Algorithm {
    
    private static Map<Simpul, Simpul> parent = new HashMap<>();
    private static int visitedCount = 0;

    public static Map<Simpul, Simpul> getParent() {
        return parent;
    }

    public static int getVisitedCount() {
        return visitedCount;
    }

    public static List<Simpul> search(Board board, int algorithm) {
        PriorityQueue<Simpul> queue = new PriorityQueue<>(Comparator.comparingInt(Simpul::getCost));
        Set<Board> visited = new HashSet<>();
        Map<Board, Integer> costMap = new HashMap<>();

        Simpul initialSimpul = new Simpul(board, '-', 0, 0);
        queue.add(initialSimpul);
        visited.add(board);
        costMap.put(board, 0);

        while (!queue.isEmpty()) {
            Simpul currentSimpul = queue.poll();
            Board currentBoard = currentSimpul.getBoard();

            // Check if the exit is reached
            if (currentBoard.isWin()) {

                // Add the exit state to the result
                return reconstructPath(parent, currentSimpul);
            }
            visited.add(currentBoard);

            // Generate next states
            List<Simpul> neighbors = currentBoard.getNeighbors();

            for (Simpul neighbor : neighbors) {
                visitedCount++;
                Board neighborBoard = neighbor.getBoard();
    
                // kalau board sudah pernah diperiksa, maka cost saat ini pasti lebih besar, bisa langsung di skip
                if (!visited.contains(neighborBoard)) {
                    int newCost = costMap.get(currentBoard) + 1;
                    newCost = getHeuristicCost(neighborBoard, algorithm, newCost);

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

    private static int getHeuristicCost(Board board, int algorithm, int UCScost) {
        switch (algorithm) {
            case 1: // UCS
                return UCScost;
            case 2: // greedy
                return Heuristic.manhattanDistance(board, 'P');
            case 3: // A*
                return UCScost + Heuristic.manhattanDistance(board, 'P');
            default:
                return 0;
        }
    }
}
