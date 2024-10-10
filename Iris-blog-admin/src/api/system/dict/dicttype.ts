import request from '@/config/axios'

export interface DictTypeVO {
  id: number
  dictType: string
  dictName: string
  remark: string
  sort: number
  createTime: Date
  updateTime: Date
}

// 字典类型列表
export const getDictTypePage = async (data: PageParam) => {
  return await request.post({ url: '/system/dict/type/list', data })
}

// 字典类型详情
export const getDictType = async (id: number) => {
  return await request.get({ url: '/system/dict/type/info', params: { id } })
}

// 新增字典类型
export const createDictType = async (data: DictTypeVO) => {
  return await request.post({ url: '/system/dict/type/save', data })
}

// 修改字典类型
export const updateDictType = async (data: DictTypeVO) => {
  return await request.post({ url: '/system/dict/type/update', data })
}

// 删除字典类型
export const deleteDictType = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/dict/type/delete', data })
}

// 字典类型状态修改
export const updateDictTypeStatus = async (id: number) => {
  return await request.get({ url: '/system/dict/type/update/status', params: { id: id } })
}

// 导出字典类型
export const exportDictTypeExcel = async (data: any) => {
  return await request.download({ url: '/system/dict/type/export', data })
}

//字典类型下拉
export const getSelectDictTypeList = () => {
  return request.get({ url: '/system/dict/type/select' })
}
