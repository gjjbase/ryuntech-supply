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

var Register = (_temp2 = _class = function (_BaseComponent) {
  _inherits(Register, _BaseComponent);

  function Register() {
    var _ref;

    var _temp, _this, _ret;

    _classCallCheck(this, Register);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, (_ref = Register.__proto__ || Object.getPrototypeOf(Register)).call.apply(_ref, [this].concat(args))), _this), _this.$usedState = ["$compid__3", "$compid__4", "$compid__5", "$compid__6", "$compid__7", "$compid__8", "$compid__9", "$compid__10", "$compid__11", "avatarUrl", "partnerId", "phone", "codeValue", "realValue", "companyValue", "checkedList", "isClose", "count", "btnDisable", "btnValue"], _this.config = {
      navigationBarTitleText: '注册',
      enablePullDownRefresh: false
    }, _this.customComponents = ["AtAvatar", "AtForm", "AtInput", "AtButton", "AtCheckbox"], _temp), _possibleConstructorReturn(_this, _ret);
  }

  _createClass(Register, [{
    key: "_constructor",
    value: function _constructor() {
      _get(Register.prototype.__proto__ || Object.getPrototypeOf(Register.prototype), "_constructor", this).apply(this, arguments);
      this.timer = null, this.state = {
        avatarUrl: '',
        partnerId: '',
        phone: '',
        codeValue: '',
        realValue: '',
        companyValue: '',
        checkedList: ['true'],
        isClose: true,
        count: 60,
        btnDisable: false,
        btnValue: '发送验证码'
      };
      this.checkboxOption = [{
        value: "list1"
      }];
      this.userInfos = {};
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
    value: function componentDidShow() {
      //从缓存获取用户头像
      try {
        var value = _index2.default.getStorageSync('userInfo');
        if (value) {
          //判断用户数据是否存在
          this.setState({
            avatarUrl: value.avatarUrl
          });
          this.setState({
            partnerId: value.partnerId
          });
        }
      } catch (e) {
        // Do something when catch error
      }
    }
  }, {
    key: "componentDidHide",
    value: function componentDidHide() {}

    //注册

  }, {
    key: "onSubmit",
    value: function onSubmit(event) {
      var _this2 = this;

      if (this.state.phone == "" || this.state.phone.length != 11) {
        _index2.default.showToast({ icon: 'none', title: "手机号错误" });
        return;
      }
      if (this.state.codeValue == "") {
        _index2.default.showToast({ icon: 'none', title: "验证码不能为空" });
        return;
      }
      if (this.state.realValue == "") {
        _index2.default.showToast({ icon: 'none', title: "真实姓名不能为空" });
        return;
      }
      if (this.state.companyValue == "") {
        _index2.default.showToast({ icon: 'none', title: "公司名不能为空" });
        return;
      }
      if (this.state.checkedList[0] != "true") {
        _index2.default.showToast({ icon: 'none', title: "请先同意协议" });
        return;
      }
      _index2.default.request({
        url: _api2.default.RegisterUpdateUser,
        method: "POST",
        data: {
          registerMobile: this.state.phone,
          codeValue: this.state.codeValue,
          partnerName: this.state.realValue,
          companyName: this.state.companyValue,
          partnerId: this.state.partnerId
        },
        header: {
          Accept: "application/json, */*",
          "Content-Type": "application/json"
        }
      }).then(function (res) {
        console.info(res);
        if (res.statusCode == 200) {
          if (res.data.tcode != 200) {
            _index2.default.showToast({ icon: 'none', title: res.data.msg });
            return;
          }
          if (res.data.data != undefined) {
            var value = _index2.default.getStorageSync('userInfo');
            console.info("res.data.data" + res.data.data);
            _this2.userInfos = {
              nickName: value.nickName,
              avatarUrl: value.avatarUrl,
              city: value.city,
              country: value.country,
              gender: value.gender,
              language: value.language,
              province: value.province,
              phone: res.data.data.registerMobile,
              openId: res.data.data.openId,
              status: res.data.data.status,
              recommend: res.data.data.recommend,
              partnerId: res.data.data.partnerId,
              partnerName: res.data.data.partnerName,
              companyName: res.data.data.companyName
            };
            _index2.default.setStorage({ key: 'userInfo', data: _this2.userInfos });
            _index2.default.reLaunch({ url: '../reorder/reorder' });
          } else {
            _index2.default.showToast({ icon: 'none', title: "注册异常" });
          }
        }
      }).catch(function (err) {
        console.info(err);
        _index2.default.showToast({ icon: 'none', title: "注册异常" });
      });
    }
  }, {
    key: "handleCheckboxChange",
    value: function handleCheckboxChange(value) {
      this.setState({
        checkedList: value
      });
    }
    // 倒计时过程事件

  }, {
    key: "timeTick",
    value: function timeTick() {
      var _this3 = this;

      //发送验证码
      console.log("-----sendCode");
      if (this.state.btnDisable) {
        _index2.default.showToast({ icon: 'none', title: "验证码已发送" }).then(function (r) {});
        return;
      }
      if (this.state.phone.length < 11) {
        _index2.default.showToast({ icon: 'none', title: "手机号错误" }).then(function (r) {});
        return;
      }
      this.setState({ btnDisable: true });
      _index2.default.request({
        url: _api2.default.SendCode + this.state.phone,
        method: "GET",
        header: {
          Accept: "application/json, */*",
          "Content-Type": "application/json"
        }
      }).then(function (res) {
        console.info(res);
        if (res.statusCode == 200) {
          if (res.data.data.code == "OK") {
            _this3.timer = setInterval(function () {
              _this3.state.count--;
              _this3.setState({ btnValue: _this3.state.count + "秒重新获取" });
              if (_this3.state.count <= 0) {
                _this3.setState({ btnValue: "发送验证码" });
                _this3.setState({ btnDisable: false });
                clearInterval(_this3.timer);
              }
            }, 1000);
          } else {
            _index2.default.showToast({ icon: 'none', title: "发送失败" });
          }
        } else {
          _index2.default.showToast({ icon: 'none', title: "发送失败" });
        }
      }).catch(function () {
        _index2.default.showToast({ icon: 'none', title: "发送异常" });
      });
    }
  }, {
    key: "handleTabsClick",
    value: function handleTabsClick(stateName, value) {
      this.setState(_defineProperty({}, stateName, value));
    }
  }, {
    key: "_createData",
    value: function _createData() {
      this.__state = arguments[0] || this.state || {};
      this.__props = arguments[1] || this.props || {};
      var __isRunloopRef = arguments[2];
      var __prefix = this.$prefix;
      ;
      var $compid__3 = (0, _index.genCompid)(__prefix + "$compid__3");
      var $compid__4 = (0, _index.genCompid)(__prefix + "$compid__4");
      var $compid__5 = (0, _index.genCompid)(__prefix + "$compid__5");
      var $compid__6 = (0, _index.genCompid)(__prefix + "$compid__6");
      var $compid__7 = (0, _index.genCompid)(__prefix + "$compid__7");
      var $compid__8 = (0, _index.genCompid)(__prefix + "$compid__8");
      var $compid__9 = (0, _index.genCompid)(__prefix + "$compid__9");
      var $compid__10 = (0, _index.genCompid)(__prefix + "$compid__10");
      var $compid__11 = (0, _index.genCompid)(__prefix + "$compid__11");
      _index.propsManager.set({
        "circle": true,
        "image": this.__state.avatarUrl,
        "size": "large"
      }, $compid__3);
      _index.propsManager.set({
        "border": false,
        "className": "meituan",
        "onSubmit": this.onSubmit.bind(this)
      }, $compid__4);
      _index.propsManager.set({
        "focus": true,
        "border": false,
        "type": "phone",
        "placeholder": "\u8BF7\u8F93\u5165\u624B\u673A\u53F7\u7801",
        "value": this.__state.phone,
        "onChange": this.handleTabsClick.bind(this, 'phone')
      }, $compid__5);
      _index.propsManager.set({
        "border": false,
        "clear": true,
        "type": "text",
        "maxLength": "4",
        "placeholder": "\u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801",
        "value": this.__state.codeValue,
        "onChange": this.handleTabsClick.bind(this, 'codeValue')
      }, $compid__6);
      _index.propsManager.set({
        "type": "primary",
        "size": "small",
        "value": this.__state.count,
        "onClick": this.timeTick.bind(this)
      }, $compid__7);
      _index.propsManager.set({
        "border": false,
        "name": "realName",
        "type": "text",
        "placeholder": "\u8BF7\u8F93\u5165\u771F\u5B9E\u59D3\u540D",
        "maxLength": "20",
        "value": this.__state.realValue,
        "onChange": this.handleTabsClick.bind(this, 'realValue')
      }, $compid__8);
      _index.propsManager.set({
        "border": false,
        "name": "companyName",
        "type": "text",
        "maxLength": "50",
        "placeholder": "\u8BF7\u8F93\u5165\u516C\u53F8\u540D",
        "value": this.__state.companyValue,
        "onChange": this.handleTabsClick.bind(this, 'companyValue')
      }, $compid__9);
      _index.propsManager.set({
        "border": false,
        "options": this.checkboxOption,
        "selectedList": this.__state.checkedList,
        "onChange": this.handleCheckboxChange.bind(this)
      }, $compid__10);
      _index.propsManager.set({
        "formType": "submit",
        "type": "primary"
      }, $compid__11);
      Object.assign(this.__state, {
        $compid__3: $compid__3,
        $compid__4: $compid__4,
        $compid__5: $compid__5,
        $compid__6: $compid__6,
        $compid__7: $compid__7,
        $compid__8: $compid__8,
        $compid__9: $compid__9,
        $compid__10: $compid__10,
        $compid__11: $compid__11
      });
      return this.__state;
    }
  }]);

  return Register;
}(_index.Component), _class.$$events = [], _class.$$componentPath = "pages/register/register", _temp2);
exports.default = Register;

Component(require('../../npm/@tarojs/taro-weapp/index.js').default.createComponent(Register, true));