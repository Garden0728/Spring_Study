package study.membership.service;

import study.membership.domain.Member;

public interface managerService {
    public Member findMem(String ID, String NAME);
    public void DropMEM(Member member);
}
