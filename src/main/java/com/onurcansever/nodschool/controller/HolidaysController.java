package com.onurcansever.nodschool.controller;

import com.onurcansever.nodschool.model.Holiday;
import com.onurcansever.nodschool.repository.HolidaysRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
public class HolidaysController {

    private final HolidaysRepository holidaysRepository;

    @Autowired
    public HolidaysController(HolidaysRepository holidaysRepository) {
        this.holidaysRepository = holidaysRepository;
    }

    @RequestMapping(value = {"/holidays", "/holidays/{display}"}, method = RequestMethod.GET)
    public String displayHolidays(@PathVariable(required = false) String display, Model model) {
        if (display == null || display.equals("all")) {
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        } else if (display.equals("festival")) {
            model.addAttribute("festival", true);
        } else {
            model.addAttribute("federal", true);
        }

        Iterable<Holiday> holidayIterable = this.holidaysRepository.findAll();

        List<Holiday> holidays = StreamSupport.stream(holidayIterable.spliterator(), false).toList();

        Holiday.Type[] types = Holiday.Type.values();

        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(), (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }

        return "holidays.html";
    }
}
