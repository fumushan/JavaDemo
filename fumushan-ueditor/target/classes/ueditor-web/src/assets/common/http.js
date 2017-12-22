import axios from 'axios'

const httpMethods = function (Vue) {

  Vue.prototype.apiGet = function (url) {
    return new Promise((resolve, reject) => {
      axios.get(url).then(
        response => {
          resolve(response);
        },
        error => {
          reject(error);
        }
      )
    })
  };

  Vue.prototype.apiPost = function (url, params) {
    return new Promise((resolve, reject) => {
      axios.post(url, params).then(
        response => {
          resolve(response);
        },
        error => {
          reject(error);
        }
      )
    })
  };

  Vue.prototype.apiDelete = function (url, data) {
    return new Promise((resolve, reject) => {
      axios.delete(url,data).then(
        (response) => {
          resolve(response)
        },
        (error) => {
          reject(error)
        }
      )
    })
  },

    Vue.prototype.apiPut = function (url, obj) {
      return new Promise((resolve, reject) => {
        axios.put(url, obj).then(
          (response) => {
            resolve(response)
          },
          (error) => {
            reject(error)
          }
        )
      })
    };

  /*存储数据*/
  Vue.prototype.saveStorageData = function (data) {
    window.sessionStorage.setItem("userInfo", JSON.stringify(data));//用户信息
    window.sessionStorage.setItem("roleInfo", JSON.stringify(data.roles));//角色信息
    window.sessionStorage.setItem("resourceInfo", JSON.stringify(data.resources));//权限列表
    window.sessionStorage.setItem("token", data.token);//权限认证
  };

  /*删除存储数据*/
  Vue.prototype.removeStorageData = function () {
    window.sessionStorage.removeItem("userInfo");//用户信息
    window.sessionStorage.removeItem("roleInfo");//角色信息
    window.sessionStorage.removeItem("resourceInfo");//权限列表
    window.sessionStorage.removeItem("token");//权限认证
  };

  /*判断是否拥有权限*/
  Vue.prototype.getHasRight = function (value) {
    let userInfo = JSON.parse(window.sessionStorage.getItem("userInfo"));
    if (userInfo.username == "admin") {
      return true;
    } else {
      let resourceInfo = JSON.parse(window.sessionStorage.getItem("resourceInfo"));
      return Array.prototype.includes(resourceInfo, value);
    }
  };


};

export default httpMethods
