package com.jing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration//代表是一个配置类  springSecurity的配置类
@EnableWebSecurity //开启SpringSecurity的默认行为
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用controller方法的安全注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    /**
//     * 操作配置 设置用户名和密码
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication()
////                .withUser("admin")
////                .password(new BCryptPasswordEncoder().encode("123456"))
////                .roles("");//登录之后具有的权限
//
//    }


    /**
     * 设置密码加密方式 上下一致
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    //设置允许iframe嵌套

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //允许iframe嵌套显示
        //http.headers().frameOptions().disable();
        //允许iframe显示
        http.headers().frameOptions().sameOrigin();

        //登录设置
        http.authorizeRequests().antMatchers("/static/**","/toLogin").permitAll()//允许不进行授权就可以访问的资源   !!!上面设置了哪些请求不需要验证,所以要放行登录请求
                .anyRequest().authenticated();//其他页面需要验证
        //设置登录页面
        http.formLogin().loginPage("/toLogin") //登录页面的地址,因为是thymeleaf页面所以需要通过请求来跳转 是请求地址
                .usernameParameter("username") //提交的参数 如果不写默认为username
                .passwordParameter("password") //提交的参数 如果不写默认为password
                .loginProcessingUrl("/login")  //提交表单处理请求的地址
                //.failureForwardUrl("/toLogin") //登录失败后跳转到哪里
                .defaultSuccessUrl("/"); // 登录成功跳转的页面地址
        //设置登出操作
        http.logout()
                .logoutUrl("/logout") //退出的路径
                .logoutSuccessUrl("/toLogin") //退出后要被重定向的url
                .invalidateHttpSession(true) //清空session中的数据
                .and()
                .csrf().disable(); // 关闭跨域请求伪造功能


        //添加自定义未授权处理器
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
