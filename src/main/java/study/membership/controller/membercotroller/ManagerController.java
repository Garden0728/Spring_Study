package study.membership.controller.membercotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import study.membership.controller.membercotroller.dto.MemberSearchRequestDto;
import study.membership.controller.membercotroller.dto.MemberSearchResponseDto;
import study.membership.domain.Member;
import study.membership.service.managerService;

@Controller
public class ManagerController {
    private managerService managerService;
    @Autowired
    public  ManagerController(managerService managerService){
        this.managerService = managerService;
    }
     @GetMapping("/member_search")
    public String member_search() {
        return "managnerhtml/member_search";
    }

    @PostMapping("/member_search")
    public String member_search(MemberSearchRequestDto dto, Model model) {
        try {
            Member member = managerService.findMem(dto.getID(), dto.getName());
            MemberSearchResponseDto memberSearchResponseDto = new MemberSearchResponseDto();
            memberSearchResponseDto.setID(member.getId());
            memberSearchResponseDto.setName(member.getName());

            model.addAttribute("member", memberSearchResponseDto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "회원정보를 찾을 수 없습니다.");
            //  return "member_search";

        } finally {
            return "managnerhtml/member_search";
        }
    }

    @PostMapping("/DROP")
    public String DROP( MemberSearchRequestDto dto, RedirectAttributes redirectAttributes) {


        Member member = managerService.findMem(dto.getID(), dto.getName());

        if (member == null || member.getId() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "삭제할 회원 정보가 없습니다.");
            return "redirect:/member_search"; // 에러 메시지를 가지고 다시 조회 페이지로 이동
        }

        managerService.DropMEM(member);
        redirectAttributes.addFlashAttribute("successMessage", "회원 삭제가 완료되었습니다.");
        return "redirect:/member_search";

    }



}
