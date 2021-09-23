import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class Stage {
    Board board;
    Optional<Piece> selectedPiece;
    List<Piece> playerPieces; 
    List<Piece> compPieces;
    boolean playAsWhite = true;
    public Stage() {
        
        board = new Board();
        playerPieces = new ArrayList<Piece>();
        compPieces = new ArrayList<Piece>();
        if(playAsWhite){
            compPieces.add(new Rook(board.squares[0][0], false));
            compPieces.add(new Knight(board.squares[1][0], false));
            compPieces.add(new Bishop(board.squares[2][0], false));
            compPieces.add(new Queen(board.squares[3][0], false));
            compPieces.add(new King(board.squares[4][0], false));
            compPieces.add(new Bishop(board.squares[5][0], false));
            compPieces.add(new Knight(board.squares[6][0], false));
            compPieces.add(new Rook(board.squares[7][0], false));

            compPieces.add(new Pawn(board.squares[0][1], false));
            compPieces.add(new Pawn(board.squares[1][1], false));
            compPieces.add(new Pawn(board.squares[2][1], false));
            compPieces.add(new Pawn(board.squares[3][1], false));
            compPieces.add(new Pawn(board.squares[4][1], false));
            compPieces.add(new Pawn(board.squares[5][1], false));
            compPieces.add(new Pawn(board.squares[6][1], false));
            compPieces.add(new Pawn(board.squares[7][1], false));

            //Add white pieces
            playerPieces.add(new Rook(board.squares[0][7], true));
            playerPieces.add(new Knight(board.squares[1][7], true));
            playerPieces.add(new Bishop(board.squares[2][7], true));
            playerPieces.add(new Queen(board.squares[3][7], true));
            playerPieces.add(new King(board.squares[4][7], true));
            playerPieces.add(new Bishop(board.squares[5][7], true));
            playerPieces.add(new Knight(board.squares[6][7], true));
            playerPieces.add(new Rook(board.squares[7][7], true));

            playerPieces.add(new Pawn(board.squares[0][6], true));
            playerPieces.add(new Pawn(board.squares[1][6], true));
            playerPieces.add(new Pawn(board.squares[2][6], true));
            playerPieces.add(new Pawn(board.squares[3][6], true));
            playerPieces.add(new Pawn(board.squares[4][6], true));
            playerPieces.add(new Pawn(board.squares[5][6], true));
            playerPieces.add(new Pawn(board.squares[6][6], true));
            playerPieces.add(new Pawn(board.squares[7][6], true));
        }
        else{
            compPieces.add(new Rook(board.squares[0][0], true));
            compPieces.add(new Knight(board.squares[1][0], true));
            compPieces.add(new Bishop(board.squares[2][0], true));
            compPieces.add(new King(board.squares[3][0], true));
            compPieces.add(new Queen(board.squares[4][0], true));
            compPieces.add(new Bishop(board.squares[5][0], true));
            compPieces.add(new Knight(board.squares[6][0], true));
            compPieces.add(new Rook(board.squares[7][0], true));

            compPieces.add(new Pawn(board.squares[0][1], true));
            compPieces.add(new Pawn(board.squares[1][1], true));
            compPieces.add(new Pawn(board.squares[2][1], true));
            compPieces.add(new Pawn(board.squares[3][1], true));
            compPieces.add(new Pawn(board.squares[4][1], true));
            compPieces.add(new Pawn(board.squares[5][1], true));
            compPieces.add(new Pawn(board.squares[6][1], true));
            compPieces.add(new Pawn(board.squares[7][1], true));

            //Add white pieces
            playerPieces.add(new Rook(board.squares[0][7], false));
            playerPieces.add(new Knight(board.squares[1][7], false));
            playerPieces.add(new Bishop(board.squares[2][7], false));
            playerPieces.add(new King(board.squares[3][7], false));
            playerPieces.add(new Queen(board.squares[4][7], false));
            playerPieces.add(new Bishop(board.squares[5][7], false));
            playerPieces.add(new Knight(board.squares[6][7], false));
            playerPieces.add(new Rook(board.squares[7][7], false));

            playerPieces.add(new Pawn(board.squares[0][6], false));
            playerPieces.add(new Pawn(board.squares[1][6], false));
            playerPieces.add(new Pawn(board.squares[2][6], false));
            playerPieces.add(new Pawn(board.squares[3][6], false));
            playerPieces.add(new Pawn(board.squares[4][6], false));
            playerPieces.add(new Pawn(board.squares[5][6], false));
            playerPieces.add(new Pawn(board.squares[6][6], false));
            playerPieces.add(new Pawn(board.squares[7][6], false));
        }
    }

    public void paint(Graphics g, Point mouseLoc) {
        board.paint(g, mouseLoc);
        //Add black pieces
        
        for(Piece p: playerPieces) {
            p.paint(g);
        }
        for(Piece p: compPieces) {
            p.paint(g);
        }
        
        Optional<Square> underMouse = board.squareAtPoint(mouseLoc);
        if(underMouse.isPresent()) {
            Square hoverCell = underMouse.get();
            
            g.drawString(String.valueOf(hoverCell.col) + convertCoord(String.valueOf(hoverCell.row + 1)), 740, 30);
        }
    }
    public void mouseClicked(int x, int y){
        selectedPiece = Optional.empty();

    }
    public String convertCoord(String row){
        switch (row){
            case "1":
                return "8";
            case "2":
                return "7";
            case "3":
                return "6";
            case "4":
                return "5";
            case "5":
                return "4";
            case "6":
                return "3";
            case "7":
                return "2";
            case "8":
                return "1";
            default:
                return "0";
        }
    }
}
