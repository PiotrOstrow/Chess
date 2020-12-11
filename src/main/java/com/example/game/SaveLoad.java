package com.example.game;

import com.example.game.pieces.*;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;


//Tanken var att kunna spara och ladda både en position utan historik och ett spel med historik
//Men för att slippa kladda ned interfacet blir nog bara spel med historik medtaget
//Får se om resten får ligga kvar ändå, eller om det rensas bort

public class SaveLoad{
    Scanner scanner;
    //File file = new File("SavedGame.txt");
    //System.out.println(new File("saves/SavedGame.txt").getAbsolutePath());
    //file.exists();
    //Scanner scanner = new Scanner(file);
    //Scanner scanner = new Scanner(new File("saves\\SavedPosition.txt"));
    Game game;

    public SaveLoad() {
        File directory = new File("saves/");
        if(!directory.exists())
            directory.mkdir();

    }


    public Game loadGame() {


        System.out.println("kom hit");
        try {
            scanner = new Scanner(new File("saves/TempSave.txt"));
            //scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (scanner.nextLine().equals("SavedGame"))
                game = loadFromSave();
        if  (scanner.nextLine().equals("SavedPosition"))
            loadFromPosition();
        scanner.close();
        return game;
        }



    public Game loadFromSave() {
        game = new Game();
        int fromx;
        int fromy;
        int tox;
        int toy;

        System.out.println("kom hit");
        try {
            scanner = new Scanner(new File("saves/NewSave.txt"));
            //scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        game.setUpNormal();


        while (scanner.hasNext()) {
            fromx = scanner.nextInt();
            fromy = scanner.nextInt();
            tox = scanner.nextInt();
            toy = scanner.nextInt();
            scanner.nextLine();
            System.out.print(fromx);
            game.getPiece(fromx,fromy);
            System.out.println(pieceToName(game.getPiece(fromx,fromy)));
            if ((game.getPiece(fromx,fromy)==null)||(!game.getPiece(fromx,fromy).getCanMove(game,tox,toy))) {
               System.out.println("Bad savefile, aborting");
                break;
            }
            game.move(game.getPiece(fromx,fromy),tox,toy);

        }


        return game;
    }


        public void loadFromPosition(){
        String piecetype;
        int x;
        int y;
        Color color;
        String colorstring;
        game = new Game();
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
        //game = something;
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

public void saveGame(Game gametosave) throws IOException {


    File savefile = new File("saves/NewSave.txt");
    if (savefile.exists()&&gametosave!=null){
        boolean deleted = savefile.delete();
        System.out.println(deleted);
        boolean create = savefile.createNewFile();
        System.out.println(create);
    }
    FileWriter fileWriter = new FileWriter("saves/NewSave.txt");
    PrintWriter printWriter = new PrintWriter(fileWriter);


    Stack<Move> movelog = gametosave.getMoveLogStack();

    for (int i=0;i<movelog.size();i++) {
        System.out.println(movelog.get(i).fromX + " " + movelog.get(i).fromY + " " + movelog.get(i).toX + " " + movelog.get(i).toY);
    }
    //printWriter.write("SavedGame");

    for (int i=0;i<movelog.size();i++){
        printWriter.println(movelog.get(i).fromX + " " + movelog.get(i).fromY + " " + movelog.get(i).toX + " " + movelog.get(i).toY);
    }
    fileWriter.close();

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