package study.membership.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.membership.domain.Member;

import java.util.Optional;

@Repository
public interface SpringDataRepository extends JpaRepository<Member, String>,MemberRepository {



   @Query("SELECT m FROM Member m WHERE m.id = :id AND m.name = :name")
   Optional<Member> findMember(@Param("id") String id, @Param("name") String name);
}

