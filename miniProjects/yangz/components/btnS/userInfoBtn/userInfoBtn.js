//引入工具js
const util=require('../../../utils/util.js');

//获取应用实例
const app=getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    canIUse: wx.canIUse("button.open-type.getUserInfo"),
    navUrl:'/pages/index/index',
    code:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that=this;

    wx.login({
      success:function(res){
        if(res.code){
          that.setData({
            code: res.code
          });
          console.log("btn中获取信息：" + res.userInfo)
        }
      }
    });  

  },

  //通过微信定义的open-type为getuserinfo的button调用的方法，里面有e事件
  bindGetUserInfo:function(e){
    let that=this;
    //调用后台服务器
    if(that.data.code){
      //如果此页面在加载，调用login成功获取到code，则可以调用后台服务器，请求调接口,因为code是

    }
  }

})
