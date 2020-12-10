package com.example.game;

import com.example.game.pieces.*;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class SaveLoad{
    Scanner scanner;
    //Scanner scanner = new Scanner(new File("saves\\SavedPosition.txt"));
    Game game = new Game();

    public SaveLoad()  {
        try {
            scanner = new Scanner(new File("saves/SavedGame.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Game loadGame() {

        if (scanner.nextLine().equals("SavedGame"))
                loadFromSave();
        if  (scanner.nextLine().equals("SavedPosition"))
            loadFromPosition();
        scanner.close();
        return this.game;
        }



    public void loadFromSave() {
        int fromx;
        int fromy;
        int tox;
        int toy;

        game.setUpNormal();

        while (scanner.hasNext()) {
            fromx = scanner.nextInt();
            fromy = scanner.nextInt();
            tox = scanner.nextInt();
            toy = scanner.nextInt();
            scanner.nextLine();
            if (!game.getPiece(fromx,fromy).getCanMove(game,tox,toy)) {
                System.out.println("Bad savefile, aborting");
                break;
            }
            game.move(game.getPiece(fromx,fromy),tox,toy);

        }
    }


        public void loadFromPosition(){
        String piecetype;
        int x;
        int y;
        Color color;
        String colorstring;
        while (scanner.hasNext()) {
            piecetype = scanner.next();
            if (piecetype.equals("BLACKTOMOVE")){
                game.setCurrentMovePlayer(Color.BLACK);
                break;
            }
            if (piecetype.equals("WHITETOMOVE")){
                game.setCurrentMovePlayer(Color.WHITE);
                break;
            }

            x = scanner.nextInt();
            y = scanner.nextInt();
            colorstring = scanner.next();

            if (x<0||y<0||x>7||y>7||!(colorstring.equals("WHITE")||colorstring.equals("BLACK"))){
                System.out.println("Bad positionfile, aborting");
                break;
            }
            color = Color.WHITE;
            if (colorstring.equals("BLACK")) {
                color = Color.BLACK;}
            switch (piecetype) {
                case "King":
                    game.addPiece(new King(x, y, color));
                    break;
                case "Queen":
                    game.addPiece(new Queen(x, y, color));
                    break;
                case "Rook":
                    game.addPiece(new Rook(x, y, color));
                    break;
                case "Bishop":
                    game.addPiece(new Bishop(x, y, color));
                    break;
                case "Knight":
                    game.addPiece(new Knight(x, y, color));
                    break;
                case "Pawn":
                    game.addPiece(new Pawn(x, y, color));
                    break;
            }
        }

    }



public void savePosition() throws IOException {
    FileWriter fileWriter = new FileWriter("saves\\NewSave.txt");
    PrintWriter printWriter = new PrintWriter(fileWriter);

    printWriter.print("SavedPosition");
    for (int i=0;i<8;i++){
        for (int j=0;j<8;j++){
            String color = "WHITE";
            if (game.getPiece(i,j).getColor()==Color.BLACK)
                color = "BLACK";
            if (game.getPiece(i,j)!=null)
                printWriter.print(pieceToName(game.getPiece(i,j)) + " " + i + " " + j + " " + color);
        }
    }
    if (game.getCurrentMovePlayer()==Color.WHITE){
        printWriter.print("WHITETOMOVE");
    }
    else {
        printWriter.print("BLACKTOMOVE");
    }

}

public void saveGame() throws IOException {
    FileWriter fileWriter = new FileWriter("saves\\NewSave.txt");
    PrintWriter printWriter = new PrintWriter(fileWriter);

    printWriter.print("SavedGame");
    Stack<Move> movelog = game.getMoveLogStack();
    for (int i=0;i<movelog.size();i++){
        printWriter.print(movelog.get(i).fromX + " " + movelog.get(i).fromY + " " + movelog.get(i).toX + " " + movelog.get(i).toY);
    }

}

private String pieceToName(ChessPiece piece){
    if (piece instanceof King) return "King";
    if (piece instanceof Queen) return "Queen";
    if (piece instanceof Rook) return "Rook";
    if (piece instanceof Bishop) return "Bishop";
    if (piece instanceof Knight) return "Knight";
    if (piece instanceof Pawn) return "Pawn";
    return "ERROR!";
}

}