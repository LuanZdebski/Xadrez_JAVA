package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	protected boolean isThereOponnentPiece(Position pos) 
	{
		ChessPiece piece = (ChessPiece) getBoard().piece(pos);
		return (piece != null && piece.getColor() != color);
	}

	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition()
	{
		return ChessPosition.fromPosition(position);
	}

	
}
