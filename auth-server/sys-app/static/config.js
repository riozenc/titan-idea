/*
 在 index.html 引入
 2018-04-17
 */

window.serverconf = {
  appId: 'test',
  appSecret: 'test',
  production: {
    // nginx 代理
    authUrl: 'http://www.mxclass.cn/auth',
    baseURL: 'http://www.mxclass.cn',
    baseUrl: 'http://www.mxclass.cn'
  },
  development: {
    authUrl: 'http://127.0.0.1:18001/auth',
    baseURL: 'http://127.0.0.1:9911',
    baseUrl: 'http://127.0.0.1:8080'
  }
}
