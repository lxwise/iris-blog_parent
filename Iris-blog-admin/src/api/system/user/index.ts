import request from '@/config/axios'
export interface UserVO {
  id: number
  username: string
  nickname: string
  deptId: number
  postIds: string[]
  email: string
  mobile: string
  sex: number
  avatar: string
  loginIp: string
  status: boolean
  remark: string
  loginDate: Date
  createTime: Date
}

// 查询用户管理列表
export const getUserPage = (data: PageParam) => {
  return request.post({ url: '/system/user/list', data })
}

// 查询所有用户列表
export const getAllUser = () => {
  return request.get({ url: '/system/user/all' })
}

// 查询用户详情
export const getUser = (id: number) => {
  return request.get({ url: '/system/user/info?id=' + id })
}

// 新增用户
export const createUser = (data: UserVO) => {
  return request.post({ url: '/system/user/save', data })
}

// 修改用户
export const updateUser = (data: UserVO) => {
  return request.post({ url: '/system/user/update', data })
}

// 删除用户
export const deleteUser = (id: number) => {
  const data: number[] = [id]
  return request.post({ url: '/system/user/delete', data })
}

// 导出用户
export const exportUser = (params) => {
  return request.download({ url: '/system/user/export', params })
}

// 下载用户导入模板
export const importUserTemplate = () => {
  return request.download({ url: '/system/user/get-import-template' })
}

// 用户密码重置
export const resetUserPwd = (id: number, password: string) => {
  const data = {
    id,
    password
  }
  return request.post({ url: '/system/user/update/password', data: data })
}

// 用户状态修改
export const updateUserStatus = (id: number) => {
  return request.get({
    url: '/system/user/update/status',
    params: {
      id: id
    }
  })
}

// 获取用户精简信息列表
export const getSimpleUserList = (): Promise<UserVO[]> => {
  return request.get({ url: '/system/user/simple-list' })
}
