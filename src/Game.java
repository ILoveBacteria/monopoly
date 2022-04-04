public class Game {
    private Player[] players;
    private int round = 1;
    private GameBoard gameBoard = GameBoard.getInstance();

    public Game(Player[] players) {
        this.players = players;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public int getRound() {
        return round;
    }

    public void goNextRound() {
        ++this.round;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
