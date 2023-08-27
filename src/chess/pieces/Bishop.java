package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{

	public Bishop(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString()
	{
		return "B";
	}
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position actualPos = new Position(0, 0);
		
		//Cima-esquerda
		actualPos.setValues(position.getRow() - 1, position.getColumn() - 1);
		while(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
			actualPos.setValues(actualPos.getRow() - 1, actualPos.getColumn() - 1);
		}
		if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
		}
		
		//Cima-direita
		actualPos.setValues(position.getRow() - 1, position.getColumn() + 1);
		while (getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos)) {
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
			actualPos.setValues(actualPos.getRow() - 1, actualPos.getColumn() + 1);
		}
		if (getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos)) {
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
		}
		
		//Baixo-esquerda
		actualPos.setValues(position.getRow() + 1, position.getColumn() - 1);
		while(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
			actualPos.setValues(actualPos.getRow() + 1, actualPos.getColumn() - 1);
		}
		if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
		}
		//Baixo-Direita
		actualPos.setValues(position.getRow() + 1, position.getColumn() + 1);
		while(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
			actualPos.setValues(actualPos.getRow() + 1, actualPos.getColumn() + 1);
		}
		if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
		}
		
		return mat;
	}
	
	
}