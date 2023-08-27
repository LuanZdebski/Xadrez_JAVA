package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
	//Movimento especial roque
	private boolean testCastling(Position pos)
	{
		ChessPiece piece = (ChessPiece)getBoard().piece(pos);
		return (piece != null && piece instanceof Tower && piece.getColor() == getColor() && piece.getMoveCount() == 0);
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
		//Movimento especial roque
		if(getMoveCount() == 0 && !chessMatch.getCheck())
		{
			//direita
			Position posTower1 = new Position(position.getRow(), position.getColumn() + 3);
			if(testCastling(posTower1))
			{
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null)
				{
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
				
			}
			//esquerda
			Position posTower2 = new Position(position.getRow(), position.getColumn() - 4);
			if(testCastling(posTower2))
			{
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null)
				{
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
				
			}
		}
	
		return mat;
	}
	

}
