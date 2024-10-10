import request from '@/config/axios'
import { SystemMenuTypeEnum } from '@/utils/constants'

export interface SearchMenuVO {
  name: string
  status: number
}
export interface MenuVO {
  id: number
  name: string
  permission: string
  type: number
  sort: number
  parentId: number
  path: string
  icon: string
  component: string
  componentName?: string
  status: number
  visible: boolean
  keepAlive: boolean
  alwaysShow?: boolean
  createTime: Date
}

// 查询菜单（精简）列表
export const getSimpleMenusList = () => {
  return request.get({ url: '/system/menu/select/list' })
}

// 查询菜单列表
export const getMenuList = (data: any) => {
  return request.post({ url: '/system/menu/list', data })
}

// 获取菜单详情
export const getMenu = (id: number) => {
  return request.get({ url: '/system/menu/info', params: { id } })
}

// 新增菜单
export const createMenu = (data: MenuVO) => {
  return request.post({ url: '/system/menu/save', data })
}

// 修改菜单
export const updateMenu = (data: MenuVO) => {
  return request.post({ url: '/system/menu/update', data })
}

// 删除菜单
export const deleteMenu = (id: number) => {
  return request.get({ url: '/system/menu/delete', params: { id } })
}

export const menuTypeToText = (type: number) => {
  if (type === SystemMenuTypeEnum.DIR) {
    return '目录'
  } else if (type === SystemMenuTypeEnum.MENU) {
    return '菜单'
  } else if (type === SystemMenuTypeEnum.BUTTON) {
    return '按钮'
  }
}
