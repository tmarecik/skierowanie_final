package com.example.thymeleaf.skierowanie.controller;

import com.example.thymeleaf.skierowanie.dao.MiejsceDao;
import com.example.thymeleaf.skierowanie.dao.PracownikDao;
import com.example.thymeleaf.skierowanie.dao.SkierowanieDoLekarzaDao;
import com.example.thymeleaf.skierowanie.dto.SkierowanieDoLekarzaDTO;
import com.example.thymeleaf.skierowanie.model.Miejsce;
import com.example.thymeleaf.skierowanie.model.Pracownik;
import com.example.thymeleaf.skierowanie.model.SkierowanieDoLekarza;
import com.example.thymeleaf.skierowanie.service.SkierowanieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/skierowanie")
public class SkierowanieController {

    @Autowired
    SkierowanieDoLekarzaDao skierowanieDoLekarzaDao;

    @Autowired
    MiejsceDao miejsceDao;

    @Autowired
    PracownikDao pracownikDao;

    SkierowanieService service;

    public SkierowanieController(SkierowanieService service) {
        this.service = service;
    }

    @ResponseBody
    @GetMapping("/test")
    public List<SkierowanieDoLekarza> test() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return skierowanieDoLekarzaDao.findAllByPacjentAndTerminOrderById("Nekton", dateFormat.parse("2020-02-29"));
//        return skierowanieDoLekarzaDao.test();
    }

    @ResponseBody
    @GetMapping("/test1")
    public List<SkierowanieDoLekarza> test1() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return skierowanieDoLekarzaDao.test();
    }

    @ResponseBody
    @GetMapping("/test3")
    public List<SkierowanieDoLekarza> test3() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return skierowanieDoLekarzaDao.cwiczenie("Wiliam");
    }

    @ResponseBody
    @GetMapping("/test4")
    public Miejsce test4() throws ParseException {
        SkierowanieDoLekarza skierowanieDoLekarza = skierowanieDoLekarzaDao.findById(3).get();

        Miejsce miejsce = new Miejsce();
        miejsce.setAdres("Krakowsa 8");
        miejsce.setKodPocztowy("33-333");
        miejsce.setMiasto("Wulka");
        miejsce.setDyskoteka("Wuubuu");
        miejsce.getSkierowanieDoLekarzas().add(skierowanieDoLekarza);
        miejsce = miejsceDao.save(miejsce);

        List<Pracownik> pracowniks = new ArrayList<>();
        for(int i =0; i< 5; i++){
            Pracownik pracownik = new Pracownik();
            pracownik.setImie("Gunter" + i);
            pracownik = pracownikDao.save(pracownik);
            pracowniks.add(pracownik);
        }

        miejsce.setPracowniks(pracowniks);
        miejsce = miejsceDao.save(miejsce);

//        miejsce = miejsceDao.findById(miejsce.getId()).get();
//        miejsce.getSkierowanieDoLekarzas().size();

        return miejsce;
    }


    @GetMapping("/list") // /skierowanie/list -> list-skierowanie.html
    public String listSkierowanie(Model model) {
        model.addAttribute("skierowania", service.listSkierowanie());
        return "list-skierowanie";
    }

    @GetMapping("/{id}")
    public String getSkierowanie(@PathVariable Integer id, Model model) {
        SkierowanieDoLekarzaDTO skierowanieDoLekarza = service.getSkierowanie(id);
        model.addAttribute("skierowanie", skierowanieDoLekarza);
        return "get-skierowanie";
    }

    @GetMapping("/dodaj")
    public String dodajSkierowanie(Model model) {
        model.addAttribute("skierowanie", new SkierowanieDoLekarzaDTO());
        return "dodaj-skierowanie";
    }

    @PostMapping("/dodaj")
    public String stworzSkierowanie(
            @Valid
            @ModelAttribute SkierowanieDoLekarzaDTO skierowanieDoLekarza,
            BindingResult bindingResult,
            Model model
            ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("skierowanie", skierowanieDoLekarza);
            return "dodaj-skierowanie";
        }

        service.createSkierowanie(skierowanieDoLekarza);
        return "redirect:/skierowanie/list";
    }

    @GetMapping("/modyfikuj/{id}")
    public String modyfikujSkierowanie(@PathVariable Integer id, Model model) {
        model.addAttribute("skierowanie", service.getSkierowanie(id));
        return "modifikuj-skierowanie";
    }

    @PostMapping("/modyfikuj")
    public String updateSkierowanie(
            @Valid
            @ModelAttribute SkierowanieDoLekarzaDTO skierowanieDoLekarza,
            BindingResult bindingResult,
            Model model
    ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("skierowanie", skierowanieDoLekarza);
            return "modifikuj-skierowanie";
        }
        service.updateSkierowanie(skierowanieDoLekarza);
        return String.format("redirect:/skierowanie/%d", skierowanieDoLekarza.getId());
    }

    @GetMapping("/usun/{id}")
    public String usunSkierowanie(@PathVariable Integer id) {
        service.deleteSkierowanie(id);
        return "redirect:/skierowanie/list";
    }

}
