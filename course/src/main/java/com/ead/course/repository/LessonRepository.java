package com.ead.course.repository;

import com.ead.course.models.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonModel, UUID> {

    @Query(value = "select * from lesson_model where module_id = :moduleId", nativeQuery = true)
    List<LessonModel> findAllLessonsIntoModule(UUID moduleId);

    @Query(value = "select * from lesson_model where module_id = :moduleId and lesson_id = :lessonId", nativeQuery = true)
    Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);

}
