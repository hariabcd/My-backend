package com.backend.localshare.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @SequenceGenerator(
            name = "user-seq",
            sequenceName = "user-seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user-seq"
    )
    private Long userId;
    @Column(name = "user_name", unique = true)
    private String userName;
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    private String profileUrl;
    @Column(name = "object_key", unique = true)
    private String objectKey;
    private String viewAt;
    private String location;
}
