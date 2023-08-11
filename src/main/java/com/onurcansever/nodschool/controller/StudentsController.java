package com.onurcansever.nodschool.controller;

import com.onurcansever.nodschool.model.Courses;
import com.onurcansever.nodschool.model.SchoolClass;
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

import java.util.Optional;

@Slf4j
@Controller
public class StudentsController {

    private final ClassesRepository classesRepository;
    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;

    @Autowired
    public StudentsController(ClassesRepository classesRepository, UserRepository userRepository, CoursesRepository coursesRepository) {
        this.classesRepository = classesRepository;
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
    }

    @RequestMapping(value = "/displayStudents", method = RequestMethod.GET)
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session, @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;

        ModelAndView modelAndView = new ModelAndView("students.html");

        Optional<SchoolClass> schoolClass = this.classesRepository.findById(classId);

        modelAndView.addObject("schoolClass", schoolClass.get());
        modelAndView.addObject("user", new User());
        session.setAttribute("schoolClass", schoolClass.get());

        if (error != null) {
            errorMessage = "Invalid email address!";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("user") User user, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User retrievedUser = this.userRepository.findByEmail(user.getEmail()).orElse(null);
        SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");

        if (retrievedUser == null || !(retrievedUser.getUserId() > 0)) {
            modelAndView.setViewName("redirect:/displayStudents?classId=" + schoolClass.getClassId() + "&error=true");

            return modelAndView;
        }

        retrievedUser.setSchoolClass(schoolClass);
        this.userRepository.save(retrievedUser);

        schoolClass.getUsers().add(retrievedUser);
        this.classesRepository.save(schoolClass);

        modelAndView.setViewName("redirect:/displayStudents?classId=" + schoolClass.getClassId());

        return modelAndView;
    }

    @RequestMapping(value = "/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int userId, HttpSession session) {
        SchoolClass schoolClass = (SchoolClass) session.getAttribute("schoolClass");
        Optional<User> user = this.userRepository.findById(userId);

        // Remove class reference from the user
        user.get().setSchoolClass(null);
        // Remove user reference from the class
        schoolClass.getUsers().remove(user.get());

        SchoolClass savedClass = this.classesRepository.save(schoolClass);

        session.setAttribute("schoolClass", savedClass);

        ModelAndView modelAndView = new ModelAndView("redirect:/displayStudents?classId=" + schoolClass.getClassId());

        return modelAndView;
    }

    @RequestMapping(value = "/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id, HttpSession session, @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses = this.coursesRepository.findById(id);

        modelAndView.addObject("courses", courses.get());
        modelAndView.addObject("user", new User());

        session.setAttribute("courses", courses.get());

        if (error != null) {
            errorMessage = "Invalid email address!";
            modelAndView.addObject("errorMessage", errorMessage);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/student/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
