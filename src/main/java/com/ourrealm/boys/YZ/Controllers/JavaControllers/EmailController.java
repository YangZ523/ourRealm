package com.ourrealm.boys.YZ.Controllers.JavaControllers;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理邮箱邮件controller
 * 原理：
 * ReceiveMail定时工具类，拉取邮箱中的邮件，确保时时获取最新的邮件存入数据库中
 */

@RequestMapping("/ReceiveMail")
public class EmailController {
}
