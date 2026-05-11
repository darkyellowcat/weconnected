<template>
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="userAccount"
          name="userAccount"
          label="账号"
          placeholder="请输入账号"
          :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
          v-model="userPassword"
          type="password"
          name="userPassword"
          label="密码"
          placeholder="请输入密码"
          :rules="[{ required: true, message: '请填写密码' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>
</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {ref} from "vue";
import myAxios from "../plugins/myAxios";
import {Toast} from "vant";

const router = useRouter();
const route = useRoute();

const userAccount = ref('');
const userPassword = ref('');

const onSubmit = async () => {
  try {
    const res: any = await myAxios.post('/api/user/login', {
      userAccount: userAccount.value,
      userPassword: userPassword.value,
    });
    if (res.code === 0 && res.data) {
      Toast.success('登录成功');
      const redirectUrl = route.query?.redirect as string ?? '/';
      const decoded = decodeURIComponent(redirectUrl);
      if (decoded.startsWith('/') && !decoded.startsWith('//')) {
        router.push(decoded);
      } else {
        router.push('/');
      }
    } else {
      Toast.fail(res.description || '登录失败');
    }
  } catch (e: any) {
    Toast.fail(e.message || '请求失败');
  }
};

</script>

<style scoped>

</style>
