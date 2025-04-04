package study.membership.service.imple;

import org.springframework.stereotype.Service;
import study.membership.domain.Member;
import study.membership.repository.MemberRepository;
import study.membership.service.AbstractMemberService;
import study.membership.service.managerService;

import java.util.Optional;
@Service
public class ManagerServiceImpl extends AbstractMemberService implements managerService {

    public ManagerServiceImpl(MemberRepository memberRepository) {
        super(memberRepository);
    }
    @Override
    public void DropMEM(Member member) {

        memberRepository.delete(member);

    }
    @Override
    public Member findMem(String ID, String NAME) {
        Optional<Member> member = memberRepository.findMember(ID, NAME);
        if (member.isPresent()) {

            Member findMEM = member.get();

            return findMEM;
        } else {
            throw new IllegalStateException("회원정보를 찾을 수 없습니다.");
        }


    }
}
