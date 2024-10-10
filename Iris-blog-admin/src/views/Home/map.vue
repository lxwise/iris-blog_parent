<template>
  <div class="mb-2">
    <div
      ref="qualityMap"
      style="width: 100%; height: 660px;"
    ></div>
    <el-button
      type="primary"
      plain
      @click="backToTop"
      v-if="currentLevel != 0"
      class="el-button"
    >返回上一级
    </el-button
    >
  </div>
</template>

<script lang="ts" setup>
import * as echarts from 'echarts'
import chinaJson from '@/assets/map/china.json';
import china from '@/assets/map/china.json';
import worldJson from '@/assets/map/world.json';
import xinjiang from '@/assets/map/geometryProvince/xinjiang.json';
import shanghai from '@/assets/map/geometryProvince/shanghai.json';
import hebei from '@/assets/map/geometryProvince/hebei.json';
import shanxi from '@/assets/map/geometryProvince/shanxi.json';
import neimenggu from '@/assets/map/geometryProvince/neimenggu.json';
import liaoning from '@/assets/map/geometryProvince/liaoning.json';
import jilin from '@/assets/map/geometryProvince/jilin.json';
import heilongjiang from '@/assets/map/geometryProvince/heilongjiang.json';
import jiangsu from '@/assets/map/geometryProvince/jiangsu.json';
import zhejiang from '@/assets/map/geometryProvince/zhejiang.json';
import anhui from '@/assets/map/geometryProvince/anhui.json';
import fujian from '@/assets/map/geometryProvince/fujian.json';
import jiangxi from '@/assets/map/geometryProvince/jiangxi.json';
import shandong from '@/assets/map/geometryProvince/shandong.json';
import henan from '@/assets/map/geometryProvince/henan.json';
import hubei from '@/assets/map/geometryProvince/hubei.json';
import hunan from '@/assets/map/geometryProvince/hunan.json';
import guangdong from '@/assets/map/geometryProvince/guangdong.json';
import guangxi from '@/assets/map/geometryProvince/guangxi.json';
import hainan from '@/assets/map/geometryProvince/hainan.json';
import sichuan from '@/assets/map/geometryProvince/sichuan.json';
import guizhou from '@/assets/map/geometryProvince/guizhou.json';
import yunnan from '@/assets/map/geometryProvince/yunnan.json';
import xizang from '@/assets/map/geometryProvince/xizang.json';
import shanxi1 from '@/assets/map/geometryProvince/shanxi1.json';
import gansu from '@/assets/map/geometryProvince/gansu.json';
import qinghai from '@/assets/map/geometryProvince/qinghai.json';
import ningxia from '@/assets/map/geometryProvince/ningxia.json';
import beijing from '@/assets/map/geometryProvince/beijing.json';
import tianjin from '@/assets/map/geometryProvince/tianjin.json';
import chongqing from '@/assets/map/geometryProvince/chongqing.json';
import xianggang from '@/assets/map/geometryProvince/xianggang.json';
import aomen from '@/assets/map/geometryProvince/aomen.json';
import taiwan from '@/assets/map/geometryProvince/taiwan.json';
import * as GatewayLogApi from '@/api/gateway/log'

const files = import.meta.glob('/src/assets/map/geometryCouties/*.json')
defineOptions({name: 'HomeMap'})

// 根据 ID 获取 JSON 文件路径
function getJsonPath(id) {
  return `/src/assets/map/geometryCouties/${id}00.json`;
}

//地图数据
let jsonMap: any = {
  上海市: shanghai,
  河北: hebei,
  山西: shanxi,
  内蒙古: neimenggu,
  辽宁: liaoning,
  吉林: jilin,
  黑龙江: heilongjiang,
  江苏: jiangsu,
  浙江: zhejiang,
  安徽: anhui,
  福建: fujian,
  江西: jiangxi,
  山东: shandong,
  河南: henan,
  湖北: hubei,
  湖南: hunan,
  广东: guangdong,
  广西: guangxi,
  海南: hainan,
  四川: sichuan,
  贵州: guizhou,
  云南: yunnan,
  西藏: xizang,
  陕西: shanxi1,
  甘肃: gansu,
  青海: qinghai,
  宁夏: ningxia,
  新疆: xinjiang,
  北京市: beijing,
  天津市: tianjin,
  重庆市: chongqing,
  香港: xianggang,
  澳门: aomen,
  台湾: taiwan,
}


let currentLevel = ref(0) //0-全球 1 全国下省份, 2 省份下市区  3 县区
//当前点击的层级
let currentClick: any = null

let currentCityList = []
const qualityMap = ref()
let chart: any = ref()

//返回上一级
const backToTop = () => {
  if (currentLevel.value == 0) {
    return
  }
  if (currentLevel.value == 3) {
    initEcharts()
  }
  currentLevel.value -= 1
  initEcharts()
}
// 全球地域中文映射
let nameMap = {
  Canada: '加拿大',
  Turkmenistan: '土库曼斯坦',
  'Saint Helena': '圣赫勒拿',
  'Lao PDR': '老挝',
  Lithuania: '立陶宛',
  Cambodia: '柬埔寨',
  Ethiopia: '埃塞俄比亚',
  'Faeroe Is.': '法罗群岛',
  Swaziland: '斯威士兰',
  Palestine: '巴勒斯坦',
  Belize: '伯利兹',
  Argentina: '阿根廷',
  Bolivia: '玻利维亚',
  Cameroon: '喀麦隆',
  'Burkina Faso': '布基纳法索',
  Aland: '奥兰群岛',
  Bahrain: '巴林',
  'Saudi Arabia': '沙特阿拉伯',
  'Fr. Polynesia': '法属波利尼西亚',
  'Cape Verde': '佛得角',
  'W. Sahara': '西撒哈拉',
  Slovenia: '斯洛文尼亚',
  Guatemala: '危地马拉',
  Guinea: '几内亚',
  'Dem. Rep. Congo': '刚果（金）',
  Germany: '德国',
  Spain: '西班牙',
  Liberia: '利比里亚',
  Netherlands: '荷兰',
  Jamaica: '牙买加',
  'Solomon Is.': '所罗门群岛',
  Oman: '阿曼',
  Tanzania: '坦桑尼亚',
  'Costa Rica': '哥斯达黎加',
  'Isle of Man': '曼岛',
  Gabon: '加蓬',
  Niue: '纽埃',
  Bahamas: '巴哈马',
  'New Zealand': '新西兰',
  Yemen: '也门',
  Jersey: '泽西岛',
  Pakistan: '巴基斯坦',
  Albania: '阿尔巴尼亚',
  Samoa: '萨摩亚',
  'Czech Rep.': '捷克',
  'United Arab Emirates': '阿拉伯联合酋长国',
  Guam: '关岛',
  India: '印度',
  Azerbaijan: '阿塞拜疆',
  'N. Mariana Is.': '北马里亚纳群岛',
  Lesotho: '莱索托',
  Kenya: '肯尼亚',
  Belarus: '白俄罗斯',
  Tajikistan: '塔吉克斯坦',
  Turkey: '土耳其',
  Afghanistan: '阿富汗',
  Bangladesh: '孟加拉国',
  Mauritania: '毛里塔尼亚',
  'Dem. Rep. Korea': '朝鲜',
  'Saint Lucia': '圣卢西亚',
  'Br. Indian Ocean Ter.': '英属印度洋领地',
  Mongolia: '蒙古',
  France: '法国',
  'Cura?ao': '库拉索岛',
  'S. Sudan': '南苏丹',
  Rwanda: '卢旺达',
  Slovakia: '斯洛伐克',
  Somalia: '索马里',
  Peru: '秘鲁',
  Vanuatu: '瓦努阿图',
  Norway: '挪威',
  Malawi: '马拉维',
  Benin: '贝宁',
  'St. Vin. and Gren.': '圣文森特和格林纳丁斯',
  Korea: '韩国',
  Singapore: '新加坡',
  Montenegro: '黑山共和国',
  'Cayman Is.': '开曼群岛',
  Togo: '多哥',
  China: '中国',
  'Heard I. and McDonald Is.': '赫德岛和麦克唐纳群岛',
  Armenia: '亚美尼亚',
  'Falkland Is.': '马尔维纳斯群岛（福克兰）',
  Ukraine: '乌克兰',
  Ghana: '加纳',
  Tonga: '汤加',
  Finland: '芬兰',
  Libya: '利比亚',
  'Dominican Rep.': '多米尼加',
  Indonesia: '印度尼西亚',
  Mauritius: '毛里求斯',
  'Eq. Guinea': '赤道几内亚',
  Sweden: '瑞典',
  Vietnam: '越南',
  Mali: '马里',
  Russia: '俄罗斯',
  Bulgaria: '保加利亚',
  'United States': '美国',
  Romania: '罗马尼亚',
  Angola: '安哥拉',
  Chad: '乍得',
  'South Africa': '南非',
  Fiji: '斐济',
  Liechtenstein: '列支敦士登',
  Malaysia: '马来西亚',
  Austria: '奥地利',
  Mozambique: '莫桑比克',
  Uganda: '乌干达',
  Japan: '日本',
  Niger: '尼日尔',
  Brazil: '巴西',
  Kuwait: '科威特',
  Panama: '巴拿马',
  Guyana: '圭亚那',
  Madagascar: '马达加斯加',
  Luxembourg: '卢森堡',
  'American Samoa': '美属萨摩亚',
  Andorra: '安道尔',
  Ireland: '爱尔兰',
  Italy: '意大利',
  Nigeria: '尼日利亚',
  'Turks and Caicos Is.': '特克斯和凯科斯群岛',
  Ecuador: '厄瓜多尔',
  'U.S. Virgin Is.': '美属维尔京群岛',
  Brunei: '文莱',
  Australia: '澳大利亚',
  Iran: '伊朗',
  Algeria: '阿尔及利亚',
  'El Salvador': '萨尔瓦多',
  "C?te d'Ivoire": '科特迪瓦',
  Chile: '智利',
  'Puerto Rico': '波多黎各',
  Belgium: '比利时',
  Thailand: '泰国',
  Haiti: '海地',
  Iraq: '伊拉克',
  'S?o Tomé and Principe': '圣多美和普林西比',
  'Sierra Leone': '塞拉利昂',
  Georgia: '格鲁吉亚',
  Denmark: '丹麦',
  Philippines: '菲律宾',
  'S. Geo. and S. Sandw. Is.': '南乔治亚岛和南桑威奇群岛',
  Moldova: '摩尔多瓦',
  Morocco: '摩洛哥',
  Namibia: '纳米比亚',
  Malta: '马耳他',
  'Guinea-Bissau': '几内亚比绍',
  Kiribati: '基里巴斯',
  Switzerland: '瑞士',
  Grenada: '格林纳达',
  Seychelles: '塞舌尔',
  Portugal: '葡萄牙',
  Estonia: '爱沙尼亚',
  Uruguay: '乌拉圭',
  'Antigua and Barb.': '安提瓜和巴布达',
  Lebanon: '黎巴嫩',
  Uzbekistan: '乌兹别克斯坦',
  Tunisia: '突尼斯',
  Djibouti: '吉布提',
  Greenland: '格陵兰',
  'Timor-Leste': '东帝汶',
  Dominica: '多米尼克',
  Colombia: '哥伦比亚',
  Burundi: '布隆迪',
  'Bosnia and Herz.': '波斯尼亚和黑塞哥维那',
  Cyprus: '塞浦路斯',
  Barbados: '巴巴多斯',
  Qatar: '卡塔尔',
  Palau: '帕劳',
  Bhutan: '不丹',
  Sudan: '苏丹',
  Nepal: '尼泊尔',
  Micronesia: '密克罗尼西亚',
  Bermuda: '百慕大',
  Suriname: '苏里南',
  Venezuela: '委内瑞拉',
  Israel: '以色列',
  'St. Pierre and Miquelon': '圣皮埃尔和密克隆群岛',
  'Central African Rep.': '中非',
  Iceland: '冰岛',
  Zambia: '赞比亚',
  Senegal: '塞内加尔',
  'Papua New Guinea': '巴布亚新几内亚',
  'Trinidad and Tobago': '特立尼达和多巴哥',
  Zimbabwe: '津巴布韦',
  Jordan: '约旦',
  Gambia: '冈比亚',
  Kazakhstan: '哈萨克斯坦',
  Poland: '波兰',
  Eritrea: '厄立特里亚',
  Kyrgyzstan: '吉尔吉斯斯坦',
  Montserrat: '蒙特塞拉特',
  'New Caledonia': '新喀里多尼亚',
  Macedonia: '马其顿',
  Paraguay: '巴拉圭',
  Latvia: '拉脱维亚',
  Hungary: '匈牙利',
  Syria: '叙利亚',
  Honduras: '洪都拉斯',
  Myanmar: '缅甸',
  Mexico: '墨西哥',
  Egypt: '埃及',
  Nicaragua: '尼加拉瓜',
  Cuba: '古巴',
  Serbia: '塞尔维亚',
  Comoros: '科摩罗',
  'United Kingdom': '英国',
  'Fr. S. Antarctic Lands': '南极洲',
  Congo: '刚果（布）',
  Greece: '希腊',
  'Sri Lanka': '斯里兰卡',
  Croatia: '克罗地亚',
  Botswana: '博茨瓦纳',
  'Siachen Glacier': '锡亚琴冰川地区',
  'São Tomé and Principe': '圣多美及普林西比',
}

//地图展示的数据
let showData = [
  {name: '北京市', value: 9999},
  {name: '中国', value: 9999},
  {name: 'China', value: 9999},
  {name: '天津市', value: Math.round(Math.random() * 100)},
  {name: '上海', value: Math.round(Math.random() * 100)},
  {name: '重庆市', value: Math.round(Math.random() * 100)},
  {name: '河北', value: Math.round(Math.random() * 100)},
  {name: '河南', value: Math.round(Math.random() * 100)},
  {name: '云南', value: Math.round(Math.random() * 100)},
  {name: '辽宁', value: Math.round(Math.random() * 100)},
  {name: '黑龙江', value: Math.round(Math.random() * 100)},
  {name: '湖南', value: Math.round(Math.random() * 100)},
  {name: '安徽', value: Math.round(Math.random() * 100)},
  {name: '山东', value: Math.round(Math.random() * 100)},
  {name: '新疆', value: Math.round(Math.random() * 100)},
  {name: '江苏', value: Math.round(Math.random() * 100)},
  {name: '浙江', value: Math.round(Math.random() * 100)},
  {name: '江西', value: Math.round(Math.random() * 100)},
  {name: '湖北', value: Math.round(Math.random() * 100)},
  {name: '广西', value: Math.round(Math.random() * 100)},
  {name: '甘肃', value: Math.round(Math.random() * 100)},
  {name: '山西', value: Math.round(Math.random() * 100)},
  {name: '内蒙古', value: Math.round(Math.random() * 100)},
  {name: '陕西', value: Math.round(Math.random() * 100)},
  {name: '吉林', value: Math.round(Math.random() * 100)},
  {name: '福建', value: Math.round(Math.random() * 100)},
  {name: '贵州', value: Math.round(Math.random() * 100)},
  {name: '广东', value: Math.round(Math.random() * 100)},
  {name: '青海', value: Math.round(Math.random() * 100)},
  {name: '西藏', value: Math.round(Math.random() * 100)},
  {name: '四川', value: Math.round(Math.random() * 100)},
  {name: '宁夏', value: Math.round(Math.random() * 100)},
  {name: '海南', value: Math.round(Math.random() * 100)},
  {name: '台湾', value: Math.round(Math.random() * 100)},
  {name: '香港', value: Math.round(Math.random() * 100)},
  {name: '澳门', value: Math.round(Math.random() * 100)},
  {name: '成都市', value: 9999},
  {name: '马祖', value: 9999},
  {name: '台湾', value: 9999},
  {name: '那曲地区', value: 9999},
]
//请求本地的数据
const requestJsonData = (id: any) => {
  // 根据名字来匹配地图数据
  return jsonMap[currentClick]
}

const initEcharts = async () => {
  let regionsList = []
//   在点击一级地图时，将一级地图的地图绘制清空。重新绘制 就可以每次都居中显示
  chart.clear()

  //全球
  if (currentLevel.value == 0) {
    regionsList = worldJson.features.map((item) => ({
      name: item.properties.name,
      value: 1500,
      type: '全球',
      itemStyle: {
        color: 'rgba(222, 224, 232, 1)',
        areaColor: 'rgba(222, 224, 232, 1)',
      },
    })) //地图上要显示的区域以及颜色

    let outProvince = [] //数组定义在这里, 可以在地图上去除部分省份 =》 ['江苏', '江西']
    let objMap = {
      ...worldJson,
      features: worldJson.features.filter(
        (item) => !outProvince.includes(item.properties.name)
      ), //这里可以过滤不需要展示省份，可以和regionsList联动使用
    }
    echarts.registerMap('worldJson', objMap) //chinaJSon自定义名称，但是无法显示右下角南海诸岛， 可以设置成china
  }

  //全国下省份
  if (currentLevel.value == 1) {
    regionsList = chinaJson.features.map((item) => ({
      name: item.properties.name,
      value: 1500,
      type: '省份',
      itemStyle: {
        color: 'rgba(222, 224, 232, 1)',
        areaColor: 'rgba(222, 224, 232, 1)',
      },
    })) //地图上要显示的区域以及颜色

    let outProvince = [] //数组定义在这里, 可以在地图上去除部分省份 =》 ['江苏', '江西']
    let objMap = {
      ...chinaJson,
      features: chinaJson.features.filter(
        (item) => !outProvince.includes(item.properties.name)
      ), //这里可以过滤不需要展示省份，可以和regionsList联动使用
    }
    echarts.registerMap('china', objMap) //chinaJSon自定义名称，但是无法显示右下角南海诸岛， 可以设置成china
  }

  //省份下市区
  if (currentLevel.value == 2) {
    let {id} = china.features.filter(
      (item) => currentClick == item.properties.name
    )[0]?.properties
    //找出  @/map/china.json 里面内蒙古id  同事在此路径geometryProvince文件下  id.json就是对应该省份对应的市级数据
    let cityList = await requestJsonData(id)

    regionsList = cityList.features.map((item) => ({
      name: item.properties.name,
      type: '城市',
      itemStyle: {
        color: 'rgba(222, 224, 232, 1)',
        areaColor: 'rgba(222, 224, 232, 1)',
      },
    }))
    currentCityList = cityList
    let outCityList = [] //剔除不需要城市
    let objMap = {
      ...china,
      features: cityList.features.filter(
        (item) => !outCityList.includes(item.properties.name)
      ), //这里可以过滤不需要展示城市，可以和regionsList联动使用
    }
    echarts.registerMap('chinaJson', objMap) //chinaJSon自定义名称，但是无法显示右下角南海诸岛， 可以设置成china
  }

// 市区下县区
  if (currentLevel.value == 3) {
    let foundCity = currentCityList.features.find((item) => currentClick == item.properties.name);
    if (!foundCity) {
      console.error("City not found:", currentClick);
      return; // 可能需要处理城市不存在的情况
    }

    let {id} = foundCity.properties;
    const objMap = await loadMapData(id);
    echarts.registerMap('cityRegion', objMap);
  }

// 异步加载地图数据的方法
  async function loadMapData(id) {
    const jsonPath = getJsonPath(id);
    if (files[jsonPath]) {
      try {
        const module = await files[jsonPath]();
        const areaList = module.default;

        // 处理地图数据
        let regionsList = areaList.features.map((item) => ({
          name: item.properties.name,
          type: '县区',
          itemStyle: {
            color: 'rgba(222, 224, 232, 1)',
            areaColor: 'rgba(222, 224, 232, 1)'
          }
        }));

        let outAreaList = []; // 剔除不需要县区
        let objMap = {
          ...areaList,
          features: areaList.features.filter((item) => !outAreaList.includes(item.properties.name))
        };

        return objMap; // 返回处理好的地图数据对象
      } catch (error) {
        console.error("Error loading JSON file:", error);
        throw error; // 抛出异常以便上层处理
      }
    } else {
      console.error("JSON file not found:", jsonPath);
      throw new Error("JSON file not found"); // 抛出异常以便上层处理
    }
  }


  /*随机颜色数组*/
  let colorArray = [
    '#f21347', '#f3243e', '#f33736', '#f34131', '#f34e2b',
    '#f56321', '#f56f1c', '#f58414', '#f58f0e', '#f5a305',
    '#e7ab0b', '#dfae10', '#d5b314', '#c1bb1f', '#b9be23',
    '#a6c62c', '#96cc34', '#89d23b', '#7ed741', '#77d64c',
    '#71d162', '#6bcc75', '#65c78b', '#5fc2a0', '#5abead',
    '#52b9c7', '#4fb6d2', '#4ab2e5'
  ];

  /*随机线条颜色*/
  function randomColor() {
    return colorArray[Math.floor(Math.random() * colorArray.length)];
  }

  /*
  地图配置
  * */
  let option = {
    tooltip: {
      show: true,
      triggerOn: 'mousemove',
      formatter: function (params, ticket, callback) {
        let value = params?.data?.value || 0;
        return value;
      },
    },
    visualMap: {
      min: 0,
      max: 1400,
      left: 'left',
      top: 'bottom',
      text: ['高', '低'], //取值范围的文字
      inRange: {
        color: ['#aeeeee', '#006edd'], //取值范围的颜色
      },
      show: false,
    },
    geo: {
      map:
        currentLevel.value == 0
          ? 'worldJson'
          : currentLevel.value == 1
            ? 'china'
            : currentLevel.value == 2
              ? 'chinaJson'
              : currentLevel.value == 3
                ? 'cityRegion'
                : '',
      label: {
        show: currentLevel.value != 0,
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '20',
          fontWeight: 'bold',
        },
      },
      zoom: 1.2,
      roam: currentLevel.value == 0 ? true : true,
      scaleLimit: {
        // 缩放的极限控制
        min: 0.5,
        max: 4,
      },
      data: showData,
      nameMap: currentLevel.value == 0 ? nameMap : {},
      itemStyle: {
        normal: {
          areaColor: '#aeeeee',
        },
      },
    },
    series: [
      {
        type: 'map',
        map:
          currentLevel.value == 0
            ? 'worldJson'
            : currentLevel.value == 1
              ? 'china'
              : currentLevel.value == 2
                ? 'chinaJson'
                : currentLevel.value == 3
                  ? 'cityRegion'
                  : '',
        geoIndex: 0,
        label: {
          show: currentLevel.value != 0,
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '20',
            fontWeight: 'bold',
          },
        },
        zoom: 1.2,
        roam: currentLevel.value == 0 ? true : true,
        scaleLimit: {
          min: 0.5,
          max: 4,
        },
        data: showData,
        nameMap: currentLevel.value == 0 ? nameMap : {},
        itemStyle: {
          normal: {
            areaColor: '#aeeeee',
          },
        },
      },
      {
        type: 'lines',
        zlevel: 2,
        effect: {
          show: true,
          period: 3, //箭头指向速度，值越小速度越快
          trailLength: 0.3, //特效尾迹长度[0,1]值越大，尾迹越长重
          symbol: 'arrow', //箭头图标
          symbolSize: 6, //图标大小
        },
        lineStyle: {
          width: 1, //线条宽度
          opacity: 0.5, //尾迹线条透明度
          curveness: 0.3, //尾迹线条曲直度
        },
        data: dynamicStartPoint.map((point, index) => ({
          coords: [
            point,                   //使用每个动态起点
            [104.066002, 30.657000], // 固定终结点
          ],
          lineStyle: {
            color: randomColor(),  // 生成随机颜色
            type: 'dashed',
            width: 1, //线条宽度
          },
        })),
      },
      {
        type: 'effectScatter',
        coordinateSystem: 'geo',
        zlevel: 2,
        rippleEffect: {
          period: 4,
          brushType: 'stroke',
          scale: 4,
        },
        label: {
          normal: {
            show: false,
            position: 'right',
            offset: [5, 0],
          },
        },
        symbol: 'circle',
        symbolSize: 10,
        itemStyle: {
          normal: {
            color: '#E0C896',
          },
        },
        data: dynamicStartPoint.map(point => ({
          value: point,                  // 使用每个动态起点
          itemStyle: {color: randomColor()}, // 生成随机颜色
        })),
      },
      {
        type: 'effectScatter',
        coordinateSystem: 'geo',
        zlevel: 2,
        rippleEffect: {
          period: 4,
          brushType: 'stroke',
          scale: 4,
        },
        label: {
          normal: {
            show: false,
            position: 'right',
            offset: [5, 0],
          },
        },
        symbol: 'circle',
        symbolSize: 10,
        itemStyle: {
          normal: {
            color: '#e05853',
          },
        },
        data: [
          {
            value: [104.066002, 30.657000], // 固定终点
            itemStyle: {color: randomColor()},
          },
        ],
      },
    ],
    dataZoom: {
      type: 'inside',
    },
  };


  chart.setOption(option)
  chart.off('click') //解决点击地图会触发两次问题
  chart.off('contextmenu') //解决点击地图会触发两次问题
  document.oncontextmenu = function () {
    return false
  }

/*
地图点击事件
* */
  chart.on('click', (params) => {
    if (currentLevel.value == 3) {
      return
    }
    if (currentLevel.value == 0 && params.name != '中国') {
      return
    }

    if (params.name == '南海诸岛') {
      return
    }
    currentLevel.value += 1
    currentClick = params.name

    initEcharts() //县区下没有别的级别
  })
  chart.on('contextmenu', (params) => {
    if (currentLevel.value == 0) {
      return
    }
    currentLevel.value -= 1
    currentClick = params.name
    initEcharts()
  })
}

/*
坐标数组
*/
let dynamicStartPoint = [116.405285, 39.904989];

/** 查询列表 */
const getList = async () => {
  try {
    const response = await GatewayLogApi.selectGatewayIPList();
    response.map(item => {
      // console.log("经度:"+item.longitude)
      console.log("纬度:"+item.latitude)
    })
    dynamicStartPoint = response.map(item => [
      parseFloat(item.longitude),
      parseFloat(item.latitude)
    ]);
    console.log("获取的坐标数组:", dynamicStartPoint);

    // 在数据加载完成后初始化地图和调用 initEcharts()
    chart = echarts.init(qualityMap.value as unknown as HTMLElement);
    initEcharts();

  } catch (error) {
    console.error("Error fetching the list:", error);
  }
};

onMounted(() => {
  getList();
});
onUnmounted(() => {
    chart.dispose()
})
</script>

<style lang="postcss" scoped>
.el-button {
  position: absolute;
  left: 30px;
  top: 20px;
}
</style>
