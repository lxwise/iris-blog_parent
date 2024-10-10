<script setup>
const props = defineProps({
    liHeight: {
        type: String,
        default: "30px",
    },
    list: {
        type: Array,
        default: () => {
            const list = [];
            for (let i = 0; i < 30; i++) {
                list.push(`内容${i}`);
            }
            return list;
        },
    },
});

// 初始化数据
const copyList = ref([]); // 复制的数据
const requestId = ref(null); // 保存帧动画id
const listBoxRef = ref(null); // 渲染数据的大容器 -- 限制高度
const listRef = ref(null); // 渲染数据的容器
const speed = ref(1); // 滚动速度

// 开始滚动
const onScroll = () => {
    if (listRef.value && listBoxRef.value) {
        listRef.value.scrollTop += speed.value;

        // 当滚动到达复制列表的一半时，立即回到起点，实现无缝滚动
        if (listRef.value.scrollTop >= listRef.value.scrollHeight / 2) {
            listRef.value.scrollTop = 0;
        }

        requestId.value = requestAnimationFrame(onScroll);
    }
};

// 开始滚动动画
const start = () => {
    if (requestId.value) {
        window.cancelAnimationFrame(requestId.value);
    }

    // 将列表复制两份进行无缝滚动
    copyList.value = [...props.list, ...props.list];

    // 开始动画
    requestId.value = requestAnimationFrame(onScroll);
};

// 结束滚动动画
const stop = () => {
    if (requestId.value) {
        window.cancelAnimationFrame(requestId.value);
    }
};

onMounted(() => {
    // 页面加载后自动开始滚动
    if (props.list.length > 0) {
        start();
    }
});

// 监听列表变化，自动重启滚动
watch(() => props.list, (newList) => {
    if (newList.length > 0) {
        start();
    }
});

// 鼠标移入停止滚动
const onMouseOver = () => {
    stop();
};

// 鼠标移出继续滚动
const onMouseOut = () => {
    if (props.list.length > 0) {
        start();
    }
};
</script>

<template>
    <div ref="listBoxRef" class="list-box auto-scroll-list" @mouseenter="onMouseOver" @mouseleave="onMouseOut">
        <div ref="listRef" class="list">
            <div :style="{ height: liHeight, lineHeight: liHeight }" v-for="(item, index) in copyList" :key="index">
                <slot :row="item" :index="index">{{ item }}</slot>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.list-box {
  height: 300px;
  overflow: hidden;
  border-radius: 2px;
  margin: auto;
  padding: 10px;

  .list {
    height: 100%;  /* 确保容器高度充满父元素 */
    overflow-y: scroll;  /* 启用滚动 */
    display: block;
    position: relative;

    /* 隐藏滚动条 */
    scrollbar-width: none; /* 对 Firefox 有效 */
    -ms-overflow-style: none; /* 对 IE 和 Edge 有效 */
  }

  .list::-webkit-scrollbar {
    display: none; /* 对 Chrome、Safari 和 Opera 有效 */
  }

  div {
    height: 30px;
    line-height: 30px;
  }
}
</style>
