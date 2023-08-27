package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	public Pawn(Board board, Color color) {
		super(board, color);
		
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
		}
		else 
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
		}
		
		return mat;
	}
	
	

}
