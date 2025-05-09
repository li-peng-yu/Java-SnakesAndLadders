public enum Item {
    FROZEN_TRAP("冰冻陷阱", "冻结对手一回合"),
    POSITION_SWAP("位置交换器", "立即与对手交换位置");
    
    private final String name;
    private final String description;
    
    Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
}
