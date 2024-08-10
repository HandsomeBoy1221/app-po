package com.ceshiren.member;

import com.ceshiren.entity.Member;
import com.ceshiren.page.AddMemberPage;
import com.ceshiren.page.MainPage;
import com.ceshiren.page.SearchResultPage;
import com.ceshiren.util.FakerUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AddMemberTest {
    AddMemberPage addMemberPage;

    @BeforeEach
    public void setUp() {
        addMemberPage = new MainPage()
                .toConcatPage()
                .toAddMemberPage()
                .click_AddMember();
    }

    @ParameterizedTest
    @MethodSource
    public void addMember(String text,int num) {
        System.out.println("text：" + text + ",num:" + num);
        String name = FakerUtil.get_name();
        String zh_phone = FakerUtil.get_zh_phone();
        SearchResultPage searchResultPage = addMemberPage
                .send_addMember(name, zh_phone)
                .toConcatPage()
                .toSearchPage()
                .send_Member(name);
        Member member = searchResultPage.member_Result();
        searchResultPage.toConcatPage();
        assertAll(
                () -> assertThat(member.getName(), is(equalTo(name))),
                () -> assertThat(member.getToast(), is(equalTo("添加成功")))
        );
    }

    public static Stream<Arguments> addMember(){
        return Stream.of(
                Arguments.arguments("111",1),
                Arguments.arguments("222",2)
        );
    }


    @AfterEach
    public void tearDown() {
        addMemberPage.quit();
    }
}
