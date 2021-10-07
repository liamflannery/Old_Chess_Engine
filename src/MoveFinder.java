import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveFinder {
    int pos;
    int type;
    boolean moved;
    int[] boardPos;
    int[] moves;
    int[] borders;
    int move;
    int[] directionIndex;
    int[][] numSquaresToEdge;
    int[] knightDir;
    boolean willCheck;
    public boolean inCheck;
    public boolean cantKC;
    public boolean cantQC;
    Move returnMove;
    int qCastle;
    int kCastle;
    boolean facingUp;
    List<Piece> myPieces;
    List<Integer> bottomCastleSquares;
    List<Integer> topCastleSquares;
    boolean willStopKC;
    boolean willStopQC;
    public MoveFinder(){
        directionIndex = new int[]{8,-8,-1,1,7,-7,9,-9};
        knightDir = new int[]{-17,-15,-10,-6,6,10,15,17};
        willCheck = false;
        computeSquares();        
    }
    public Move findMoves(int position, int inType, boolean inMoved, int[] inBoardPos, boolean inFacingUp, List<Piece> inMyPieces){
        qCastle = -1;
        kCastle = -1;
        myPieces = inMyPieces;
        willCheck = false;
        pos = position;
        type = inType;
        moved = inMoved;
        boardPos = inBoardPos;
        moves = new int[64];
        facingUp = inFacingUp;
        willStopKC = false;
        willStopQC = false;

       switch(type){
            case(-1):
                if(facingUp){
                    singleMoves(0,1);
                    singleMoves(4, 5);
                    singleMoves(6, 7);
                }
                else{
                    singleMoves(1,2);
                    singleMoves(5, 6);
                    singleMoves(7, 8);
                }
                pawnMoves();
                break;
            case(1):
                if(facingUp){
                    singleMoves(0,1);
                    singleMoves(4, 5);
                    singleMoves(6, 7);
                }
                else{
                    singleMoves(1,2);
                    singleMoves(5, 6);
                    singleMoves(7, 8);
                }
                
                pawnMoves();
                break;
            case(6):
                singleMoves(0, 8);
                castle();
                break;
            case(-6):
                singleMoves(0, 8);
                castle();
                break;
            case(5):
                slidingMoves(0, 8);
                break;
            case(-5):
                slidingMoves(0, 8);
                break;
            case(-2):
                slidingMoves(0, 4);
                break;
            case(2):
                slidingMoves(0, 4);
                break;
            case(4):
                slidingMoves(4, 8);
                break;
            case(-4):
                slidingMoves(4, 8);
                break;
            case(3):
                knightMoves();
                break;
            case(-3):
                knightMoves();
                break;
            default:
                Arrays.fill(moves, 1);
        }
        returnMove = new Move(moves, willCheck, kCastle, qCastle, willStopKC, willStopQC);
        return returnMove;
    }
    public void singleMoves(int direction, int directionEnd){
        for(int i = direction; i < directionEnd ; i++){
            move = pos + directionIndex[i];
            if(move < boardPos.length && move >= 0 && numSquaresToEdge[pos][i] > 0){
                if(!(Math.abs(type) == 1 && boardPos[move] != 0 && i < 3)){
                    if(!(Math.abs(type) == 1 && boardPos[move] == 0 && i > 3))
                        vetMove();
                }
            }
            
        }
    }
    public void slidingMoves(int direction, int directionEnd){
        for(int i = direction; i < directionEnd ; i++){
            for(int j = 1; j <= numSquaresToEdge[pos][i]; j++){
                move = pos + directionIndex[i] * j;
                if(move < boardPos.length && move >= 0){
                    if(boardPos[move] != 0){
                        if(boardPos[move] * type < 0){
                            vetMove();
                            break;
                        }
                        else{
                            break;
                        }
                    }
                    else{
                        vetMove();
                    }
                        
                    
                }
                else{
                    break;
                }
            }
            
        }
    }
    public void knightMoves(){
        int inc = 0;
      //  -17,-15,-10,-6,6,10,15,17
        for(int i = 0; i < knightDir.length; i++){
            switch(knightDir[i]){
                case(-17):
                    if(numSquaresToEdge[pos][2] >= 1 && numSquaresToEdge[pos][1] >= 2){
                        inc = knightDir[i];
                    }
                    break;
                case(-15):
                if(numSquaresToEdge[pos][1] >= 2 && numSquaresToEdge[pos][3] >= 1){
                    inc = knightDir[i];
                }
                break;
                case(-10):
                    if(numSquaresToEdge[pos][1] >= 1 && numSquaresToEdge[pos][2] >= 2){
                        inc = knightDir[i];
                    }
                    break;
                case(-6):
                if(numSquaresToEdge[pos][3] >= 2 && numSquaresToEdge[pos][1] >= 1){
                    inc = knightDir[i];
                }
                break;
                case(17):
                    if(numSquaresToEdge[pos][3] >= 1 && numSquaresToEdge[pos][0] >= 2){
                        inc = knightDir[i];
                    }
                    break;
                case(15):
                if(numSquaresToEdge[pos][0] >= 2 && numSquaresToEdge[pos][2] >= 1){
                    inc = knightDir[i];
                }
                break;
                case(10):
                    if(numSquaresToEdge[pos][0] >= 1 && numSquaresToEdge[pos][3] >= 2){
                        inc = knightDir[i];
                    }
                    break;
                case(6):
                if(numSquaresToEdge[pos][2] >= 2 && numSquaresToEdge[pos][0] >= 1){
                    inc = knightDir[i];
                }
                break;
                default:
                    inc = knightDir[i];
                    
            }
            move = pos + inc;
            vetMove();
        }
        
        
    }
    public void pawnMoves(){
        if(!(moved)){
            int direction;
            if(facingUp){
                direction = 1;
            }
            else{
                direction = -1;
            }
            move = pos + 16 * direction;
            if(boardPos[move] == 0 && boardPos[pos + 8 * direction] == 0){
                vetMove();
            }
            
        }
    }
    public void castle(){
        if(!(inCheck)){
            if(!(moved)){
                for(Piece piece: myPieces){
                    if(piece.getClass().getName().equals("Rook")){
                        if(!(piece.moved)){
                            int difference = pos - piece.posOnBoard;
                            difference = Math.abs(difference);
                            if(piece.loc.col == 'A'){   
                                if((difference == 3 & !cantKC) || (difference == 4 && !cantQC)){
                                    if(canCastle(pos, 2)){
                                        kCastle = myPieces.indexOf(piece);
                                        move = pos -2;
                                        vetMove();
                                    }
                                }                           
                                                               
                            }
                            if(piece.loc.col == 'H'){
                                if((difference == 3 & !cantKC) || (difference == 4 && !cantQC)){
                                    if(canCastle(pos, 3)){
                                        qCastle = myPieces.indexOf(piece);
                                        move = pos + 2;
                                        vetMove();
                                    }
                                }
                            }
                        }
                    }
                }  
            }            
        }
    }
    public void vetMove(){
        if(move >= 0 && move < boardPos.length){
            if(boardPos[move] != 0){
                if(boardPos[move] * type < 0){
                    if(Math.abs(boardPos[move]) == 6){
                        moves[move] = 2;
                        willCheck = true;
                    }
                    else{
                        moves[move] = 1;
                    }
                   
                }
            }
            else{
                moves[move] = 1;
            }
            if(facingUp){
                if(Math.abs(boardPos[60]) == 6){
                    if(attackCastle(60, 3).contains(move)){
                        willStopKC = true;
                    }
                    if(attackCastle(60, 2).contains(move)){
                        willStopQC = true;
                    }
                }
                if(Math.abs(boardPos[59]) == 6){
                    if(attackCastle(59, 3).contains(move)){
                        willStopQC = true;
                    }
                    if(attackCastle(59, 2).contains(move)){
                        willStopKC = true;
                    }
                }
            }
            else{
                if(Math.abs(boardPos[4]) == 6){
                    if(attackCastle(4, 3).contains(move)){
                        willStopKC = true;
                    }
                    if(attackCastle(4, 2).contains(move)){
                        System.out.println(type + " " + move + " QC");
                        willStopQC = true;
                    }
                }
                if(Math.abs(boardPos[3]) == 6){
                    if(attackCastle(3, 3).contains(move)){
                        System.out.println(type + " " + move + " QC");
                        willStopQC = true;
                    }
                    if(attackCastle(3, 2).contains(move)){
                        willStopKC = true;
                    }
                }
            }
    }
    }
    public boolean canCastle(int inPos, int direction){
        boolean returnValue = true;
        for(int i = 1; i < numSquaresToEdge[inPos][direction]; i++){
            if(direction == 2){
                if(boardPos[pos - i] != 0){
                    returnValue = false;
                    break;
                }
            }
            else{
                if(boardPos[pos + i] != 0){
                    returnValue = false;
                    break;
                }
            }
            
        }
        return returnValue;
    }
    public List<Integer> attackCastle(int kingPos, int direction){
        List<Integer> returnValue = new ArrayList<Integer>();
        for(int i = 1; i < numSquaresToEdge[kingPos][direction]; i++){
            if(direction == 2){
                if(boardPos[kingPos - i] != 0){
                    returnValue.add(pos-i);
                }
            }
            else{
                if(boardPos[kingPos + i] != 0){
                    returnValue.add(pos+i);
                }
            }
        }
        return returnValue;
    }
    public void computeSquares(){
        numSquaresToEdge = new int[64][];
        for(int squareIndex = 0; squareIndex < 64; squareIndex++){
            int y = squareIndex / 8;
				int x = squareIndex - y * 8;

				int north = 7 - y;
				int south = y;
				int west = x;
				int east = 7 - x;
				numSquaresToEdge[squareIndex] = new int[8];
				numSquaresToEdge[squareIndex][0] = north;
				numSquaresToEdge[squareIndex][1] = south;
				numSquaresToEdge[squareIndex][2] = west;
				numSquaresToEdge[squareIndex][3] = east;
				numSquaresToEdge[squareIndex][4] = Math.min(north, west);
				numSquaresToEdge[squareIndex][5] = Math.min(south, east);
				numSquaresToEdge[squareIndex][6] = Math.min(north, east);
				numSquaresToEdge[squareIndex][7] = Math.min(south, west);
        }
    }
}
