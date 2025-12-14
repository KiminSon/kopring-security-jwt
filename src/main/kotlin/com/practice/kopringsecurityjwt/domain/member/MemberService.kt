package com.practice.kopringsecurityjwt.domain.member

import com.practice.kopringsecurityjwt.common.exception.InvalidInputException
import com.practice.kopringsecurityjwt.common.status.ROLE
import com.practice.kopringsecurityjwt.domain.member.dto.MemberDtoRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository
) {
    /*
    * 회원가입
    * */
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }

        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        val memberRole: MemberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }
}
