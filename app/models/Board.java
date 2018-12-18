package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.OrderBy;
import java.util.List;
import java.util.Objects;
import io.ebean.*;

@Entity
public class Board extends Model {

    public static final Finder<Long, Board> find = new Finder<>(Board.class);

    @Id public Long id;
    @Column(nullable = false) public String name;

    @JsonIgnore @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @Column(nullable = false) public User owner;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "board")
    @OrderBy("sortPosition ASC, id ASC")
    @JsonIgnore
    public List<List> Lists;

    protected Board() {
    }

    public static Board create(User owner, String name) {
        Board board = new Board();
        board.name = name;
        board.owner = owner;
        board.save();
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


