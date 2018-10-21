package main.de.mj.bb.core.playerobject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User implements UserInterface {

    private Set<User> users = new HashSet<>();
    private String name;
    private UUID uuid;
    private boolean afk;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isAFK() {
        return afk;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(User user) {
        this.users.add(user);
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }
}
