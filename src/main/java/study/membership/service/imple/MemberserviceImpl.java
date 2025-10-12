package study.membership.service.imple;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.membership.controller.membercotroller.dto.SignupRequestDto;
import study.membership.domain.Member;
import study.membership.repository.MemberRepository;
import study.membership.service.AbstractMemberService;
import study.membership.service.ImageService;
import study.membership.service.Memberservice;

import java.io.IOException;
import java.util.Optional;

@Service

public class MemberserviceImpl extends AbstractMemberService implements Memberservice {

    private final BCryptPasswordEncoder passwordEncoder;
    private final ImageService imageService;
    @Autowired
    public MemberserviceImpl(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder,ImageService imageService) {
        super(memberRepository);
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }





    @Transactional
    @Override
    public String join(SignupRequestDto dto) throws IOException {

        //중복 회원 나중에 체크 해야함  //영속
        Member member = new Member();
            member.setId(dto.getID());
            member.setPassword(dto.getPassword());
            member.setName(dto.getName());
            String url = imageService.UpLoad(dto.getImagePath());
            member.setImagePath(url);
        findID(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        Member savedMember = memberRepository.save(member);
        return savedMember.getImagePath();
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
    public boolean isAdmin(Member member) {
        return member.isRole();
    }

}
