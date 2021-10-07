import java.util.List;

public class Move {
   int[] moves;
   boolean willCheck;
   public int kCastle;
   public int qCastle;
   boolean willStopKC;
   boolean willStopQC;
   public Piece piece;
   List<Square> squares;
   public Move(int[] movesIn, boolean willCheckIn, int inKCastle, int inQCastle, boolean inKC, boolean inQC){
       moves = movesIn;
       willCheck = willCheckIn;
       kCastle = inKCastle;
       qCastle = inQCastle;
       willStopKC = inKC;
       willStopQC = inQC;
   } 
   public Move(List<Square> squaresIn, boolean willCheckIn, int inKCastle, int inQCastle, boolean inKC, boolean inQC){
       squares = squaresIn;
       willCheck = willCheckIn;
       kCastle = inKCastle;
       qCastle = inQCastle;
       willStopKC = inKC;
       willStopQC = inQC;
   }
   
   public int[] getMoves(){
    return moves;
   }
   public boolean testCheck(){
       return willCheck;
   }
}
