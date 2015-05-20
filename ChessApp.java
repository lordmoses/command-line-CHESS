import java.util.*;
import javax.swing.*;
public class ChessApp {
	
	
    static int blackKingPosition, whiteKingPosition;
    static int depthLimit=5;
    static boolean moveOrdering = true;
    static String rateMo;
    static String rateMa;
    static String ratePo;
    static boolean notValidMove = true;
    
    /* Constructing the Initial Chess Board State */
    static String chessBoard[][]={
        {"r","k","b","q","a","b","k","r"},
        {"p","p","p","p","p","p","p","p"},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {"P","P","P","P","P","P","P","P"},
        {"R","K","B","Q","A","B","K","R"}};
    
    public static void main(String[] args) {
    	 
    	log("       .___.");
     	log("    ,-^     ^-.");
     	log("   /           \\");
     	log("  /  __     __  \\");
     	log("  | />>\\   />>\\ |");
     	log("  | \\__/   \\__/ |      ALONZO");
     	log("   \\    /|\\    / ");
     	log("    \\   \\_/   /");
     	log("     |       | ");
     	log("     |+H+H+H+|");
     	log("     \\       /");
     	log("      ^-----^");
     	log("        ||");
     	log("       /  \\");
     	log("        ||");
     	
     	
     	
        if (args.length < 1){
           log("\n- Default AI Intelligence level  " + depthLimit +" -");
           log("Move Ordering Default - Enforced");
        }else if(args.length == 1){
        	depthLimit = Integer.valueOf(args[0]);
        	log("\n- User Customized AI Intelligence level  " + depthLimit +" -");
        	log("Move Ordering Default - Enforced");
        }else if(args.length == 2){
        	depthLimit = Integer.valueOf(args[0]);
        	log("\n- User Customized AI Intelligence level  " + depthLimit +" -");
        	if(args[1].charAt(0) == 'f'){
        		moveOrdering = false;
        		log("Move Ordering falsified by User");
        	}else{
        		log("\nUsage: java ChessApp [optional: Search Depth-Limit] [optional: f]");
         	   log("e.g.: java ChessApp  or java ChessApp 5   or  java ChessApp 5  f");
         		System.exit(1);
        	}
        }else{
        	   log("\nUsage: java ChessApp [optional: Search Depth-Limit] [optional: f]");
        	   log("e.g.: java ChessApp  or java ChessApp 5   or  java ChessApp 5  f");
        		System.exit(1);
        }
       
        Scanner input = new Scanner(System.in);
    	boolean checkmate = false;
    	boolean stalemate = false;
    	
    	
    	log("\nWELCOME TO MOSES CHESS AI aka Alonzo 1.0");
    	log("PLEASE ADHERE TO <MOVE ENTERING> FORMAT WHEN ENTERING MOVE, see README.txt\n\n");
    	
    	printBoard();
    	
    	log("\nPress Enter to Continue ");
    	input.skip("\n");
    	log("USER CONFIGURABLE PLAY STRATEGY\n");
    	log("RATE MATERIAL    Y   or   N");
    	rateMa = input.next();
    	
    	log("RATE KING MOVEABILITY    Y   or   N");
    	rateMo = input.next();
    	if(rateMo.equals("n")){
    		log(rateMo);}
    	log("RATE PIECE POSITIONS    Y   or   N");
    	ratePo = input.next();
    	log(ratePo);
    	log("\nPress Enter to Continue ");
    	input.skip("\n"); input.skip("\n");
    	
    	
        while (!"A".equals(chessBoard[blackKingPosition/8][blackKingPosition%8])) {blackKingPosition++;}
        while (!"a".equals(chessBoard[whiteKingPosition/8][whiteKingPosition%8])) {whiteKingPosition++;}
        String userInput, userPossibleMoves, stringMoved;
        double startTime, endTime;
       
        // BEGIN PLAY, Computer Always Start First //
       
        while(!checkmate && !stalemate){
        	log("\nwaiting for Alonzo to play..");
        	startTime=System.currentTimeMillis();
        	stringMoved = MinMax_AlphaBeta(depthLimit, Integer.MAX_VALUE, Integer.MIN_VALUE, "", 0);
        	executeMove(stringMoved);
            endTime=System.currentTimeMillis();
         
          
            log("\nAlonso Played: " + 
            interprete(chessBoard[Integer.valueOf(stringMoved.substring(2,3))][Integer.valueOf(stringMoved.substring(3,4))])
            + " in " +  (endTime-startTime)/1000+" s Your Turn");
            turnBoardAround();
        	 printBoard();
        	
        	 log("");
        	userPossibleMoves = possibleMoves();
        	log("Your Possible Moves: \n" + userPossibleMoves + "\nEnter Move: \n");
        	startTime=System.currentTimeMillis();
        	
        	
        	notValidMove = true;
			while (notValidMove) {
				userInput = input.next();
	        	endTime=System.currentTimeMillis();
				if (userPossibleMoves.replaceAll(userInput, "").length() < userPossibleMoves
						.length()) {
					// if the move is valid
					notValidMove = false;
					executeMove(userInput + " ");
					log("You Played:"
							+ interprete(chessBoard[Integer.valueOf(userInput
									.substring(2, 3))][Integer
									.valueOf(userInput.substring(3, 4))])
							+ " in " + (endTime - startTime) / 1000 + " s ");
					printBoard();
					turnBoardAround();
				} else {
					log("INVALID MOVE !, Enter Valid Move");
					
				}
			}
        }  
    }
    public static String MinMax_AlphaBeta(int depth, int beta, int alpha, String move, int player) {
        String list=possibleMoves();
        if (depth==0 || list.length()==0) {return move+(Heuristics.rating(list.length(), depth)*(player*2-1));}
        
        if(moveOrdering){
        	list=sortMoves(list);
        }
        player=1-player;
        for (int i=0;i<list.length();i+=5) {
            executeMove(list.substring(i,i+5));
            turnBoardAround();
            String returnString=MinMax_AlphaBeta(depth-1, beta, alpha, list.substring(i,i+5), player);
            int value=Integer.valueOf(returnString.substring(5));
            turnBoardAround();
            undoMove(list.substring(i,i+5));
            if (player==0) {
                if (value<=beta) {beta=value; if (depth==depthLimit) {move=returnString.substring(0,5);}}
            } else {
                if (value>alpha) {alpha=value; if (depth==depthLimit) {move=returnString.substring(0,5);}}
            }
            if (alpha>=beta) {
                if (player==0) {return move+beta; } else { return move+alpha;}
            }
        }
        if (player==0) {return move+beta;} else {return move+alpha;}
    }
    public static void turnBoardAround() {
        String temp;
        for (int i=0;i<32;i++) {
            int r=i/8, c=i%8;
            if (Character.isUpperCase(chessBoard[r][c].charAt(0))) {
                temp=chessBoard[r][c].toLowerCase();
            } else {
                temp=chessBoard[r][c].toUpperCase();
            }
            if (Character.isUpperCase(chessBoard[7-r][7-c].charAt(0))) {
                chessBoard[r][c]=chessBoard[7-r][7-c].toLowerCase();
            } else {
                chessBoard[r][c]=chessBoard[7-r][7-c].toUpperCase();
            }
            chessBoard[7-r][7-c]=temp;
        }
        int kingTemp=blackKingPosition;
        blackKingPosition=63-whiteKingPosition;
        whiteKingPosition=63-kingTemp;
    }
    public static void executeMove(String move) {
        if (move.charAt(4)!='P') {
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=" ";
            if ("A".equals(chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
                blackKingPosition=8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
            }
        } else {
            chessBoard[1][Character.getNumericValue(move.charAt(0))]=" ";
            chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
        }
    }
    public static void undoMove(String move) {
        if (move.charAt(4)!='P') {
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=String.valueOf(move.charAt(4));
            if ("A".equals(chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
                blackKingPosition=8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
            }
        } else {
            //PAWN PROMOTION
            chessBoard[1][Character.getNumericValue(move.charAt(0))]="P";
            chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));
        }
    }
    public static String possibleMoves() {
        String list="";
        for (int i=0; i<64; i++) {
            switch (chessBoard[i/8][i%8]) {
                case "P": list+=possibleP(i);
                    break;
                case "K": list+=possibleK(i);
            		break;
                case "B": list+=possibleB(i);
                    break;
                case "Q": list+=possibleQ(i);
                    break;
                case "A": list+=possibleA(i);
                    break;
                case "R": list+=possibleR(i);
                	break;
            }
        }
        return list;
    }
    public static String possibleP(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            try {
                if (Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i>=16) {
                    oldPiece=chessBoard[r-1][c+j];
                    chessBoard[r][c]=" ";
                    chessBoard[r-1][c+j]="P";
                    if (kingSafe()) {
                        list=list+r+c+(r-1)+(c+j)+oldPiece;
                    }
                    chessBoard[r][c]="P";
                    chessBoard[r-1][c+j]=oldPiece;
                }
            } catch (Exception e) {}
            try {
                if (Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i<16) {
                    String[] temp={"Q","R","B","K"};
                    for (int k=0; k<4; k++) {
                        oldPiece=chessBoard[r-1][c+j];
                        chessBoard[r][c]=" ";
                        chessBoard[r-1][c+j]=temp[k];
                        if (kingSafe()) {
                           
                            list=list+c+(c+j)+oldPiece+temp[k]+"P";
                        }
                        chessBoard[r][c]="P";
                        chessBoard[r-1][c+j]=oldPiece;
                    }
                }
            } catch (Exception e) {}
        }
        try {
            if (" ".equals(chessBoard[r-1][c]) && i>=16) {
                oldPiece=chessBoard[r-1][c];
                chessBoard[r][c]=" ";
                chessBoard[r-1][c]="P";
                if (kingSafe()) {
                    list=list+r+c+(r-1)+c+oldPiece;
                }
                chessBoard[r][c]="P";
                chessBoard[r-1][c]=oldPiece;
            }
        } catch (Exception e) {}
        try {
            if (" ".equals(chessBoard[r-1][c]) && i<16) {
                String[] temp={"Q","R","B","K"};
                for (int k=0; k<4; k++) {
                    oldPiece=chessBoard[r-1][c];
                    chessBoard[r][c]=" ";
                    chessBoard[r-1][c]=temp[k];
                    if (kingSafe()) {
                        list=list+c+c+oldPiece+temp[k]+"P";
                    }
                    chessBoard[r][c]="P";
                    chessBoard[r-1][c]=oldPiece;
                }
            }
        } catch (Exception e) {}
        try {
            if (" ".equals(chessBoard[r-1][c]) && " ".equals(chessBoard[r-2][c]) && i>=48) {
                oldPiece=chessBoard[r-2][c];
                chessBoard[r][c]=" ";
                chessBoard[r-2][c]="P";
                if (kingSafe()) {
                    list=list+r+c+(r-2)+c+oldPiece;
                }
                chessBoard[r][c]="P";
                chessBoard[r-2][c]=oldPiece;
            }
        } catch (Exception e) {}
        return list;
    }
    public static String possibleR(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j+=2) {
            try {
                while(" ".equals(chessBoard[r][c+temp*j]))
                {
                    oldPiece=chessBoard[r][c+temp*j];
                    chessBoard[r][c]=" ";
                    chessBoard[r][c+temp*j]="R";
                    if (kingSafe()) {
                        list=list+r+c+r+(c+temp*j)+oldPiece;
                    }
                    chessBoard[r][c]="R";
                    chessBoard[r][c+temp*j]=oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(chessBoard[r][c+temp*j].charAt(0))) {
                    oldPiece=chessBoard[r][c+temp*j];
                    chessBoard[r][c]=" ";
                    chessBoard[r][c+temp*j]="R";
                    if (kingSafe()) {
                        list=list+r+c+r+(c+temp*j)+oldPiece;
                    }
                    chessBoard[r][c]="R";
                    chessBoard[r][c+temp*j]=oldPiece;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(chessBoard[r+temp*j][c]))
                {
                    oldPiece=chessBoard[r+temp*j][c];
                    chessBoard[r][c]=" ";
                    chessBoard[r+temp*j][c]="R";
                    if (kingSafe()) {
                        list=list+r+c+(r+temp*j)+c+oldPiece;
                    }
                    chessBoard[r][c]="R";
                    chessBoard[r+temp*j][c]=oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(chessBoard[r+temp*j][c].charAt(0))) {
                    oldPiece=chessBoard[r+temp*j][c];
                    chessBoard[r][c]=" ";
                    chessBoard[r+temp*j][c]="R";
                    if (kingSafe()) {
                        list=list+r+c+(r+temp*j)+c+oldPiece;
                    }
                    chessBoard[r][c]="R";
                    chessBoard[r+temp*j][c]=oldPiece;
                }
            } catch (Exception e) {}
            temp=1;
        }
        return list;
    }
    public static String possibleK(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    if (Character.isLowerCase(chessBoard[r+j][c+k*2].charAt(0)) || " ".equals(chessBoard[r+j][c+k*2])) {
                        oldPiece=chessBoard[r+j][c+k*2];
                        chessBoard[r][c]=" ";
                        if (kingSafe()) {
                            list=list+r+c+(r+j)+(c+k*2)+oldPiece;
                        }
                        chessBoard[r][c]="K";
                        chessBoard[r+j][c+k*2]=oldPiece;
                    }
                } catch (Exception e) {}
                try {
                    if (Character.isLowerCase(chessBoard[r+j*2][c+k].charAt(0)) || " ".equals(chessBoard[r+j*2][c+k])) {
                        oldPiece=chessBoard[r+j*2][c+k];
                        chessBoard[r][c]=" ";
                        if (kingSafe()) {
                            list=list+r+c+(r+j*2)+(c+k)+oldPiece;
                        }
                        chessBoard[r][c]="K";
                        chessBoard[r+j*2][c+k]=oldPiece;
                    }
                } catch (Exception e) {}
            }
        }
        return list;
    }
    public static String possibleB(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    while(" ".equals(chessBoard[r+temp*j][c+temp*k]))
                    {
                        oldPiece=chessBoard[r+temp*j][c+temp*k];
                        chessBoard[r][c]=" ";
                        chessBoard[r+temp*j][c+temp*k]="B";
                        if (kingSafe()) {
                            list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                        }
                        chessBoard[r][c]="B";
                        chessBoard[r+temp*j][c+temp*k]=oldPiece;
                        temp++;
                    }
                    if (Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
                        oldPiece=chessBoard[r+temp*j][c+temp*k];
                        chessBoard[r][c]=" ";
                        chessBoard[r+temp*j][c+temp*k]="B";
                        if (kingSafe()) {
                            list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                        }
                        chessBoard[r][c]="B";
                        chessBoard[r+temp*j][c+temp*k]=oldPiece;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        return list;
    }
    public static String possibleQ(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        int temp=1;
        for (int j=-1; j<=1; j++) {
            for (int k=-1; k<=1; k++) {
                if (j!=0 || k!=0) {
                    try {
                        while(" ".equals(chessBoard[r+temp*j][c+temp*k]))
                        {
                            oldPiece=chessBoard[r+temp*j][c+temp*k];
                            chessBoard[r][c]=" ";
                            chessBoard[r+temp*j][c+temp*k]="Q";
                            if (kingSafe()) {
                                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                            }
                            chessBoard[r][c]="Q";
                            chessBoard[r+temp*j][c+temp*k]=oldPiece;
                            temp++;
                        }
                        if (Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
                            oldPiece=chessBoard[r+temp*j][c+temp*k];
                            chessBoard[r][c]=" ";
                            chessBoard[r+temp*j][c+temp*k]="Q";
                            if (kingSafe()) {
                                list=list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
                            }
                            chessBoard[r][c]="Q";
                            chessBoard[r+temp*j][c+temp*k]=oldPiece;
                        }
                    } catch (Exception e) {}
                    temp=1;
                }
            }
        }
        return list;
    }
    public static String possibleA(int i) {
        String list="", oldPiece;
        int r=i/8, c=i%8;
        for (int j=0;j<9;j++) {
            if (j!=4) {
                try {
                    if (Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])) {
                        oldPiece=chessBoard[r-1+j/3][c-1+j%3];
                        chessBoard[r][c]=" ";
                        chessBoard[r-1+j/3][c-1+j%3]="A";
                        int kingTemp=blackKingPosition;
                        blackKingPosition=i+(j/3)*8+j%3-9;
                        if (kingSafe()) {
                            list=list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
                        }
                        chessBoard[r][c]="A";
                        chessBoard[r-1+j/3][c-1+j%3]=oldPiece;
                        blackKingPosition=kingTemp;
                    }
                } catch (Exception e) {}
            }
        }
        return list;
    }
    public static String sortMoves(String list) {
        int[] score=new int [list.length()/5];
        for (int i=0;i<list.length();i+=5) {
            executeMove(list.substring(i, i+5));
            score[i/5]=-Heuristics.rating(-1, 0);
            undoMove(list.substring(i, i+5));
        }
        String newListA="", newListB=list;
        for (int i=0;i<Math.min(6, list.length()/5);i++) {//first few moves only
            int max=-1000000, maxLocation=0;
            for (int j=0;j<list.length()/5;j++) {
                if (score[j]>max) {max=score[j]; maxLocation=j;}
            }
            score[maxLocation]=-1000000;
            newListA+=list.substring(maxLocation*5,maxLocation*5+5);
            newListB=newListB.replace(list.substring(maxLocation*5,maxLocation*5+5), "");
        }
        return newListA+newListB;
    }
    public static boolean kingSafe() {
        int temp=1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    while(" ".equals(chessBoard[blackKingPosition/8+temp*i][blackKingPosition%8+temp*j])) {temp++;}
                    if ("b".equals(chessBoard[blackKingPosition/8+temp*i][blackKingPosition%8+temp*j]) ||
                            "q".equals(chessBoard[blackKingPosition/8+temp*i][blackKingPosition%8+temp*j])) {
                        return false;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        for (int i=-1; i<=1; i+=2) {
            try {
                while(" ".equals(chessBoard[blackKingPosition/8][blackKingPosition%8+temp*i])) {temp++;}
                if ("r".equals(chessBoard[blackKingPosition/8][blackKingPosition%8+temp*i]) ||
                        "q".equals(chessBoard[blackKingPosition/8][blackKingPosition%8+temp*i])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(chessBoard[blackKingPosition/8+temp*i][blackKingPosition%8])) {temp++;}
                if ("r".equals(chessBoard[blackKingPosition/8+temp*i][blackKingPosition%8]) ||
                        "q".equals(chessBoard[blackKingPosition/8+temp*i][blackKingPosition%8])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
        }
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ("k".equals(chessBoard[blackKingPosition/8+i][blackKingPosition%8+j*2])) {
                        return false;
                    }
                } catch (Exception e) {}
                try {
                    if ("k".equals(chessBoard[blackKingPosition/8+i*2][blackKingPosition%8+j])) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }
        if (blackKingPosition>=16) {
            try {
                if ("p".equals(chessBoard[blackKingPosition/8-1][blackKingPosition%8-1])) {
                    return false;
                }
            } catch (Exception e) {}
            try {
                if ("p".equals(chessBoard[blackKingPosition/8-1][blackKingPosition%8+1])) {
                    return false;
                }
            } catch (Exception e) {}
          
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("a".equals(chessBoard[blackKingPosition/8+i][blackKingPosition%8+j])) {
                                return false;
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        }
        return true;
    }
    public static void log(String string){
    	System.out.println(string);
    }
    public static String interprete(String piece){
    	switch (piece) {
        case "P": return "PAWN";
           
        case "K": return "KNIGHT";
    		
        case "B": return "BISHOP";
           
        case "Q": return "QUEEN";
           
        case "A": return "KING";
           
        case "R": return "ROOK";
        	
    }	
    	return "";
    }
    
    public static void printBoard(){
    	log("              ---------------------------------");
    	 for (int i=0; i<64; i++) {
    		 if(i%8 == 0){
    			 System.out.print("          "+ (i/8) + "   | " + chessBoard[i/8][i%8] + " ");
    		 }else{
    			 System.out.print("| " + chessBoard[i/8][i%8] + " ");
    		 }
    		 if(i%8 == 7){ 
    			 log("|"+"\n              ---------------------------------");
    		} 
    	 }
    	 log("\n                0   1   2   3   4   5   6   7");
    }
}