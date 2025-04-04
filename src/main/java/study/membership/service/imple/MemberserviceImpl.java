package study.membership.service.imple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.membership.domain.Member;
import study.membership.repository.MemberRepository;
import study.membership.service.AbstractMemberService;
import study.membership.service.Memberservice;

import java.util.Optional;
@Service
public class MemberserviceImpl extends AbstractMemberService implements Memberservice {

    private final BCryptPasswordEncoder passwordEncoder;

    public MemberserviceImpl(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        super(memberRepository);
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public void DropMEM(Member member) {

        memberRepository.delete(member);

    }

    @Transactional
    @Override
    public String join(Member member) {

        //중복 회원 나중에 체크 해야함  //영속

        findID(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    private void findID(Member member) {
        memberRepository.findById(member.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 있는 아이디");

                });

    }

    @Override
    public Optional<Member> Login(String memberID, String password) {
        return Optional.ofNullable(memberRepository.findById(memberID)
                .filter(member -> passwordEncoder.matches(password, member.getPassword()))
                .orElseThrow(() -> new IllegalStateException("입력하신 아이디 혹은 비밀번호를 확인하세요!.")));
    }

}
