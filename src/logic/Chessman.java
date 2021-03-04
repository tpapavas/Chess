package logic;

import auxiliary.Duplet;

import java.util.ArrayList;
import java.util.HashMap;

public class Chessman {
    Duplet coords;
    ChessmanType type;
    ColorType colorType;
    boolean firstMove;

    static HashMap<ChessmanType,ArrayList<Duplet>> allMoves;

    static {
        allMoves = new HashMap<>();

        //Castle moves
        ArrayList<Duplet> castles = new ArrayList<>();
        castles.add(new Duplet(-1,0));
        castles.add(new Duplet(0,-1));
        castles.add(new Duplet(0,1));
        castles.add(new Duplet(1,0));
        allMoves.put(ChessmanType.CASTLE,castles);

        //Horse moves
        ArrayList<Duplet> horses = new ArrayList<>();
        horses.add(new Duplet(-2,-1));
        horses.add(new Duplet(-2,1));
        horses.add(new Duplet(2,-1));
        horses.add(new Duplet(2,1));
        allMoves.put(ChessmanType.HORSE,horses);

        //Officer moves
        ArrayList<Duplet> officers = new ArrayList<>();
        officers.add(new Duplet(-1,-1));
        officers.add(new Duplet(-1,1));
        officers.add(new Duplet(1,-1));
        officers.add(new Duplet(1,1));
        allMoves.put(ChessmanType.OFFICER,officers);

        //King moves
        ArrayList<Duplet> king = new ArrayList<>();
        king.add(new Duplet(-1,0));
        king.add(new Duplet(0,-1));
        king.add(new Duplet(0,1));
        king.add(new Duplet(1,0));
        allMoves.put(ChessmanType.KING,king);

        //Queen moves
        ArrayList<Duplet> queen = new ArrayList<>();
        queen.add(new Duplet(-1,-1));
        queen.add(new Duplet(-1,1));
        queen.add(new Duplet(1,-1));
        queen.add(new Duplet(1,1));
        queen.add(new Duplet(-1,0));
        queen.add(new Duplet(0,-1));
        queen.add(new Duplet(0,1));
        queen.add(new Duplet(1,0));
        allMoves.put(ChessmanType.QUEEN,queen);

        //Soldier moves
        ArrayList<Duplet> soldiers = new ArrayList<>();
        soldiers.add(new Duplet(1,0));
        allMoves.put(ChessmanType.SOLDIER,soldiers);
    }

    public Chessman(int x, int y, ChessmanType type, ColorType colorType) {
        this.coords = new Duplet(x,y);
        this.type = type;
        this.colorType = colorType;
        this.firstMove = true;
    }

    public ArrayList<Duplet> getMoves() {
        ArrayList<Duplet> moves = new ArrayList<>();
        for(Duplet moveCoords : allMoves.get(type)) {
            int factor = colorType == ColorType.BLACK ? 1 : colorType == ColorType.WHITE ? -1 : 0;
            int moveX = coords.getX() + factor*moveCoords.getX();
            int moveY = coords.getY() + factor*moveCoords.getY();
            moves.add(new Duplet(moveX,moveY));
        }
        return moves;
    }

    public ChessmanType getType() { return type; }
    public Duplet getCoords() { return coords; }
    public ColorType getColorType() { return colorType; }
    public boolean getFirstMove() { return firstMove; }

    public void setType(ChessmanType type) { this.type = type; }
    public void setColorType(ColorType colorType) { this.colorType = colorType; }
    public void setFirstMove(boolean b) { this.firstMove = b; }
}
