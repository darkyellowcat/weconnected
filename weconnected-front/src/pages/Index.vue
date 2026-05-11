<template>
  <van-cell center title="心动模式">
    <template #right-icon>
      <van-switch v-model="isMatchMode" size="24" />
    </template>
  </van-cell>
  <user-card-list :user-list="userList" :loading="loading"/>
  <van-empty v-if="!userList || userList.length < 1" description="数据为空"/>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue';
import myAxios from "../plugins/myAxios";
import {Toast} from "vant";
import UserCardList from "../components/UserCardList.vue";
import {UserType} from "../models/user";

const isMatchMode = ref<boolean>(false);

const userList = ref([]);
const loading = ref(true);

/**
 * 加载数据
 */
const loadData = async () => {
  let userListData;
  loading.value = true;
  try {
    // 心动模式，根据标签匹配用户
    if (isMatchMode.value) {
      const num = 10;
      const response: any = await myAxios.get('/api/user/match', {
        params: { num },
      });
      console.log('/user/match succeed', response);
      userListData = response?.data;
    } else {
      // 普通模式，直接分页查询用户
      const response: any = await myAxios.get('/api/user/recommend', {
        params: {
          pageSize: 8,
          pageNum: 1,
        },
      });
      console.log('/user/recommend succeed', response);
      userListData = response?.data?.records;
    }
    
    if (userListData) {
      userListData.forEach((user: UserType) => {
        if (user.tags) {
          user.tags = JSON.parse(user.tags);
        }
      })
      userList.value = userListData;
    }
  } catch (error) {
    console.error('加载数据失败:', error);
    // 如果是 401 错误，不显示 Toast，让拦截器处理跳转
    // 其他错误才显示提示
    if (error.response && error.response.status !== 401) {
      Toast.fail('请求失败');
    }
  } finally {
    loading.value = false;
  }
}

watchEffect(() => {
  loadData();
})

</script>

<style scoped>

</style>
