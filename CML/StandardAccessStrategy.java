package CML;

public class StandardAccessStrategy implements AccessStrategy {
    @Override
    public boolean checkAccess(Card card, String floorLevel) {
        return card.getAccessLevel().equals(floorLevel);
    }
}