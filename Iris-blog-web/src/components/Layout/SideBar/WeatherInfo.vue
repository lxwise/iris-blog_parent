<template>
  <div class="weather-container" v-if="weatherData && weatherData.data">
    <!-- 标题 -->
    <div class="weather-title">
      <svg-icon icon-class="weather" size="2.5rem" class="margin-icon"></svg-icon>
      今日天气
    </div>
    <!-- 日期及时间 -->
    <div class="weather-datetime">
      <div class="date">
        {{ formattedDate }} {{ formattedTime }} {{ formattedWeekday }}
      </div>
    </div>
    <!-- 主要天气信息 -->
    <div class="weather-main">
      <!-- 温度 -->
      <div class="temperature">{{ weatherData.data.wendu }} ℃</div>
      <div class="weather-condition">
        <!-- 天气 -->
        <svg-icon :icon-class="weatherIcon" size="2rem" class="margin-icon"></svg-icon>
        {{ weatherData.data.forecastDay.type }}
      </div>
      <div class="location">
        <svg-icon icon-class="location" size="2rem" class="margin-icon"></svg-icon>
        {{ weatherData.cityInfo.city }}
      </div>
    </div>
    <!-- 详细的天气信息 -->
    <div class="weather-details">
      <div class="detail-item">
        <svg-icon icon-class="hight_wen" size="2rem" class="margin-icon"></svg-icon>
        <span>{{ weatherData.data.forecastDay.high }}</span>
      </div>
      <div class="detail-item">
        <svg-icon icon-class="low_wen" size="2rem" class="margin-icon"></svg-icon>
        <span>{{ weatherData.data.forecastDay.low }}</span>
      </div>
      <div class="detail-item">
        <svg-icon icon-class="feng_x" size="2rem" class="margin-icon"></svg-icon>
        <span>{{ weatherData.data.forecastDay.fx }}</span>
      </div>
      <div class="detail-item">
        <svg-icon icon-class="feng" size="2rem" class="margin-icon"></svg-icon>
        <span>{{ weatherData.data.forecastDay.fl }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {getWeatherInfo, report} from "@/api/blogInfo";
import {IPAddressVO, WeatherVO} from "@/api/blogInfo/types";
import cities from "@/assets/map/json/citycode.json";

// 天气数据
const respData = ref<IPAddressVO>(<IPAddressVO>{});
const weatherData = ref<WeatherVO>(<WeatherVO>{});
const selectedProvince = ref('');
const selectedCity = ref('');
const selectedCityCode = ref('');
const weatherIcon = ref('');

// 当前日期和时间
const formattedDate = ref('');
const formattedTime = ref('');
const formattedWeekday = ref('');

const updateDateTime = () => {
  const date = new Date();
  formattedDate.value = date.toLocaleDateString('zh-CN', { year: 'numeric', month: 'long', day: 'numeric' });
  formattedTime.value = date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit', second: '2-digit' });
  formattedWeekday.value = date.toLocaleDateString('zh-CN', { weekday: 'long' });
};

/**
 * 天气类型与图标的映射关系
 */
const weatherIconMap: { [key: string]: string } = {
  '晴': 'sunny',
  '多云': 'cloudy',
  '阴': 'yin',
  '雨': 'rain',
  '雪': 'snow',
  '沙': 'sandstorm',
  '霾': 'haze',
  '雾': 'fog',
};

/**
 * 根据省份和城市名称获取城市代码
 * @param provinceName
 * @param cityName
 */
const findCityCode = ({ provinceName, cityName }: { provinceName: any, cityName: any }) => {
  const province = cities.find(city => city.city_name.includes(provinceName.replace('省', '').replace('市', '').trim()) && city.pid === 0);
  if (province) {
    if (provinceName === cityName) {
      return province.city_code;
    }
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

/**
 * 处理天气数据icon
 * @param data
 */
const handlerWeatherData = async (data: WeatherVO) => {
  if (data.data && data.data.forecastDay && data.data.forecastDay.type) {
    const type = data.data.forecastDay.type;
    // 从映射中查找对应的图标
    let matched = false;
    // 根据天气类型匹配图标
    for (const key in weatherIconMap) {
      if (type.includes(key)) {
        weatherIcon.value = weatherIconMap[key]; // 只使用英文图标
        matched = true;
        break;
      }
    }
    // 如果未匹配到，设置默认图标为 "sunny"
    if (!matched) {
      weatherIcon.value = 'sunny';
    }
  }
};


/**
 * 获取位置信息
 */
const fetchProvinceCityFromBackend = async () => {
  selectedProvince.value = respData.value?.province as string;
  selectedCity.value = respData.value?.city as string;
  selectedCityCode.value = findCityCode({ provinceName: selectedProvince.value, cityName: selectedCity.value });
  weatherData.value = await getWeatherInfo(selectedCityCode.value);
  await handlerWeatherData(weatherData.value);
};

onMounted(async () => {
  respData.value = await report();
  await fetchProvinceCityFromBackend();
  updateDateTime();
  setInterval(updateDateTime, 1000); // 每秒更新一次时间
});
</script>

<style lang="scss" scoped>
.margin-icon {
  padding-right: 0.25rem;
}

.weather-container {
  background-color: var(--grey-1);
  border-radius: 12px;
  padding: 0.175rem;
  text-align: center;

  .weather-title {
    display: flex;
    align-items: center;
    font-size: 1.2em;
  }
}

.weather-datetime {
  margin-bottom: 0.15rem;
  .date {
    display: flex;
    justify-content: center;
    padding: 0.175rem;
    font-size: 1rem;
    color: #1e3a8a;
  }

  .time {
    font-size: 1.2rem;
    color: #9ca3af;
  }

  .weekday {
    font-size: 1rem;
    color: #6b7280;
  }
}

.weather-main {
  margin-bottom: 0.15rem;
}

.temperature {
  font-size: 2rem;
  color: #1e3a8a;
}

.weather-condition {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.2rem;
  margin-top: 0.01rem;
}

.location {
  display: flex;
  justify-content: center;
  font-size: 1.2rem;
  color: var(--grey-7);
  margin-top: 0.18rem;
}

.weather-details {
  display: flex;
  justify-content: space-around;
  padding: 12px;
  background-color: white;
  border-radius: 8px;
}

.detail-item {
  display: flex;
  align-items: center;
  font-size: 0.75rem;
}

.detail-item svg-icon {
  margin-right: 4px;
}
</style>
