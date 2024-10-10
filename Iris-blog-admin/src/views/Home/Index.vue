<template>
  <div class="dashboard-container">
    <el-row :gutter="16" justify="space-between">
      <el-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
        <div class="flex items-center">
          <el-avatar :src="avatar" :size="70" class="mr-16px">
            <img src="@/assets/login/logo.gif" alt=""/>
          </el-avatar>
          <div>
            <div class="text-20px">
              {{ t('workplace.welcome') }} {{ username }} {{ t('workplace.happyDay') }}
            </div>
            <div class="mt-10px text-14px text-gray-500">
              <strong>{{new Date().toLocaleString() }}，气温: {{ weatherData.data?.wendu ?? '25℃' }}，湿度: {{ weatherData.data?.shidu ?? '50%' }} {{ weatherData.data?.forecastDay?.low ?? '25℃' }} - {{ weatherData.data?.forecastDay?.high ?? '32℃' }}！</strong>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="16" class="mt-16px">
    <el-col>
      <!-- 地图数据   -->
      <HomeMap/>
    </el-col>
    </el-row>
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <router-link :to="'/gateway/gateway-log'">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-message my-svg-size">
              <Icon :size="50" icon="svg-icon:api" lass-tagName="card-panel-icon"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text"> 接口请求量</div>
              <span class="card-panel-num">{{ urlCount }}</span>
            </div>
          </div>
        </router-link>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <router-link :to="'/gateway/gateway-log'">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-ip my-svg-size">
              <Icon :size="50" icon="svg-icon:ip" lass-tagName="card-panel-icon"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text"> IP请求量</div>
              <span class="card-panel-num">{{ ipCount }}</span>
            </div>
          </div>
        </router-link>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <router-link :to="'/gateway/gateway-log'">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-message my-svg-size">
              <Icon :size="50" icon="svg-icon:dataSize" lass-tagName="card-panel-icon"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text"> 数据流量(KB)</div>
              <span class="card-panel-num">{{ dataSize }}</span>
            </div>
          </div>
        </router-link>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <router-link :to="'/gateway/gateway-log'">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-message my-svg-size">
              <Icon :size="50" icon="svg-icon:time" lass-tagName="card-panel-icon"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text"> 平均响应时长(秒)</div>
              <span class="card-panel-num">{{ avgRespTime }}</span>
            </div>
          </div>
        </router-link>
      </el-col>
    </el-row>
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <router-link :to="'/gateway/gateway-log'">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-view my-svg-size">
              <Icon :size="50" icon="svg-icon:button" lass-tagName="card-panel-icon"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text"> 访问量</div>
              <span class="card-panel-num">{{ viewCount }}</span>
            </div>
          </div>
        </router-link>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <router-link :to="'/articles/article'">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-money my-svg-size">
              <Icon :size="50" icon="svg-icon:edit" lass-tagName="card-panel-icon"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text"> 文章量</div>
              <span class="card-panel-num">{{ articleCount }}</span>
            </div>
          </div>
        </router-link>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <router-link :to="'/system/user'">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-people my-svg-size">
              <Icon :size="50" icon="svg-icon:peoples" lass-tagName="card-panel-icon"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text"> 用户量</div>
              <span class="card-panel-num">{{ userCount }}</span>
            </div>
          </div>
        </router-link>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <router-link :to="'/message/leave'">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-message my-svg-size">
              <Icon :size="50" icon="svg-icon:comment" lass-tagName="card-panel-icon"/>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text"> 留言量</div>
              <span class="card-panel-num">{{ messageCount }}</span>
            </div>
          </div>
        </router-link>
      </el-col>
    </el-row>
    <el-row class="data-card" style="margin-bottom: 32px">
      <div class="title">文章贡献统计🎉</div>
      <calendar-heatmap
          style="width: 100%; margin-top: 0.5rem"
          :values="articleStatisticsList"
          :end-date="new Date()"
          :range-color="['#ebedf0', '#9be9a8', '#40c463', '#30a14e', '#216e39']"
      />
    </el-row>
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <div class="title">文章浏览量排行🚀</div>
          <Echarts :options="ariticleRank" height="350px"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <div class="title">文章分类统计🍉</div>
          <Echarts :options="category" height="350px"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <div class="title">文章标签统计🌈</div>
          <tag-cloud v-if="tagLoad" :data="tagList"/>
        </div>
      </el-col>
    </el-row>
    <el-row class="data-card">
      <div class="title">一周访问量✨</div>
      <Echarts :options="userView" height="350px"/>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import TagCloud from '@/components/TagCloud/index.vue'
import CalendarHeatmap from '@/components/CalendarHeatmap/index.vue'
import Echarts from '@/components/Echart/index.vue'

const loading = ref(true) // 列表的加载中
import * as ArticleTagApi from '@/api/articles/tag/index'
import * as HomeApi from '@/api/home/index'
import {onMounted, reactive, ref} from 'vue'

import {useUserStore} from '@/store/modules/user'
import cities from "@/assets/map/json/citycode.json";
import HomeMap from './map.vue'
const {t} = useI18n()
const userStore = useUserStore()
const avatar = userStore.getUser.avatar
const username = userStore.getUser.nickname

const urlCount = ref(0)
const ipCount = ref(0)
const dataSize = ref(0)
const avgRespTime = ref(0)
const tagList = ref<ArticleTagApi.ArticleTagVO[]>([])
const viewCount = ref(0)
const messageCount = ref(0)
const userCount = ref(0)
const tagLoad = ref(false)
const articleCount = ref(0)
const articleStatisticsList = ref<HomeApi.articleStatisticsListItem[]>([])
let userView = reactive({
  xAxis: {
    data: [] as string[],
    boundaryGap: false,
    axisTick: {
      show: false
    }
  },
  grid: {
    left: 8,
    right: 35,
    bottom: 0,
    top: 30,
    containLabel: true
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross'
    },
    padding: [5, 10]
  },
  yAxis: {
    axisTick: {
      show: false
    }
  },
  legend: {
    data: ['访问量(PV)', '独立访客(UV)']
  },
  series: [
    {
      name: '访问量(PV)',
      itemStyle: {
        color: '#FF005A'
      },
      lineStyle: {
        color: '#FF005A',
        width: 2
      },
      smooth: true,
      type: 'line',
      data: [] as number[],
      animationDuration: 2800,
      animationEasing: 'cubicInOut'
    },
    {
      name: '独立访客(UV)',
      smooth: true,
      type: 'line',
      itemStyle: {
        color: '#3888fa'
      },
      lineStyle: {
        color: '#3888fa',
        width: 2
      },
      areaStyle: {
        color: '#f3f8ff'
      },
      data: [] as number[],
      animationDuration: 2800,
      animationEasing: 'quadraticOut'
    }
  ]
})
let category = reactive({
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    top: 'bottom'
  },
  series: [
    {
      name: '分类统计',
      type: 'pie',
      radius: [15, 95],
      center: ['50%', '38%'],
      roseType: 'area',
      itemStyle: {
        borderRadius: 6
      },
      data: [] as {
        value: number
        name: string
      }[]
    }
  ]
})
let ariticleRank = reactive({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  color: ['#58AFFF'],
  grid: {
    left: '0%',
    right: '0%',
    bottom: '0%',
    top: '10%',
    containLabel: true
  },
  xAxis: {
    data: [] as string[],
    axisTick: {
      alignWithLabel: true
    }
  },
  yAxis: {
    type: 'value',
    axisTick: {
      show: false
    }
  },
  series: [
    {
      name: '浏览量',
      type: 'bar',
      data: [] as number[]
    }
  ]
})
const getList = async () => {
  loading.value = true
  try {
    const data = await HomeApi.homeStatistics()
    const dataPanel = await HomeApi.homeDataPanel()
    //网关相关
    urlCount.value = data.gatewayStatisticsVO.urlCount
    ipCount.value = data.gatewayStatisticsVO.ipCount
    dataSize.value = data.gatewayStatisticsVO.dataSize
    avgRespTime.value = data.gatewayStatisticsVO.avgRespTime
    //文章相关
    viewCount.value = data.viewCount
    messageCount.value = data.messageCount
    userCount.value = data.userCount
    articleCount.value = data.articleCount
    //图表相关
    articleStatisticsList.value = dataPanel.articleStatisticsList
    if (dataPanel.tagVOList != null) {
      tagList.value = dataPanel.tagVOList
      tagLoad.value = true
    }
    if (dataPanel.articleRankVOList != null) {
      dataPanel.articleRankVOList.forEach((item) => {
        ariticleRank.series[0].data.push(item.views)
        ariticleRank.xAxis.data.push(item.title)
      })
    }
    if (dataPanel.categoryVOList != null) {
      dataPanel.categoryVOList.forEach((item) => {
        category.series[0].data.push({
          value: item.articleCount,
          name: item.name
        })
      })
    }
    if (dataPanel.userViewVOList != null) {
      dataPanel.userViewVOList.forEach((item) => {
        userView.xAxis.data.push(item.date)
        userView.series[0].data.push(item.pv)
        userView.series[1].data.push(item.uv)
      })
    }
  } finally {
    loading.value = false
  }
}

const respData = ref<HomeApi.IPAddressVO>({})
const weatherData = ref<HomeApi.WeatherVO>({})
const selectedProvince = ref('');
const selectedCity = ref('');
const selectedCityCode = ref('');

const findCityCode = (provinceName, cityName) => {
  // 模糊匹配省份
  const province = cities.find(city => city.city_name.includes(provinceName.replace('省', '').replace('市', '').trim()) && city.pid === 0);
  if (province) {
    if (provinceName === cityName) {
      return province.city_code;
    }
    // 模糊匹配城市
    const city = cities.find(city => city.city_name.includes(cityName.replace('市', '').trim()) && city.pid === province.id);
    if (city) {
      return city.city_code;
    }
    console.log("没有找到匹配的市");
    return '';
  }
  console.log("没有找到匹配的省");
  return '';
};
/*
*获取位置信息
*/
const fetchProvinceCityFromBackend = async () => {
  respData.value = await HomeApi.getAddressInfo();
  selectedProvince.value = respData.value?.province as string;
  selectedCity.value = respData.value?.city as string;
  selectedCityCode.value = findCityCode(selectedProvince.value, selectedCityCode.value);
  weatherData.value = await HomeApi.getWeatherInfo(selectedCityCode.value);

};
/*
*获取天气信息
*/
onMounted(() => {
  fetchProvinceCityFromBackend()
  getList()
})
</script>
<style lang="scss" scoped>
.title {
  font-size: 14px;
  color: var(--el-text-color-secondary);
  font-weight: 700;
}

.data-card {
  background: var(--el-bg-color-overlay);
  padding: 1rem;
}

.dashboard-container {
  padding: 32px;
  background: var(--el-bg-color-page);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: var(--el-bg-color-overlay);
    padding: 1rem;
    margin-bottom: 2rem;
  }
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: var(--el-bg-color-overlay);
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-view {
        background: #34bfa3;
      }

      .icon-ip {
        background: #7cf5d9;
      }
    }


    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-view {
      color: #34bfa3;
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: var(--el-text-color-secondary);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
