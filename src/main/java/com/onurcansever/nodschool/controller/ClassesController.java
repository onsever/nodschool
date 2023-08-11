package com.onurcansever.nodschool.controller;

import com.onurcansever.nodschool.model.SchoolClass;
import com.onurcansever.nodschool.model.User;
import com.onurcansever.nodschool.repository.ClassesRepository;
import com.onurcansever.nodschool.repository.UserRepository;
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
public class ClassesController {

    private final ClassesRepository classesRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClassesController(ClassesRepository classesRepository, UserRepository userRepository) {
        this.classesRepository = classesRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/displayClasses", method = RequestMethod.GET)
    public ModelAndView displayClassesPage(Model model) {
        List<SchoolClass> schoolClasses = this.classesRepository.findAll();

        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("schoolClasses", schoolClasses);
        modelAndView.addObject("schoolClass", new SchoolClass());

        return modelAndView;
    }

    @RequestMapping(value = "/addNewClass", method = RequestMethod.POST)
    public ModelAndView addNewClass(Model model, @ModelAttribute("schoolClass") SchoolClass schoolClass) {
        this.classesRepository.save(schoolClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/displayClasses");

        return modelAndView;
    }

    @RequestMapping(value = "/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<SchoolClass> schoolClass = this.classesRepository.findById(id);

        for (User user : schoolClass.get().getUsers()) {
            user.setSchoolClass(null);
            this.userRepository.save(user);
        }

        this.classesRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/displayClasses");

        return modelAndView;
    }
}
