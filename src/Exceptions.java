public class Exceptions extends Exception{
}

class AreaWithoutRentException extends Exception{
    public AreaWithoutRentException() {
        System.out.println("AreaWithoutRentException");
    }
}

class InsufficientAssetException extends Exception{
    public InsufficientAssetException() {
        System.out.println("InsufficientAssetException");
    }
}

class UnPurchasableAreaException extends Exception{
    public UnPurchasableAreaException() {
        System.out.println("UnPurchasableAreaException");
    }
}

class UnrelatedBuyerException extends Exception{
    public UnrelatedBuyerException() {
        System.out.println("UnrelatedBuyerException");
    }
}

class UnrelatedUserException extends Exception{
    public UnrelatedUserException() {
        System.out.println("UnrelatedUserException");
    }
}

class UnrelatedAreaException extends Exception {
    UnrelatedAreaException() {
        System.out.println("UnrelatedAreaException");
    }
}

class NotInJailException extends Exception{
    public NotInJailException() {
        System.out.println("NotInJailException");
    }
}

class MaxHotelBuiltException extends Exception{
    public MaxHotelBuiltException() {
        System.out.println("MaxHotelBuiltException");
    }
}
class MaxHousesBuiltException extends Exception{
    public MaxHousesBuiltException() {
        System.out.println("MaxHousesBuiltException");
    }
}

class UnableToBuildException extends Exception{
    public UnableToBuildException() {
        System.out.println("UnableToBuildException");
    }
}

class BoughtAreaException extends Exception{
    BoughtAreaException() {
        System.out.println("BoughtAreaException");
    }
}

class MustSellRealEstatesException extends Exception {
    public MustSellRealEstatesException() {
        System.out.println("MustSellRealEstatesException");
    }
}

class BankruptcyException extends Exception{
    public BankruptcyException() {
        System.out.println("BankruptcyException");
    }
}

class UnbalancedBuildingsCountException extends Exception {
    public UnbalancedBuildingsCountException() {
        System.out.println("UnbalancedBuildingsCountException");
    }
}