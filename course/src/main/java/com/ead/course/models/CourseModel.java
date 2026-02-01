package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Entity
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column()
    private String imageUrl;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseStatus courseStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseLevel courseLevel;

    @Column(nullable = false)
    private UUID userInstructor;

    // https://fasterxml.github.io/jackson-annotations/javadoc/2.6/com/fasterxml/jackson/annotation/JsonProperty.Access.html
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)// Relacionamento 1:N com ModuleModel, mapeado pelo atributo "course" e carregado sob demanda
    //@OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)//
    @Fetch(FetchMode.SUBSELECT)// Evita múltiplas queries (N+1) buscando os filhos em uma subquery única
    //@OnDelete(action = OnDeleteAction.CASCADE)// delega para o banco de dados fazer a remoção dos modules vinculados (dependendo do volume pode piorar a perfomance, e não a controle direto no que esta sendo deletado)
    private Set<ModuleModel> modules;

}
