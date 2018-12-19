package models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Card extends Model {
    public static Finder<Long, Card> find = new Finder<>(Card.class);

    @Id
    public Long id;
    @Column(nullable = false) public String name;
    @Column(nullable = false) public Long sortPosition = 0L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @Column(nullable = false) public BoardList list;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @Column(nullable = true) public User cardMember;

    protected Card() {
    }

    public static Card create(BoardList list, String name) {
        Card maxSortCard = Card.find.query().orderBy("sortPosition DESC").setMaxRows(1).findOne();
        long nextSortPos = (maxSortCard != null) ? (maxSortCard.sortPosition + 1) : 1;

        Card card = new Card();
        card.name = name;
        card.list = list;
        card.sortPosition = nextSortPos;
        card.save();

        return card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
