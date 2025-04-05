package study.membership.controller.membercotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.membership.controller.membercotroller.dto.LoginrequestDto;
import study.membership.controller.membercotroller.dto.SignupRequestDto;
import study.membership.domain.Member;
import study.membership.service.Memberservice;


import java.util.Optional;

@Controller
public class Membercontroller {

    private final Memberservice memberservice;
    @Autowired
    public Membercontroller(Memberservice memberservice) {
        this.memberservice = memberservice;
    }


    @GetMapping("/members/new")
    public String createForm() {
        return "memberhtml/createForm";
    }

    @PostMapping("/members/new")
  //  public String create(@ModelAttribute MemberForm form, Model model) {
    public String create(SignupRequestDto dto, Model model) {
        try {
            Member member = new Member();
            member.setId(dto.getID());
            member.setPassword(dto.getPassword());
            member.setName(dto.getName());
            memberservice.join(member);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "이미 존재하는 아이디입니다.");
            return "memberhtml/createForm";
        }

    }

    @GetMapping("/Login")
    public String login() {
        return "Login";
    }

    @PostMapping("/Login")
    //public String login(@RequestParam("ID") String ID, @RequestParam("PASS") String password, Model model)
    public String login(LoginrequestDto dto, Model model) {
        try {

            Optional<Member> member = memberservice.Login(dto.getID(), dto.getPassword());
            if(member.isPresent() && memberservice.isAdmin(member.get())){
                return "managnerhtml/managerHome";
            }

            return "redirect:/";


        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "아이디 혹은 비밀번호를 확인해주세요");
            return "Login";

        }
    }



}
