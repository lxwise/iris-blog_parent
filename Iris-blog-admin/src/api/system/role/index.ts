import request from '@/config/axios'

export interface RoleVO {
  id: number
  name: string
  code: string
  remarks: string
}

export interface UpdateStatusReqVO {
  id: number
  status: number
}

// 查询角色列表
export const getRolePage = async (data: PageParam) => {
  return await request.post({ url: '/system/role/list', data })
}

// 查询角色（精简)列表
export const getSimpleRoleList = async (): Promise<RoleVO[]> => {
  return await request.get({ url: '/system/role/drop-list' })
}

// 查询角色详情
export const getRole = async (id: number) => {
  return await request.get({ url: '/system/role/info', params: { id } })
}

// 新增角色
export const createRole = async (data: RoleVO) => {
  return await request.post({ url: '/system/role/save', data })
}

// 修改角色
export const updateRole = async (data: RoleVO) => {
  return await request.post({ url: '/system/role/update', data })
}

// 删除角色
export const deleteRole = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/role/delete', data })
}
