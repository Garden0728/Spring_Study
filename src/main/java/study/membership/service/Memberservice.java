package study.membership.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import study.membership.domain.Member;
import study.membership.repository.MemberRepository;

import java.util.Optional;

public interface Memberservice {
    public Member findMem(String ID, String NAME);

    public void DropMEM(Member member);

    public String join(Member member);

    public Optional<Member> Login(String memberID, String password);


}
