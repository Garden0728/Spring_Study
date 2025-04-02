package study.membership.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import study.membership.domain.Member;
import study.membership.repository.SpringDataRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Transactional

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Memberservice_test {

    @Autowired
    private Memberservice memberservice;

    @Autowired
    private SpringDataRepository memberRepository;
    @Autowired
    private  PasswordEncoder encoder;




    @Test
    void 회원가입() {


        Member member = new Member();
        member.setName("안도건f");
        member.setId("dksehrjs55");
        member.setPassword("skrr12");


        String saveId = memberservice.join(member);


        Optional<Member> findMemberOptional = memberRepository.findById(saveId);

        // 조회된 회원 정보 확인
        if (findMemberOptional.isPresent()) {
            Member findMember = findMemberOptional.get();
            System.out.println("저장된 회원 이름: " + findMember.getName());  // 출력
            assertThat(findMember.getName()).isEqualTo(member.getName());  // 비교
        } else {
            fail("회원 정보를 찾을 수 없습니다.");
        }
    }
    @Test
    void 로그인(){
         String ID = "dksehrjs";
         String paswowrd = "skrr12";

         Optional<Member> member_f = memberRepository.findById(ID);
         if(member_f.isPresent()){
             Member findmember = member_f.get();
              if(encoder.matches(paswowrd,findmember.getPassword())) {
                  System.out.println(findmember.getName());
              }
              else{
                  fail("회원 정보를 찾을 수 없습니다.");
              }


         }else {
         fail("회원 정보를 찾을 수 없습니다.");
        }




    }
}
