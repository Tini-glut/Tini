package edu.glut.tini.data.item;


public class FriendsListItem  {
    private String userName;
    private char firstLetter;
    private boolean showFirstLetter;

    public FriendsListItem() {
    }

    public FriendsListItem(String userName, char firstLetter, boolean showFirstLetter) {
        this.userName = userName;
        this.firstLetter = firstLetter;
        this.showFirstLetter = showFirstLetter;
    }

    public FriendsListItem(String userName, char firstLetter) {
        this.userName = userName;
        this.firstLetter = firstLetter;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    public boolean isShowFirstLetter() {
        return showFirstLetter;
    }

    public void setShowFirstLetter(boolean showFirstLetter) {
        this.showFirstLetter = showFirstLetter;
    }

    @Override
    public String toString() {
        return "FriendsListItem{" +
                "userName='" + userName + '\'' +
                ", firstLetter=" + firstLetter +
                ", showFirstLetter=" + showFirstLetter +
                '}';
    }
}
