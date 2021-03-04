package gui;

import auxiliary.Duplet;
import logic.ColorType;
import logic.Chessman;
import logic.ChessmanType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * The graphics user interface of the chess game.
 */
public class GUI {
    private static final int CHESS_LENGTH = 8;
    private static final Color COLOR_DEFAULT;
    static {
        JButton b = new JButton();
        COLOR_DEFAULT = b.getBackground();
    }

    boolean keepPlaying;  //defines whether the game is still going.
    boolean pcTurn;  //defines whether its pc's turn.
    boolean aButtonIsOn;  //tells if a button is "open".

    ChessFrame frame;  //the main frame of the game.
    ChessButton[][] chessCell;
    ChessButton parentCell;

    /**
     * This is a button that represents a cell (box) of the chess panel.
     */
    public static class ChessButton extends JButton {
        Chessman chessman;
        boolean isClicked;

        private ChessButton(Chessman chessman) {
            super();
            this.chessman = chessman;
            isClicked = false;
            setBackground(COLOR_DEFAULT);
        }

        private Chessman getChessman() { return chessman; }

        private void setChessman(Chessman chessman) { this.chessman = chessman; }
    }

    /**
     * This is the chess 8x8 chess panel.
     */
    private class ChessFrame extends JFrame {
        JLabel label;
        JPanel table;

        /**
         * A mouse listener for the chess cells.
         */
        private class ChessMouseListener implements MouseListener{
            private ChessMouseListener() {
                super();
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override

            public void mouseClicked (MouseEvent e) {
                ChessButton thisCell = (ChessButton) e.getSource();
                ArrayList<Duplet> moves = _getMoves(thisCell.getChessman());

                if (thisCell.isClicked) {  //It is going to release a button.
                    thisCell.setBackground(COLOR_DEFAULT);
                    thisCell.isClicked = false;
                    aButtonIsOn = false;

                    //sets color of possible moves to default.
//                    for (Duplet move : thisCell.chessman.getMoves()) {
//                        if (move.isInsideLimits()) {
//                            chessCell[move.getX()][move.getY()].setBackground(COLOR_DEFAULT);
//                        }
//                    }

                    ////THIS IS A TEST////
                    for (Duplet move : moves) {
                        if (move.isInsideLimits()) {
                            chessCell[move.getX()][move.getY()].setBackground(COLOR_DEFAULT);
                        }
                    }
                } else if (!aButtonIsOn) {  //It is going to activate a button.
                    if(thisCell.getChessman().getColorType() == ColorType.WHITE) {  //this cell is a chessman
                        thisCell.setBackground(Color.GREEN);
                        thisCell.isClicked = true;
                        aButtonIsOn = true;
                        parentCell = thisCell;

                        colorizeAdjacent(moves);
                    }
                } else {  //A player tries to move a piece to thisButton.
                    if(moveChessman(thisCell))
                        pcTurn = true;
                }
            }
        }

        private ChessFrame() {
            setTitle("Chess");
            label = new JLabel("My Chess Game");
            table = new JPanel(new GridLayout(CHESS_LENGTH,CHESS_LENGTH));
            aButtonIsOn = false;
            parentCell = null;
            ChessMouseListener listener = new ChessMouseListener();

            chessCell = new ChessButton[CHESS_LENGTH][CHESS_LENGTH];
            setupCells();
            for(int i = 0; i < CHESS_LENGTH; i++) {
                for (int j = 0; j < CHESS_LENGTH; j++) {
                    chessCell[i][j].addMouseListener(listener);
                    table.add(chessCell[i][j]);
                }
            }

            loadIcons();
            setLayout(new BorderLayout());
            add(label,BorderLayout.NORTH);
            add(table);
            //setup frame
            setSize(500,500);
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        private void loadIcons() {
            Icon blackKing = new ImageIcon("icons\\black\\king.jpg");
            Icon blackQueen = new ImageIcon("icons\\black\\queen.jpg");
            Icon blackCastle = new ImageIcon("icons\\black\\castle.jpg");
            Icon blackHorse = new ImageIcon("icons\\black\\horse.jpg");
            Icon blackOfficer = new ImageIcon("icons\\black\\officer.jpg");
            Icon blackSoldier = new ImageIcon("icons\\black\\soldier.jpg");

            chessCell[0][0].setIcon(blackCastle);
            chessCell[0][7].setIcon(blackCastle);

            chessCell[0][1].setIcon(blackHorse);
            chessCell[0][6].setIcon(blackHorse);

            chessCell[0][2].setIcon(blackOfficer);
            chessCell[0][5].setIcon(blackOfficer);

            chessCell[0][3].setIcon(blackQueen);
            chessCell[0][4].setIcon(blackKing);

            for(int i = 0; i < CHESS_LENGTH; i++)
                chessCell[1][i].setIcon(blackSoldier);

            Icon whiteKing = new ImageIcon("icons\\white\\king.jpg");
            Icon whiteQueen = new ImageIcon("icons\\white\\queen.jpg");
            Icon whiteCastle = new ImageIcon("icons\\white\\castle.jpg");
            Icon whiteHorse = new ImageIcon("icons\\white\\horse.jpg");
            Icon whiteOfficer = new ImageIcon("icons\\white\\officer.jpg");
            Icon whiteSoldier = new ImageIcon("icons\\white\\soldier.jpg");

            chessCell[7][0].setIcon(whiteCastle);
            chessCell[7][7].setIcon(whiteCastle);

            chessCell[7][1].setIcon(whiteHorse);
            chessCell[7][6].setIcon(whiteHorse);

            chessCell[7][2].setIcon(whiteOfficer);
            chessCell[7][5].setIcon(whiteOfficer);

            chessCell[7][3].setIcon(whiteQueen);
            chessCell[7][4].setIcon(whiteKing);

            for(int i = 0; i < CHESS_LENGTH; i++)
                chessCell[6][i].setIcon(whiteSoldier);
        }

        private void setupCells() {
            for(int i = 0; i < CHESS_LENGTH; i++) {
                for (int j = 0; j < CHESS_LENGTH; j++) {
                    chessCell[i][j] = new ChessButton(new Chessman(i, j, ChessmanType.NULL, ColorType.NULL));
                }
            }
//
//            for(int i = CHESS_LENGTH-2; i < CHESS_LENGTH; i++)
//                for(int j = 0; j < CHESS_LENGTH; j++)
//                    chessCell[i][j].setPlayer(true);

            //Black Chessmen
            chessCell[0][0].setChessman(new Chessman(0,0,ChessmanType.CASTLE, ColorType.BLACK));
            chessCell[0][7].setChessman(new Chessman(0,7,ChessmanType.CASTLE, ColorType.BLACK));

            chessCell[0][1].setChessman(new Chessman(0,1,ChessmanType.HORSE, ColorType.BLACK));
            chessCell[0][6].setChessman(new Chessman(0,6,ChessmanType.HORSE, ColorType.BLACK));

            chessCell[0][2].setChessman(new Chessman(0,2,ChessmanType.OFFICER, ColorType.BLACK));
            chessCell[0][5].setChessman(new Chessman(0,5,ChessmanType.OFFICER, ColorType.BLACK));

            chessCell[0][3].setChessman(new Chessman(0,3,ChessmanType.QUEEN, ColorType.BLACK));
            chessCell[0][4].setChessman(new Chessman(0,4,ChessmanType.KING, ColorType.BLACK));

            for(int i = 0; i < CHESS_LENGTH; i++)
                chessCell[1][i].setChessman(new Chessman(1,i,ChessmanType.SOLDIER, ColorType.BLACK));

            //White Chessmen
            chessCell[7][0].setChessman(new Chessman(7,0,ChessmanType.CASTLE, ColorType.WHITE));
            chessCell[7][7].setChessman(new Chessman(7,7,ChessmanType.CASTLE, ColorType.WHITE));

            chessCell[7][1].setChessman(new Chessman(7,1,ChessmanType.HORSE, ColorType.WHITE));
            chessCell[7][6].setChessman(new Chessman(7,6,ChessmanType.HORSE, ColorType.WHITE));

            chessCell[7][2].setChessman(new Chessman(7,2,ChessmanType.OFFICER, ColorType.WHITE));
            chessCell[7][5].setChessman(new Chessman(7,5,ChessmanType.OFFICER, ColorType.WHITE));

            chessCell[7][3].setChessman(new Chessman(7,3,ChessmanType.QUEEN, ColorType.WHITE));
            chessCell[7][4].setChessman(new Chessman(7,4,ChessmanType.KING, ColorType.WHITE));

            for(int i = 0; i < CHESS_LENGTH; i++)
                chessCell[6][i].setChessman(new Chessman(6,i,ChessmanType.SOLDIER, ColorType.WHITE));

        }
    }

    public GUI() {
        frame = new ChessFrame();
        frame.setVisible(true);

        aButtonIsOn = false;
        keepPlaying = true;
        pcTurn = false;

        Timer myTimer = new Timer(100,null);
        myTimer.setRepeats(false);

        Timer pcTimer = new Timer(100,null);
        myTimer.setRepeats(false);

        //this is game loop
        while(keepPlaying) {
            //this is player's move
            do {
                myTimer.start();
            } while(!pcTurn);

            //this is PC's move
            pcTimer.start();
            movePC();
            pcTurn = false;
        }
    }

    /**
     * PC's logic for moving one of its chessman.
     * Random for now.
     */
    private void movePC() {
      //  System.out.println("YOHOHO");
        for(int i = 0; i < CHESS_LENGTH; i++) {
            for(int j = 0; j < CHESS_LENGTH; j++) {
                if(chessCell[i][j].getChessman().getColorType() == ColorType.BLACK) {
                    parentCell = chessCell[i][j];
//                    for(Duplet d : parentCell.getChessman().getMoves() ) {
//                        if(d.isInsideLimits() && moveChessman(chessCell[d.getX()][d.getY()])) {
//                            return;
//                        }
//                    }
                    ////THIS IS A TEST
                    for(Duplet d : _getMoves(parentCell.getChessman()) ) {
                        if(d.isInsideLimits() && moveChessman(chessCell[d.getX()][d.getY()])) {
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean moveChessman(ChessButton thisCell) {
        Duplet thisDuplet = new Duplet(thisCell.getChessman().getCoords().getX(),thisCell.getChessman().getCoords().getY());
        ArrayList<Duplet> moves = _getMoves(parentCell.getChessman());

        //IT HAS ISSUES WITH THE SOLDIERS//
//        if (parentCell.getChessman().getMoves().contains(thisDuplet) && thisCell.getChessman().getColorType() != parentCell.getChessman().getColorType()) {  //the activated piece can be moved to this cell.

        ////THIS IS A TEST////
        if (moves.contains(thisDuplet) && thisCell.getChessman().getColorType() != parentCell.getChessman().getColorType()) {

            //"Moves" the piece to the new position.
            thisCell.setIcon(parentCell.getIcon());
            thisCell.setChessman(new Chessman(thisDuplet.getX(),thisDuplet.getY(),parentCell.getChessman().getType(),parentCell.getChessman().getColorType()));

            parentCell.setIcon(null);

            //clear the place
   //         Timer timer = new Timer(120, e1 -> {
//                for (Duplet move : parentCell.getChessman().getMoves()) {
//                    if (move.isInsideLimits()) {
//                       // System.out.println(move.getX() + " " + move.getY());
//                        chessCell[move.getX()][move.getY()].setBackground(COLOR_DEFAULT);
//                    }
//                }

            for (Duplet move : moves) {
                if (move.isInsideLimits()) {
                    // System.out.println(move.getX() + " " + move.getY());
                    chessCell[move.getX()][move.getY()].setBackground(COLOR_DEFAULT);
                }
            }


                parentCell.setBackground(COLOR_DEFAULT);
                parentCell.getChessman().setType(ChessmanType.NULL);
                parentCell.getChessman().setColorType(ColorType.NULL);
                parentCell.isClicked = false;
                aButtonIsOn = false;
     //       });

       //     timer.setRepeats(false);
 //           timer.start();
            return true;
        }

        return false;
    }

    /**
     *Finds thisCell's possible moves and sets their color to YELLOW.
     * @param moves the possible moves
     */
    private void colorizeAdjacent(ArrayList<Duplet> moves) {
//        for (Duplet move : thisCell.getChessman().getMoves()) {

        ////THIS IS A TEST////
        for (Duplet move : moves) {
            ChessButton moveCell;
            if (move.isInsideLimits()) {
                moveCell = chessCell[move.getX()][move.getY()];
                if (moveCell.getChessman().getColorType() != ColorType.WHITE) {
                    chessCell[move.getX()][move.getY()].setBackground(Color.YELLOW);
                } else {
                    //System.out.println(thisCell.getChessman().getMoves().remove(move) + " " + move.getX() + " " + move.getY());
                }
            }
        }
    }

    private ArrayList<Duplet> _getMoves(Chessman chessman) {
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
                    if(chessCell[i][j].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

                //go up-right
                for(int i = thisX-1, j = thisY+1; i >= 0 && j < CHESS_LENGTH; i--,j++) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessCell[i][j].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

                //go down-left
                for(int i = thisX+1, j = thisY-1; i < CHESS_LENGTH && j >= 0; i++,j--) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessCell[i][j].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

                //go down-right
                for(int i = thisX+1, j = thisY+1; i < CHESS_LENGTH && j < CHESS_LENGTH; i++,j++) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessCell[i][j].getChessman().getColorType() != ColorType.NULL)
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
                    if(chessCell[i][j].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

                //go up-right
                for(int i = thisX-1, j = thisY+1; i >= 0 && j < CHESS_LENGTH; i--,j++) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessCell[i][j].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

                //go down-left
                for(int i = thisX+1, j = thisY-1; i < CHESS_LENGTH && j >= 0; i++,j--) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessCell[i][j].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

                //go down-right
                for(int i = thisX+1, j = thisY+1; i < CHESS_LENGTH && j < CHESS_LENGTH; i++,j++) {
                    spot = new Duplet(i,j);
                    moves.add(spot);
                    if(chessCell[i][j].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

            case CASTLE:
                //go up
                for(int i = thisX-1; i >= 0; i--) {
                    spot = new Duplet(i,thisY);
                    moves.add(spot);
                    if(chessCell[i][thisY].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

                //go down
                for(int i = thisX+1; i < CHESS_LENGTH; i++) {
                    spot = new Duplet(i,thisY);
                    moves.add(spot);
                    if(chessCell[i][thisY].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

                //go left
                for(int j = thisY-1; j >= 0; j--) {
                    spot = new Duplet(thisX,j);
                    moves.add(spot);
                    if(chessCell[thisX][j].getChessman().getColorType() != ColorType.NULL)
                        break;
                }

                //go right
                for(int j = thisY+1; j < CHESS_LENGTH; j++) {
                    spot = new Duplet(thisX,j);
                    moves.add(spot);
                    if(chessCell[thisX][j].getChessman().getColorType() != ColorType.NULL)
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
                    if(new Duplet(x,y).isInsideLimits() && chessCell[x][y].getChessman().getColorType() == opponentType)
                        moves.add(new Duplet(x,y));
                }

                //check straight
                if((thisX+shift) >= 0 && chessCell[thisX+shift][thisY].getChessman().getColorType() == ColorType.NULL)
                    moves.add(new Duplet(thisX+shift,thisY));
                if(chessman.getCoords().getX() == firstX && chessCell[thisX + (shift * 2)][thisY].getChessman().getColorType() == ColorType.NULL)
                    moves.add(new Duplet(thisX + (shift * 2), thisY));
                break;

            default:
                moves = null;
        }
        return moves;
    }

}
