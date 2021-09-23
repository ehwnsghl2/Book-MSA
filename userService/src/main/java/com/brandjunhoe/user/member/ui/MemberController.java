package com.brandjunhoe.user.member.ui;

import com.brandjunhoe.user.member.application.MemberService;
import com.brandjunhoe.user.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RefreshScope
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Value("${server.port}")
    private int port;

    @Value("${spring.message}")
    private String message;

   /* @Value("${user.password}")
    private String password;*/

    @GetMapping("/user/detail")
    public String member() {
        return "User Detail - Port " + port + " - " + message;
    }

   /* @GetMapping("/member/password")
    public String memberPassword() {
        return "Member Password - " + password;
    }*/

    @GetMapping("/pay/detail")
    public String pay() {
        return "Pay Detail - Port " + port + " - " + message;
    }

    @GetMapping("/user/list")
    public List<Member> list(){
        return memberService.list();
    }

}
