"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _get = function get(object, property, receiver) { if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { return get(parent, property, receiver); } } else if ("value" in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } };

var _class, _temp2;

var _index = require("../../npm/@tarojs/taro-weapp/index.js");

var _index2 = _interopRequireDefault(_index);

var _api = require("../../servers/api.js");

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var logo = "/assets/images/person/logo.png";

var Auth = (_temp2 = _class = function (_BaseComponent) {
  _inherits(Auth, _BaseComponent);

  function Auth() {
    var _ref;

    var _temp, _this, _ret;

    _classCallCheck(this, Auth);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, (_ref = Auth.__proto__ || Object.getPrototypeOf(Auth)).call.apply(_ref, [this].concat(args))), _this), _this.$usedState = ["$compid__0"], _this.config = {
      navigationBarTitleText: '授权'
    }, _this.getUserInfo = function (userInfo) {
      if (userInfo.detail.userInfo) {
        //同意
        _this.setState({
          basicInfo: userInfo.detail.userInfo
        });
        _index2.default.showLoading({ title: "授权中" });
        //将用户信息存入redux
        _index2.default.setStorage({ key: 'userInfo', data: userInfo.detail.userInfo }).then(function (rst) {
          //将用户信息存入缓存中
          _index2.default.login().then(function (response) {
            return _index2.default.request({
              url: _api2.default.AuthLogin,
              method: 'POST',
              data: {
                codeValue: response.code,
                nickname: userInfo.detail.userInfo.nickName,
                sex: userInfo.detail.userInfo.gender,
                avatar: userInfo.detail.userInfo.avatarUrl,
                provinceNo: userInfo.detail.userInfo.province,
                city: userInfo.detail.userInfo.city
              }
            }).then(function (res) {
              if (res.statusCode == 200) {
                console.log("res" + res);
                _index2.default.hideLoading();
                //判断是否存在openidI
                if (res.data.data.openId == "") {
                  _index2.default.showToast({ title: '用户标识不存在，请重试!', icon: 'none' });
                } else {
                  var userInfos = _index2.default.getStorageSync('userInfo');
                  _this.userInfos = {
                    nickName: userInfos.nickName,
                    avatarUrl: userInfos.avatarUrl,
                    city: userInfos.city,
                    country: userInfos.country,
                    gender: userInfos.gender,
                    language: userInfos.language,
                    province: userInfos.province,
                    phone: res.data.data.registerMobile,
                    openId: res.data.data.openId,
                    status: res.data.data.status,
                    recommend: res.data.data.recommend,
                    partnerId: res.data.data.partnerId,
                    partnerName: res.data.data.partnerName,
                    companyName: res.data.data.companyName
                    /*     realName: '',
                         companyName: '',
                         phone: ''*/

                    // userInfo.detail.userInfo.operatorname = res.data.data.operatorname;
                  };_index2.default.setStorage({ key: 'userInfo', data: _this.userInfos });
                  console.info("userInfo" + JSON.stringify(_index2.default.getStorageSync('userInfo')));
                  console.info("registerMobile" + res.data.data.registerMobile);

                  if (res.data.data.registerMobile == "" || res.data.data.registerMobile == null) {
                    //不存在，去往注册页面
                    _index2.default.reLaunch({ url: '../register/register' });
                  } else {
                    _index2.default.reLaunch({ url: '../reorder/reorder' });
                  }
                }
              }
            });
          }).catch(function (err) {
            console.log(err);
            _index2.default.showToast({ title: '发生错误，请重试!', icon: 'none' });
          });
        });
      }
    }, _this.customComponents = ["AtAvatar"], _temp), _possibleConstructorReturn(_this, _ret);
  }

  _createClass(Auth, [{
    key: "_constructor",
    value: function _constructor() {
      _get(Auth.prototype.__proto__ || Object.getPrototypeOf(Auth.prototype), "_constructor", this).apply(this, arguments);
      this.state = {};
      this.userInfos = {
        nickName: "",
        avatarUrl: "",
        city: "",
        country: "",
        gender: "",
        language: "",
        province: "",
        phone: "",
        openId: "",
        status: "",
        recommend: "",
        partnerId: "",
        partnerName: "",
        companyName: ""
      };
      this.$$refs = [];
    }
  }, {
    key: "componentWillMount",
    value: function componentWillMount() {}
  }, {
    key: "componentDidMount",
    value: function componentDidMount() {}
  }, {
    key: "componentWillUnmount",
    value: function componentWillUnmount() {}
  }, {
    key: "componentDidShow",
    value: function componentDidShow() {}
  }, {
    key: "componentDidHide",
    value: function componentDidHide() {}
  }, {
    key: "_createData",
    value: function _createData() {
      this.__state = arguments[0] || this.state || {};
      this.__props = arguments[1] || this.props || {};
      var __isRunloopRef = arguments[2];
      var __prefix = this.$prefix;
      ;
      var $compid__0 = (0, _index.genCompid)(__prefix + "$compid__0");
      _index.propsManager.set({
        "circle": true,
        "image": logo,
        "size": "large"
      }, $compid__0);
      Object.assign(this.__state, {
        $compid__0: $compid__0
      });
      return this.__state;
    }
  }]);

  return Auth;
}(_index.Component), _class.$$events = ["getUserInfo"], _class.$$componentPath = "pages/auth/auth", _temp2);
exports.default = Auth;

Component(require('../../npm/@tarojs/taro-weapp/index.js').default.createComponent(Auth, true));