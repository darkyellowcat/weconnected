<template>
  <div class="chat-container">
    <van-nav-bar
      :title="'队伍聊天 - ' + teamName"
      left-arrow
      @click-left="goBack"
    />

    <div class="message-list" ref="messageListRef">
      <div
        v-for="(message, index) in messages"
        :key="index"
        class="message-item"
        :class="{ 'my-message': message.senderId === currentUser?.id }"
      >
        <div v-if="message.type === 'JOIN' || message.type === 'LEAVE'" class="system-message">
          {{ message.content }}
        </div>

        <div v-else class="chat-message">
          <van-image
            round
            width="40px"
            height="40px"
            :src="message.senderAvatar || 'https://api.dicebear.com/7.x/bottts-neutral/svg?seed=default'"
            fit="cover"
          />
          <div class="message-content">
            <div class="message-sender">{{ message.senderName }}</div>
            <div class="message-bubble">{{ message.content }}</div>
            <div class="message-time">{{ formatTime(message.timestamp) }}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="input-area">
      <van-field
        v-model="messageContent"
        placeholder="输入消息..."
        @keyup.enter="sendMessage"
      >
        <template #button>
          <van-button size="small" type="primary" @click="sendMessage">发送</van-button>
        </template>
      </van-field>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import myAxios from '../plugins/myAxios';
import { Toast } from 'vant';
import { getCurrentUser } from '../services/user';

const route = useRoute();
const router = useRouter();
const currentUser = ref();

const teamId = ref<number>(Number(route.query.teamId));
const teamName = ref<string>(String(route.query.teamName || '队伍'));
const messages = ref<any[]>([]);
const messageContent = ref('');
const messageListRef = ref<HTMLElement | null>(null);
const NULL_CHAR = String.fromCharCode(0);

let ws: WebSocket | null = null;
let stompConnected = false;

const connectWebSocket = () => {
  const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
  const host = window.location.hostname;
  const wsUrl = `${protocol}//${host}:8080/api/ws-chat-raw`;
  ws = new WebSocket(wsUrl);

  ws.onopen = () => {
    const frame = 'CONNECT\naccept-version:1.1,1.0\nheart-beat:10000,10000\n\n' + NULL_CHAR;
    ws!.send(frame);
  };

  ws.onmessage = (event) => {
    handleStompFrame(event.data);
  };

  ws.onerror = () => {
    Toast.fail('连接失败，请刷新重试');
  };

  ws.onclose = () => {
    stompConnected = false;
  };
};

// PLACEHOLDER_SCRIPT_PART2

const handleStompFrame = (data: string) => {
  if (data === '\n') return;

  const nullIndex = data.indexOf(NULL_CHAR);
  const frame = nullIndex !== -1 ? data.substring(0, nullIndex) : data;
  const firstNewline = frame.indexOf('\n');
  const command = firstNewline === -1 ? frame : frame.substring(0, firstNewline);

  if (command === 'CONNECTED') {
    stompConnected = true;
    subscribeToChannel();
    sendJoinMessage();
    return;
  }

  if (command === 'MESSAGE') {
    const headerEnd = frame.indexOf('\n\n');
    if (headerEnd !== -1) {
      const body = frame.substring(headerEnd + 2);
      if (body) {
        try {
          const chatMessage = JSON.parse(body);
          messages.value.push(chatMessage);
          nextTick(() => scrollToBottom());
        } catch (e) {
          // ignore non-JSON frames
        }
      }
    }
  }
};

const sendStompMessage = (destination: string, body: any): boolean => {
  if (!ws || ws.readyState !== WebSocket.OPEN || !stompConnected) {
    return false;
  }
  const bodyStr = JSON.stringify(body);
  const byteLength = new TextEncoder().encode(bodyStr).length;
  const frame = `SEND\ndestination:${destination}\ncontent-type:application/json\ncontent-length:${byteLength}\n\n${bodyStr}` + NULL_CHAR;
  ws.send(frame);
  return true;
};

const subscribeToChannel = () => {
  if (!ws || !stompConnected) return;
  const frame = `SUBSCRIBE\nid:sub-0\ndestination:/topic/team/${teamId.value}\n\n` + NULL_CHAR;
  ws.send(frame);
};

// PLACEHOLDER_SCRIPT_PART3

const sendJoinMessage = () => {
  sendStompMessage('/app/team/join', { teamId: teamId.value });
};

const sendMessage = () => {
  if (!messageContent.value.trim()) {
    Toast.fail('请输入消息内容');
    return;
  }
  const success = sendStompMessage('/app/team/send', {
    teamId: teamId.value,
    content: messageContent.value
  });
  if (success) {
    messageContent.value = '';
  }
};

const scrollToBottom = () => {
  if (messageListRef.value) {
    messageListRef.value.scrollTop = messageListRef.value.scrollHeight;
  }
};

const formatTime = (timestamp: string) => {
  if (!timestamp) return '';
  const date = new Date(timestamp);
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  return `${hours}:${minutes}`;
};

const loadHistory = async () => {
  try {
    const response: any = await myAxios.get('/api/chat/history', {
      params: { teamId: teamId.value }
    });
    if (response.code === 0 && response.data) {
      messages.value = response.data;
      nextTick(() => scrollToBottom());
    }
  } catch (error) {
    console.error('加载历史消息失败:', error);
  }
};

const goBack = () => {
  if (stompConnected) {
    sendStompMessage('/app/team/leave', { teamId: teamId.value });
  }
  if (ws) {
    ws.close();
  }
  router.back();
};

onMounted(async () => {
  currentUser.value = await getCurrentUser();
  if (!currentUser.value) {
    Toast.fail('请先登录');
    router.push('/user/login');
    return;
  }
  await loadHistory();
  connectWebSocket();
});

onUnmounted(() => {
  if (ws && stompConnected) {
    sendStompMessage('/app/team/leave', { teamId: teamId.value });
  }
  if (ws) {
    ws.close();
  }
});
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f5f5f5;
}
.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
}
.message-item {
  margin-bottom: 16px;
}
.system-message {
  text-align: center;
  color: #999;
  font-size: 12px;
  padding: 8px 0;
}
.chat-message {
  display: flex;
  align-items: flex-start;
  gap: 8px;
}
.my-message .chat-message {
  flex-direction: row-reverse;
}
.my-message .message-content {
  align-items: flex-end;
}
.my-message .message-bubble {
  background-color: #07c160;
  color: white;
}
.message-content {
  display: flex;
  flex-direction: column;
  max-width: 70%;
}
.message-sender {
  font-size: 12px;
  color: #666;
  margin-bottom: 4px;
}
.message-bubble {
  background-color: white;
  padding: 8px 12px;
  border-radius: 8px;
  word-wrap: break-word;
  line-height: 1.5;
}
.message-time {
  font-size: 10px;
  color: #999;
  margin-top: 4px;
}
.input-area {
  border-top: 1px solid #eee;
  background-color: white;
}
</style>
