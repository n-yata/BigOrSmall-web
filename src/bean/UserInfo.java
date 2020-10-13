package bean;

public class UserInfo {
    private Chip chip;
    private Card prev; // 先に引いたカード
    private Card follow; // 後に引いたカード
    private Trump trump;
    private int bet;
    private int bs; // Big = 0, Small = 1
    private String bigSmall;
    private boolean doubleUp;

    public UserInfo() {
        chip = new Chip(100);
        trump = new Trump();
        trump.createDeck();
        prev = trump.draw();
        doubleUp = false;
    }

    public Chip getChip() {
        return chip;
    }
    public void setChip(Chip chip) {
        this.chip = chip;
    }
    public Card getPrev() {
        return prev;
    }
    public void setPrev(Card prev) {
        this.prev = prev;
    }
    public Card getFollow() {
        return follow;
    }
    public void setFollow(Card follow) {
        this.follow = follow;
    }
    public Trump getTrump() {
        return trump;
    }
    public void setTrump(Trump trump) {
        this.trump = trump;
    }
    public int getBet() {
        return bet;
    }
    public void setBet(int bet) {
        this.bet = bet;
    }
    public int getBs() {
        return bs;
    }
    public void setBs(int bs) {
        this.bs = bs;
    }
    public String getBigSmall() {
        return bigSmall;
    }
    public void setBigSmall(String bigSmall) {
        this.bigSmall = bigSmall;
    }
    public boolean isDoubleUp() {
        return doubleUp;
    }
    public void setDoubleUp(boolean doubleUp) {
        this.doubleUp = doubleUp;
    }
}
