import java.util.Random;
import java.util.Scanner;

public class ox {
    public static void main(String[] args) {
        new OX();
    }
}

class OX {
    int X = 3, O = 5, B = 2;
    int board[][] = { { B, B, B }, { B, B, B }, { B, B, B } };
    int user;

    OX() {
        // Row, Col
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select X or O");
        String selectedPlayer = scanner.next();
        int player;
        if (selectedPlayer.charAt(0) == 'X') {
            player = X;
        } else if (selectedPlayer.charAt(0) == 'O') {
            player = O;
        } else {
            System.out.println("Input is invalid");
            return;
        }
        System.out.println("You are " + getBoardChar(player));
        for (int i = 0; i < 10; i++) {
            if (player == O) {
                markRandom(X);
                printArray();
                if (isWin(X)) {
                    System.out.println(getBoardChar(X) + " is winner");
                    return;
                }
            }
            System.out.println("Enter Position : ");
            System.out.print("Row : ");
            int row = scanner.nextInt();
            System.out.print("Col : ");
            int col = scanner.nextInt();
            boolean res = mark(player, row - 1, col - 1);
            printArray();
            if (isWin(player)) {
                System.out.println(getBoardChar(player) + " is winner");
                return;
            }
            if (!res) {
                System.out.println("It is already marked");
                i--;
                continue;
            }
            if (isWin(O)) {
                System.out.println("O is winner");
                return;
            }

            if (player == X) {
                markRandom(O);
                printArray();
                if (isWin(O)) {
                    System.out.println(getBoardChar(O) + " is winner");
                    return;
                }
            }
        }
    }

    void markRandom(int player) {
        Random rand = new Random();
        int col = rand.nextInt(3);
        int row = rand.nextInt(3);
        boolean res = mark(player, row, col);
        if (!res) {
            markRandom(player);
        }
    }

    boolean mark(int player, int row, int col) {
        if (board[row][col] != B) {
            return false;
        }

        board[row][col] = player;
        return true;
    }

    boolean isWin(int player) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == player) {
                    boolean res = checkWin(player, i, j);
                    if (res) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean checkWin(int player, int row, int col) {
        int winnerProd = player * player * player;
        int prod = 1;
        // Check for horizontal;
        for (int c = 0; c < 3; c++) {
            prod *= board[row][c];
        }
        if (prod == winnerProd) {
            return true;
        }
        prod = 1;
        // Check for vertical;
        for (int r = 0; r < 3; r++) {
            prod *= board[r][col];
        }
        if (prod == winnerProd) {
            return true;
        }

        // Check for diago
        prod = 1;

        if ((row == 0 || row % 2 == 1) && (col == 0 || col % 2 == 1)) {
            if (row == 0 && col == 2) {
                prod *= board[row][col];
                int nR = 1, nC = 1;
                prod *= board[nR][nC];
                nR++;
                nC--;
                prod *= board[nR][nC];
                if (prod == winnerProd) {
                    return true;
                }
            }

            prod = 1;
            if (row == 0 && col == 0) {
                prod *= board[row][col];
                int nR = 1, nC = 1;
                prod *= board[nR][nC];
                nR++;
                nC++;
                prod *= board[nR][nC];
                if (prod == winnerProd) {
                    return true;
                }
            }
        }

        return false;
    }

    void printArray() {
        String format = "------------" + "\n" +
                " %s | %s | %s" + "\n" +
                "------------" + "\n" +
                " %s | %s | %s" + "\n" +
                "------------" + "\n" +
                " %s | %s | %s" + "\n" +
                "------------" + "\n";
        System.out.printf(format, getBoardChar(board[0][0]), getBoardChar(board[0][1]), getBoardChar(board[0][2]),
                getBoardChar(board[1][0]), getBoardChar(board[1][1]), getBoardChar(board[1][2]),
                getBoardChar(board[2][0]), getBoardChar(board[2][1]), getBoardChar(board[2][2]));
    }

    char getBoardChar(int c) {
        if (c == X) {
            return 'X';
        }
        if (c == O) {
            return 'O';
        }
        return '-';
    }
}