package study.membership.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import study.membership.domain.Member;
import study.membership.repository.MembeRepository;
import study.membership.repository.springdataRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@SpringBootTest
//@Transactional

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Memberservice_test {

    @Autowired
    private Memberservice memberservice;

    @Autowired
    private MembeRepository membeRepository;
    @Autowired
    private  PasswordEncoder encoder;




    @Test
    void 회원가입() {


        Member member = new Member();
        member.setName("안도건f");
        member.setId("dksehrjs55");
        member.setPassword("skrr12");


        String saveId = memberservice.join(member);


        Optional<Member> findMemberOptional = memberservice.findOne(saveId);

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

         Optional<Member> member_f = membeRepository.findById(ID);
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
