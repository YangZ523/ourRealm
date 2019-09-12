// pages/me/me.js

//获取app实例
const app = getApp();
const user=require('../../utils/user.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    showPopup: false,
    showPhonePopup: false,
    userInfo: {},
    canIUse: wx.canIUse("button.open-type.getUserInfo"),
    userPhone: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

//从缓存中获取用户的信息，token和手机号
    let userInfo = wx.getStorageSync("userInfo");
    let token = wx.getStorageSync("token");
    let userPhone = wx.getStorageSync("userPhone");

//判断，是否需要弹出授权框

    if (userInfo && token) {
      //如果缓存中有，把数据存到app对象中，全局对象定义

      console.log("缓存中已经有用户信息以及token信息");
      app.globalData.userInfo = userInfo; //保存至app实例中
      app.globalData.token = token;

      //赋值给本页数据中
      this.setData({
        userInfo: userInfo,
        showPopup: false //不需要弹框授权
      });

      // //先授权微信用户信息，再授权手机号
      // if (userPhone) {
      //   app.globalData.userPhone = userPhone;
      //   this.setData({
      //     userPhone: userPhone,
      //     showPhonePopup: false //不需要弹框手机号授权
      //   });
      // }else{
      //   this.setData({
      //     showPhonePopup:true//允许手机授权弹框弹出
      //   });
      // }

    }else{
      //缓存中没有用户信息和token

      this.setData({
        showPopup: true//允许弹出用户授权弹框
      });

    }
  },

  //引入的组件中的按钮调用方法，调用后台请求用户的相关详细信息
  bindGetUserInfo:function(e){
    console.log("me.js中的bingGetUserInfo方法获取到的事件详情:");
    console.log(e);
    //虽然只有缓存中没有数据才会能够按到这个页面的的按钮，但是还是判断一下，缓存中是否有数据，严谨曾上线
    let userInfo=wx.getStorageSync("userInfo");
    let token=wx.getStorageSync("token");
    if(userInfo && token){
      return;
    }

    //看来需要调用方法从新获取用户的相关信息咯，并从后台调接口，获取到token等相关信息
    if(e.detail.userInfo){//如果用户同意了

      //请求后台调用微信接口，获取对应的openid,以及token，将用户信息以及openid和对应的access_token保存到缓存中
      user.loginByWeixin(e.detail).then(res=>{
        console.log("调loginByWeixin方法成功，关弹窗，展示数据")
        //通过user.js中封装方法，调用后台，并请求微信接口，成功获取到相关信息后
        this.setData({
          showPopup:!this.data.showPopup,//关闭外部引入弹窗
          userInfo:res.data.userInfo,
        });

        //此前，在user.js中已经存入缓存，此处只需要赋值给全局变量
        app.globalData.userInfo=res.data.userInfo;
        app.globalData.token=res.data.token;

        /**
         * 继续判断缓存中是否有手机号，进而引导用户进行手机号的获取，但是个人小程序无法获取到用户的手机号，所以此处编写，但是不执行
         */
        // //验证手机号是否存在
        // let userPhone = wx.getStorageSync('userPhone');
        // //页面显示手机号弹框
        // if (userPhone) {
        //   app.globalData.userPhone = userPhone;
        //   this.setData({
        //     userPhone: app.globalData.userPhone,
        //     showPhonePopup: false
        //   });
        // } else {
        //   this.setData({
        //     showPhonePopup: true,
        //   });
        // }


      }).catch((err)=>{
        console.log(err);
      });

    }else{

      //用户拒绝了授权，跳转页面,跳转到首页这样的tab页面，需要用switchTab方法
      wx.switchTab({
        url: '../index/index',
      })

    }

  },

})