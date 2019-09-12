// pages/index/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    opacity:1,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面显示,第一次显示此页面才会调用的方法
   */
  onShow: function () {
    //调用图片渐变方法
    this.hide();
  },

  //图片渐变效果
  hide:function(){
    var vm=this
    //interval定时器
    var interval=setInterval(function(){
      if(vm.data.opacity>0){
        //清除interval,否则会一直往上加
        clearInterval(interval);
        vm.setData({opacity:vm.data.opacity-0.05});
        vm.hide();
      }
    },200);
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})