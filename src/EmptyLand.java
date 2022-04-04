public class EmptyLand extends Area {

    private int housesCount = 0;
    private int hotelsCount = 0;

    public EmptyLand(int areaNumber) {
        super(areaNumber, true);
        super.setBuyPrice(100);
    }

    public int getHousesCount() {
        return housesCount;
    }

    public void setHousesCount(int housesCount) {
        this.housesCount = housesCount;
    }

    public int getHotelsCount() {
        return hotelsCount;
    }

    public void setHotelsCount(int hotelsCount) {
        this.hotelsCount = hotelsCount;
    }
}
