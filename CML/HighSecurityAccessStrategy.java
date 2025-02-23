package CML;

public class HighSecurityAccessStrategy implements AccessStrategy {
    @Override
    public boolean checkAccess(Card card, String floorLevel) {
        return card.getAccessLevel().equals("high") && floorLevel.equals("high");
    }
}