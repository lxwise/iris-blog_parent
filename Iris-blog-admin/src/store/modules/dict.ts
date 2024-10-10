import { defineStore } from 'pinia'
import { store } from '../index'
// @ts-ignore
import { DictDataVO, getSelectDictDataList } from '@/api/system/dict/dictdata'
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'

const { wsCache } = useCache('sessionStorage')

export interface DictValueType {
  value: any
  label: string
}

export interface DictTypeType {
  dictType: string
  value: DictValueType[]
}

export interface DictState {
  dictMap: Map<string, any>
  isSetDict: boolean
}

export const useDictStore = defineStore('dictData', {
  state: (): DictState => ({
    dictMap: new Map<string, any>(),
    isSetDict: false
  }),
  getters: {
    getDictMap(): Recordable {
      const dictMap = wsCache.get(CACHE_KEY.DICT_CACHE)
      if (dictMap) {
        this.dictMap = dictMap
      }
      return this.dictMap
    },
    getIsSetDict(): boolean {
      return this.isSetDict
    }
  },
  actions: {
    async setDictMap() {
      const dictMap = wsCache.get(CACHE_KEY.DICT_CACHE)
      if (dictMap) {
        this.dictMap = dictMap
        this.isSetDict = true
      } else {
        const res = await getSelectDictDataList()
        // 设置数据
        const dictDataMap = new Map<string, any>()
        res.forEach((dictData: DictDataVO) => {
          // 获得 dictType 层级
          const enumValueObj = dictDataMap[dictData.dictType]
          if (!enumValueObj) {
            dictDataMap[dictData.dictType] = []
          }
          // 处理 dictValue 层级
          dictDataMap[dictData.dictType].push({
            value: dictData.dictValue,
            label: dictData.dictLabel
          })
        })
        this.dictMap = dictDataMap
        this.isSetDict = true
        wsCache.set(CACHE_KEY.DICT_CACHE, dictDataMap, { exp: 90 }) // 90 秒 过期
      }
    },
    getDictByType(type: string) {
      if (!this.isSetDict) {
        this.setDictMap()
      }
      return this.dictMap[type]
    },
    async resetDict() {
      wsCache.delete(CACHE_KEY.DICT_CACHE)
      const res = await getSelectDictDataList()
      // 设置数据
      const dictDataMap = new Map<string, any>()
      res.forEach((dictData: DictDataVO) => {
        // 获得 dictType 层级
        const enumValueObj = dictDataMap[dictData.dictType]
        if (!enumValueObj) {
          dictDataMap[dictData.dictType] = []
        }
        // 处理 dictValue 层级
        dictDataMap[dictData.dictType].push({
          value: dictData.dictValue,
          label: dictData.dictLabel
        })
      })
      this.dictMap = dictDataMap
      this.isSetDict = true
      wsCache.set(CACHE_KEY.DICT_CACHE, dictDataMap, { exp: 90 }) // 90 秒 过期
    }
  }
})

export const useDictStoreWithOut = () => {
  return useDictStore(store)
}
