package CML;

public abstract class CardDecorator extends Card {
    protected Card decoratedCard;

    public CardDecorator(Card decoratedCard) {
        super(decoratedCard.getCardId(), decoratedCard.getUserId(), decoratedCard.getAccessLevel(),
                decoratedCard.getValidityPeriod(), "defaultField", "anotherField");
        this.decoratedCard = decoratedCard;
    }

    public abstract void addFeature();
}