package com.poly.hangnt169.controller;

import com.poly.hangnt169.entity.MayTinh;
import com.poly.hangnt169.repository.MayTinhRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/may-tinh/")
public class MayTinhController {

    @Autowired
    private MayTinhRepository mayTinhRepository;

    @GetMapping("hien-thi")
    public String getAll(@RequestParam(defaultValue = "0", name = "page") int number, Model model) {
        Pageable pageable = PageRequest.of(number, 5);
        Page<MayTinh> listMayTinh = mayTinhRepository.findAll(pageable);
        model.addAttribute("listMayTinh", listMayTinh);
        return "index";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable("id") String id, Model model) {
        MayTinh mayTinh = mayTinhRepository.findById(UUID.fromString(id)).orElse(null);
        Pageable pageable = PageRequest.of(0, 5);
        Page<MayTinh> listMayTinh = mayTinhRepository.findAll(pageable);
        model.addAttribute("mayTinh", mayTinh);
        model.addAttribute("listMayTinh", listMayTinh);
        return "detail";
    }

    @GetMapping("view-update/{id}")
    public String viewUpdate(@PathVariable("id") String id, Model model) {
        MayTinh mayTinh = mayTinhRepository.findById(UUID.fromString(id)).orElse(null);
        model.addAttribute("mayTinh", mayTinh);
        return "update";
    }

    @GetMapping("view-add")
    public String view() {
        return "add";
    }

    @PostMapping("add")
    public String addMayTinh(@RequestParam("ma") String ma, @RequestParam("ten") String ten,
                             @RequestParam("gia") String gia, @RequestParam("boNho") String boNho,
                             @RequestParam("mauSac") String mauSac, @RequestParam("hang") String hang,
                             @RequestParam("moTa") String moTa) {
        MayTinh mayTinh = MayTinh.builder()
                .ma(ma)
                .boNho(boNho)
                .ten(ten)
                .gia(Float.valueOf(gia))
                .hang(hang)
                .mauSac(mauSac)
                .mieuTa(moTa)
                .build();
        mayTinhRepository.save(mayTinh);
        return "redirect:/may-tinh/hien-thi";
    }

    @PostMapping("update")
    public String updateMayTinh(@RequestParam("id") String id, @RequestParam("ma") String ma, @RequestParam("ten") String ten,
                                @RequestParam("gia") String gia, @RequestParam("boNho") String boNho,
                                @RequestParam("mauSac") String mauSac, @RequestParam("hang") String hang,
                                @RequestParam("moTa") String moTa) {
        MayTinh mayTinh = MayTinh.builder()
                .ma(ma)
                .boNho(boNho)
                .ten(ten)
                .gia(Float.valueOf(gia))
                .hang(hang)
                .mauSac(mauSac)
                .mieuTa(moTa)
                .build();
        MayTinh findMayTinh = mayTinhRepository.findById(UUID.fromString(id)).orElse(null);
        mayTinh.setId(findMayTinh.getId());
        BeanUtils.copyProperties(mayTinh, findMayTinh);
        mayTinhRepository.save(findMayTinh);
        return "redirect:/may-tinh/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") String id) {
        mayTinhRepository.deleteById(UUID.fromString(id));
        return "redirect:/may-tinh/hien-thi";
    }

}
