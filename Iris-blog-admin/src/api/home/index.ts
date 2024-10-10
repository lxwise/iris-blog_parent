import request from '@/config/axios'
import { ArticleVO } from '@/api/articles/article'
import { ArticleCategoryVO } from '@/api/articles/category'
import { ArticleTagVO } from '@/api/articles/tag'

export interface SystemHomeStatisticsDataVO {
  viewCount: number
  messageCount: number
  userCount: number
  articleCount: number
  categoryVOList: Array<ArticleCategoryVO>
  tagVOList: Array<ArticleTagVO>
  articleRankVOList: Array<ArticleVO>
  articleStatisticsList: Array<articleStatisticsListItem>
  userViewVOList: Array<userViewVOListItem>
  gatewayStatisticsVO: gatewayStatisticsVOItem
}
export interface articleStatisticsListItem {
  date: string
  count: number
}

export interface userViewVOListItem {
  date: string
  uv: number
  pv: number
}

export interface gatewayStatisticsVOItem {
  avgRespTime: number
  ipCount: number
  dataSize: number
  urlCount: number
}

export interface WeatherVO {
	date: string;
	time: string;
	message: string;
	status: number;
    cityInfo:CityInfoVO;
    data: WeatherInfoVO;
}
export interface CityInfoVO {
	parent: string;
	city: string;
	citykey: string;
	updateTime: string;
}
export interface WeatherInfoVO {
	shidu: string;
	pm25: number;
	pm10: number;
	ganmao: string;
	wendu: string;
	quality: string;
  forecast:ForecastVO[];
  forecastDay:ForecastVO;
}

export interface ForecastVO {
	date: string;
	ymd: string;
	high: string;
	sunrise: string;
	fx: string;
	week: string;
	low: string;
	fl: string;
	sunset: string;
	aqi: number;
	type: string;
	notice: string;
}


export interface IPAddressVO {
    ipAddress: string;
    device: string;
    country: string;
    province: string;
    city: string;
    fullLocation: string;
    latitude: string;
    longitude: string;
}
// 首页统计信息
export const homeStatistics = () => {
  return request.get({ url: '/system/home/statistics' })
}
// 首页数据看板
export const homeDataPanel = () => {
  return request.get({ url: '/system/home/data/panel' })
}
//获取天气数据
export const getWeatherInfo = (cityCode:string) => {
  return request.get({ url: '/system/common/get/weather', params: {cityCode} })
}
//获取位置信息
export const getAddressInfo = () => {
  return request.get({ url: '/system/common/get/ip' })
}
