package control;

import game.Table;
import logic.Chessman;
import logic.Duplet;

import java.util.ArrayList;

public class Controller {
    Table game;

    public Controller() {
        game = new Table();
    }

    public boolean moveChessman(int cx, int cy, int nx, int ny) {
        return game.moveChessman(cx, cy, nx, ny);
    }

    public ArrayList<Duplet> getMoves(Chessman chessman) {
        return game.getMoves(chessman);
    }

    public Table getTable() {
        return game;
    }
}
