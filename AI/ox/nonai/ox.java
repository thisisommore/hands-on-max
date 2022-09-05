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
    int oppo;
    Scanner scanner = new Scanner(System.in);

    OX() {
        // Row, Col

        System.out.println("Select X or O");
        String selectedPlayer = scanner.next();

        if (selectedPlayer.charAt(0) == 'X') {
            oppo = X;
        } else if (selectedPlayer.charAt(0) == 'O') {
            oppo = O;
        } else {
            System.out.println("Input is invalid");
            return;
        }
        System.out.println("You are " + getBoardChar(oppo));
        startGame();
    }

    void startGame() {
        if (oppo == X) {
            ifXoppo();
        }else{
            ifOoppo();
        }

    }

    private void ifXoppo() {
        user();
        boolean mark = go(O, 1, 1);
        if (mark) {
            cornerMove(O, 0);
        } else {
            go(X, 1, 1);

        }

        int[] blankCordX = user();
        if (blankCordX[0] != -1) {
            go(O, blankCordX[0], blankCordX[1]);
        } else {
            cornerMove(O, 2);
        }

        blankCordX = user();
        int[] blankCordO = posWin(O);
        if (blankCordO[0] != -1) {
            go(O, blankCordO[0], blankCordO[1]);
        } else if (blankCordX[0] != -1) {
            go(O, blankCordX[0], blankCordX[1]);
        } else if (!cornerMove(O, 3)) {
            makeMove(O);
        }

        user();
        makeMove(O);
        user();
    }

    private void ifOoppo() {
        go(X, 1, 1);
        user();
        cornerMove(X, 0);

        int[] blankCordO = user();
        

        int[] blankCordX = posWin(X);
        if (blankCordX[0] != -1) {
            go(X, blankCordX[0], blankCordX[1]);
        } else if (blankCordO[0] != -1) {
            go(X, blankCordO[0], blankCordO[1]);
        }else {
            cornerMove(X, 3);
        }

        blankCordX = user();

        if(blankCordX[0] != -1){
            go(X, blankCordX[0],blankCordX[1]);
        }else{
            makeMove(X);
        }
        user();
        makeMove(X);
    }




    void makeMove(int player) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == B) {
                    go(player, row, col);
                    return;
                }
            }
        }
    }

    private int[] user() {
        System.out.print("Row: ");
        int row = scanner.nextInt();
        System.out.print("Col: ");
        int col = scanner.nextInt();
        boolean marked = go(oppo, row, col);
        if (marked) {
            System.out.println("already marked !!");
            return user();
        }

        return posWin(oppo);
    }

    boolean cornerMove(int player, int corner) {
        int[][] corners = {
                { 0, 0 },
                { 0, 2 },
                { 2, 0 },
                { 2, 2 },
                { 0, 0 },
                { 0, 2 },
                { 2, 0 },
                { 2, 2 }
        };

        while (true) {
            boolean mark = go(player, corners[corner][0], corners[corner][1]);
            if (!mark) {
                return true;
            }
            corner++;
            if (corner > 7) {
                return false;
            }
        }

    }

    boolean go(int player, int row, int col) {
        if (board[row][col] != B) {
            return true;
        }
        board[row][col] = player;
        printArray();
        if (checkWin(player)) {
            System.out.printf("%c is Winner\n", getBoardChar(player));
            System.exit(0);
        }
        ;

        return false;

    }

    int[] posWin(int player) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == player || board[i][j] == B) {
                    int[] res = _posWin(player, i, j);
                    if (res[0] != -1) {
                        return res;
                    }
                }
            }
        }
        return new int[] { -1, -1 };
    }

    
    boolean checkWin(int player) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == player || board[i][j] == B) {
                    Boolean res = _checkWin(player, i, j);
                    if (res) {
                        return res;
                    }
                }
            }
        }
        return false;
    }




    boolean _checkWin(int player, int row, int col) {
        int winnerProd = player * player * player;
        int prod = 1;
        // Check for Vertical;
        for (int c = 0; c < 3; c++) {
            prod *= board[row][c];
        }
        if (prod == winnerProd) {
            return true;
        }
        prod = 1;
        // Check for Horizontal;
        for (int r = 0; r < 3; r++) {
            prod *= board[r][col];
        }
        if (prod == winnerProd) {
            return true;
        }

        // Check for diagonal
        prod = 1;

        if ((row == 0 || row % 2 == 0) && (col == 0 || col % 2 == 0)) {
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

    int[] _posWin(int player, int row, int col) {
        int winnerProd = player * player * B;
        int prod = 1;

        int blankRow = -1;
        int blankCol = -1;
        // Check for horizontal;
        for (int c = 0; c < 3; c++) {
            prod *= board[row][c];
            if (B == board[row][c]) {
                blankRow = row;
                blankCol = c;
            }
        }
        if (prod == winnerProd) {
            return new int[] { blankRow, blankCol };
        }

        blankRow = -1;
        blankCol = -1;
        prod = 1;
        // Check for vertical;
        for (int r = 0; r < 3; r++) {
            prod *= board[r][col];
            if (B == board[r][col]) {
                blankRow = r;
                blankCol = col;
            }
        }
        if (prod == winnerProd) {
            return new int[] { blankRow, blankCol };
        }

        // Check for diago
        blankRow = -1;
        blankCol = -1;
        prod = 1;

        if ((row == 0 || row % 2 == 0) && (col == 0 || col % 2 == 0)) {
            if (row == 0 && col == 2) {
                int nC = 2;
                for (int nR = 0; nR < 3; nR++) {
                    prod *= board[nR][nC];
                    if (B == board[nR][nC]) {
                        blankRow = nR;
                        blankCol = nC;
                    }
                    nC--;
                }
                if (prod == winnerProd) {
                    return new int[] { blankRow, blankCol };
                }
            }

            prod = 1;
            if (row == 0 && col == 0) {
                for (int cI = 0; cI < 3; cI++) {
                    prod *= board[cI][cI];
                    if (B == board[cI][cI]) {
                        blankRow = cI;
                        blankCol = cI;
                    }
                }
                if (prod == winnerProd) {
                    return new int[] { blankRow, blankCol };
                }
            }
        }

        blankRow = -1;
        blankCol = -1;

        return new int[] { blankRow, blankCol };
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