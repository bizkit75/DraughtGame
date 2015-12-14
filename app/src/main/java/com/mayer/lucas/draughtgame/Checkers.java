package com.mayer.lucas.draughtgame;

import java.util.ArrayList;
import java.util.Collection;

public class Checkers implements Game {
    private Piece[][] plate;
    private Player player;
    private State state;

    public Checkers() { state = State.NotStarted; }

    public Piece get(int x, int y) {
        return plate[x][y];
    }

    public Piece get(Position p) {
        return get(p.getX(), p.getY());
    }

    public void set(Position pos, Piece piece) {
        plate[pos.getX()][pos.getY()] = piece;
    }

    private boolean empty(int x, int y) {
        return get(x, y) == Piece.Empty;
    }

    private boolean empty(Position p) {
        return empty(p.getX(), p.getY());
    }

    private boolean onBorder(int x, int y) {
        return x == 0 || x == size() - 1 || y == 0 || y == size() - 1;
    }

    private boolean pieceOf(int x, int y, Player p) {
        return !empty(x, y) && get(x, y).color() == p;
    }

    public int size() { return plate.length; }

    @Override
    public void start(int size) {
        plate = new Piece[size][];
        for (int i = 0; i < size; ++i) {
            plate[i] = new Piece[size];
            for (int j = 0; j < size; ++j)
                if ((i + j) % 2 == 0)
                    if (i < 2)
                        plate[i][j] = Piece.WPawn;
                    else if (i >= size - 2)
                        plate[i][j] = Piece.BPawn;
                    else
                        plate[i][j] = Piece.Empty;
                else
                    plate[i][j] = Piece.Empty;
        }

        player = Player.White;
    }

    @Override
    public Player turn() {
        return player;
    }

    @Override
    public Piece[][] getPlate() {
        return plate;
    }

    private boolean canMove(int x, int y, int forward, int direction) {
        return empty(x + direction, y + forward);
    }

    private boolean canCapture(int x, int y, Player owner, int forward, int direction) {
        int ny = y + forward;
        int nx = x + direction;

        if (onBorder(nx, ny))
            return false;
        if (empty(nx, ny))
            return false;
        if (pieceOf(nx, ny, owner))
            return false;

        ny += forward;
        nx += direction;

        if (!empty(nx, ny))
            return false;

        return true;
    }

    @Override
    public Collection<Move> allowedMoves(Position p) {
        ArrayList<Move> moves = new ArrayList<Move>();

        int forward = player == Player.White ? -1 : 1;
        int backward = -forward;

        boolean capture = false;

        for (int x = 0; x < size(); ++x)
            for (int y = 0; y < size(); ++y) {
                if (pieceOf(x, y, player)) {
                    if (canCapture(x, y, player, forward, -1)) {
                        capture = true;
                        if (p.getX() == x && p.getY() == y)
                            moves.add(new Move(p, new Position(p.getX() - 2, p.getY() + 2 * forward)));
                    }
                    if (canCapture(x, y, player, forward,  1)) {
                        capture = true;
                        if (p.getX() == x && p.getY() == y)
                            moves.add(new Move(p, new Position(p.getX() + 2, p.getY() + 2 * forward)));
                    }
                }
                if (get(x, y) == player.queen()) {
                    if (canCapture(x, y, player, backward, -1)) {
                        capture = true;
                        if (p.getX() == x && p.getY() == y)
                            moves.add(new Move(p, new Position(p.getX() - 2, p.getY() + 2 * backward)));
                    }
                    if (canCapture(x, y, player, backward,  1)) {
                        capture = true;
                        if (p.getX() == x && p.getY() == y)
                            moves.add(new Move(p, new Position(p.getX() + 2, p.getY() + 2 * backward)));
                    }
                }
            }

        if (!capture) {
            int x = p.getX();
            int y = p.getY();

            if (pieceOf(x, y, player)) {
                if (canMove(x, y, forward, -1))
                    moves.add(new Move(p, new Position(p.getX() - 1, p.getY() + forward)));
                if (canMove(x, y, forward,  1))
                    moves.add(new Move(p, new Position(p.getX() + 1, p.getY() + forward)));
            }
            if (get(x, y) == player.queen()) {
                if (canMove(x, y, backward, -1))
                    moves.add(new Move(p, new Position(p.getX() - 1, p.getY() + backward)));
                if (canMove(x, y, backward,  1))
                    moves.add(new Move(p, new Position(p.getX() + 1, p.getY() + backward)));
            }
        }

        return moves;
    }

    @Override
    public boolean move(Move move) {
        if (empty(move.getSrc()))
            return false;
        if (!pieceOf(move.getSrc().getX(), move.getSrc().getY(), player))
            return false;

        Collection<Move> allowed = allowedMoves(move.getSrc());
        if (!allowed.contains(move))
            return false;

        set(move.getDst(), get(move.getSrc()));
        set(move.getSrc(), Piece.Empty);

        if (   (player == Player.White && move.getDst().getY() == size() - 1)
                || (player == Player.Black && move.getDst().getY() == 0))
            set(move.getDst(), player.queen());

        return true;
    }

    @Override
    public State gameState() {
        return state;
    }
}
