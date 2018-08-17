package pub.toki.annotation;


import java.util.List;

public abstract class AuthUser {
    String userName;
    List<String> groupNames;

    public abstract AuthUser getUser(String userName);

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }
}
