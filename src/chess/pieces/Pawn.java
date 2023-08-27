package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	private ChessMatch chessMatch; 
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
		
	}
	@Override
	public String toString()
	{
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position actualPos = new Position(0, 0);
		
		if(getColor() == Color.WHITE)
		{
			//movimento padrao
			actualPos.setValues(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos))
			{
				mat[actualPos.getRow()][actualPos.getColumn()] = true;
			}
			//movimento inicial
			actualPos.setValues(position.getRow() - 2, position.getColumn());
			Position pos2 = new Position(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos) && getBoard().positionExists(pos2) && !getBoard().thereIsAPiece(pos2) &&getMoveCount() == 0)
			{
				mat[actualPos.getRow()][actualPos.getColumn()] = true;
			}
			//ataque cima esquerda
			actualPos.setValues(position.getRow() - 1, position.getColumn() - 1);
			if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
			{
				mat[actualPos.getRow()][actualPos.getColumn()] = true;
			}
			//ataque cima direita
			actualPos.setValues(position.getRow() - 1, position.getColumn() + 1);
			if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
			{
				mat[actualPos.getRow()][actualPos.getColumn()] = true;
			}
			//Movimento especial enPassant
			if (position.getRow() == 3 ) 
			{
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOponnentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable())
				{
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOponnentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable())
				{
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
		}
		else //BLACK
		{
			//movimento padrao
			actualPos.setValues(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos))
			{
				mat[actualPos.getRow()][actualPos.getColumn()] = true;
			}
			//movimento inicial
			actualPos.setValues(position.getRow() + 2, position.getColumn());
			Position pos2 = new Position(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos) && getBoard().positionExists(pos2) && !getBoard().thereIsAPiece(pos2) &&getMoveCount() == 0)
			{
				mat[actualPos.getRow()][actualPos.getColumn()] = true;
			}
			//ataque baixo esquerda
			actualPos.setValues(position.getRow() + 1, position.getColumn() - 1);
			if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
			{
				mat[actualPos.getRow()][actualPos.getColumn()] = true;
			}
			//ataque baixo direita
			actualPos.setValues(position.getRow() + 1, position.getColumn() + 1);
			if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
			{
				mat[actualPos.getRow()][actualPos.getColumn()] = true;
			}
			//Movimento especial enPassant
			if (position.getRow() == 4 ) 
			{
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOponnentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable())
				{
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOponnentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable())
				{
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		
		return mat;
	}
	
	

}
