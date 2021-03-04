package logic;

public enum ChessmanType {
    CASTLE(1),
    HORSE(2),
    OFFICER(3),
    KING(4),
    QUEEN(5),
    SOLDIER(6),
    NULL(0);

    private final int id;

    ChessmanType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
