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

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var logo = "/assets/images/person/logo.png";

var Login = (_temp2 = _class = function (_BaseComponent) {
  _inherits(Login, _BaseComponent);

  function Login() {
    var _ref;

    var _temp, _this, _ret;

    _classCallCheck(this, Login);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, (_ref = Login.__proto__ || Object.getPrototypeOf(Login)).call.apply(_ref, [this].concat(args))), _this), _this.$usedState = ["$compid__12", "$compid__13", "$compid__14", "$compid__15", "$compid__16", "$compid__17", "phone", "codeValue", "isClose", "count", "btnDisable", "btnValue", "basicInfo"], _this.config = {
      navigationBarTitleText: '登录',
      enablePullDownRefresh: false
    }, _this.getUserInfo = function (userInfo) {
      console.log('userinfo', userInfo);
      if (userInfo.detail.userInfo) {
        //同意
        _this.setState({
          basicInfo: userInfo.detail.userInfo
        }); //将用户信息存入redux
        _index2.default.setStorage({ key: 'userInfo', data: userInfo.detail.userInfo }).then(function (rst) {
          //将用户信息存入缓存中
          // Taro.navigateBack()
          console.log('userinfo', userInfo);
        });
      }
    }, _this.customComponents = ["AtAvatar", "AtForm", "AtInput", "AtButton"], _temp), _possibleConstructorReturn(_this, _ret);
  }

  _createClass(Login, [{
    key: "_constructor",
    value: function _constructor() {
      _get(Login.prototype.__proto__ || Object.getPrototypeOf(Login.prototype), "_constructor", this).apply(this, arguments);
      this.state = {
        phone: '',
        codeValue: '',
        isClose: false,
        count: 5,
        btnDisable: false,
        btnValue: '发送验证码',
        basicInfo: null
      };
      this.$$refs = [];
    }
  }, {
    key: "componentWillMount",
    value: function componentWillMount() {}
  }, {
    key: "componentDidMount",
    value: function componentDidMount() {
      try {
        var value = _index2.default.getStorageSync('userInfo');
        if (value) {
          _index2.default.reLaunch({
            url: '../reorder/reorder'
          });
        }
      } catch (e) {
        // Do something when catch error
      }
    }
  }, {
    key: "componentWillUnmount",
    value: function componentWillUnmount() {}
  }, {
    key: "componentDidShow",
    value: function componentDidShow() {}
  }, {
    key: "componentDidHide",
    value: function componentDidHide() {}

    //注册

  }, {
    key: "onSubmit",
    value: function onSubmit(event) {
      //
      _index2.default.reLaunch({
        url: '../reorder/reorder'
      });
    }
    // 倒计时过程事件

  }, {
    key: "timeTick",
    value: function timeTick(remainingSecond) {
      var _this2 = this;

      //发送验证码
      console.log("-----sendCode");
      if (this.state.phone.length < 11) {
        _index2.default.showToast({ icon: 'none', title: "手机号错误" }).then(function (r) {});
        return;
      }
      _index2.default.request({
        url: _api2.default.SendCode + this.state.phone,
        method: "GET",
        header: {
          Accept: "application/json, */*",
          "Content-Type": "application/json"
        },
        fail: function fail(e) {
          _index2.default.showToast({
            icon: 'none',
            title: "发送失败"
          });
        }
      }).then(function (res) {
        console.info(res);
        if (res.statusCode == 200) {
          setTimeout(function () {
            _this2.getRemainingSecond();
          }, 1000);
        } else {
          _index2.default.showToast({
            icon: 'none',
            title: "发送失败"
          });
        }
      });
    }
  }, {
    key: "handleTabsClick",
    value: function handleTabsClick(stateName, value) {
      this.setState(_defineProperty({}, stateName, value));
    }
  }, {
    key: "getRemainingSecond",
    value: function getRemainingSecond() {
      var remainingSecond = this.state.count;
      remainingSecond--;

      this.setState({ count: remainingSecond });
      this.setState({ btnValue: remainingSecond + "秒重新获取" });
      this.setState({ btnDisable: true });
      if (remainingSecond > 0 && !this.state.isClose) {
        this.timeTick(remainingSecond);
      } else {
        clearTimeout(this.timer);
        this.setState({
          btnValue: "发送验证码"
        });
        this.setState({
          btnDisable: false
        });
      }
    }
  }, {
    key: "_createData",
    value: function _createData() {
      this.__state = arguments[0] || this.state || {};
      this.__props = arguments[1] || this.props || {};
      var __isRunloopRef = arguments[2];
      var __prefix = this.$prefix;
      ;
      var $compid__12 = (0, _index.genCompid)(__prefix + "$compid__12");
      var $compid__13 = (0, _index.genCompid)(__prefix + "$compid__13");
      var $compid__14 = (0, _index.genCompid)(__prefix + "$compid__14");
      var $compid__15 = (0, _index.genCompid)(__prefix + "$compid__15");
      var $compid__16 = (0, _index.genCompid)(__prefix + "$compid__16");
      var $compid__17 = (0, _index.genCompid)(__prefix + "$compid__17");
      _index.propsManager.set({
        "circle": true,
        "image": logo,
        "size": "large"
      }, $compid__12);
      _index.propsManager.set({
        "border": false,
        "className": "meituan",
        "onSubmit": this.onSubmit.bind(this)
      }, $compid__13);
      _index.propsManager.set({
        "border": false,
        "type": "phone",
        "placeholder": "\u8BF7\u8F93\u5165\u624B\u673A\u53F7\u7801",
        "value": this.__state.phone,
        "onChange": this.handleTabsClick.bind(this, 'phone')
      }, $compid__14);
      _index.propsManager.set({
        "border": false,
        "clear": true,
        "type": "text",
        "maxLength": "4",
        "placeholder": "\u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801",
        "value": this.__state.codeValue,
        "onChange": this.handleTabsClick.bind(this, 'codeValue')
      }, $compid__15);
      _index.propsManager.set({
        "type": "primary",
        "disabled": this.__state.btnDisable,
        "size": "small",
        "value": this.__state.count,
        "onClick": this.timeTick.bind(this)
      }, $compid__16);
      _index.propsManager.set({
        "formType": "submit",
        "type": "primary"
      }, $compid__17);
      Object.assign(this.__state, {
        $compid__12: $compid__12,
        $compid__13: $compid__13,
        $compid__14: $compid__14,
        $compid__15: $compid__15,
        $compid__16: $compid__16,
        $compid__17: $compid__17
      });
      return this.__state;
    }
  }]);

  return Login;
}(_index.Component), _class.$$events = [], _class.$$componentPath = "pages/login/login", _temp2);
exports.default = Login;

Component(require('../../npm/@tarojs/taro-weapp/index.js').default.createComponent(Login, true));