package study.membership.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import study.membership.controller.membercotroller.dto.SignupRequestDto;
import study.membership.domain.Member;
import study.membership.repository.MemberRepository;

import java.io.IOException;
import java.util.Optional;

public interface Memberservice {


    public String join(SignupRequestDto member) throws IOException;

    public Optional<Member> Login(String memberID, String password);

    public boolean isAdmin(Member member);

}
