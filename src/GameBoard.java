public class GameBoard {
    private static final GameBoard gameBoard = new GameBoard();
    public Area[] areas = new Area[25];

    public final int AIRPORT_1 = 3;
    public final int AIRPORT_2 = 11;
    public final int AIRPORT_3 = 20;
    public final int JAIL = 13;
    public final int BANK = 21;

    public final int AIRPLANE_TICKET_COST = 50;
    public final int RELEASE_FROM_JAIL_COST = 50;
    public final int EMPTY_LAND_COST = 100;
    public final int BUILD_HOUSE_COST = 150;
    public final int BUILD_HOTEL_COST = 100;
    public final int EMPTY_LAND_RENT = 50;
    public final int ADD_HOUSE_RENT_ACCELERATION = 100;
    public final int ADD_HOTEL_RENT_ACCELERATION = 150;
    public final int CINEMA_TICKET_COST = 25;



    private GameBoard() {
        areas[1] = new Parking(1);
        areas[2] = new EmptyLand(2);
        areas[3] = new Airport(3);
        areas[4] = new Cinema(4);
        areas[5] = new Road(5);
        areas[6] = new Prize(6);
        areas[7] = new EmptyLand(7);
        areas[8] = new Cinema(8);
        areas[9] = new EmptyLand(9);
        areas[10] = new Road(10);
        areas[11] = new Airport(11);
        areas[12] = new EmptyLand(12);
        areas[13] = new Prison(13);
        areas[14] = new EmptyLand(14);
        areas[15] = new Cinema(15);
        areas[16] = new Road(16);
        areas[17] = new Tax(17);
        areas[18] = new EmptyLand(18);
        areas[19] = new EmptyLand(19);
        areas[20] = new Airport(20);
        areas[21] = new Bank(21);
        areas[22] = new Cinema(22);
        areas[23] = new EmptyLand(23);
        areas[24] = new ChanceCard(24);
    }

    public static GameBoard getInstance() {
        return gameBoard;
    }

    public Area[] getAreas() {
        return areas;
    }
}