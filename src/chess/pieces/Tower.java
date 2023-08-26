package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Tower extends ChessPiece{

	public Tower(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString()
	{
		return "T";
	}
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		return mat;
	}
	
	
}
