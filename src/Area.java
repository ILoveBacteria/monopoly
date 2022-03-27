public abstract class Area {
    private final int areaNumber;
    private Integer rentPrice = null;
    private Integer buyPrice = null;
    private Player owner = null;
    private final boolean purchasable;

    public Area(int areaNumber, boolean purchasable) {
        this.areaNumber = areaNumber;
        this.purchasable = purchasable;
    }

    public int getAreaNumber() {
        return areaNumber;
    }

    public Integer getRentPrice() {
        return rentPrice;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isPurchasable() {
        return purchasable;
    }

    public void setRentPrice(Integer rentPrice) {
        this.rentPrice = rentPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
