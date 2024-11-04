package com.haifachagwey.notification;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {

    @Id
    @SequenceGenerator(
            name = "notification_id_sequence",
            sequenceName = "notification_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_id_sequence"
    )
    private Integer id;

    private String message;

    private String sender;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "to_cutomer_email")
    private String toCustomerEmail;

    @Column(name = "to_cutomer_id")
    private Integer toCustomerId;
}
