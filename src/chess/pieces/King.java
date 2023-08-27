package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position pos)
	{
		ChessPiece piece = (ChessPiece) getBoard().piece(pos);
		return (piece == null || piece.getColor() != getColor());
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		//Acima
		p.setValues(position.getRow() - 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		//Abaixo
		p.setValues(position.getRow() + 1, position.getColumn());
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		//Esquerda
		p.setValues(position.getRow(), position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		//Esquerda
		p.setValues(position.getRow(), position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		//Cima-esquerda
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		//Cima-direita
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		//Baixo-esquerda
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
		//Baixo-direita
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if(getBoard().positionExists(p) && canMove(p))
		{
			mat[p.getRow()][p.getColumn()] = true;
		}
	
		return mat;
	}
	

}
