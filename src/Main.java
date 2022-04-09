import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Game game = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        startNewGame(scanner);

        System.out.println("round " + game.getRound());

        // Player turn
        for (int i = 0;; i++) {
            if (i == game.getPlayers().length) {
                i = 0;
                game.goNextRound();
                System.out.println("round " + game.getRound());
            }

            Player playerTurn = game.getPlayers()[i];
            if (playerTurn.bankruptcy) {
                continue;
            }

            System.out.println(playerTurn.getName() + "'s turn");

            rollDice(scanner, playerTurn);
            payRent(scanner, playerTurn);

            // Check chance card
            if (playerTurn.getLocation() == 24) {
                ((ChanceCard) game.getGameBoard().getAreas()[24]).randomChoiceCard(playerTurn);
            }
            // Pay tax
            else if (playerTurn.getLocation() == 17) {
                playerTurn.payTax();
            }
            // Collect prize
            else if (playerTurn.getLocation() == 6) {
                playerTurn.collectRent(200);
                System.out.println("You earned 200$");
            }

            // Player commands
            while (true) {
                boolean isExit = commands(scanner, playerTurn);
                if (isExit)
                    break;
            }
        }
    }

    private static void payRent(Scanner scanner, Player player) {
        while (true) {
            try {
                player.payRent(game.getGameBoard().getAreas()[player.getLocation()]);
                System.out.println("Payed: " +
                        game.getGameBoard().getAreas()[player.getLocation()].getRentPrice() + "$");
                break;
            } catch (MustSellRealEstatesException e) {
                System.out.println(e.getMessage());
                commands(scanner, player);
            } catch (BankruptcyException e) {
                System.out.println(e.getMessage());
                player.bankruptcy = true;
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private static void rollDice(Scanner scanner, Player player) {
        int diceNumber;

        while (true) {
            System.out.print("Dice Number: ");
            diceNumber = scanner.nextInt();
            if (diceNumber >= 1 && diceNumber <= 6) {
                break;
            } else {
                System.out.println("Invalid dice number");
            }
        }

        if (diceNumber == 6) {
            while (true) {
                System.out.print("Dice Number: ");
                int nextDiceNumber = scanner.nextInt();
                if (nextDiceNumber >= 1 && nextDiceNumber <= 6) {
                    diceNumber += nextDiceNumber;
                    break;
                } else {
                    System.out.println("Invalid dice number");
                }
            }
        }

        if (diceNumber == 12) {
            System.out.println("You was moved to prison area");
            player.setLocation(13);
            player.inJail = true;
        } else {
            int newLocation = player.getLocation() + diceNumber;
            if (newLocation > 24) {
                player.setLocation(newLocation - 24);
            } else {
                player.setLocation(newLocation);
            }
        }
    }

    private static void startNewGame(Scanner scanner) {
        while (true) {
            String inputCommand = scanner.next();

            if (inputCommand.equals("create_game")) {
                System.out.println("Type player names:");
                ArrayList<Player> playerArrayList = new ArrayList<>();

                while (true) {
                    String inputName = scanner.next();

                    if (inputName.equals("start_game")) {
                        if (playerArrayList.size() >= 2) {
                            break;
                        } else {
                            System.out.println("Not enough players!");
                            continue;
                        }
                    }

                    Player player = new Player(inputName);
                    playerArrayList.add(player);
                    if (playerArrayList.size() == 4)
                        break;
                }

                game = new Game(playerArrayList.toArray(new Player[0]));
                break;
            }

            else if (inputCommand.equals("start_game")) {
                System.out.println("no game created");
            }

            else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static boolean commands(Scanner scanner, Player player) {
        String inputCommand = scanner.next();

        switch (inputCommand) {
            case "index":
                int location = player.getLocation();
                System.out.println(location + ": " + game.getGameBoard().getAreas()[location].getClass().getSimpleName());
                break;
            case "property":
                System.out.println(player.printProperties());
                break;
            case "buy":
                try {
                    player.buy(game.getGameBoard().getAreas()[player.getLocation()]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "build":
                try {
                    player.build(game.getGameBoard().getAreas()[player.getLocation()]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "sell":
                int locationArea = scanner.nextInt();

                try {
                    player.sell(game.getGameBoard().getAreas()[locationArea]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "fly":
                int destinationArea = scanner.nextInt();

                try {
                    player.fly(destinationArea);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "free":
                try {
                    player.free();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "invest":
                try {
                    player.invest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "rank":
                int rank = 0;
                int[] totalProperties = new int[game.getPlayers().length];
                for (int j = 0; j < game.getPlayers().length; j++) {
                    totalProperties[j] = game.getPlayers()[j].totalProperty();
                }

                Arrays.sort(totalProperties);
                for (int j = 0; j < totalProperties.length; j++) {
                    if (player.totalProperty() == totalProperties[j]) {
                        rank = game.getPlayers().length - j;
                    }
                }

                System.out.println(rank);
                break;
            case "exit":
                return true;
            default:
                System.out.println("Invalid command!");
        }

        return false;
    }
}
