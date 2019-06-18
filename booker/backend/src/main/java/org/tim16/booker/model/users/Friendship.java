package org.tim16.booker.model.users;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friendships")
public class Friendship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user1", referencedColumnName = "id", nullable = false)
    private RegisteredUser user1;

    @ManyToOne
    @JoinColumn(name = "user2", referencedColumnName = "id", nullable = false)
    private RegisteredUser user2;

    @Enumerated
    @Column( nullable = false)
    private FriendshipStatus status;

    public Friendship() { /* empty constructor */}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RegisteredUser getUser1() {
        return user1;
    }

    public void setUser1(RegisteredUser user1) {
        this.user1 = user1;
    }

    public RegisteredUser getUser2() {
        return user2;
    }

    public void setUser2(RegisteredUser user2) {
        this.user2 = user2;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }
}
