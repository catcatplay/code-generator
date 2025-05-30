<#import "macro.ftl" as print>

<#macro greet index>
    System.out.println("hello,我是宏${index}");
</#macro>
package com;

/**
* 循环打印
* @author: ${author}
*/
public class loop {
    public static void main(String[] args) {
        for (int i = 0; i < ${loop}; i++) {
            System.out.println("hello, 我是第" + i + "次循环!");
            <@greet loop/>
            <@print.do_twice>System.out.println("${author}");</@print.do_twice>
        }
    <#if loop == 5>
        System.out.println("有五次循环!");
    </#if>
    }
}