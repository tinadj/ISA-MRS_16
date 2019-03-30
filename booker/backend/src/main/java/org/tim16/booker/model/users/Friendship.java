package org.tim16.booker.model.users;

import javax.persistence.*;

@Entity
@Table(name = "friendships")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user1", referencedColumnName = "id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2", referencedColumnName = "id", nullable = false)
    private User user2;

    @Enumerated
    @Column( nullable = false)
    private FriendshipStatus status;

    public Friendship() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
