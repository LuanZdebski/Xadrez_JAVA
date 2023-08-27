package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.BoardException;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Tower;

public class ChessMatch {
	
	private Board board;
	private Color currentPlayer;
	private int turn;
	private boolean check;
	
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
		
		check = (testCheck(opponent(currentPlayer))) ? true:false;
		
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	private Piece makeMove(Position source, Position target)
	{
		Piece piece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(piece, target);
		if(capturedPiece != null)
		{
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	private void undoMove(Position source, Position target, Piece capturedPiece)
	{
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		
		if(capturedPiece != null)
		{
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
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
		placeNewPiece('c', 1, new Tower(board, Color.WHITE));
		placeNewPiece('c', 2, new Tower(board, Color.WHITE));
		placeNewPiece('d', 2, new Tower(board, Color.WHITE));
		placeNewPiece('e', 2, new Tower(board, Color.WHITE));
		placeNewPiece('e', 1, new Tower(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));
		
		placeNewPiece('c', 7, new Tower(board, Color.BLACK));
		placeNewPiece('c', 8, new Tower(board, Color.BLACK));
		placeNewPiece('d', 7, new Tower(board, Color.BLACK));
		placeNewPiece('e', 7, new Tower(board, Color.BLACK));
		placeNewPiece('e', 8, new Tower(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
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
	
	
}
