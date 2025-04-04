package study.membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import study.membership.domain.Member;
import study.membership.repository.MemberRepository;

import java.util.Optional;

public abstract class AbstractMemberService {
     protected final MemberRepository memberRepository;

    @Autowired
    public AbstractMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;

    }

}
