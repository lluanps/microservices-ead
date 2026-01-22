package com.ead.authuser.models;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserModel extends RepresentationModel<UserModel> implements Serializable {
    private static final long serialVerionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 20)
    private String cpf;
    private String imageUrl;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    //@CreationTimestamp
    //As anotações @CreationTimestamp e @UpdateTimestamp evitam as duas linhas a seguir
    // userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
    // userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    //@UpdateTimestamp
    //As anotações @CreationTimestamp e @UpdateTimestamp evitam as duas linhas a seguir
    // userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
    // userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    private LocalDateTime lastUpdateDate;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    private UserType userType;

}