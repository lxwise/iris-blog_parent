<template>
  <div
    :class="prefixCls"
    class="relative h-[100%] lt-md:px-10px lt-sm:px-10px lt-xl:px-10px lt-xl:px-10px"
  >
    <!-- 粒子效果背景 -->
    <vue-particles id="tsparticles" :options="particlesOptions" class="absolute top-0 left-0 w-full h-full z-0" />
    <div class="relative mx-auto h-full flex">
      <!--  左侧布局  -->
      <Transition appear enter-active-class="animate__animated animate__bounceInLeft">
      <div :class="`${prefixCls}__left flex-1 bg-gray-#F3F3F3 relative p-30px lt-xl:hidden`">
        <!-- 左边布局,上方小图标-->
        <div class="relative flex justify-between">
          <img alt="" class="ml-100px h-50px w-100px" src="@/assets/login/noDataYet.svg" />
          <img alt="" class="mr-100px h-50px w-100px" src="@/assets/login/net_err.svg" />
        </div>
        <!-- 左边布局,中间背景图-->
        <div class="h-[calc(100%-150px)] flex flex-col items-center justify-center">
          <TransitionGroup
            appear
            enter-active-class="animate__animated animate__bounceInLeft"
            tag="div"
          >
            <img alt="" class="w-450px my-4" src="@/assets/login/machent.svg" />
          </TransitionGroup>
          <img alt="" class="mr-120px h-50px w-100px mt-4" src="@/assets/login/rocket.svg" />
        </div>
        <!-- 左边布局,下方小图标-->
        <div class="relative flex justify-between">
          <img alt="" class="h-100px w-100px" src="@/assets/login/setting.svg" />
          <img alt="" class="mr-150px mt-50px w-100px" src="@/assets/login/net_no.svg" />
        </div>
      </div>
      </Transition>
      <!--  右侧布局  -->
      <div class="relative flex-1 p-30px dark:bg-[var(--login-bg-color)] lt-sm:p-10px">
        <!-- 右上角的主题、语言选择 -->
        <div
          class="flex items-center justify-between text-white at-2xl:justify-end at-xl:justify-end"
        >
          <div class="flex items-center at-2xl:hidden at-xl:hidden">
            <img alt="" class="mr-10px h-48px w-48px" src="@/assets/login/logo-Iris.png" />
          </div>
          <div class="flex items-center justify-end space-x-10px">
            <ThemeSwitch />
            <LocaleDropdown class="dark:text-white lt-xl:text-white" />
          </div>
        </div>
        <!-- 右边的登录界面 -->
        <Transition appear enter-active-class="animate__animated animate__bounceInRight">
          <div
            class="m-auto h-full w-[100%] flex items-center at-2xl:max-w-500px at-lg:max-w-500px at-md:max-w-500px at-xl:max-w-500px"
          >
            <!-- 账号登录 -->
            <LoginForm class="m-auto h-auto p-20px lt-xl:(rounded-3xl light:bg-white)" />
          </div>
        </Transition>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { useDesign } from '@/hooks/web/useDesign'
import { ThemeSwitch } from '@/layout/components/ThemeSwitch'
import { LocaleDropdown } from '@/layout/components/LocaleDropdown'
import { LoginForm } from './components'
import particlesOptions from '@/utils/particleEffect'
defineOptions({ name: 'Login' })
const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('login')
</script>

<style lang="scss" scoped>
$prefix-cls: #{$namespace}-login;

.#{$prefix-cls} {
  overflow: auto;

  &__left {
    &::before {
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      z-index: -1;
      width: 100%;
      height: 100%;
      background-position: center;
      background-repeat: no-repeat;
      content: '';
    }
  }
}
</style>
