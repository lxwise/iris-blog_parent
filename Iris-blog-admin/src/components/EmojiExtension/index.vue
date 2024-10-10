<template>
  <dropdown-toolbar title="emoji" :visible="state.visible" @on-change="onChange">
    <template #overlay>
      <div class="emoji-container">
        <ol class="emojis">
          <li
            v-for="(emoji, index) of emojis"
            :key="`emoji-${index}`"
            @click="emojiHandler(emoji)"
            v-text="emoji"
          ></li>
        </ol>
      </div>
    </template>
    <template #trigger>
      <img
        src="@/assets/svgs/emoji.svg"
        alt="emoji icon"
        class="md-editor-icon"
        style="width: 1.3rem"
      />
    </template>
  </dropdown-toolbar>
</template>

<script lang="ts" setup>
import MdEditor, { InsertContentGenerator } from 'md-editor-v3'
import type { PropType } from 'vue'
import { reactive } from 'vue'
import { emojis } from './data'

const DropdownToolbar = MdEditor.DropdownToolbar
const props = defineProps({
  onInsert: {
    type: Function as PropType<(generator: InsertContentGenerator) => void>,
    default: () => () => null
  }
})
const state = reactive({
  visible: false
})
const emojiHandler = (emoji: string) => {
  const generator: InsertContentGenerator = () => {
    return {
      targetValue: emoji,
      select: true,
      deviationStart: 0,
      deviationEnd: 0
    }
  }
  props.onInsert(generator)
}
const onChange = (visible: boolean) => {
  state.visible = visible
}
</script>

<style lang="scss" scoped>
.emoji-container {
  border: 1px solid #e6e6e6;
  border-radius: 3px;
}

.emojis {
  position: relative;
  width: 363px;
  padding: 0;
  margin: 10px;
  background-color: #fff;

  li {
    z-index: 11;
    float: left;
    width: 34px;
    height: 34px;
    padding: 4px 2px;
    margin: -1px 0 0 -1px;
    overflow: hidden;
    font-size: 18px;
    text-align: center;
    list-style: none;
    cursor: pointer;
    border: 1px solid #e8e8e8;

    &:hover {
      position: relative;
      z-index: 12;
      border: 1px solid #63a35c;
    }
  }

  &::after {
    display: block;
    clear: left;
    content: '';
  }

  * {
    user-select: none;
  }
}
</style>
