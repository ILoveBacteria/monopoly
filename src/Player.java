import java.util.ArrayList;

public class Player {
    private String name;
    private int asset = 1500;
    private ArrayList<Area> realEstates = new ArrayList<>();
    private int location = 1;
    public boolean bankruptcy = false;
    public boolean inJail = false;
    GameBoard gameBoard = GameBoard.getInstance();
    Bank bank;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAsset() {
        return asset;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void buy(Area area) throws Exception {
        {
            if (!area.isPurchasable())
                throw new UnPurchasableAreaException("This is not purchasable!");
            if (area.getBuyPrice() == null)
                throw new UnPurchasableAreaException("This area can not be purchased!");
            if (area.getOwner() != null)
                throw new BoughtAreaException("This area has already been purchased!");
            if (area.getBuyPrice() > asset)
                throw new InsufficientAssetException("Not enough money!");
        }

        area.setOwner(this);
        asset -= area.getBuyPrice();
        realEstates.add(area);
        if (area instanceof EmptyLand) {
            area.setRentPrice(gameBoard.EMPTY_LAND_RENT);
        }
    }

    public void collectRent(int rent) {
        asset += rent;
    }

    public void payRent(Area area) throws Exception {
        {
            if (area.getRentPrice() == null)
                throw new AreaWithoutRentException("There is no need to pay rent in this place!");
        }

        if (asset > area.getRentPrice()) {
            if (area.getOwner() != null) {
                area.getOwner().collectRent(area.getRentPrice());
            }
            asset -= area.getRentPrice();
        } else {
            int balance = asset + (totalAreaBalance() / 2);
            if (balance > area.getRentPrice()) {
                throw new MustSellRealEstatesException("Money is not enough. You have to sell one of your lands!");
            }
            throw new BankruptcyException("You went bankrupt!");
        }
    }

    public void payTax() {
        // Tax = 10%
        asset = 90 * asset / 100;
    }

    public void sell(Area area) throws Exception {
        {
            if (area.getOwner() != this)
                throw new UnrelatedUserException("You do not own this land!");
        }

        this.asset += (area.getBuyPrice() / 2);
        area.setOwner(null);
        realEstates.remove(area);
    }

    public void fly (int nextLocation) throws Exception {
        {
            if (!(gameBoard.areas[location] instanceof Airport))
                throw new UnrelatedAreaException("You are not in an airport!");
            if (nextLocation == location && !(nextLocation == gameBoard.AIRPORT_1 || nextLocation == gameBoard.AIRPORT_2 || nextLocation == gameBoard.AIRPORT_3))
                throw new UnrelatedAreaException("The next place was chosen incorrectly!"); // can be replacedced
            if (asset < gameBoard.AIRPLANE_TICKET_COST)
                if (asset < gameBoard.AIRPLANE_TICKET_COST)
                    throw new InsufficientAssetException("Not enough money!");
        }

        location = nextLocation;
        asset -= gameBoard.AIRPLANE_TICKET_COST;
    }

    public void free() throws Exception {
        {
            if (gameBoard.areas[location].getAreaNumber() != gameBoard.JAIL)
                throw new UnrelatedAreaException("You are not in prison area!");
            if (!inJail)
                throw new NotInJailException("You are not a prisoner!");
            if (asset < gameBoard.RELEASE_FROM_JAIL_COST)
                throw new InsufficientAssetException("Not enough money!");
        }

        inJail = false;
        asset -= gameBoard.RELEASE_FROM_JAIL_COST;
    }

    public void invest() throws Exception {
        if (gameBoard.areas[location].getAreaNumber() != gameBoard.BANK)
            throw new UnrelatedAreaException("You are not in bank area!");
        bank.addInvestor(this);
        asset /= 2;
    }

    public String printProperties() {
        String result = "";
        result += "Asset : " + asset + "$\n";
        result += "Real Estates : \n";
        Area[] realEstatesArr =  realEstates.toArray(new Area[0]);
        for (int i = 0; i < realEstatesArr.length-1; i++)
            for  (int j = 0; j < realEstatesArr.length-i-1; j++)
                if (realEstatesArr[j].getAreaNumber() > realEstatesArr[j+1].getAreaNumber()) {
                    Area temp = realEstatesArr[j];
                    realEstatesArr[j] = realEstatesArr[j+1];
                    realEstatesArr[j+1] = temp;
                }
        for (int i = 0; i < realEstatesArr.length; i++) {
            result += (i + 1) + " - " + realEstatesArr[i].getClass().getSimpleName() + "(" + realEstatesArr[i].getAreaNumber() + ")" + "\n";
            if (realEstatesArr[i] instanceof EmptyLand) {
                result += "   Houses Count : " + ((EmptyLand) realEstatesArr[i]).getHousesCount() + "\n";
                result += "   Hotels Count : " + ((EmptyLand) realEstatesArr[i]).getHotelsCount() + "\n";
            }
        }
        return result;
    }

    public void build(Area area) throws Exception {
        {
            if (area.getOwner() != this)
                throw new UnrelatedUserException("You do not own this land!");
            if (!(area instanceof EmptyLand))
                throw new UnrelatedAreaException("You are not in an empty land area!");
            if (((EmptyLand) area).getHotelsCount() == 1)
                throw new UnableToBuildException("You cannot build again!");
        }

        Area[] realEstateArr = realEstates.toArray(new Area[0]);
        for (int i = 0; i < realEstateArr.length; i++) {
            if (area instanceof EmptyLand) {
                if (((EmptyLand) area).getHousesCount() > ((EmptyLand) realEstateArr[i]).getHousesCount()) {
                    throw new UnbalancedBuildingsCountException("The number of your buildings should be balanced");
                }
            }
        }
        if (((EmptyLand) area).getHousesCount() == 4) {
            {
                if (asset < gameBoard.BUILD_HOTEL_COST)
                    throw new InsufficientAssetException("Not enough money!");
            }
            ((EmptyLand) area).setHotelsCount(1);
            ((EmptyLand) area).setHousesCount(0);
            area.setBuyPrice((int) (area.getBuyPrice() + gameBoard.BUILD_HOTEL_COST));
            area.setRentPrice((int) (area.getRentPrice() + gameBoard.ADD_HOTEL_RENT_ACCELERATION));
            asset -= gameBoard.BUILD_HOTEL_COST;
        } else {
            {
                if (asset < gameBoard.BUILD_HOUSE_COST)
                    throw new InsufficientAssetException("Not enough money!");
            }
            ((EmptyLand) area).setHousesCount(((EmptyLand) area).getHousesCount() + 1);
            area.setBuyPrice((int) (area.getBuyPrice() + gameBoard.BUILD_HOUSE_COST));
            area.setRentPrice((int) (area.getRentPrice() + gameBoard.ADD_HOUSE_RENT_ACCELERATION));
            asset -= gameBoard.BUILD_HOUSE_COST;
        }
    }

    public int totalProperty() {
        return asset + totalAreaBalance();
    }

    private int totalAreaBalance() {
        int result = 0;
        for (int i = 0; i < realEstates.size(); i++) {
            result += realEstates.get(i).getBuyPrice();
        }

        return result;
    }
}
