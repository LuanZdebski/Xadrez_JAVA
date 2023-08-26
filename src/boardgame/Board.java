package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {

		if(rows < 1 || columns < 1) {throw new BoardException("Erro ao criar um tabuleiro, necessario pelo menos 1 lina e 1 coluna");}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	//methods
	public Piece piece(int row, int column)
	{
		if(!positionExists(row, column)) {throw new BoardException("Posição fora do tabuleiro");}
		return pieces[row][column];
	}
	public Piece piece(Position pos)
	{
		if(!positionExists(pos)) {throw new BoardException("Posição fora do tabuleiro");}
		return pieces[pos.getRow()][pos.getColumn()];
	}
	public void placePiece(Piece piece, Position pos)
	{
		if(thereIsAPiece(pos)) {throw new BoardException("Já possui uma peça nesta posição");}
		pieces[pos.getRow()][pos.getColumn()] = piece;
		piece.position = pos;
	}
	private boolean positionExists(int row, int column)
	{
		return (row >= 0 && row < rows && columns >=0 && column < columns);
	}
	public boolean positionExists(Position pos)
	{
		return positionExists(pos.getRow(), pos.getColumn());
	}
	public boolean thereIsAPiece(Position pos)
	{
		if(!positionExists(pos)) {throw new BoardException("Posição fora do tabuleiro");}
		return (piece(pos) != null);
	}
	
	
	//getters and setters
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}


}
