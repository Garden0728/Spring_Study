package study.membership.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.membership.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository  {


         Optional<Member> findMember(String id, String name);
         Member save(Member member);
        Optional<Member> findById(String id);
        void delete(Member member);

}
