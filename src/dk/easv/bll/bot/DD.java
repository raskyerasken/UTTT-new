/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.easv.bll.bot;

import dk.easv.bll.field.IField;
import dk.easv.bll.game.IGameState;
import dk.easv.bll.move.IMove;
import dk.easv.bll.move.Move;
import java.io.Console;
import java.util.List;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jacob
 */
public class DD implements IBot{

    private int[][] preferredMoves = {
            {1, 1}, //Center
            {0, 0}, {0, 2}, {2, 0},  //Corners ordered across
            {0, 1}, {2, 1}, {1, 0}, {1, 2}}; //Outer Middles ordered across
   
    public static boolean isWin(String[][] board, IMove move, String currentPlayer){
       int localX = move.getX() % 3;
        int localY = move.getY() % 3;
        int startX = move.getX() - (localX);
        int startY = move.getY() - (localY);
        
      for (int i = startY; i < startY + 3; i++) {
            if (!board[move.getX()][i].equals(currentPlayer))
                break;
            if (i == startY + 3 - 1){ System.out.println(move.getX()+"\t"+move.getY())
                    ;return true;}
        }

        //check row
        for (int i = startX; i < startX + 3; i++) {
            if (!board[i][move.getY()].equals(currentPlayer))
                break;
            if (i == startX + 3 - 1){System.out.println(move.getX()+"\t"+move.getY());
            return true;}
        }
         //check diagonal
        if (localX == localY) {
            //we're on a diagonal
            int y = startY;
            for (int i = startX; i < startX + 3; i++) {
                if (!board[i][y++].equals(currentPlayer))
                    break;
                if (i == startX + 3 - 1) return true;
            }
        }

        //check anti diagonal
        if (localX + localY == 3 - 1) {
            int less = 0;
            for (int i = startX; i < startX + 3; i++) {
                if (!board[i][(startY + 2)-less++].equals(currentPlayer))
                    break;
                if (i == startX + 3 - 1) return true;
            }
        }
        return false;

       
    }
      
    ObservableList<IMove> goodMoves = FXCollections.observableArrayList();

    @Override
    public IMove doMove(IGameState state) {
        goodMoves.clear();
        String[][] boards = state.getField().getBoard();
       String xOrO = state.getMoveNumber()%2 == 0 ? "0" : "1"; 
       String[][] macroboards= state.getField().getMacroboard();
//               for (IMove strings : state.getField().getAvailableMoves()) {
//         int x = strings.getX()/3;
//         int y = strings.getY()/3;
//         String tresh=  macroboards[x][y];
//         macroboards[x][y]=xOrO;
//                   for (String[] macroboard : macroboards) {
//                       for (String string : macroboard) {
//                           System.out.print(string+"\t");
//                       }
//                       System.out.println("");
//                       
//                   }
//        if(isWins(macroboards, strings, xOrO))
//        {
//            System.out.println("hey mans");
//             macroboards[x][y]=tresh;
//        goodMoves.add(strings);
//        }
//        else{
//            macroboards[x][y]=tresh;
//        }
//        
//        
//                   }
//               
//               for (IMove goodMove : goodMoves) {
//                   System.out.println("heymane"+goodMove.getX()+"\t"+goodMove.getY());
//            
//        }
//        for (IMove strings : goodMoves) {
//            
//        int row=strings.getX();
//        int col=strings.getY();
//        boards[row][col]=xOrO;
//       if(isWin(boards,strings, xOrO))
//               {
//                    boards[row][col]=".";
//                   return  strings;
//               }
//       
//        else
//         {
//                 boards[row][col]=".";
//                 }
//        }
//        if(!goodMoves.isEmpty())
//        {
//            
//               return new Move(goodMoves.get(0).getX(),goodMoves.get(0).getY());         
//               
//        
//        }
//       
         for (IMove strings : state.getField().getAvailableMoves()) {
        int row=strings.getX();
        int col=strings.getY();
        boards[row][col]=xOrO;
       if(isWin(boards,strings, xOrO))
               {
                    boards[row][col]=".";
                   return  strings;
               }
        else
         {
                 boards[row][col]=".";
                 }
             
        }
        
     for (int[] move : preferredMoves)
        {
            
            if(state.getField().getMacroboard()[move[0]][move[1]].equals(IField.AVAILABLE_FIELD))
            {
                //find move to play
                
                for (int[] selectedMove : preferredMoves)
                {
                    int x = move[0]*3 + selectedMove[0];
                    int y = move[1]*3 + selectedMove[1];
                    if(state.getField().getBoard()[x][y].equals(IField.EMPTY_FIELD))
                    {
                        return new Move(x,y);
                    }
                }
            }
        }
      

        //NOTE: Something failed, just take the first available move I guess!
         return state.getField().getAvailableMoves().get(0);
    }
    @Override
    public String getBotName() {
    return "dd";
    }

    private boolean checkMacro(String[][] macroboards, String xOrO) {
      
        for (int i = 0; i < macroboards.length; i++) {
            if((macroboards[0][i]==xOrO &&
                   macroboards[1][i]==xOrO &&
                    macroboards[2][i]==xOrO))
            {return true;}
            else if((macroboards[i][0]==xOrO &&
                   macroboards[i][1]==xOrO &&
                    macroboards[i][2]==xOrO))
            {return true;}
            else  if((macroboards[0][0]==xOrO &&
                   macroboards[1][1]==xOrO &&
                    macroboards[2][2]==xOrO))
            {return true;}
             if((macroboards[2][0]==xOrO &&
                   macroboards[1][1]==xOrO &&
                    macroboards[2][0]==xOrO))
            {return true;}
            
        }  
        return false;
    }
    
}
