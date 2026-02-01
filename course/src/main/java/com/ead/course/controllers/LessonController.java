package com.ead.course.controllers;

import com.ead.course.dtos.LessonDTO;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ModuleService moduleService;

    @PostMapping("/modules/{moduleId)/lessons")
    public ResponseEntity<Object> saveLesson(@PathVariable UUID moduleId,
                                                @RequestBody @Valid LessonDTO lessonDTO) {
        Optional<ModuleModel> moduleModelOptional = moduleService.findById(moduleId);
        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module Not Found");
        }

        LessonModel lessonModel = new LessonModel();
        BeanUtils.copyProperties(lessonDTO, lessonModel);
        lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lessonModel.setModules(moduleModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService .save(lessonModel));
    }

    @DeleteMapping("/modules/{moduleId)/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId) {
        Optional<LessonModel> moduleModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }

        lessonService.delete(moduleModelOptional.get());
        return ResponseEntity.ok().body("Lesson deleted successfully");
    }

    @PutMapping("/modules/{moduleId)/lessons/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId,
                                               @RequestBody @Valid LessonDTO lessonDTO) {
        Optional<LessonModel> moduleModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (!moduleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course.");
        }

        LessonModel lessonModel = getLessonModel(lessonDTO);
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lessonModel));
    }

    private static LessonModel getLessonModel(LessonDTO lessonDTO) {
        LessonModel lessonModel = new LessonModel();
        lessonModel.setTitle(lessonDTO.getTitle());
        lessonModel.setDescription(lessonDTO.getDescription());
        lessonModel.setUrlVideo(lessonDTO.getUrlVideo());
        return lessonModel;
    }

    @GetMapping("/modules/{moduleId)/lessons")
    public ResponseEntity<List<LessonModel>> getAllLessons(@PathVariable UUID moduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllByModule(moduleId));
    }

    @GetMapping("/modules/{moduleId)/lessons/{lessonId}")
    public ResponseEntity<Object> getOneLesson(@PathVariable UUID moduleId, @PathVariable UUID lessonId) {
        Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);
        if (!lessonModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(lessonModelOptional.get());
    }

}
