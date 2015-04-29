package entity;

import javax.persistence.*;

/**
 * Created by Тимур Мухитдинов on 17.04.2015.
 */

@Entity
@Table(name="users")
public class User {
    private Integer id;
    private String name;
    private Integer wins;
    private Integer loses;
    private Integer games;
    private Boolean busy;

    public User(String name) {
        this.name = name;
        wins = 0;
        loses = 0;
        games = 0;
        busy = false;
    }

    public User() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "wins")
    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    @Column(name = "loses")
    public Integer getLoses() {
        return loses;
    }

    public void setLoses(Integer loses) {
        this.loses = loses;
    }

    @Column(name = "games")
    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }

    @Column(name = "bysy")
    public Boolean getBusy() {
        return busy;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }
}
