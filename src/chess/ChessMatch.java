package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.Horse;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Tower;

public class ChessMatch {
	
	private Board board;
	private Color currentPlayer;
	private int turn;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		InitialSetup();
	}
	//methods
	public ChessPiece[][] getPieces()
	{
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i=0; i<board.getRows(); i++)
		{
			for(int j=0; j<board.getColumns(); j++)
			{
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
		
	}
	public boolean[][] possibleMoves(ChessPosition source)
	{
		Position position = source.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	public ChessPiece performChessMove(ChessPosition sourcePos, ChessPosition targetPos)
	{
		Position source = sourcePos.toPosition();
		Position target = targetPos.toPosition();
		validateSourcePosition(source);
		validateTargerPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if(testCheck(currentPlayer))
		{
			undoMove(source, target, capturedPiece);
			throw new ChessException("Esse movimento lhe coloca em check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		//Movimento especial promoção
		promoted = null;
		if (movedPiece instanceof Pawn) 
		{
			if(movedPiece.getColor() == Color.WHITE && target.getRow() == 0 || movedPiece.getColor() == Color.BLACK && target.getRow() == 7)
			{
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
			
		check = (testCheck(opponent(currentPlayer))) ? true:false;
		
		if (testCheckMate(opponent(currentPlayer)))
		{
			checkMate = true;
		}
		else {
			nextTurn();
		}
		//Movimento especial enPassant
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2 )  )
		{
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece)capturedPiece;
	}
	public ChessPiece replacePromotedPiece(String type)
	{
		if (promoted == null) {throw new IllegalStateException("Não ah peça a ser promovida");}
		if (!type.equals("B") && !type.equals("H") && !type.equals("T") && !type.equals("Q") ) {return promoted;}
		Position position = promoted.getChessPosition().toPosition();
		Piece piece = board.removePiece(position);
		piecesOnTheBoard.remove(piece);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, position);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
		
	}
	
	private ChessPiece newPiece(String type, Color color)
	{
		if(type.equals("B")) return new Bishop(board, color);
		if(type.equals("H")) return new Horse(board, color);
		if(type.equals("Q")) return new Queen(board, color);
		return new Tower(board, color);
	}
	private Piece makeMove(Position source, Position target)
	{
		ChessPiece piece = (ChessPiece)board.removePiece(source);
		piece.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(piece, target);
		if(capturedPiece != null)
		{
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		//Movimento especial roque
		//direita
		if(piece instanceof King && target.getColumn() == source.getColumn() + 2)
		{
			Position sourceTPosition = new Position(source.getRow(), source.getColumn() + 3);
			Position targetTPosition = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece tower = (ChessPiece)board.removePiece(sourceTPosition);
			board.placePiece(tower, targetTPosition);
			tower.increaseMoveCount();
		}
		//esquerda
		if(piece instanceof King && target.getColumn() == source.getColumn() - 2)
		{
			Position sourceTPosition = new Position(source.getRow(), source.getColumn() - 4);
			Position targetTPosition = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece tower = (ChessPiece)board.removePiece(sourceTPosition);
			board.placePiece(tower, targetTPosition);
			tower.increaseMoveCount();
		}
		//movimento especial enPassant
		if (piece instanceof Pawn) 
		{
			if(source.getColumn() != target.getColumn() && capturedPiece == null)
			{
				Position pawnPosition;
				if(piece.getColor() == Color.WHITE)
				{
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				else
				{
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		return capturedPiece;
	}
	private void undoMove(Position source, Position target, Piece capturedPiece)
	{
		ChessPiece piece = (ChessPiece)board.removePiece(target);
		piece.decreaseMoveCount();
		board.placePiece(piece, source);
		
		if(capturedPiece != null)
		{
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		//Movimento especial roque
		//direita
		if(piece instanceof King && target.getColumn() == source.getColumn() + 2)
		{
			Position sourceTPosition = new Position(source.getRow(), source.getColumn() + 3);
			Position targetTPosition = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece tower = (ChessPiece)board.removePiece(targetTPosition);
			board.placePiece(tower, sourceTPosition);
			tower.decreaseMoveCount();
		}
		//esquerda
		if(piece instanceof King && target.getColumn() == source.getColumn() - 2)
		{
			Position sourceTPosition = new Position(source.getRow(), source.getColumn() - 4);
			Position targetTPosition = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece tower = (ChessPiece)board.removePiece(targetTPosition);
			board.placePiece(tower, sourceTPosition);
			tower.decreaseMoveCount();
		}
		//movimento especial enPassant
		if (piece instanceof Pawn) 
		{
			if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable)
			{
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				if(piece.getColor() == Color.WHITE)
				{
					pawnPosition = new Position(3, target.getColumn());
				}
				else
				{
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
				
			}
		}
		
	}
	private void validateSourcePosition(Position pos)
	{
		if(!board.thereIsAPiece(pos) ) {throw new ChessException("Não ah nenhuma peça na posição fornecida");}
		if(currentPlayer != ((ChessPiece) board.piece(pos)).getColor()) {throw new ChessException("Peça do adversário não pode ser movida");}
		if(!board.piece(pos).isThereAnyPossibleMove()) {throw new ChessException("Sem movimentos possiveis para essa peça");}
					
	}
	private void validateTargerPosition(Position source, Position target)
	{
		if(!board.piece(source).possibleMove(target)) {throw new ChessException("Essa peça não pode fazer esse movimento");}
					
	}
	private void nextTurn() 
	{	
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;	
	}
	private boolean testCheckMate(Color color)
	{
		if(!testCheck(color)) {return false;}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece piece : list)
		{
			boolean[][] mat = piece.possibleMoves();
			for(int i = 0; i<board.getRows(); i++)
			{
				for(int j = 0; j<board.getColumns(); j++)
				{
					if(mat[i][j])
					{
						Position source = ((ChessPiece)piece).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck) {return false;}
					}
				}
			}
		}
		return true;
	}
	private void placeNewPiece(char column, int row, ChessPiece piece) 
	{
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	private Color opponent(Color color)
	{
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	private ChessPiece king(Color color)
	{
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list)
		{
			if(p instanceof King)
			{
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("A peça rei não foi encontrada");
	}
	private boolean testCheck(Color color)
	{
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> oponnetPieces =  piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece piece : oponnetPieces)
		{
			boolean[][] mat = piece.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()])
			{
				return true;
			}
		}
		return false;
	}
	private void InitialSetup()
	{
		placeNewPiece('a', 1, new Tower(board, Color.WHITE));
		placeNewPiece('h', 1, new Tower(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('b', 1, new Horse(board, Color.WHITE));
		placeNewPiece('g', 1, new Horse(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
		
		placeNewPiece('a', 8, new Tower(board, Color.BLACK));
		placeNewPiece('h', 8, new Tower(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('b', 8, new Horse(board, Color.BLACK));
		placeNewPiece('g', 8, new Horse(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
	
	public int getTurn()
	{
		return turn;
	}
	public Color getcurrentPlayer()
	{
		return currentPlayer;
	}
	public boolean getCheck()
	{
		return check;
	}
	public boolean getCheckMate()
	{
		return checkMate;
	}
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	public ChessPiece getPromoted() {
		return promoted;
	}

	
}
