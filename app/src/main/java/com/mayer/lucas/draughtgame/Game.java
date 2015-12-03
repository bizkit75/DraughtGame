package com.mayer.lucas.draughtgame;

import java.util.Collection;

public interface Game {
    class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }
        public int getY() { return y; }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Position))
                return false;

            Position pos = (Position) o;
            return x == pos.x && y == pos.y;
        }
    }

    class Move {
        Position src;
        Position dst;

        public Move(Position src, Position dst) {
            this.src = src;
            this.dst = dst;
        }

        public Position getSrc() { return src; }
        public Position getDst() { return dst; }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Move))
                return false;

            Move move = (Move) o;
            return src.equals(move.src) && dst.equals(move.dst);
        }
    }

    void start(int size);
    Player turn();
    Piece[][] getPlate();
    Collection<Move> allowedMoves(Position p);
    boolean move(Move move);
    State gameState();

    enum State {
        NotStarted,
        OnGoing,
        BlackWon,
        WhiteWon,
        Draw;
    }

    enum Player {
        White,
        Black;

        public Piece pawn() {
            if (this == White) return Piece.WPawn;
            else               return Piece.BPawn;
        }

        public Piece queen() {
            if (this == White) return Piece.WQueen;
            else               return Piece.BQueen;
        }

        public Player opponent() {
            if (this == White) return Black;
            else               return White;
        }
    }

    enum Piece {
        Empty,
        WPawn,
        BPawn,
        WQueen,
        BQueen;

        public Player color() {
            if (this == WPawn || this == WQueen)
                return Player.White;
            else // if (this == BPawn || this == BQueen)
                return Player.Black;
        }
    }
}

