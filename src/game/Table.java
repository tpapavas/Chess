package game;

import logic.Duplet;
import logic.Chessman;
import logic.ChessmanType;
import logic.ColorType;

import java.util.ArrayList;

public class Table {
    private static final int CHESS_LENGTH = 8;
    Chessman[][] chessmen = new Chessman[CHESS_LENGTH][CHESS_LENGTH];

    public Table() {
        for(int i = 0; i < CHESS_LENGTH; i++) {
            for (int j = 0; j < CHESS_LENGTH; j++) {
                chessmen[i][j] = new Chessman(i, j, ChessmanType.NULL, ColorType.NULL);
            }
        }

        //Black Chessmen
        chessmen[0][0] = new Chessman(0,0, ChessmanType.CASTLE, ColorType.BLACK);
        chessmen[0][7] = new Chessman(0,7, ChessmanType.CASTLE, ColorType.BLACK);

        chessmen[0][1] = new Chessman(0,1, ChessmanType.HORSE, ColorType.BLACK);
        chessmen[0][6] = new Chessman(0,6, ChessmanType.HORSE, ColorType.BLACK);

        chessmen[0][2] = new Chessman(0,2, ChessmanType.OFFICER, ColorType.BLACK);
        chessmen[0][5] = new Chessman(0,5, ChessmanType.OFFICER, ColorType.BLACK);

        chessmen[0][3] = new Chessman(0,3, ChessmanType.QUEEN, ColorType.BLACK);
        chessmen[0][4] = new Chessman(0,4, ChessmanType.KING, ColorType.BLACK);

        for(int i = 0; i < CHESS_LENGTH; i++)
            chessmen[1][i] = new Chessman(1, i, ChessmanType.SOLDIER, ColorType.BLACK);

        //White Chessmen
        chessmen[7][0] = new Chessman(7,0, ChessmanType.CASTLE, ColorType.WHITE);
        chessmen[7][7] = new Chessman(7,7, ChessmanType.CASTLE, ColorType.WHITE);

        chessmen[7][1] = new Chessman(7,1, ChessmanType.HORSE, ColorType.WHITE);
        chessmen[7][6] = new Chessman(7,6, ChessmanType.HORSE, ColorType.WHITE);

        chessmen[7][2] = new Chessman(7,2, ChessmanType.OFFICER, ColorType.WHITE);
        chessmen[7][5] = new Chessman(7,5, ChessmanType.OFFICER, ColorType.WHITE);

        chessmen[7][3] = new Chessman(7,3, ChessmanType.QUEEN, ColorType.WHITE);
        chessmen[7][4] = new Chessman(7,4, ChessmanType.KING, ColorType.WHITE);

        for(int i = 0; i < CHESS_LENGTH; i++)
            chessmen[6][i] = new Chessman(6, i, ChessmanType.SOLDIER, ColorType.WHITE);

    }

    public ArrayList<Duplet> getMoves(Chessman chessman) {
        ArrayList<Duplet> moves = new ArrayList<>();
        Duplet spot;
        int thisX = chessman.getCoords().getX();
        int thisY = chessman.getCoords().getY();

        switch (chessman.getType()) {
            case HORSE:
                for(int i = -1; i < 2; i+=2) {
                    for(int j = -2; j < 3; j+=4) {
                        moves.add(new Duplet(thisX+i, thisY+j));
                        moves.add(new Duplet(thisX+j, thisY+i));
                    }
                }
                break;

            case OFFICER:
                //go up-left
                for(int i = thisX-1, j = thisY-1; i >= 0 && j >= 0; i--,j--) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessmen[i][j].getColorType() != ColorType.NULL)
                        break;
                }

                //go up-right
                for(int i = thisX-1, j = thisY+1; i >= 0 && j < CHESS_LENGTH; i--,j++) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessmen[i][j].getColorType() != ColorType.NULL)
                        break;
                }

                //go down-left
                for(int i = thisX+1, j = thisY-1; i < CHESS_LENGTH && j >= 0; i++,j--) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessmen[i][j].getColorType() != ColorType.NULL)
                        break;
                }

                //go down-right
                for(int i = thisX+1, j = thisY+1; i < CHESS_LENGTH && j < CHESS_LENGTH; i++,j++) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessmen[i][j].getColorType() != ColorType.NULL)
                        break;
                }

                break;

            case KING:
                //go up
                spot = new Duplet(thisX-1, thisY);
                moves.add(spot);
                //go down
                spot = new Duplet(thisX+1, thisY);
                moves.add(spot);
                //go left
                spot = new Duplet(thisX, thisY-1);
                moves.add(spot);
                //go right
                spot = new Duplet(thisX, thisY+1);
                moves.add(spot);

                break;

            case QUEEN:
                //go up-left
                for(int i = thisX-1, j = thisY-1; i >= 0 && j >= 0; i--,j--) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessmen[i][j].getColorType() != ColorType.NULL)
                        break;
                }

                //go up-right
                for(int i = thisX-1, j = thisY+1; i >= 0 && j < CHESS_LENGTH; i--,j++) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessmen[i][j].getColorType() != ColorType.NULL)
                        break;
                }

                //go down-left
                for(int i = thisX+1, j = thisY-1; i < CHESS_LENGTH && j >= 0; i++,j--) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessmen[i][j].getColorType() != ColorType.NULL)
                        break;
                }

                //go down-right
                for(int i = thisX+1, j = thisY+1; i < CHESS_LENGTH && j < CHESS_LENGTH; i++,j++) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessmen[i][j].getColorType() != ColorType.NULL)
                        break;
                }

            case CASTLE:
                //go up
                for(int i = thisX-1; i >= 0; i--) {
                    spot = new Duplet(i,thisY);
                    moves.add(spot);
                    if(chessmen[i][thisY].getColorType() != ColorType.NULL)
                        break;
                }

                //go down
                for(int i = thisX+1; i < CHESS_LENGTH; i++) {
                    spot = new Duplet(i,thisY);
                    moves.add(spot);
                    if(chessmen[i][thisY].getColorType() != ColorType.NULL)
                        break;
                }

                //go left
                for(int j = thisY-1; j >= 0; j--) {
                    spot = new Duplet(thisX,j);
                    moves.add(spot);
                    if(chessmen[thisX][j].getColorType() != ColorType.NULL)
                        break;
                }

                //go right
                for(int j = thisY+1; j < CHESS_LENGTH; j++) {
                    spot = new Duplet(thisX,j);
                    moves.add(spot);
                    if(chessmen[thisX][j].getColorType() != ColorType.NULL)
                        break;
                }
                break;

            case SOLDIER:
                ColorType opponentType = chessman.getColorType() == ColorType.BLACK ? ColorType.WHITE : ColorType.BLACK;
                int shift = chessman.getColorType() == ColorType.BLACK ? +1 : -1;
                int firstX = chessman.getColorType() == ColorType.BLACK ? 1 : 6;

                int x,y;

                //check for opponents
                for(int j = -1; j < 2; j+=2) {
                    x = thisX+shift;
                    y = thisY+j;
                    if(new Duplet(x,y).isInsideLimits() && chessmen[x][y].getColorType() == opponentType)
                        moves.add(new Duplet(x,y));
                }

                //check straight
                if((thisX+shift) >= 0 && chessmen[thisX+shift][thisY].getColorType() == ColorType.NULL)
                    moves.add(new Duplet(thisX+shift,thisY));
                if(chessman.getCoords().getX() == firstX && chessmen[thisX + (shift * 2)][thisY].getColorType() == ColorType.NULL)
                    moves.add(new Duplet(thisX + (shift * 2), thisY));
                break;

            default:
                moves = null;
        }
        return moves;
    }

    public boolean moveChessman(int cx, int cy, int nx, int ny) {
        Chessman from = chessmen[cx][cy];
        Chessman to = chessmen[nx][ny];
        Duplet thisDuplet = new Duplet(to.getCoords().getX(), to.getCoords().getY());
        ArrayList<Duplet> moves = getMoves(from);

        if (moves.contains(thisDuplet)) {
            chessmen[to.getCoords().getX()][to.getCoords().getY()] = from;
            chessmen[from.getCoords().getX()][from.getCoords().getY()] = to;
        }

        return true;
    }

    public Chessman[][] getChessmen() {
        return chessmen;
    }
}
