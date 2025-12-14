package com.practice.kopringsecurityjwt.domain.member

import com.practice.kopringsecurityjwt.common.authority.TokenInfo
import com.practice.kopringsecurityjwt.common.dto.BaseResponse
import com.practice.kopringsecurityjwt.common.dto.CustomUser
import com.practice.kopringsecurityjwt.domain.member.dto.LoginDto
import com.practice.kopringsecurityjwt.domain.member.dto.MemberDtoRequest
import com.practice.kopringsecurityjwt.domain.member.dto.MemberDtoResponse
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    /*
    * 회원가입
    * */
    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.login(loginDto)
        return BaseResponse(data = tokenInfo)
    }

    /**
     * 내 정보 보기
     */
    @GetMapping("/info")
    fun searchMyInfo(): BaseResponse<MemberDtoResponse> {
        val userId = (SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUser)
            .userId
        val response = memberService.searchMyInfo(userId)
        return BaseResponse(data = response)
    }
}
