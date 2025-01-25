package com.backend.localshare.settings;

import com.backend.localshare.user.User;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Settings {

    @Id
    @SequenceGenerator(
            name = "settings-seq",
            sequenceName = "settings-seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "settings-seq"
    )
    private List<String> searchTerms;
    @OneToOne
    @JoinColumn
    private User userId;
    private int totalPostStar;
    private int totalStatusStars;
}
