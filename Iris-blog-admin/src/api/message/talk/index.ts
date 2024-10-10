import request from '@/config/axios'

export interface TalkVO {
  id: number;
  userId: number;
  talkContent: string;
  isTop: number;
  status: number;
  createTime: Date;
  updateTime: Date;

}


// 说说列表
export const getTalkPage = async (data: PageParam) => {
  return await request.post({url: '/system/talk/list', data})
}

// 说说详情
export const getTalk = async (id: number) => {
  return await request.get({url: '/system/talk/info', params: {id}})
}

// 新增说说
export const createTalk = async (data: TalkVO) => {
  return await request.post({url: '/system/talk/save', data})
}

// 修改说说
export const updateTalk = async (data: TalkVO) => {
  return await request.post({url: '/system/talk/update', data})
}

// 删除说说
export const deleteTalk = async (id: number) => {
  const data: number[] = [id]
  return await request.post({url: '/talk/delete', data})
}
