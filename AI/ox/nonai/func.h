
using namespace std;

char getBoardChar(int c);
void startGame();
void ifXoppo();
void ifOoppo();
void makeMove(int player);
Cords user();
bool cornerMove(int player, int corner);
bool go(int player, int row, int col);
Cords posWin(int player);
bool checkWin(int player);
Cords _checkTarget(int player, int row, int col, int targetProd);
void printArray();
char getBoardChar(int c);