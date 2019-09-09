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

var Orcode = (_temp2 = _class = function (_BaseComponent) {
  _inherits(Orcode, _BaseComponent);

  function Orcode() {
    var _ref;

    var _temp, _this, _ret;

    _classCallCheck(this, Orcode);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, (_ref = Orcode.__proto__ || Object.getPrototypeOf(Orcode)).call.apply(_ref, [this].concat(args))), _this), _this.$usedState = ["$compid__25", "openId", "recommend", "src"], _this.config = {
      navigationBarTitleText: '我的推荐码'
    }, _this.customComponents = ["DocsHeader"], _temp), _possibleConstructorReturn(_this, _ret);
  }

  _createClass(Orcode, [{
    key: "_constructor",
    value: function _constructor(props) {
      _get(Orcode.prototype.__proto__ || Object.getPrototypeOf(Orcode.prototype), "_constructor", this).call(this, props);
      this.state = {
        openId: '',
        recommend: '',
        src: ""
      };
      this.$$refs = [];
    }
  }, {
    key: "componentWillMount",
    value: function componentWillMount() {}
  }, {
    key: "componentDidMount",
    value: function componentDidMount() {

      /* Taro.request({
         url:
           "http://127.0.0.1:9999/api/admin/partner/outFindByOpenId?openId="+this.state.openId,
         method: "GET",
         header: {
           Accept: "application/json, *!/!*",
           "Content-Type": "application/json"
         }
       }).then(res => {
         console.info(res.data.data);
         if(res.statusCode == 200){
           this.setState({
             recommend:res.data.data.recommend
           });
         }
       });*/
    }
  }, {
    key: "componentWillUnmount",
    value: function componentWillUnmount() {}
  }, {
    key: "componentDidShow",
    value: function componentDidShow() {
      var userInfo = _index2.default.getStorageSync('userInfo');
      this.setState({
        recommend: "推荐码:" + userInfo.recommend
      });
      this.setState({
        src: _api2.default.ImgPath + userInfo.recommend + ".png"
      });
      //设置url
    }
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
      var $compid__25 = (0, _index.genCompid)(__prefix + "$compid__25");
      _index.propsManager.set({
        "title": this.__state.recommend
      }, $compid__25);
      Object.assign(this.__state, {
        $compid__25: $compid__25
      });
      return this.__state;
    }
  }]);

  return Orcode;
}(_index.Component), _class.$$events = [], _class.$$componentPath = "pages/qrcode/qrcode", _temp2);
exports.default = Orcode;

Component(require('../../npm/@tarojs/taro-weapp/index.js').default.createComponent(Orcode, true));