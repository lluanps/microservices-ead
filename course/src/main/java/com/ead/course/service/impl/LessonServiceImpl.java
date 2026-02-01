package com.ead.course.service.impl;

import com.ead.course.models.LessonModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public Object save(LessonModel lessonModel) {
        return lessonRepository.save(lessonModel);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(UUID courseId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(courseId,lessonId);
    }

    @Override
    public void delete(LessonModel lessonModel) {
        lessonRepository.delete(lessonModel);
    }

    @Override
    public List<LessonModel> findAllByModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }
}
