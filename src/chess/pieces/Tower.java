package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
		
		Position actualPos = new Position(0, 0);
		
		//Acima
		actualPos.setValues(position.getRow() - 1, position.getColumn());
		while(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
			actualPos.setRow(actualPos.getRow() - 1);
		}
		if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
		}
		
		//Esquerda
		actualPos.setValues(position.getRow(), position.getColumn() - 1);
		while (getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos)) {
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
			actualPos.setColumn(actualPos.getColumn() - 1);
		}
		if (getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos)) {
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
		}
		
		//Direita
		actualPos.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
			actualPos.setColumn(actualPos.getColumn() + 1);
		}
		if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
		}
		//Abaixo
		actualPos.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(actualPos) && !getBoard().thereIsAPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
			actualPos.setRow(actualPos.getRow() + 1);
		}
		if(getBoard().positionExists(actualPos) && isThereOponnentPiece(actualPos))
		{
			mat[actualPos.getRow()][actualPos.getColumn()] = true;
		}
		
		return mat;
	}
	
	
}
