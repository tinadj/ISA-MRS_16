package org.tim16.booker.model.airline;

import org.tim16.booker.model.users.User;

public class Invitation {

    private Integer id;
    private User inviter;
    private User invited;
    private InvitationStatus status;


}
