import request from '@/config/axios'

export interface DictDataVO {
  id: number
  dictType: string
  dictLabel: string
  dictValue: string
  remark: string
  sort: number
  createTime: Date
  updateTime: Date
}

// 字典数据列表
export const getDictDataPage = (data: PageParam) => {
  return request.post({ url: '/system/dict/data/list', data })
}

// 字典数据详情
export const getDictData = async (id: number) => {
  return await request.get({ url: '/system/dict/data/info', params: { id } })
}

// 新增字典数据
export const createDictData = async (data: DictDataVO) => {
  return await request.post({ url: '/system/dict/data/save', data })
}

// 修改字典数据
export const updateDictData = async (data: DictDataVO) => {
  return await request.post({ url: '/system/dict/data/update', data })
}

// 删除字典数据
export const deleteDictData = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/dict/data/delete', data })
}

// 字典数据状态修改
export const updateDictDataStatus = async (id: number) => {
  return await request.get({ url: '/system/dict/data/update/status', params: { id: id } })
}

// 导出字典数据
export const exportDictDataExcel = async (data: any) => {
  return await request.download({ url: '/system/dict/data/export', data })
}

//字典数据下拉
export const getSelectDictDataList = () => {
  return request.get({ url: '/system/dict/data/select' })
}
