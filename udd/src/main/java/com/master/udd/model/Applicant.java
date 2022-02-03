package com.master.udd.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor

@Entity
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String surname;

    @NonNull
    @Column(nullable = false)
    private String email;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "education_level_id", referencedColumnName = "id")
    private EducationLevel educationLevel;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cv_id", referencedColumnName = "id")
    private CV cv;

//    @NonNull
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "letter_id", referencedColumnName = "id")
//    private Letter letter;
}
