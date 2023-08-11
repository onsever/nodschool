package com.onurcansever.nodschool.controller;

import com.onurcansever.nodschool.model.Courses;
import com.onurcansever.nodschool.model.User;
import com.onurcansever.nodschool.repository.ClassesRepository;
import com.onurcansever.nodschool.repository.CoursesRepository;
import com.onurcansever.nodschool.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class CoursesController {

    private final ClassesRepository classesRepository;
    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;

    @Autowired
    public CoursesController(ClassesRepository classesRepository, UserRepository userRepository, CoursesRepository coursesRepository) {
        this.classesRepository = classesRepository;
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
    }

    @RequestMapping(value = "/displayCourses")
    public ModelAndView displayCoursesPage(Model model) {
        List<Courses> courses = this.coursesRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Courses());

        return modelAndView;
    }

    @RequestMapping(value = "/addNewCourse", method = RequestMethod.POST)
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course) {
        ModelAndView modelAndView = new ModelAndView();
        this.coursesRepository.save(course);
        modelAndView.setViewName("redirect:/displayCourses");

        return modelAndView;
    }

    @RequestMapping(value = "/addStudentToCourse", method = RequestMethod.POST)
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("user") User user, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        User userEntity = this.userRepository.findByEmail(user.getEmail()).orElse(null);

        if (userEntity == null || !(userEntity.getUserId() > 0)) {
            modelAndView.setViewName("redirect:/viewStudents?id=" + courses.getCourseId() + "&error=true");
            return modelAndView;
        }

        userEntity.getCourses().add(courses);
        courses.getUsers().add(userEntity);

        this.userRepository.save(userEntity);
        session.setAttribute("courses", courses);

        modelAndView.setViewName("redirect:/viewStudents?id=" + courses.getCourseId());

        return modelAndView;
    }

    @RequestMapping(value = "/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int userId,
                                                HttpSession session) {
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<User> user = this.userRepository.findById(userId);

        user.get().getCourses().remove(courses);
        courses.getUsers().remove(user);
        this.userRepository.save(user.get());

        session.setAttribute("courses",courses);

        ModelAndView modelAndView = new
                ModelAndView("redirect:/viewStudents?id=" + courses.getCourseId());

        return modelAndView;
    }
}
