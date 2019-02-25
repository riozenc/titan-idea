import Axios from 'axios'
import VueAxios from 'vue-axios'
import I18n from '../plugin/i18n'

export default {
  init (Vue) {
    // 配置 axios
    Vue.use(VueAxios, Axios)

    // 配置 OAuth2.0
    Vue.prototype.$auth.config.appId = 'test'
    Vue.prototype.$auth.config.appSecret = 'test'

    // 设置后台请求地址前缀
    // Vue.axios.defaults.baseURL = process.env.SERVER_URL
    // Vue.prototype.$auth.config.baseUrl = process.env.BASE_URL
    // Vue.prototype.$auth.config.authUrl = process.env.AUTH_URL

    // build后配置   window.serverconf --->static/config.js
    Vue.axios.defaults.baseURL = window.serverconf[process.env.NODE_ENV]['baseURL']
    console.info("Vue.axios.defaults.baseURL:"+Vue.axios.defaults.baseURL)
    Vue.prototype.$auth.config.baseUrl = window.serverconf[process.env.NODE_ENV]['baseUrl']
    console.info("process:"+JSON.stringify(process))
    console.info("process.env:"+JSON.stringify(process.env))
    console.info("process.env.NODE_ENV:"+process.env.NODE_ENV)
    console.info("window.serverconf[process.env.NODE_ENV]['baseUrl']:"+window.serverconf[process.env.NODE_ENV]['baseUrl'])
    Vue.prototype.$auth.config.authUrl = window.serverconf[process.env.NODE_ENV]['authUrl']
    // 配置认证头
    Vue.axios.defaults.headers.common['Authorization'] = 'Bearer ' + Vue.prototype.$auth.token()
    console.info("Vue.prototype.$auth.token():"+Vue.prototype.$auth.token())
    // Vue.axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest'
    // Vue.axios.defaults.withCredentials = true
    // http 拦截器
    // Vue.axios.interceptors.request.use(
    //   config => {
    //     config.headers = {
    //       'Content-Type': 'application/x-www-form-urlencoded', // 设置很关键
    //       'Authorization': 'Bearer ' + Vue.prototype.$auth.token()
    //     }
    //     return config
    //   },
    //   err => {
    //     return Promise.reject(err)
    //   }
    // )
    Vue.axios.interceptors.response.use(function (response) {
      // Do something with response data
      return response
    }, function (error) {
      if (error && error.response) {
        switch (error.response.status) {
          case 403:
            Vue.prototype.$notify.error(I18n.messages[I18n.locale].message.DO_NOT_PERMISSION)
            break
          default:
            Vue.prototype.$notify.error(I18n.messages[I18n.locale].message.SERVER_ERROR)
        }
      } else {
        Vue.prototype.$notify.error(I18n.messages[I18n.locale].message.SERVER_ERROR)
      }
      console.error(error)
      // Do something with response error
      return Promise.reject(error)
    })
  }
}
