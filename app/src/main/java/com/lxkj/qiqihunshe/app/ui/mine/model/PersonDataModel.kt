package com.lxkj.qiqihunshe.app.ui.mine.model

import com.lxkj.qiqihunshe.app.base.BaseModel

/**
 * Created by Slingge on 2019/3/13
 */
class PersonDataModel:BaseModel() {

    var zeou_type=""// 类型（择偶条件）
    var zeou_birthplace=""// 家乡（择偶条件）
    var zeou_residence=""// 现居（择偶条件）

    var zeou_height=""// 身高（择偶条件）
    var zeou_marriage=""// 情感状态 0未婚 1已婚 2离异（择偶条件）
    var zeou_education=""// 学历（择偶条件）

    var zeou_salary=""// 收入（择偶条件）
    var zeou_car=""// 车 0无车（择偶条件）
    var zeou_house=""// 房 0无房（择偶条件）

    var zeou_plan=""// 情感计划（择偶条件）
    var account=""// 七七账号
    var adtime=""// 注册日期

    var type=""// 类型
    var birthplace=""// 家乡
    var residence=""// 现居

    var height=""// 身高
    var age=""// 年龄
    var nation=""// 民族

    var marriage=""// 情感状态 0未婚 1已婚 2离异
    var job=""// 职业
    var plan=""// 情感计划

    var interest=ArrayList<String>()// 兴趣爱好
    var locale=ArrayList<String>()// 地点标签
    var match=""// 相符值

    var impression=""// 印象值
    var count=""// 评分人数
    var car=""// 车价

    var house=""// 房屋面积
    var education=""// 学历
    var salary=""// 月薪

    var introduction=""// 个性签名
    var nickname=""// 昵称
    var icon=ArrayList<String>()// 头像

    var realname=""// 真实姓名
    var birthday=""// 生日
    var sex=""// 性别 0女 1男

}