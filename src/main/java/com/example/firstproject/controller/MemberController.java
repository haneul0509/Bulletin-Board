package com.example.firstproject.controller;


import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@Slf4j

public class MemberController {
    @Autowired
    private MemberRepository Memberrepository;

    @GetMapping("/signup")
    public String newMemberform(){
        return "members/new";
    }

    @PostMapping("/join")
    public String createMember(MemberForm form){
        log.info(form.toString());

        //dto를 엔티티로
        Member member = form.toEntity();
        log.info(member.toString());

        //리퍼지토리사용
        Member saved = Memberrepository.save(member);
        log.info(saved.toString());
        return "redirect:/members/"+saved.getId();

    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = "+ id);
        Member memberEntity = Memberrepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model){
        ArrayList<Member> memberList = Memberrepository.findAll();
        model.addAttribute("memberList", memberList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Member target = Memberrepository.findById(id).orElse(null);
        log.info(target.toString());
        model.addAttribute("member",target);

        return "members/edit";
    }


    @PostMapping("/members/update")
    public String update(MemberForm form){
        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());

        Member target = Memberrepository.findById(memberEntity.getId()).orElse(null);
        if(target != null) {
            Memberrepository.save(memberEntity);
        }

        return "redirect:/members/" + memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rtts){
        Member target = Memberrepository.findById(id).orElse(null);

        if (target != null) {
            Memberrepository.delete(target);
            rtts.addFlashAttribute("msg","삭제되었습니당!");
        }

        return "redirect:/members";
    }




}
