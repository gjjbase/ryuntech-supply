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

var app_status_1 = "/assets/images/order/app_status_1.png";
var app_status_2 = "/assets/images/order/app_status_2.png";
var order_02 = "/assets/images/order/order_02.png";
var order_03 = "/assets/images/order/order_03.png";
var order_04 = "/assets/images/order/order_04.png";
var order_05 = "/assets/images/order/order_05.png";

var Reorder = (_temp2 = _class = function (_BaseComponent) {
  _inherits(Reorder, _BaseComponent);

  function Reorder() {
    var _ref;

    var _temp, _this, _ret;

    _classCallCheck(this, Reorder);

    for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
      args[_key] = arguments[_key];
    }

    return _ret = (_temp = (_this = _possibleConstructorReturn(this, (_ref = Reorder.__proto__ || Object.getPrototypeOf(Reorder)).call.apply(_ref, [this].concat(args))), _this), _this.$usedState = ["loopArray2", "$compid__34", "app_status_1", "order_02", "order_03", "order_04", "order_05", "app_status_2", "onList", "orderIds", "expectData", "startIndex", "lastIndex", "pageCode", "totalPageCode", "pageSize", "offset", "value", "searchBarValue", "recommend"], _this.config = {
      navigationBarTitleText: "推荐订单",
      window: {
        enablePullDownRefresh: true,
        backgroundTextStyle: "light" //dark：显示刷新动画
      }
    }, _this.customComponents = ["AtSearchBar", "AtButton"], _temp), _possibleConstructorReturn(_this, _ret);
  }

  _createClass(Reorder, [{
    key: "_constructor",
    value: function _constructor(props) {
      _get(Reorder.prototype.__proto__ || Object.getPrototypeOf(Reorder.prototype), "_constructor", this).apply(this, arguments);
      this.state = {
        onList: [],
        orderIds: null,
        expectData: [],
        startIndex: 0,
        lastIndex: 0,
        //当前页
        pageCode: 1,
        //总页数
        totalPageCode: 0,
        //每页显示的条数
        pageSize: 10,
        offset: 0,
        value: '',
        searchBarValue: '',
        recommend: ""
      };
      this.userInfo = {
        recommend: ""
      };
      this.$$refs = [];
    }
    //获取初始化的数据

  }, {
    key: "getOrderOnList",
    value: function getOrderOnList() {
      var _this2 = this;

      _index2.default.showLoading({ title: "加载中" });
      _index2.default.request({
        url: _api2.default.OrderList + "?pageCode=" + this.state.pageCode + "&pageSize=" + this.state.pageSize,
        method: "POST",
        header: {
          Accept: "application/json, */*",
          "Content-Type": "application/json"
        },
        data: {
          memberName: this.state.searchBarValue,
          referralCode: this.userInfo.recommend
        }
      }).then(function (res) {

        if (res.statusCode == 200) {
          _index2.default.hideLoading();
          if (res.data.data.records != undefined) {
            _this2.setState({
              onList: res.data.data.records,
              totalPageCode: res.data.data.pages
            });
          }
        } else {
          _this2.setState({ onList: null });
        }
      }).catch(function () {
        _index2.default.hideLoading();
        _index2.default.showToast({ icon: 'none', title: "列表加载异常" });
      });
    }
  }, {
    key: "appendToList",
    value: function appendToList() {
      var _this3 = this;

      _index2.default.showLoading({ title: '加载中' });
      var url = _api2.default.OrderList;
      var pageCode = this.state.pageCode;
      var totalPageCode = this.state.totalPageCode;
      if (pageCode >= totalPageCode) {
        _index2.default.showToast({ title: '没有更多数据了', icon: 'success', duration: 2000 });
        return false;
      }
      for (var i = 0; i < 10; i++) {
        if (pageCode <= totalPageCode) {
          pageCode++;
        }
      }
      _index2.default.request({
        url: url + "?pageCode=" + pageCode + "&totalPageCode=" + totalPageCode,
        method: 'POST',
        header: {
          Accept: "application/json, */*",
          "Content-Type": "application/json"
        },
        data: {
          memberName: this.state.searchBarValue,
          referralCode: this.userInfo.recommend
        }
      }).then(function (res) {
        if (res.statusCode == 200) {
          _index2.default.hideLoading();
          _this3.setState({
            pageCode: pageCode
          });
          if (res.data.data.records != undefined) {
            _this3.setState({
              onList: _this3.state.onList.concat(res.data.records),
              totalPageCode: res.data.records.pages
            });
            _index2.default.showToast({ title: '没有更多数据了', icon: 'success', duration: 2000 });
          }
        }
      }).catch(function () {
        _index2.default.hideLoading();
        _index2.default.showToast({ icon: 'none', title: "列表加载异常" });
      });
    }
  }, {
    key: "componentDidMount",
    value: function componentDidMount() {
      this.getOrderOnList();
    }
  }, {
    key: "componentWillMount",
    value: function componentWillMount() {}
  }, {
    key: "componentWillUnmount",
    value: function componentWillUnmount() {}
  }, {
    key: "componentDidShow",
    value: function componentDidShow() {
      try {
        this.userInfo = _index2.default.getStorageSync('userInfo');
      } catch (e) {
        // Do something when catch error
        console.info(e);
      }
    }
  }, {
    key: "componentDidHide",
    value: function componentDidHide() {}
  }, {
    key: "onActionClick",
    value: function onActionClick() {
      var _this4 = this;

      console.log(this.state.searchBarValue);
      this.setState({ pageCode: 1 });
      _index2.default.showLoading({ title: "搜索中" });
      _index2.default.request({
        url: _api2.default.OrderList + "?pageCode=" + this.state.pageCode + "&pageSize=" + this.state.pageSize,
        method: "POST",
        data: {
          memberName: this.state.searchBarValue,
          referralCode: this.userInfo.recommend
        },
        header: {
          Accept: "application/json, */*",
          "Content-Type": "application/json"
        }
      }).then(function (res) {
        if (res.statusCode == 200) {
          _index2.default.hideLoading();
          if (res.data.data.records != undefined) {
            _this4.setState({
              onList: res.data.data.records,
              totalPageCode: res.data.data.pages
            });
          }
        } else {
          _this4.setState({ onList: null });
        }
      }).catch(function (err) {
        console.log(err);
      });
    }
  }, {
    key: "handleSearchBarChange",
    value: function handleSearchBarChange(stateName, value) {
      this.setState(_defineProperty({}, stateName, value));
    }
    /*
    发起结算
    */

  }, {
    key: "settlement",
    value: function settlement(stateName, value) {
      var _this5 = this;

      console.log("settlement");
      console.log("value" + value);
      console.log("stateName" + stateName);
      _index2.default.request({
        url: _api2.default.Settlement + "?orderid=" + stateName,
        method: "GET",
        header: {
          Accept: "application/json, */*",
          "Content-Type": "application/json"
        }
      }).then(function (res) {
        console.info(res);
        console.info(res.data.msg);
        if (res.statusCode == 200) {
          _index2.default.showToast({ icon: 'none', title: "发起结算成功" });
          _this5.setState({ pageCode: 1 });
          _this5.getOrderOnList();
        } else {
          _index2.default.showToast({ icon: 'none', title: "发起结算失败" });
        }
      }).catch(function () {
        _index2.default.showToast({ icon: 'none', title: "发起结算异常" });
      });
    }
  }, {
    key: "onPullDownRefresh",
    value: function onPullDownRefresh() {
      var _this6 = this;

      this.setState({ pageCode: 1 });
      _index2.default.request({
        url: _api2.default.OrderList + "?pageCode=" + this.state.pageCode + "&pageSize=" + this.state.pageSize,
        method: "POST",
        data: {
          memberName: this.state.searchBarValue,
          referralCode: this.userInfo.recommend
        },
        header: {
          Accept: "application/json, */*",
          "Content-Type": "application/json"
        }
      }).then(function (res) {
        if (res.statusCode == 200) {
          _index2.default.hideLoading();
          if (res.data.data.records != undefined) {
            _this6.setState({
              onList: res.data.data.records,
              totalPageCode: res.data.data.pages
            });
          }
        } else {
          _this6.setState({ onList: null });
        }
        _index2.default.stopPullDownRefresh();
      }).catch(function (err) {
        console.log(err);
        _index2.default.stopPullDownRefresh();
      });
    }
  }, {
    key: "_createData",
    value: function _createData() {
      var _this7 = this;

      this.__state = arguments[0] || this.state || {};
      this.__props = arguments[1] || this.props || {};
      var __isRunloopRef = arguments[2];
      var __prefix = this.$prefix;
      ;
      var $compid__34 = (0, _index.genCompid)(__prefix + "$compid__34");

      var loopArray2 = this.__state.onList.map(function (item, index) {
        item = {
          $original: (0, _index.internal_get_original)(item)
        };
        var $compid__33 = (0, _index.genCompid)(__prefix + "yWZsqtUesc" + index);
        _index.propsManager.set({
          "type": "primary",
          "size": "small",
          "onClick": _this7.settlement.bind(_this7, item.$original.orderid)
        }, $compid__33);
        return {
          $compid__33: $compid__33,
          $original: item.$original
        };
      });

      _index.propsManager.set({
        "actionName": "\u641C\u4E00\u4E0B",
        "value": this.__state.searchBarValue,
        "onChange": this.handleSearchBarChange.bind(this, 'searchBarValue'),
        "onActionClick": this.onActionClick.bind(this)
      }, $compid__34);
      Object.assign(this.__state, {
        loopArray2: loopArray2,
        $compid__34: $compid__34,
        app_status_1: app_status_1,
        order_02: order_02,
        order_03: order_03,
        order_04: order_04,
        order_05: order_05,
        app_status_2: app_status_2
      });
      return this.__state;
    }
  }]);

  return Reorder;
}(_index.Component), _class.$$events = ["appendToList"], _class.$$componentPath = "pages/reorder/reorder", _temp2);
exports.default = Reorder;

Component(require('../../npm/@tarojs/taro-weapp/index.js').default.createComponent(Reorder, true));