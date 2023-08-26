package application;

import chess.ChessPiece;

public class UI {
	
	public static void printBoard(ChessPiece[][] pieces)
	{
		//assumindo que o tabuleiro medirá 8x8
		for(int i=0; i<pieces.length; i++)
		{
			System.out.print((8 - i) + " ");
			for(int j=0; j<pieces.length; j++)
			{
				printPiece(pieces[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	public static void printPiece(ChessPiece piece)
	{
		//local vazio
		if(piece == null)
		{
			System.out.print("-");
		}//peça existe
		else
		{
			System.out.print(piece);
		}
		System.out.print(" ");
	}

}
