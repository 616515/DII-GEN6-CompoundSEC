package CML;

public class PremiumCardDecorator extends CardDecorator {
    public PremiumCardDecorator(Card decoratedCard) {
        super(decoratedCard);
    }

    @Override
    public void addFeature() {
        System.out.println("Added premium feature to card: " + decoratedCard.getCardId());
    }
}