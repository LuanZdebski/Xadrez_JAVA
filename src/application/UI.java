package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	//cores para o console
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void clearScreen()
	{
		System.out.print("\033[2J");
		System.out.flush();
	}
	
	public static ChessPosition readChessPosition(Scanner sc)
	{
		try 
		{
			String entry = sc.nextLine();
			char column = entry.charAt(0);
			int row = Integer.parseInt(entry.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) 
		{
			throw new InputMismatchException("Erro lendo posição");
		}

	}
	
	public static void printMatch(ChessMatch chessMatch)
	{
		printBoard(chessMatch.getPieces());
		System.out.println();
		System.out.println("Turno : " + chessMatch.getTurn());
		String color = (chessMatch.getcurrentPlayer() == Color.WHITE) ? "brancas" : "pretas";
		System.out.println("Vez das peças " + color);
	}
	public static void printBoard(ChessPiece[][] pieces)
	{
		//assumindo que o tabuleiro medirá 8x8
		for(int i=0; i<pieces.length; i++)
		{
			System.out.print((8 - i) + " ");
			for(int j=0; j<pieces.length; j++)
			{
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	//sobrecarga que gera posições coloridas de movimento
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves)
	{
		//assumindo que o tabuleiro medirá 8x8
		for(int i=0; i<pieces.length; i++)
		{
			System.out.print((8 - i) + " ");
			for(int j=0; j<pieces.length; j++)
			{
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	public static void printPiece(ChessPiece piece, boolean background)
	{
		//cor de fundo para possivel movimento
		if(background)
		{
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		//local vazio
		if(piece == null)
		{
			System.out.print("-" + ANSI_RESET);
		}//peça existe
		else
		{
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			}
			else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
			
		}
		System.out.print(" ");
	}

}
