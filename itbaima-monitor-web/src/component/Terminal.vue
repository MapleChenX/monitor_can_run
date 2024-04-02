<script setup>
import {onBeforeUnmount, onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import {AttachAddon} from "xterm-addon-attach/src/AttachAddon";
import {Terminal} from "xterm";
import "xterm/css/xterm.css";

const props = defineProps({
  id: Number
})
const emits = defineEmits(['dispose'])
const terminalRef = ref()

const socket = new WebSocket(`ws://127.0.0.1:8080/terminal/${props.id}`)
socket.onclose = evt => {
  if(evt.code !== 1000) {
    ElMessage.warning(`连接失败: ${evt.reason}`)
  } else {
    ElMessage.success('远程SSH连接已断开')
  }
  emits('dispose')
}

// 终端本端
const term = new Terminal({
  lineHeight: 1.2,
  rows: 20,
  fontSize: 13,
  fontFamily: "Monaco, Menlo, Consolas, 'Courier New', monospace",
  fontWeight: "bold",
  theme: {
    background: '#000000'
  },
  // 光标闪烁
  cursorBlink: true,
  cursorStyle: 'underline',
  scrollback: 100,
  tabStopWidth: 4,
});

// 插件挂到元素上
const attachAddon = new AttachAddon(socket);
// 终端通过插件在元素上挂载显示
term.loadAddon(attachAddon);

onMounted(() => {
    // 页面一打开终端就打开
    term.open(terminalRef.value)
    term.focus()
})

onBeforeUnmount(() => {
    // 当页面一关闭，连接就关闭
    socket.close()
    // 终端也关闭
    term.dispose()
})
</script>

<template>
  <div ref="terminalRef" class="xterm"/>
</template>

<style scoped>

</style>
