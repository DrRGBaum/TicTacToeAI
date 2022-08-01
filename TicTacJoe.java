import java.util.*;
import java.util.concurrent.TimeUnit;

public class TicTacJoe {

  // Window window = new Window();

  TicTacJoeAI ai = new TicTacJoeAI();

  char[][] game = new char[3][3];
  Scanner in = new Scanner(System.in);

  int option;
  String SOption;
  int pick;
  char pickChar;
  String yn;

  int counter1 = 0;
  int counter2 = 0;

  boolean[] victory = new boolean[2];
  int vic;
  boolean draw = false;
  boolean ende = false;

  int aiSwitch = 0;
  int Change;
  char figure = 'X';
  String player = "Player 1";

  boolean withAI, playerswitch;
  boolean aiEasy, aiMedium, aiHard, aiImpossible;
  boolean PvP, PvE, EvE;

  public static void main(String[] args) {
    new TicTacJoe().run();

  }

  public void run() {
    reset();
    select();
    matchfield();

    while (PvP) {
      turn();
      change();
    }
    while (PvE) {
      turn();
      change();
      turnAI();
      change();
    }
    while (EvE) {
      turnAI();
      change();
    }
  }

  public void turn() { // zug, output, gameover
    outerloop: while (true) {
      System.out.println("Turn of " + player + " \n");
      pick = in.nextInt();
      // pick = window.run();

      if (pick == 404) {
        System.out.println("Neustart");
        run();
      }
      pickChar = Character.forDigit(pick, 10);
      for (int i = 0; i < game.length; i++) {
        for (int j = 0; j < game.length; j++) {
          if (game[j][i] == pickChar) {
            game[j][i] = figure;
            break outerloop;
          }
        }
      }
      System.out.println("Ungültige eingabe \n");
    }
    output();
    gameover();
  }

  public void turnAI() { // AI je nach schwierigkeit, output, gameover
    System.out.println("Turn of the AI (" + player + ") \n");

    try { // wartet kurz
      for (int i = 0; i < 2; i++) {
        TimeUnit.MILLISECONDS.sleep(500);
      }
    } catch (Exception e) {
      System.out.println("Exception");
    }

    if (EvE) { // switches after every turn AI1 and AI2
      if (aiSwitch % 2 == 0) {
        playerswitch = false;
      } else if (aiSwitch % 2 == 1) {
        playerswitch = true;
      }
      aiSwitch++;
    }

    if (aiEasy) {
      pick = ai.aiEasy(game, playerswitch);
    } else if (aiMedium) {
      pick = ai.aiMedium(game, playerswitch);
    } else if (aiHard) {
      pick = ai.aiHard(game, playerswitch);
    } else if (aiImpossible) {
      pick = ai.aiImpossible(game, playerswitch);
    } else {
      System.out.println("Keine Schwierigkeit für die AI festgelegt");
    }

    pickChar = Character.forDigit(pick, 10);
    outerloop: for (int i = 0; i < game.length; i++) {
      for (int j = 0; j < game.length; j++) {
        if (game[j][i] == pickChar) {
          game[j][i] = figure;
          break outerloop;
        }
      }
    }

    output();
    gameover();
  }

  public void change() { // switches player 1 and 2
    if (figure == 'O') {
      player = "Player 1";
      figure = 'X';
      vic = 0;
    } else if (figure == 'X') {
      figure = 'O';
      player = "Player 2";
      vic = 1;
    }
  }

  public void select() { // auswählen zwischen pvp oder pve
    withAI = false;
    aiEasy = false;
    aiMedium = false;
    aiHard = false;
    aiImpossible = false;
    PvP = false;
    PvE = false;
    EvE = false;

    System.out.println("Wähle den Spielmodus:");
    System.out.println(" 1. Player    VS    Player");
    System.out.println(" 2. Player    VS    Computer");
    System.out.println(" 3. Computer  VS    Computer \n");
    SOption = in.next();

    if (SOption.equals("1")) {
      PvP = true;
    } else if (SOption.equals("2")) {
      PvE = true;
    } else if (SOption.equals("3")) {
      EvE = true;
    } else {
      System.out.println("Keine gültige eingabe");
      select();
    }

    if (PvE || EvE) {
      System.out.println("Welche schwierigkeit soll die AI haben? \n");
      System.out.println(" 1. Noob (Wählt zufällig felder aus)");
      System.out.println(" 2. Eine Gehirnzelle (Blockt dich)");
      System.out.println(" 3. Joe modus (Versucht dich zu schlagen)");
      System.out.println(" 4. Asia (Wenn du die AI schlägst bekommst du einen halben keks!) \n");
      SOption = in.next();
      if (SOption.equals("1")) {
        aiEasy = true;
      } else if (SOption.equals("2")) {
        aiMedium = true;
      } else if (SOption.equals("3")) {
        aiHard = true;
      } else if (SOption.equals("4")) {
        aiImpossible = true;
      } else {
        System.out.println("Keine gültige eingabe");
        select();
      }
    }
  }

  public void again() { // fragt den benutzter nach einem neuen spiel, wenn nein programmende
    System.out.println("Erneut spielen? Y/N \n");
    yn = in.next();
    if (yn.equals("y") || yn.equals("Y")) {
      run();
    } else {
      System.exit(0);
    }
  }

  public void end() { // gibt aus wer gewonnen hat
    if (victory[0]) {
      System.out.println(player + " has won!!");
      again();
    } else if (victory[1]) {
      System.out.println(player + " has won!!");
      again();
    } else if (draw) {
      System.out.println("Its a Draw!!");
      again();
    }
  }

  public void gameover() { // überprüft wann das spielzuende ist, end
    for (int o = 0; o < 2; o++, change()) {
      for (int i = 0; i < game.length; i++) { // prüft auf der x achse
        counter1 = 0;
        counter2 = 0;
        for (int j = 0; j < game.length; j++) {
          if (game[j][i] == figure) {
            counter1++;
            if (counter1 == 3) {
              victory[vic] = true;
            }
          }
          if (game[i][j] == figure) {
            counter2++;
            if (counter2 == 3) {
              victory[vic] = true;
            }
          }
        }
      }
      if (game[0][0] == figure && game[1][1] == figure && game[2][2] == figure) { // prüft die zwei kreuzmöglichkeiten
        victory[vic] = true;
      }
      if (game[2][0] == figure && game[1][1] == figure && game[0][2] == figure) {
        victory[vic] = true;
      }
      counter1 = 0;
      for (int i = 0; i < game.length; i++) { // wenn unentschieden ist
        for (int j = 0; j < game.length; j++) {
          if (game[j][i] == 'O' || game[j][i] == 'X') {
            counter1++;
            if (counter1 == 9) {
              draw = true;
            }
          }
        }
      }
    }
    end();
  }

  public void reset() { // setzt alle werte auf standart
    victory[0] = false;
    victory[1] = false;
    draw = false;
    ende = false;
    withAI = false;
    aiEasy = false;
    aiMedium = false;
    aiHard = false;
    aiImpossible = false;
    PvP = false;
    PvE = false;
    EvE = false;
    player = "Player 1";
    figure = 'X';
    vic = 0;
  }

  public void matchfield() { // setzt das spielfeld zurück
    char k = '1';
    for (int i = 0; i < game.length; i++) {
      for (int j = 0; j < game.length; j++) {
        game[j][i] = k;
        k++;
      }
    }
    output();
  }

  public void output() { // gibt die aktuellen variabeln als spielfeld aus
    System.out.println("\n\n\n");
    System.out.println("" + game[0][0] + " | " + game[1][0] + " | " + game[2][0] + " ");
    System.out.println("----------");
    System.out.println("" + game[0][1] + " | " + game[1][1] + " | " + game[2][1] + " ");
    System.out.println("----------");
    System.out.println("" + game[0][2] + " | " + game[1][2] + " | " + game[2][2] + " ");
    System.out.println("\n");
    // 1 | 2 | 3
    // 4 | 5 | 6
    // 7 | 8 | 9
    // window.update(game);

  }
}