package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	protected boolean isThereOponnentPiece(Position pos) 
	{
		ChessPiece piece = (ChessPiece) getBoard().piece(pos);
		return (piece != null && piece.getColor() != color);
	}
	public void increaseMoveCount()
	{
		moveCount++;
	}
	public void decreaseMoveCount()
	{
		moveCount--;
	}

	public int getMoveCount()
	{
		return moveCount;
	}
	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition()
	{
		return ChessPosition.fromPosition(position);
	}

	
}
