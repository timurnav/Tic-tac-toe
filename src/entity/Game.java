package entity;

import DAO.GameDAO;

import javax.persistence.*;

/**
 * Created by Тимур Мухитдинов on 17.04.2015.
 */

@Entity
@Table(name="games")
public class Game {
    private Integer id;
    private Integer x;
    private Integer y;
    private Integer field;
    private String status;
    private String current;

    public Game() {
    }

    public Game(Integer x) {
        this.x = x;
        this.y = x;
        this.field = 0;
        this.status = GameDAO.Status.CREATED.toString();
        this.current = GameDAO.CurrentPlayer.X.toString();
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "x")
    public Integer getX() {
        return x;
    }
    @Column(name = "y")
    public Integer getY() {
        return y;
    }
    @Column(name = "field")
    public Integer getField() {
        return field;
    }
    @Column(name = "status")
    public String getStatus() {
        return status;
    }
    @Column(name = "current")
    public String getCurrent() {
        return current;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setField(Integer field) {
        this.field = field;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}
