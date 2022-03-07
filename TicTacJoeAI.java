import java.util.*;

public class TicTacJoeAI {

  Random rand = new Random();

  ArrayList<Integer> free = new ArrayList<Integer>(); // freie felder
  ArrayList<Integer> player1 = new ArrayList<Integer>(); // felder von spieler 1 (gegner der ki)
  ArrayList<Integer> player2 = new ArrayList<Integer>(); // felder von spieler 2 (eigene der ki)

  ArrayList<Integer> pickList = new ArrayList<Integer>();
  ArrayList<Integer> players = new ArrayList<Integer>();

  int pickAI = 0;
  int medium;
  int hard;

  int x, y, z;
  int random;

  boolean playerswitch;
  char figure1 = 'X';
  char figure2 = 'O';

  public int aiImpossible(char[][] game, boolean ps) { // verlieren ist keine option
    playerswitch = ps;
    analysis(game);

    pickAI = free.get(rand.nextInt(free.size()));

    if (player1.contains(5) || player2.contains(5)) {// random eckfeld um gegner zu blocken oder falle zu stellen
      pickList.clear();
      x = 1;
      y = 7;
      for (; y < 10; y = y + 2, x = x + 2) {
        if (free.contains(x)) {
          pickList.add(x);
        } else if (free.contains(y)) {
          pickList.add(y);
        }
      }
      if (pickList.size() != 0) {
        pickAI = pickList.get(rand.nextInt(pickList.size()));
      }
    }

    // setzt in in die ränder wenn gegner zuerst in die ecken setzt
    if (player1.contains(1) || player1.contains(3) || player1.contains(7) || free.contains(9)) {
      pickList.clear();
      for (int i = 2; i < 9; i = i + 2) { // wählt random ein freies randfeld aus
        if (free.contains(i)) {
          pickList.add(i);
        }
      }
      if (pickList.size() != 0) {
        pickAI = pickList.get(rand.nextInt(pickList.size()));
      }
    }

    // setzt in die ecken wenn gegner ränder zuerst setzt
    if (player1.contains(2) || player1.contains(4) || player1.contains(6) || player1.contains(8)) {
      pickList.clear();
      x = 1;
      y = 7;
      for (; y < 10; y = y + 2, x = x + 2) {
        if (free.contains(x)) {
          pickList.add(x);
        } else if (free.contains(y)) {
          pickList.add(y);
        }
      }
      if (pickList.size() != 0) {
        pickAI = pickList.get(rand.nextInt(pickList.size()));
      }
    }

    if (free.contains(5)) {
      pickAI = 5;
    }

    third();

    return pickAI;
  }

  public int aiHard(char[][] game, boolean ps) { // versucht intelligint zu gewinnen, versucht unentschieden (schlagbar)
    playerswitch = ps;

    analysis(game);

    pickAI = free.get(rand.nextInt(free.size()));

    if (free.contains(5)) {
      pickAI = 5;
    }

    if (player2.size() >= 1) {// setzt auf angrenzende freihe felder, bei mehreren möglichkeiten per zufall
      for (int i = 0; i < player2.size(); i++) {
        if (free.contains(player2.get(i) + 1)) {
          if (player2.get(i) + 1 != 4 || player2.get(i) + 1 != 7) { // rechts
            pickList.add(player2.get(i) + 1);
          } // ------------------------------------
        }
        if (free.contains(player2.get(i) - 1)) {
          if (player2.get(i) - 1 != 3 || player2.get(i) - 1 != 6) { // links
            pickList.add(player2.get(i) - 1);
          }
          // ------------------------------------
        }
        if (free.contains(player2.get(i) + 3)) { // unten
          pickList.add(player2.get(i) + 3);
          // ------------------------------------
        }
        if (free.contains(player2.get(i) - 3)) { // oben
          pickList.add(player2.get(i) - 3);
          // ------------------------------------
        }
        if (free.contains(player2.get(i) + 4)) { // schräg
          pickList.add(player2.get(i) + 4);
          // ------------------------------------
        }
        if (free.contains(player2.get(i) - 4)) { // schräg
          pickList.add(player2.get(i) - 4);
          // ------------------------------------
        }
      }
      pickAI = pickList.get(rand.nextInt(pickList.size()));
    }

    third();

    return pickAI;
  }

  public int aiMedium(char[][] game, boolean ps) { // blockiert den gegner, und versucht zu gewinnen, sonst zufällig
    playerswitch = ps;
    analysis(game);

    pickAI = free.get(rand.nextInt(free.size()));

    third();

    return pickAI;
  }

  public int aiEasy(char[][] game, boolean ps) { // einfache ai, wählt zufällig felder aus
    playerswitch = ps;
    analysis(game);

    pickAI = free.get(rand.nextInt(free.size()));

    return pickAI;
  }

  public void third() { // bockiert gegner wenn notwendig, platziert das dritte zum gewinnen
    players = player1;
    for (int o = 0; o < 2; o++, players = player2) {// bockiert gegner wenn notwendig, platziert das dritte zum gewinnen
      outerloop: while (true) {
        x = 1;
        y = 2;
        z = 3;
        for (int i = 0; i < 3; i++, x = x + 3, y = y + 3, z = z + 3) { // x- achse alle varianten von 2 feldern belegt
          if (players.contains(x) && players.contains(y) && free.contains(z)) {
            pickAI = z;
            break outerloop;
          } else if (players.contains(x) && players.contains(z) && free.contains(y)) {
            pickAI = y;
            break outerloop;
          } else if (players.contains(y) && players.contains(z) && free.contains(x)) {
            pickAI = x;
            break outerloop;
          }
        }
        x = 1;
        y = 4;
        z = 7;
        for (int i = 0; i < 3; i++, x++, y++, z++) { // y- achse alle varianten von 2 felder belegt
          if (players.contains(x) && players.contains(y) && free.contains(z)) {
            pickAI = z;
            break outerloop;
          } else if (players.contains(x) && players.contains(z) && free.contains(y)) {
            pickAI = y;
            break outerloop;
          } else if (players.contains(y) && players.contains(z) && free.contains(x)) {
            pickAI = x;
            break outerloop;
          }
        }
        x = 1;
        y = 5;
        z = 9;
        for (int i = 0; i < 2; i++, x = 3, z = 7) { // schräge varianten
          if (players.contains(x) && players.contains(y) && free.contains(z)) {
            pickAI = z;
            break outerloop;
          } else if (players.contains(x) && players.contains(z) && free.contains(y)) {
            pickAI = y;
            break outerloop;
          } else if (players.contains(y) && players.contains(z) && free.contains(x)) {
            pickAI = x;
            break outerloop;
          }
        }
        break;
      }
    }
  }

  public void analysis(char[][] game2) { // üperpüft welche felder frei und auf welchen X und O platziert sind
    if (playerswitch) {
      figure1 = 'O';
      figure2 = 'X';
    } else if (!playerswitch) {
      figure1 = 'X';
      figure2 = 'O';
    }
    int feld = 0;
    free.clear();
    player1.clear();
    player2.clear();
    pickList.clear();
    players.clear();
    for (int i = 0; i < game2.length; i++) {
      for (int j = 0; j < game2.length; j++) {
        feld++;
        if (game2[j][i] == figure1) {
          player1.add(feld);
        } else if (game2[j][i] == figure2) {
          player2.add(feld);
        } else {
          free.add(feld);
        }
      }
    }
  }
}