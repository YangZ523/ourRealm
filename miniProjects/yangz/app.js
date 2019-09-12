//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now());

  },

  globalData: {
    userInfo: null,
    token:null,
    userPhone:null
  }
})