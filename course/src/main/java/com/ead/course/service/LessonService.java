package com.ead.course.service;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {

    Object save(LessonModel lessonModel);

    Optional<LessonModel> findLessonIntoModule(UUID courseId, UUID lessonId);

    void delete(LessonModel lessonModel);

    List<LessonModel> findAllByModule(UUID moduleId);
}
