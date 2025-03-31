package study.membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.membership.domain.Member;
import study.membership.repository.MembeRepository;

import java.util.Optional;


@Transactional
@Service

public class Memberservice {


    private final MembeRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public Memberservice(MembeRepository memberRepository, BCryptPasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
    }

    @Transactional
    public String join(Member member) {

        //중복 회원 나중에 체크 해야함  //영속

        findID(member);
        member.setPassword(encoder.encode(member.getPassword()));
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    private void findID(Member member) {
        memberRepository.findById(member.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 있는 아이디");

                });

    }

    public Member findMem(String ID, String NAME) {
        Optional<Member> member = memberRepository.findMember(ID, NAME);
        if (member.isPresent()) {
            // 값이 있을 때 처리
            Member findMEM = member.get();

            return findMEM;
        } else {
            throw new IllegalStateException("입력하신 아이디 혹은 비밀번호를 확인하세요!.");
        }


    }

    public Optional<Member> Login(String memberID, String password) {
        return Optional.ofNullable(memberRepository.findById(memberID)
                .filter(member -> encoder.matches(password, member.getPassword()))
                .orElseThrow(() -> new IllegalStateException("입력하신 아이디 혹은 비밀번호를 확인하세요!.")));
    }
    public void DropMEM(Member member){

        memberRepository.delete(member);

    }


}
