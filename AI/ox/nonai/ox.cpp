// ox.cpp
#include <iostream>
#include <string>
#include "cords.h"
#include "func.h"

int X = 3;
int O = 5;
int B = 2;
int board[3][3] = {{B, B, B}, {B, B, B}, {B, B, B}};
int player;
int main()
{
    cout << "Select X or O" << endl;
    string selectedPlayer;
    cin >> selectedPlayer;
    if (selectedPlayer[0] == 'X')
    {
        player = X;
    }
    else if (selectedPlayer[0] == 'O')
    {
        player = O;
    }
    else
    {
        cout << "Input is invalid" << endl;
        return 0;
    }
    cout << "You are " << getBoardChar(player) << endl;
    startGame();
    return 0;
}
void startGame()
{
    if (player == X)
    {
        ifXoppo();
    }
    else
    {
        ifOoppo();
    }
}

void ifXoppo()
{
    user();
    bool mark = go(O, 1, 1);
    if (mark)
    {
        cornerMove(O, 0);
    }
    Cords blankCordX = user();
    if (blankCordX.row != -1)
    {
        go(O, blankCordX.row, blankCordX.col);
    }
    else
    {
        cornerMove(O, 2);
    }
    blankCordX = user();
    Cords blankCordO = posWin(O);
    if (blankCordO.row != -1)
    {
        go(O, blankCordO.row, blankCordO.col);
    }
    else if (blankCordX.row != -1)
    {
        go(O, blankCordX.row, blankCordX.col);
    }
    else if (!cornerMove(O, 3))
    {
        makeMove(O);
    }
    user();
    makeMove(O);
    user();
}
void ifOoppo()
{
    go(X, 1, 1);
    user();
    cornerMove(X, 0);
    Cords blankCordO = user();
    Cords blankCordX = posWin(X);
    if (blankCordX.row != -1)
    {
        go(X, blankCordX.row, blankCordX.col);
    }
    else if (blankCordO.row != -1)
    {
        go(X, blankCordO.row, blankCordO.col);
    }
    else
    {
        cornerMove(X, 3);
    }

    blankCordO = user();
    blankCordX = posWin(X);
    if (blankCordX.row != -1)
    {
        go(X, blankCordX.row, blankCordX.col);
    }
    else if (blankCordO.row != -1)
    {
        go(X, blankCordO.row, blankCordO.col);
    }
    else
    {
        makeMove(X);
    }
    user();
    makeMove(X);
}

void makeMove(int player)
{
    for (int row = 0; row < 3; row++)
    {
        for (int col = 0; col < 3; col++)
        {
            if (board[row][col] == B)
            {
                go(player, row, col);
                return;
            }
        }
    }
}

Cords user()
{
    cout << "Row: ";
    int row, col;
    cin >> row;
    cout << "Col: ";
    cin >> col;
    bool alreadyMarked = go(player, --row, --col);
    if (alreadyMarked)
    {
        cout << "already marked !!" << endl;
        return user();
    }
    return posWin(player);
}

bool cornerMove(int player, int corner)
{
    int corners[8][2] = {
        {0, 0},
        {0, 2},
        {2, 0},
        {2, 2},
        {0, 0},
        {0, 2},
        {2, 0},
        {2, 2}};
    while (corner < 7)
    {
        bool alreadyMarked = go(player, corners[corner][0], corners[corner][1]);
        if (!alreadyMarked)
        {
            return true;
        }
        corner++;
    }
    return false;
}
bool go(int player, int row, int col)
{
    if (board[row][col] != B)
    {
        return true;
    }
    board[row][col] = player;
    printArray();
    if (checkWin(player))
    {
        printf("%c is Winner\n", getBoardChar(player));
        exit(0);
    };
    return false;
}
Cords posWin(int player)
{
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            if (board[i][j] == player || board[i][j] == B)
            {
                Cords res = _checkTarget(player, i, j, player * player * B);
                if (res.row != -1)
                {
                    return res;
                }
            }
        }
    }
    return Cords{-1, -1};
}
bool checkWin(int player)
{
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            if (board[i][j] == player || board[i][j] == B)
            {
                Cords res = _checkTarget(player, i, j, player * player * player);
                if (res.result)
                    return res.result;
            }
        }
    }
    return false;
}
Cords _checkTarget(int player, int row, int col, int targetProd)
{
    int prod = 1;
    int blankRow = -1;
    int blankCol = -1;
    // Check for horizontal;
    for (int c = 0; c < 3; c++)
    {
        prod *= board[row][c];
        if (B == board[row][c])
        {
            blankRow = row;
            blankCol = c;
        }
    }
    if (prod == targetProd)
    {
        return Cords{blankRow, blankCol, true};
    }
    blankRow = -1;
    blankCol = -1;
    prod = 1;
    // Check for vertical;
    for (int r = 0; r < 3; r++)
    {
        prod *= board[r][col];
        if (B == board[r][col])
        {
            blankRow = r;
            blankCol = col;
        }
    }
    if (prod == targetProd)
    {
        return Cords{blankRow, blankCol, true};
    }
    // Check for diago
    blankRow = -1;
    blankCol = -1;
    prod = 1;
    bool rowCornerCheck = row == 0 || row % 2 == 0;
    bool colCornerCheck = col == 0 || col % 2 == 0;
    if (rowCornerCheck && colCornerCheck)
    {
        if (row == 0 && col == 2)
        {
            int deCol = 2;
            for (int incCol = 0; incCol < 3; incCol++)
            {
                prod *= board[incCol][deCol];
                if (B == board[incCol][deCol])
                {
                    blankRow = incCol;
                    blankCol = deCol;
                }
                deCol--;
            }
            if (prod == targetProd)
            {
                return Cords{blankRow, blankCol, true};
            }
        }
        prod = 1;
        if (row == 0 && col == 0)
        {
            for (int incRowCol = 0; incRowCol < 3; incRowCol++)
            {
                prod *= board[incRowCol][incRowCol];
                if (B == board[incRowCol][incRowCol])
                {
                    blankRow = incRowCol;
                    blankCol = incRowCol;
                }
            }
            if (prod == targetProd)
            {
                return Cords{blankRow, blankCol, true};
            }
        }
    }
    return Cords{-1, -1, false};
}
void printArray()
{
    printf("------------\n"
           " %c | %c | %c\n"
           "------------\n"
           " %c | %c | %c\n"
           "------------\n"
           " %c | %c | %c\n"
           "------------\n",
           getBoardChar(board[0][0]), getBoardChar(board[0][1]), getBoardChar(board[0][2]),
           getBoardChar(board[1][0]), getBoardChar(board[1][1]), getBoardChar(board[1][2]),
           getBoardChar(board[2][0]), getBoardChar(board[2][1]), getBoardChar(board[2][2]));
}
char getBoardChar(int c)
{
    if (c == X)
    {
        return 'X';
    }
    if (c == O)
    {
        return 'O';
    }
    return '-';
}
