import request from '@/config/axios'
import { SystemMenuTypeEnum } from '@/utils/constants'

export interface JobVO {
  jobName: string
  remark: string
  updateTime: Date
  jobGroup: string
  cronExpression: string
  createBy: string
  updateBy: string
  createTime: Date
  invokeTarget: string
  misfirePolicy: number
  id: number
  status: number
  nextValidTime: Date[]
}

export interface JobLogVO {
  jobName: string
  jobId: number
  jobMessage: string
  createTime: Date
  invokeTarget: string
  updateTime: Date
  startTime: Date
  stopTime: Date
  id: number
  jobGroup: string
  exceptionInfo: string
  status: number
  duration: number
}

export interface UpdateStatusReqVO {
  id: number
  status: number
}

// 查询任务列表
export const getJobPage = async (data: PageParam) => {
  return await request.post({ url: '/system/job/list', data })
}

// 查询任务详情
export const getJob = async (id: number) => {
  return await request.get({ url: '/system/job/info', params: { id } })
}

// 新增任务
export const createJob = async (data: JobVO) => {
  return await request.post({ url: '/system/job/save', data })
}

// 修改任务
export const updateJob = async (data: JobVO) => {
  return await request.post({ url: '/system/job/update', data })
}

// 删除任务
export const deleteJob = async (id: number) => {
  return await request.get({ url: '/system/job/delete', params: { id } })
}

// 任务状态修改
export const updateJobStatus = (id: number) => {
  return request.get({ url: '/system/job/change/status', params: { id } })
}
// 任务状态修改
export const runJob = (id: number) => {
  return request.get({ url: '/system/job/run', params: { id } })
}

export const misfirePolicyTypeToText = (type: number) => {
  if (type === SystemMenuTypeEnum.DIR) {
    return '立即执行'
  } else if (type === SystemMenuTypeEnum.MENU) {
    return '执行一次'
  } else if (type === SystemMenuTypeEnum.BUTTON) {
    return '放弃执行'
  }
}

//=================定时任务日志==========================
// 查询任务列表
export const getJobLogPage = async (data: PageParam) => {
  return await request.post({ url: '/system/job/log/list', data })
}

// 查询任务详情
export const getJobLog = async (id: number) => {
  return await request.get({ url: '/system/job/log/info', params: { id } })
}
