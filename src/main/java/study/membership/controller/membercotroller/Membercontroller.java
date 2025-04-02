package study.membership.controller.membercotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        return "createForm";
    }

    @PostMapping("/members/new")
    public String create(@ModelAttribute MemberForm form, Model model) {
        try {
            Member member = new Member();
            member.setId(form.getID());
            member.setPassword(form.getPassword());
            member.setName(form.getName());
            memberservice.join(member);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "이미 존재하는 아이디입니다.");
            return "createForm";
        }

    }

    @GetMapping("/Login")
    public String login() {
        return "Login";
    }

    @PostMapping("/Login")
    public String login(@RequestParam("ID") String ID, @RequestParam("PASS") String password, Model model) {
        try {

            Optional<Member> member = memberservice.Login(ID, password);

            return "redirect:/";


        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "아이디 혹은 비밀번호를 확인해주세요");
            return "Login";

        }
    }

    @GetMapping("/member_search")
    public String member_search() {
        return "member_search";
    }

    @PostMapping("/member_search")
    public String member_search(@RequestParam("ID") String ID, @RequestParam("NAME") String name, Model model) {
        try {
            Member member = memberservice.findMem(ID, name);

            model.addAttribute("member", member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "회원정보를 찾을 수 없습니다.");
            //  return "member_search";

        } finally {
            return "member_search";
        }
    }

    @PostMapping("/DROP")
    public String DROP(@ModelAttribute MemberForm form, RedirectAttributes redirectAttributes) {


        Member member = memberservice.findMem(form.getID(), form.getName());

        if (member == null || member.getId() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "삭제할 회원 정보가 없습니다.");
            return "member_search"; // 에러 메시지를 가지고 다시 조회 페이지로 이동
        }

        memberservice.DropMEM(member);
        redirectAttributes.addFlashAttribute("successMessage", "회원 삭제가 완료되었습니다.");
        return "redirect:/member_search";

    }


}
