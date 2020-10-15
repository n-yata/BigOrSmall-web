package bean;

/**
 * ユーザー情報Bean
 * @author yata1
 */
public class UserInfo {
    /** チップ枚数 */
    private Chip chip;
    /** 先に引いたカード */
    private Card prev;
    /** 後に引いたカード */
    private Card follow;
    /** 山札 */
    private Trump trump;
    /** ベット枚数 */
    private int bet;
    /** Big or Smallの選択情報 */
    private String bigSmall;
    /** ダブルアップの選択情報 */
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
