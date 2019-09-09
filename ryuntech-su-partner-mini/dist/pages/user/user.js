"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _get = function get(object, property, receiver) { if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { return get(parent, property, receiver); } } else if ("value" in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } };

var _class, _temp2; /**
                     * Created by Administrator on 2018/8/9 0009.
                     */


var _index = require("../../npm/@tarojs/taro-weapp/index.js");

var _index2 = _interopRequireDefault(_index);

var _api = require("../../servers/api.js");

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var myData = "/assets/images/person/my_data.png";
var myQrcode = "/assets/images/person/my_qrcode.png";
var partner_01 = "/assets/images/person/partner_01.png";

var User = (_temp2 = _class = function (_BaseComponent) {
  _inherits(User, _BaseComponent);

  function User() {
    var _ref;

    var _temp, _this, _ret;

    _classCallCheck(this, User);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, (_ref = User.__proto__ || Object.getPrototypeOf(User)).call.apply(_ref, [this].concat(args))), _this), _this.$usedState = ["$compid__18", "partner_01", "myData", "myQrcode", "avatarUrl", "nickName", "mobile", "status", "openId", "partner"], _this.config = {
      navigationBarTitleText: '我的',
      enablePullDownRefresh: false
    }, _this.customComponents = ["AtAvatar"], _temp), _possibleConstructorReturn(_this, _ret);
  }

  _createClass(User, [{
    key: "_constructor",
    value: function _constructor(props) {
      _get(User.prototype.__proto__ || Object.getPrototypeOf(User.prototype), "_constructor", this).call(this, props);
      this.state = {
        avatarUrl: '',
        nickName: '',
        mobile: '',
        status: '未审核',
        openId: '000',
        partner: {
          totalLending: "0.00",
          totalFeed: "0.00",
          totalNum: "0",
          totalOrderNum: "0",
          status: ""
        }
      };
      this.userInfo = {};
      this.$$refs = [];
    }
  }, {
    key: "componentWillMount",
    value: function componentWillMount() {
      console.info("componentWillMount");
    }
  }, {
    key: "componentWillUnmount",
    value: function componentWillUnmount() {
      console.info("componentWillUnmount");
    }
  }, {
    key: "componentDidShow",
    value: function componentDidShow() {
      var _this2 = this;

      console.info("componentDidShow");
      try {
        this.userInfo = _index2.default.getStorageSync('userInfo');
        if (this.userInfo.openId != null && this.userInfo.openId != "") {
          this.setState({
            avatarUrl: this.userInfo.avatarUrl
          });
          this.setState({
            nickName: this.userInfo.nickName
          });
          this.setState({
            openId: this.userInfo.openId
          });
          _index2.default.request({
            url: _api2.default.FindByOpenId + "?openId=" + this.userInfo.openId,
            method: "GET",
            header: {
              Accept: "application/json, */*",
              "Content-Type": "application/json"
            }
          }).then(function (res) {
            if (res.statusCode == 200) {
              console.info(res);
              _this2.setState({
                partner: res.data.data
              });
              var userInfos = _index2.default.getStorageSync('userInfo');
              _this2.userInfos = {
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
              };

              if (res.data.data.status == "00") {
                _this2.setState({ status: "未审核" });
              } else if (res.data.data.status == "01") {
                _this2.setState({ status: "审核通过" });
              }
              _index2.default.setStorage({ key: 'userInfo', data: _this2.userInfos });
            }
          });
        }
      } catch (e) {}
    }
  }, {
    key: "componentDidHide",
    value: function componentDidHide() {}
  }, {
    key: "componentDidMount",
    value: function componentDidMount() {
      console.info("componentDidMount");
    }
  }, {
    key: "navigateToDetail",
    value: function navigateToDetail(url) {
      if (this.state.partner.status == "00") {
        _index2.default.showToast({ icon: 'none', title: "请等待管理员审核" });
      } else if (this.state.partner.status == "01") {
        _index2.default.navigateTo({ url: url });
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
      var $compid__18 = (0, _index.genCompid)(__prefix + "$compid__18");
      _index.propsManager.set({
        "circle": true,
        "image": this.__state.avatarUrl,
        "size": "large"
      }, $compid__18);
      Object.assign(this.__state, {
        $compid__18: $compid__18,
        partner_01: partner_01,
        myData: myData,
        myQrcode: myQrcode
      });
      return this.__state;
    }
  }]);

  return User;
}(_index.Component), _class.$$events = ["navigateToDetail"], _class.$$componentPath = "pages/user/user", _temp2);
exports.default = User;

Component(require('../../npm/@tarojs/taro-weapp/index.js').default.createComponent(User, true));