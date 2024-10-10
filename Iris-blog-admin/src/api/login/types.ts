export type UserLoginVO = {
  username: string
  password: string
  captchaCode: string
  socialType?: string
  socialCode?: string
  socialState?: string
}

export type TokenType = {
  id: number // 编号
  accessToken: string // 访问令牌
  refreshToken: string // 刷新令牌
  userId: number // 用户编号
  userType: number //用户类型
  clientId: string //客户端编号
  expiresTime: number //过期时间
}
