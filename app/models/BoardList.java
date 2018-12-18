package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class BoardList extends Model {

    public static final Model.Finder<Long, List> find = new Model.Finder<>(List.class);

    @Id public Long id;
    @Column(nullable = false) public String name;
    @Column(nullable = false) public Long sortPosition = 0L;

    @JsonIgnore @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @Column(nullable = false) public Board board;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "list")
    @OrderBy("sortPosition ASC, id ASC")
    @JsonIgnore
    public List<Card> cards;

    protected BoardList() {
    }

    public static List create(Board board, String name) {
        List maxSortList = List.find.orderBy("sortPosition DESC").setMaxRows(1).findUnique();
        long nextSortPos = (maxSortList != null) ? (maxSortList.sortPosition + 1L) : 1L;

        BoardList list = new BoardList();
        list.name = name;
        list.board = board;
        list.sortPosition = nextSortPos;
        list.save();

        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardList list = (BoardList) o;
        return Objects.equals(id, List.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
