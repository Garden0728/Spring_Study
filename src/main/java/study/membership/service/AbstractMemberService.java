package study.membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import study.membership.repository.MemberRepository;

public class AbstractMemberService {
     protected final MemberRepository memberRepository;
    @Autowired
    public AbstractMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
