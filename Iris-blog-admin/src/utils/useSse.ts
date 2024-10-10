// useSse.js
import {ref, onMounted, onBeforeUnmount} from 'vue'
import {getAccessToken} from '@/utils/auth' // 导入获取token的方法
import * as CommentApi from '@/api/message/comment/index'
import {ElNotification} from "element-plus"; // 导入你的request方法

export function useSse(url) {
  const eventSource = ref(null);
  const messages = ref([]);
  const setAuthCookie = () => {
    document.cookie = `Authorization=${getAccessToken()}; path=/`;
  };
  const subscribeWarnMsg = () => {
    setAuthCookie(); // 设置认证 Cookie

    eventSource.value = new EventSource(url, {
      withCredentials: true,
    });

    eventSource.value.onmessage = (event) => {
      console.log(event.data);
      messages.value.push(event.data); // 将接收到的消息存储到messages中
      ElNotification({
        title: '您有新消息',
        message: event.data,
        type: 'info',
      })
    };

    eventSource.value.onerror = (err) => {
      console.error('解析失败或连接丢失:', err);
      eventSource.value.close();
    };
  };

  const closeMessage = () => {
    CommentApi.sseClose()
  };

  onMounted(() => {
    subscribeWarnMsg();
  });

  onBeforeUnmount(() => {
    closeMessage();
  });

  return {
    messages, // 返回messages
  };
}
